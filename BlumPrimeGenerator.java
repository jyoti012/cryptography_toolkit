import java.math.BigInteger;
import java.util.Random;

public class BlumPrimeGenerator {
	public static BigInteger generateBlumPrime(int bits, int k) {
		Random random = new Random();
		// Define the minimum and maximum bounds for generating the prime
		BigInteger min = BigInteger.ONE.shiftLeft(bits - 1).add(BigInteger.ONE);
		BigInteger max = BigInteger.ONE.shiftLeft(bits).subtract(BigInteger.ONE);

		while (true) {
			// Generate a random candidate within the specified bounds
			BigInteger candidate = min.add(new BigInteger(bits, random));
			// Check if the candidate is a Blum prime (satisfies certain conditions)
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

		// Check for divisibility by 2 or 3
		if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO) || n.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO))
			return false;

		for (int i = 0; i < k; i++) {
			// Choose a random 'a' for the Miller-Rabin test
			BigInteger a = BigIntegerUtils.randomBigInteger(BigInteger.TWO, n.subtract(BigInteger.TWO));
			if (!millerRabinTest(n, a))
				return false;
		}

		return true;
	}

	public static boolean millerRabinTest(BigInteger n, BigInteger a) {
		BigInteger s = BigInteger.ZERO;
		BigInteger d = n.subtract(BigInteger.ONE);

		// Express n-1 as 2^s * d
		while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO) && d.compareTo(BigInteger.TWO) > 0) {
			s = s.add(BigInteger.ONE);
			d = d.divide(BigInteger.TWO);
		}

		// Compute x = a^d mod n
		BigInteger x = a.modPow(d, n);
		if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
			return true;

		for (BigInteger r = BigInteger.ONE; r.compareTo(s) < 0; r = r.add(BigInteger.ONE)) {
			// Check for the Miller-Rabin conditions
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

		// Generate a Blum prime number with the specified number of bits and iterations
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
			// Generate a random BigInteger within the specified range
			result = new BigInteger(maxLength, random);
		} while (result.compareTo(min) < 0 || result.compareTo(range) > 0);

		return result;
	}
}
