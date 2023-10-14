import java.math.BigInteger;
import java.util.Random;

public class BlumPrimeGenerator {
	public static BigInteger generateBlumPrime(int bits, int k) {
		Random random = new Random();
		BigInteger min = BigInteger.ONE.shiftLeft(bits - 1).add(BigInteger.ONE);
		BigInteger max = BigInteger.ONE.shiftLeft(bits).subtract(BigInteger.ONE);

		while (true) {
			BigInteger candidate = min.add(new BigInteger(bits, random));
			if (candidate.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && isPrime(candidate, k)) {
				return candidate;
			}
		}
	}

	public static boolean isPrime(BigInteger n, int k) {
		if (n.compareTo(BigInteger.ONE) <= 0)
			return false;
		if (n.compareTo(BigInteger.valueOf(3)) <= 0)
			return true;

		if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO) || n.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO))
			return false;

		for (int i = 0; i < k; i++) {
			BigInteger a = BigIntegerUtils.randomBigInteger(BigInteger.TWO, n.subtract(BigInteger.TWO));
			if (!millerRabinTest(n, a))
				return false;
		}

		return true;
	}

	public static boolean millerRabinTest(BigInteger n, BigInteger a) {
		BigInteger s = BigInteger.ZERO;
		BigInteger d = n.subtract(BigInteger.ONE);

		while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO) && d.compareTo(BigInteger.TWO) > 0) {
			s = s.add(BigInteger.ONE);
			d = d.divide(BigInteger.TWO);
		}

		BigInteger x = a.modPow(d, n);
		if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
			return true;

		for (BigInteger r = BigInteger.ONE; r.compareTo(s) < 0; r = r.add(BigInteger.ONE)) {
			x = x.modPow(BigInteger.TWO, n);
			if (x.equals(BigInteger.ONE))
				return false;
			if (x.equals(n.subtract(BigInteger.ONE)))
				return true;
		}

		return false;
	}

	public static void main(String[] args) {
		int bits = 256; // Adjust the number of bits as needed
		int k = 50; // Number of Miller-Rabin iterations

		BigInteger blumPrime = generateBlumPrime(bits, k);
		System.out.println("Generated Blum Prime: " + blumPrime);
	}
}

class BigIntegerUtils {
	public static BigInteger randomBigInteger(BigInteger min, BigInteger max) {
		Random random = new Random();
		BigInteger range = max.subtract(min);
		int maxLength = max.bitLength();

		BigInteger result;
		do {
			result = new BigInteger(maxLength, random);
		} while (result.compareTo(min) < 0 || result.compareTo(range) > 0);

		return result;
	}
}
