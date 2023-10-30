import java.util.Scanner;

public class ModularArithmeticCalculator {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter an integer (dividend): ");
    int dividend = scanner.nextInt();

    System.out.print("Enter another integer (divisor): ");
    int divisor = scanner.nextInt();

    dividend = Math.abs(dividend);
    divisor = Math.abs(divisor);

    int quotient = dividend / divisor;
    int remainder = dividend % divisor;

    System.out.println("Quotient: " + quotient);
    System.out.println("Remainder: " + remainder);

    // Close the scanner to prevent the resource leak
    scanner.close();
  }
}
