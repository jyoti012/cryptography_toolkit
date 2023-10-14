import java.math.BigInteger;

public class TonelliAlgorithm {
	public static BigInteger tonelli(BigInteger n, BigInteger p) {
		if (!n.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), p).equals(BigInteger.ONE)) {
			return null; // n is not a quadratic residue modulo p
		}

		BigInteger Q = p.subtract(BigInteger.ONE), C, R, t, Z = BigInteger.ONE;
		int S = 0;

		while (Q.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
			Q = Q.divide(BigInteger.TWO);
			S++;
		}

		if (S == 1) {
			return n.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
		}

		while (Z.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p).equals(BigInteger.ONE)) {
			Z = Z.add(BigInteger.ONE);
		}

		C = Z.modPow(Q, p);
		R = n.modPow(Q.add(BigInteger.ONE).divide(BigInteger.TWO), p);
		t = n.modPow(Q, p);

		while (!t.equals(BigInteger.ONE)) {
			int i = 1;
			BigInteger t2i = t.multiply(t);

			while (!t2i.modPow(BigInteger.TWO.pow(i), p).equals(BigInteger.ONE)) {
				i++;
			}

			BigInteger b = C.modPow(BigInteger.TWO.pow(S - i - 1), p);
			R = R.multiply(b).mod(p);
			C = b.modPow(BigInteger.TWO, p);
			t = t.multiply(C).mod(p);
			S = i;
		}

		return R;
	}

	public static void main(String[] args) {
		BigInteger n = new BigInteger("24"); // The number for which you want to find the square root
		BigInteger p = new BigInteger("45"); // The prime modulus

		BigInteger result = tonelli(n, p);

		if (result != null) {
			System.out.println("Square root of " + n + " modulo " + p + " is: " + result);
		} else {
			System.out.println(n + " is not a quadratic residue modulo " + p);
		}
	}
}
