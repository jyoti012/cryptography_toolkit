package CS616Toolkit;

import java.math.BigInteger;

public class ElGamalPrivateKeyFinder {
  public static void main(String[] args) {
    // Given public key (p, g, y)
    BigInteger p = new BigInteger("99333611752778158313691997689669641978712476822507750499831812831584630785973"); // Prime                                                                                                             // modulus
    BigInteger g = new BigInteger("89590362417598127889763750591434581694337369135165007793050455118722836261003"); // Generator
    BigInteger y = new BigInteger("67931852888628538370037206937600742353104147135380482916521197699075937305150"); // Public
                                                                                                                    // key

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
