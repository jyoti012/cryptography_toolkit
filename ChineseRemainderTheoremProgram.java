import java.math.BigInteger;
import java.util.Scanner;

public class ChineseRemainderTheoremProgram {
  public static BigInteger recoverX(BigInteger[] a, BigInteger[] m) {
    int n = a.length;
    BigInteger M = BigInteger.ONE;
    BigInteger[] M_values = new BigInteger[n];
    BigInteger[] inv_values = new BigInteger[n];

    // Calculate the product of all moduli
    for (int i = 0; i < n; i++) {
      M = M.multiply(m[i]);
    }

    // Calculate partial products M_i and their inverses
    for (int i = 0; i < n; i++) {
      M_values[i] = M.divide(m[i]);
      inv_values[i] = M_values[i].modInverse(m[i]);
    }

    // Calculate x using the CRT formula
    BigInteger x = BigInteger.ZERO;
    for (int i = 0; i < n; i++) {
      x = x.add(a[i].multiply(M_values[i]).multiply(inv_values[i]));
    }

    // Reduce x modulo M to get the final result
    x = x.mod(M);

    return x;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the number of congruences (n): ");
    int n = scanner.nextInt();

    BigInteger[] a = new BigInteger[n];
    BigInteger[] m = new BigInteger[n];

    for (int i = 0; i < n; i++) {
      System.out.print("Enter remainder a" + (i + 1) + ": ");
      a[i] = new BigInteger(scanner.next());

      System.out.print("Enter modulus m" + (i + 1) + ": ");
      m[i] = new BigInteger(scanner.next());
    }

    BigInteger result = recoverX(a, m);
    System.out.println("The recovered value of x is: " + result);

    scanner.close();
  }
}
