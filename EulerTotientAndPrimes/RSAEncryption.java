package EulerTotientAndPrimes;

import java.math.BigInteger;

public class RSAEncryption {

	public static void main(String[] args) {
		BigInteger p = new BigInteger("934721999678209");
		BigInteger q = new BigInteger("897411054846433");
		BigInteger e = new BigInteger("227");
		BigInteger d = new BigInteger("928757626817739");
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

		BigInteger plaintext = new BigInteger("512");

		// Encrypt the plaintext using the PKCS#1 private key
		BigInteger ciphertext1 = plaintext.modPow(e, p.multiply(q));

		// Encrypt the plaintext using the Euler Totient function private key
		BigInteger ciphertext2 = plaintext.modPow(e, phi);

		System.out.println("The ciphertext using the PKCS#1 private key is: " + ciphertext1);
		System.out.println("The ciphertext using the Euler Totient function private key is: " + ciphertext2);
	}
}