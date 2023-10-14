import java.math.BigInteger;

public class RSAKeyGenerator {
	public static BigInteger calculatePrivateKey(BigInteger p, BigInteger q, BigInteger e) {
		BigInteger n = p.multiply(q); // Calculate the modulus (n)
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // Calculate Euler's Totient (φ)

		// Calculate the private exponent (d)
		BigInteger d = e.modInverse(phi);

		return d;
	}

	public static void main(String[] args) {
		BigInteger p = new BigInteger("61"); // Replace with your prime factors p and q
		BigInteger q = new BigInteger("53");
		BigInteger e = new BigInteger("17"); // Public exponent e

		BigInteger privateKey = calculatePrivateKey(p, q, e);
		System.out.println("RSA Private Exponent (d): " + privateKey);
	}
}
