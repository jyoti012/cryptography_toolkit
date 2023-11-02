// https://www.eecis.udel.edu/~amer/CISC651/ASCII-Conversion-Chart.pdf
package CS616Toolkit;

import java.math.BigInteger;

public class ChineseRemainderTheorem {

	public static void main(String[] args) {
		// enter c1, c2, and c3 here
		BigInteger[] ai = { new BigInteger("886534656665328399297603173406"),
				new BigInteger("2212035097862473138152491479452"),
				new BigInteger("2081115204144205981960655525917") };
		// enter n1, n2, and n3 values here
		BigInteger[] mi = { new BigInteger("2267493974874992855694451011493"),
				new BigInteger("3352097303933636037193800460127"),
				new BigInteger("3223732200443613340467041133433") };
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
