public class HexToPolynomial {
	public static String hexToPolynomial(String hexString) {
		if (hexString.startsWith("0x")) {
			hexString = hexString.substring(2);
		}

		StringBuilder polynomial = new StringBuilder();
		int degree = hexString.length() * 4 - 1;

		for (char hexDigit : hexString.toCharArray()) {
			int decimalValue = Character.digit(hexDigit, 16);
			for (int j = 3; j >= 0; j--) {
				if (((decimalValue >> j) & 1) == 1) {
					polynomial.append((polynomial.length() == 0 ? "" : " + ") + "x^" + degree);
				}
				degree--;
			}
		}

		return polynomial.length() == 0 ? "0" : polynomial.toString();
	}

	public static void main(String[] args) {
		String hexString = "0x0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000V"; // Replace with your hexadecimal input
		String polynomial = hexToPolynomial(hexString);

		System.out.println("Hex to Polynomial: " + polynomial);
	}
}
