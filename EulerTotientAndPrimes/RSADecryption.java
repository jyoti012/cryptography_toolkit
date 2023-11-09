package EulerTotientAndPrimes;

import java.math.BigInteger;

public class RSADecryption {

	public static void main(String[] args) {
		BigInteger p = new BigInteger("934721999678209");
		BigInteger q = new BigInteger("897411054846433");
		BigInteger e = new BigInteger("227");
		BigInteger d = new BigInteger("620807998946506614576211313867");

		BigInteger ciphertext1 = new BigInteger("443628806214724635321945867908");
		BigInteger ciphertext2 = new BigInteger("579783955014899384981594292224");

		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

		// Decrypt the ciphertext using the PKCS#1 private key
		BigInteger plaintext1 = ciphertext1.modPow(d, p.multiply(q));

		// Decrypt the ciphertext using the Euler Totient function private key
		BigInteger plaintext2 = ciphertext2.modPow(d, phi);

		System.out.println("The plaintext using the PKCS#1 private key is: " + plaintext1);
		System.out.println("The plaintext using the Euler Totient function private key is: " + plaintext2);
	}
}