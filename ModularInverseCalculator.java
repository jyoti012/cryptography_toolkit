import java.util.Scanner;

public class ModularInverseCalculator {
  public static int modInverse(int a, int m) {
    a = a % m;
    for (int x = 1; x < m; x++) {
      if ((a * x) % m == 1) {
        return x;
      }
    }
    return -1; // No modular inverse exists
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the integer (a): ");
    int a = scanner.nextInt();

    System.out.print("Enter the modulo (m): ");
    int m = scanner.nextInt();

    int inverse = modInverse(a, m);

    if (inverse == -1) {
      System.out.println("No modular inverse exists for " + a + " modulo " + m);
    } else {
      System.out.println("The modular inverse of " + a + " modulo " + m + " is " + inverse);
    }
    scanner.close();
  }
}
