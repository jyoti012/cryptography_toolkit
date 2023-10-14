import java.math.BigInteger;

public class GarnersFormula {
	public static BigInteger garner(BigInteger[] a, BigInteger[] m) {
		if (a.length != m.length) {
			throw new IllegalArgumentException("Input arrays a and m must have the same length.");
		}

		int n = a.length;
		BigInteger[] x = new BigInteger[n];
		BigInteger[] c = new BigInteger[n];

		for (int i = 0; i < n; i++) {
			x[i] = a[i];
			for (int j = 0; j < i; j++) {
				x[i] = x[i].subtract(c[j].multiply(x[j])).mod(m[i]);
			}
			c[i] = x[i].modInverse(m[i]);
		}

		BigInteger result = x[0];
		for (int i = 1; i < n; i++) {
			result = result.add(c[i].multiply(x[i].subtract(result)).mod(m[i]).multiply(m[i]));
		}

		return result;
	}

	public static void main(String[] args) {
		BigInteger[] a = { BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(2) };
		BigInteger[] m = { BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5) };

		BigInteger result = garner(a, m);
		System.out.println("The solution is x ≡ " + result);
	}
}
