public class HexToBinary {
	public static String hexToBinary(String hex) {
		// Remove "0x" prefix if it exists
		if (hex.startsWith("0x") || hex.startsWith("0X")) {
			hex = hex.substring(2);
		}

		// Use built-in functions to convert hex to binary
		int intValue = Integer.parseInt(hex, 16);
		String binaryValue = Integer.toBinaryString(intValue);

		// Ensure leading zeros are included
		int padding = hex.length() * 4 - binaryValue.length();
		if (padding > 0) {
			binaryValue = "0".repeat(padding) + binaryValue;
		}

		return binaryValue;
	}

	public static String binaryToHex(String binary) {
		// Use built-in functions to convert binary to hex
		int intValue = Integer.parseInt(binary, 2);
		return Integer.toHexString(intValue).toUpperCase();
	}

	public static void main(String[] args) {
		String hexValue = "1A3"; // Replace with your hex input
		String binaryValue = "1111101100101"; // Replace with your binary input

		String convertedHexToBinary = hexToBinary(hexValue);
		String convertedBinaryToHex = binaryToHex(binaryValue);

		System.out.println("Hex to Binary: " + convertedHexToBinary);
		System.out.println("Binary to Hex: " + convertedBinaryToHex);
	}
}
