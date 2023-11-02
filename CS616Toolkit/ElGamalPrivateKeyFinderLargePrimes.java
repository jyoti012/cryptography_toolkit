package CS616Toolkit;

// NOTE: THIS PROGRAM DOESN'T RETURN SAME O/P AS OTHER ELGAMAL ENCRYPTION PROGRAM

import java.math.BigInteger;
import java.util.HashMap;

public class ElGamalPrivateKeyFinderLargePrimes {
  public static void main(String[] args) {
    // Given public key (p, g, y)
    BigInteger p = new BigInteger("3847");
    BigInteger g = new BigInteger("1892");
    BigInteger y = new BigInteger("1954");

    // Calculate the private key (x) based on the provided information
    BigInteger x = findPrivateKey(p, g, y);

    System.out.println("Private Key (x): " + x);
  }

  // Find the private key (x) based on the provided information using Baby-Step
  // Giant-Step algorithm
  private static BigInteger findPrivateKey(BigInteger p, BigInteger g, BigInteger y) {
    BigInteger m = sqrt(p).add(BigInteger.ONE);
    HashMap<BigInteger, BigInteger> values = new HashMap<>();

    // Precompute g^j for j in [0, m)
    for (BigInteger j = BigInteger.ZERO; j.compareTo(m) < 0; j = j.add(BigInteger.ONE)) {
      BigInteger gj = g.modPow(j, p);
      values.put(gj, j);
    }

    // Compute g^(-m)
    BigInteger gm = g.modPow(m.negate(), p);

    // Search for a matching y value
    for (BigInteger i = BigInteger.ZERO; i.compareTo(m) < 0; i = i.add(BigInteger.ONE)) {
      BigInteger gy = y.multiply(gm.modPow(i, p)).mod(p);
      if (values.containsKey(gy)) {
        BigInteger j = values.get(gy);
        return i.add(j.multiply(m));
      }
    }

    return null; // Private key not found
  }

  // Helper function to find the square root of a BigInteger
  private static BigInteger sqrt(BigInteger n) {
    BigInteger a = BigInteger.ONE;
    BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
    while (b.compareTo(a) >= 0) {
      BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
      if (mid.multiply(mid).compareTo(n) > 0) {
        b = mid.subtract(BigInteger.ONE);
      } else {
        a = mid.add(BigInteger.ONE);
      }
    }
    return a.subtract(BigInteger.ONE);
  }
}
