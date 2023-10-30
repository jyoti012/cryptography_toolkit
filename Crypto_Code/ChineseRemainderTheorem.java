package Crypto_Code;

import java.math.BigInteger;

public class ChineseRemainderTheorem {

	public static void main(String[] args) {
		// enter c1 , c2 and c3 here
		BigInteger[] ai = { new BigInteger("191699250018696932235548276819"),
				new BigInteger("2262766903820045933008151262425"),
				new BigInteger("1643829619688527494120778009131") };
		// enter n1 , n2 and n3 values here
		BigInteger[] mi = { new BigInteger("4459740564724538700519142326997"),
				new BigInteger("2281806784635143785292256501293"),
				new BigInteger("2467881921864340392351277277159") };
		BigInteger crtSqRoot = CRT(ai, mi);
		BigInteger cubeRoot = Utils.iRootInt(crtSqRoot, 3);
		System.out.println(cubeRoot);

	}

	public static BigInteger CRT(BigInteger ai[], BigInteger mi[]) {
		BigInteger M = BigInteger.valueOf(1), Mi[], Ni[], x = BigInteger.valueOf(0);
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
}
