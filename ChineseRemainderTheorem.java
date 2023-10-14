import java.math.BigInteger;

public class ChineseRemainderTheorem {
  public static BigInteger findCRT(int[] a, int[] m) {
    int n = a.length;
    BigInteger M = BigInteger.ONE;

    // Calculate the product of all moduli
    for (int i = 0; i < n; i++) {
      M = M.multiply(BigInteger.valueOf(m[i]));
    }

    BigInteger result = BigInteger.ZERO;

    for (int i = 0; i < n; i++) {
      BigInteger Mi = M.divide(BigInteger.valueOf(m[i]));
      BigInteger MiInverse = Mi.modInverse(BigInteger.valueOf(m[i]));
      result = result.add(BigInteger.valueOf(a[i]).multiply(Mi).multiply(MiInverse));
    }

    return result.mod(M);
  }

  public static void main(String[] args) {
    int[] a = { 2, 3, 2 }; // Values a1, a2, a3
    int[] m = { 3, 4, 5 }; // Moduli m1, m2, m3

    BigInteger result = findCRT(a, m);
    System.out.println("The solution is x ≡ " + result);
  }
}
