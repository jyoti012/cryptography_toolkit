package CS616Toolkit;


public class KeyConverter {
    public static void main(String[] args) {
        // Input key
        String inputKey = "AA:BB:CC:DD:EE:FF:EE:11";

        // Remove colons and concatenate HEX values
        String[] hexValues = inputKey.split(":");
        StringBuilder hexRepresentation = new StringBuilder();

        for (String hexValue : hexValues) {
            int decimalValue = Integer.parseInt(hexValue, 16);
            hexRepresentation.append(String.format("%02X", decimalValue));
        }

        System.out.println("Input key: " + inputKey);
        System.out.println("HEX representation: " + hexRepresentation.toString());
    }
}
