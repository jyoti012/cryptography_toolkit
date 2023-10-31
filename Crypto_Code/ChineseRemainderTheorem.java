package Crypto_Code;

import java.math.BigInteger;

public class ChineseRemainderTheorem {

	public static void main(String[] args) {
		// enter c1, c2, and c3 here
		BigInteger[] ai = { new BigInteger("191699250018696932235548276819"),
				new BigInteger("2262766903820045933008151262425"),
				new BigInteger("1643829619688527494120778009131") };
		// enter n1, n2, and n3 values here
		BigInteger[] mi = { new BigInteger("4459740564724538700519142326997"),
				new BigInteger("2281806784635143785292256501293"),
				new BigInteger("2467881921864340392351277277159") };
		BigInteger crtResult = CRT(ai, mi);

		// Calculate the cube root of the CRT result
		BigInteger cubeRoot = iRoot(crtResult, 3);

		System.out.println(cubeRoot);
	}

	public static BigInteger CRT(BigInteger ai[], BigInteger mi[]) {
		BigInteger M = BigInteger.ONE, Mi[], Ni[], x = BigInteger.ZERO;
		Mi = new BigInteger[ai.length];
		Ni = new BigInteger[ai.length];

		for (int i = 0; i < ai.length; i++) {
			M = M.multiply(mi[i]);
		}

		for (int i = 0; i < ai.length; i++) {
			Mi[i] = M.divide(mi[i]);
			Ni[i] = Mi[i].modInverse(mi[i]);
		}

		for (int i = 0; i < ai.length; i++) {
			x = x.add(ai[i].multiply(Ni[i].multiply(Mi[i]))); // same as: x = x + ai[i] * Ni[i] * Mi[i];
		}
		return x.mod(M);
	}

	// Calculate the integer cube root
	public static BigInteger iRoot(BigInteger x, int n) {
		BigInteger low = BigInteger.ONE;
		BigInteger high = x;
		while (low.compareTo(high) <= 0) {
			BigInteger mid = low.add(high).divide(BigInteger.valueOf(2));
			BigInteger midToTheN = mid.pow(n);
			if (midToTheN.compareTo(x) < 0) {
				low = mid.add(BigInteger.ONE);
			} else if (midToTheN.compareTo(x) > 0) {
				high = mid.subtract(BigInteger.ONE);
			} else {
				return mid;
			}
		}
		return low.subtract(BigInteger.ONE);
	}
}
