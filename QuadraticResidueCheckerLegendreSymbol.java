public class QuadraticResidueCheckerLegendreSymbol {
	public static boolean isQuadraticResidue(int a, int n) {
		if (a % n == 0)
			return true; // 0 is always a quadratic residue

		int legendreSymbol = (int) Math.pow(a, (n - 1) / 2) % n;
		System.out.println("legendreSymbol" +  legendreSymbol);
		return legendreSymbol == 1;
	}

	public static void main(String[] args) {
		int a = 4; // The integer to check
		int n = 8; // The modulo n

		if (isQuadraticResidue(a, n)) {
			System.out.println(a + " is a quadratic residue modulo " + n);
		} else {
			System.out.println(a + " is not a quadratic residue modulo " + n);
		}
	}
}
/*
For large numbers 

import java.math.BigInteger;

public class QuadraticResidueCheckerLegendreSymbol {
	public static boolean isQuadraticResidue(BigInteger a, BigInteger n) {
		if (a.mod(n).equals(BigInteger.ZERO)) {
			return true; // 0 is always a quadratic residue
		}

		BigInteger exponent = n.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
		BigInteger legendreSymbol = a.modPow(exponent, n);
		return legendreSymbol.equals(BigInteger.ONE);
	}

	public static void main(String[] args) {
		BigInteger a = new BigInteger("1234567890123456789012345678901234567890"); // Large integer to check
		BigInteger n = new BigInteger("1234567890123456789012345678901234567891"); // Large prime n

		if (isQuadraticResidue(a, n)) {
			System.out.println(a + " is a quadratic residue modulo " + n);
		} else {
			System.out.println(a + " is not a quadratic residue modulo " + n);
		}
	}
}


*/ 

