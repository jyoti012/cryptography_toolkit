package EulerTotientAndPrimes;

import java.math.BigInteger;

public class RSAPrivateKey {

  public static void main(String[] args) {
    BigInteger p = new BigInteger("934721999678209");
    BigInteger q = new BigInteger("897411054846433");
    BigInteger e = new BigInteger("227");

    BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    BigInteger d = e.modInverse(phi);

    System.out.println("The RSA PKCS#1 corresponding private key, d, is: " + d);
  }
}