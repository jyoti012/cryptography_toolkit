package EulerTotientAndPrimes;

import java.math.BigInteger;

public class RSAKeyGenerator {
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

    // Print the computed private key d
    System.out.println("Private Key (d): " + d);

    // Now, you have the RSA private key d.

    // Perform encryption and decryption if needed.

    // To encrypt a plaintext:
    BigInteger plaintext = new BigInteger("512"); // Replace with your plaintext
    BigInteger ciphertext = plaintext.modPow(e, n);

    // To decrypt a ciphertext:
    BigInteger decrypted = ciphertext.modPow(d, n);

    System.out.println("Original plaintext: " + plaintext);
    System.out.println("Encrypted ciphertext: " + ciphertext);
    System.out.println("Decrypted plaintext: " + decrypted);
  }
}

// 620807998946506614576211313867
