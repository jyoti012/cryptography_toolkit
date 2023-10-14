public class EulerTotientFunction {
	public static int eulerTotient(int n) {
		int result = n;

		for (int p = 2; p * p <= n; p++) {
			if (n % p == 0) {
				while (n % p == 0)
					n /= p;
				result -= result / p;
			}
		}

		if (n > 1)
			result -= result / n;

		return result;
	}

	public static void main(String[] args) {
		int n = 12; // Adjust the input number as needed
		System.out.println("φ(" + n + ") = " + eulerTotient(n));
	}
}
