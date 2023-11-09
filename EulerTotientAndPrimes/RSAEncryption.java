package EulerTotientAndPrimes;

import java.math.BigInteger;

public class RSAEncryption {

	public static void main(String[] args) {
		BigInteger p = new BigInteger("934721999678209");
		BigInteger q = new BigInteger("897411054846433");
		BigInteger e = new BigInteger("227");
		BigInteger d = new BigInteger("620807998946506614576211313867");
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


/* The Euler Totient function is a relatively simple function to compute, while the RSA PKCS#1 corresponding private key, d, is more complex to compute. The RSA PKCS#1 corresponding private key, d, requires the computation of the modular inverse of e modulo phi, which can be computationally expensive for large numbers.

The Euler Totient function is defined as the number of positive integers less than or equal to n that are relatively prime to n. In other words, the Euler Totient function counts the number of integers that can be multiplied with n to give a product that is 1 modulo n.

The RSA PKCS#1 corresponding private key, d, is defined as the modular inverse of e modulo phi. The modular inverse of a number x modulo n is the number y such that xy = 1 modulo n. */