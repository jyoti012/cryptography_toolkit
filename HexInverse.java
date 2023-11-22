import java.math.BigInteger;

public class HexInverse {

  public static void main(String[] args) {
    String hexValue = "265a116eb08513f56fb93ce5a8ffbdc";
    BigInteger decValue = new BigInteger(hexValue, 16);

    // Fix the modulus calculation:
    BigInteger modulus = BigInteger.valueOf(2).pow(128)
        .add(BigInteger.valueOf(2).pow(8))
        .add(BigInteger.valueOf(2).pow(6))
        .add(BigInteger.valueOf(2).pow(5))
        .add(BigInteger.valueOf(2).pow(4))
        .add(BigInteger.valueOf(2))
        .add(BigInteger.ONE);

    BigInteger decInverse = decValue.modInverse(modulus);

    if (decInverse == null) {
      System.out.println("The inverse does not exist.");
    } else {
      String hexInverse = decInverse.toString(16);
      System.out.println(hexInverse);
    }
  }
}