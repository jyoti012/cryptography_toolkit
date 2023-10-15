public class PolynomialToHex {
	public static String polynomialToHex(String polynomial) {
		if (polynomial.equals("0")) {
			return "0";
		}

		StringBuilder hexString = new StringBuilder();
		int degree = -1;
		for (String term : polynomial.split("\\s*\\+\\s*")) {
			int termDegree = Integer.parseInt(term.substring(term.indexOf("^") + 1));
			int zeroCount = termDegree - degree - 1;
			for (int i = 0; i < zeroCount; i++) {
				hexString.append("0");
			}
			int hexDigit = 1 << (3 - (termDegree % 4));
			if (hexString.length() == 0) {
				hexString.append(Integer.toHexString(hexDigit));
			} else {
				hexString.setCharAt(hexString.length() - 1, (char) (hexString.charAt(hexString.length() - 1) + hexDigit));
			}
			degree = termDegree;
		}
		return "0x" + hexString.toString();
	}

	public static void main(String[] args) {
		String polynomial = "x^7 + x^3 + 1"; // Replace with your polynomial
		String hexString = polynomialToHex(polynomial);

		System.out.println("Polynomial to Hex: " + hexString);
	}
}
