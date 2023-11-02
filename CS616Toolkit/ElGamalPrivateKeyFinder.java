package CS616Toolkit;

import java.math.BigInteger;

public class ElGamalPrivateKeyFinder {
  public static void main(String[] args) {
    // Given public key (p, g, y)
    BigInteger p = new BigInteger("3847");
    BigInteger g = new BigInteger("1892");
    BigInteger y = new BigInteger("1954");

    // Calculate the private key (x) based on the provided information
    BigInteger x = findPrivateKey(p, g, y);

    System.out.println("Private Key (x): " + x);
  }

  // Find the private key (x) based on the provided information
  private static BigInteger findPrivateKey(BigInteger p, BigInteger g, BigInteger y) {
    BigInteger x = BigInteger.ONE;
    while (x.compareTo(p) < 0) {
      if (g.modPow(x, p).equals(y)) {
        return x;
      }
      x = x.add(BigInteger.ONE);
    }
    return null; // Private key not found
  }
}
