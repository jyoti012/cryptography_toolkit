public class QuadraticResidueCheckerLegendreSymbol {
	public static boolean isQuadraticResidue(int a, int n) {
		if (a % n == 0)
			return true; // 0 is always a quadratic residue

		int legendreSymbol = (int) Math.pow(a, (n - 1) / 2) % n;
		return legendreSymbol == 1;
	}

	public static void main(String[] args) {
		int a = 7; // The integer to check
		int n = 11; // The modulo n

		if (isQuadraticResidue(a, n)) {
			System.out.println(a + " is a quadratic residue modulo " + n);
		} else {
			System.out.println(a + " is not a quadratic residue modulo " + n);
		}
	}
}
