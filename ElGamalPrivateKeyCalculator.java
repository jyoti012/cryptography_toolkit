import java.math.BigInteger;

public class ElGamalPrivateKeyCalculator {
	public static void main(String[] args) {
		// Public key parameters
		// BigInteger p = new BigInteger("689455301"); // Prime modulus
		// BigInteger g = new BigInteger("283208122"); // Generator
		// BigInteger y = new BigInteger("213724415"); // Public key

		BigInteger p = new BigInteger("3847"); // Prime modulus
		BigInteger g = new BigInteger("1892"); // Generator
		BigInteger y = new BigInteger("1954"); // Public key

		BigInteger x = findPrivateKey(p, g, y);

		if (x != null) {
			System.out.println("Private key (x): " + x);
		} else {
			System.out.println("Error: Unable to find the private key.");
		}
	}

	private static BigInteger findPrivateKey(BigInteger p, BigInteger g, BigInteger y) {
		for (BigInteger x = BigInteger.ONE; x.compareTo(p) < 0; x = x.add(BigInteger.ONE)) {
			if (g.modPow(x, p).equals(y)) {
				return x;
			}
		}
		return null;
	}
}
