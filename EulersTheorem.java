public class EulersTheorem {
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

	public static boolean checkEulersTheorem(int a, int n) {
		if (gcd(a, n) == 1) {
			int phi = eulerTotient(n);
			return (int) Math.pow(a, phi) % n == 1;
		}
		return false;
	}

	public static int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	public static void main(String[] args) {
		int a = 3; // Adjust the values of a and n as needed
		int n = 10;

		boolean theoremHolds = checkEulersTheorem(a, n);

		if (theoremHolds) {
			System.out.println("Euler's Theorem holds for a = " + a + " and n = " + n);
		} else {
			System.out.println("Euler's Theorem does not hold for a = " + a + " and n = " + n);
		}
	}
}
