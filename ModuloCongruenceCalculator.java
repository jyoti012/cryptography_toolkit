import java.util.Scanner;

public class ModuloCongruenceCalculator {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the first integer (a): ");
    int a = scanner.nextInt();

    System.out.print("Enter the second integer (b): ");
    int b = scanner.nextInt();

    System.out.print("Enter the modulus (n): ");
    int n = scanner.nextInt();

    boolean congruent = isCongruent(a, b, n);

    if (congruent) {
      System.out.println(a + " is congruent to " + b + " modulo " + n);
    } else {
      System.out.println(a + " is not congruent to " + b + " modulo " + n);
    }

    // Close the scanner to prevent the resource leak
    scanner.close();
  }

  public static boolean isCongruent(int a, int b, int n) {
    // Check if n divides (a - b)
    return (a - b) % n == 0;
  }
}
