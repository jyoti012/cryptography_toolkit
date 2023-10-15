import java.util.Scanner;

public class CarmichaelFunction {
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static int carmichaelFunction(int n) {
		if (n <= 1)
			return 1;

		int result = 1;
		for (int a = 2; a <= n; a++) {
			if (gcd(a, n) == 1) {
				int k = 1, power = a;
				while ((power = (power * a) % n) != 1)
					k++;
				result = Math.max(result, k);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a positive integer (n): ");
		int n = scanner.nextInt();

		if (n <= 0) {
			System.out.println("Please enter a positive integer.");
		} else {
			System.out.println("Carmichael function (λ) for " + n + " is " + carmichaelFunction(n));
		}
		scanner.close();
	}
}
