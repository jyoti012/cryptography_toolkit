package EulerTotientAndPrimes;

import java.math.BigInteger;

public class RSAKeyGeneratorAndDecryptor {
  public static void main(String[] args) {
    // Replace these values with your actual prime numbers and e value
    BigInteger p = new BigInteger("934721999678209");
    BigInteger q = new BigInteger("897411054846433");
    BigInteger e = new BigInteger("227");

    // Step 1: Compute n
    BigInteger n = p.multiply(q);

    // Step 2: Compute φ(n)
    BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

    // Step 3: Find d (modular multiplicative inverse of e modulo φ(n))
    BigInteger d = e.modInverse(phiN);

    System.out.println("Private Key (d): " + d);

    // Encrypt and Decrypt a plaintext (P=512) as a test
    BigInteger plaintext = new BigInteger("512");

    // Encryption: Ciphertext = (plaintext ^ e) % n
    BigInteger ciphertext = plaintext.modPow(e, n);

    System.out.println("Encrypted Ciphertext: " + ciphertext);

    // Decryption: Decrypted = (ciphertext ^ d) % n
    BigInteger decrypted = ciphertext.modPow(d, n);

    System.out.println("Decrypted Plaintext: " + decrypted);
  }
}
