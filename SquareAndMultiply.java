// The Square-and-Multiply algorithm, also known as the Exponentiation by Squaring algorithm
public class SquareAndMultiply {
    public static double squareAndMultiply(double x, int n) {
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        double result = 1.0;
        double base = x;

        while (n > 0) {
            if (n % 2 == 1) {
                result *= base;
            }
            base *= base; // Square
            n /= 2;
        }

        return result;
    }

    public static void main(String[] args) {
        double x = 2.0; // Base
        int n = 10;    // Exponent

        double result = squareAndMultiply(x, n);

        System.out.println(x + " raised to the power of " + n + " is " + result);
    }
}
