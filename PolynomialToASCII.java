public class PolynomialToASCII {
	public static String polynomialToASCII(String polynomial) {
		if (polynomial.equals("0")) {
			return "0";
		}

		StringBuilder asciiString = new StringBuilder();
		int degree = -1;
		for (String term : polynomial.split("\\s*\\+\\s*")) {
			int termDegree = Integer.parseInt(term.substring(term.indexOf("^") + 1));
			// int zeroCount = termDegree - degree - 1;
			// for (int i = 0; i < zeroCount; i++) {
			// 	asciiString.append(" ");
			// }
			char asciiChar = (char) ('A' + termDegree);
			asciiString.append(asciiChar);
			degree = termDegree;
		}
		return asciiString.toString();
	}

	public static void main(String[] args) {
		String polynomial = "x^9 + x^9 + x^9 + 1"; // Replace with your polynomial
		String asciiString = polynomialToASCII(polynomial);

		System.out.println("Polynomial to ASCII: " + asciiString);
	}
}
