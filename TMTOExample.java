import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class TMTOExample {

    public static void main(String[] args) throws Exception {
        // Create a key generator.
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);

        // Generate a secret key.
        SecretKey secretKey = keyGenerator.generateKey();

        // Create a cipher object.
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

        // Initialize the cipher.
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Create a plaintext array.
        byte[] plaintext = new byte[32];
        for (int i = 0; i < plaintext.length; i++) {
            plaintext[i] = (byte) 0x11;
        }

        // Create an initial chain value array.
        String initialChainValueHex = "99aaf4b8e24df5dca79ead5ee8441afac138c2aabcf6f177aa0ae84d9f5f0c0a";
        byte[] initialChainValue = hexToBytes(initialChainValueHex);

        // Create a chain length integer.
        int chainLength = 120;

        // Find the final chain value.
        byte[] finalChainValue = new byte[16];
        for (int i = 0; i < chainLength; i++) {
            // Encrypt the plaintext.
            byte[] ciphertext = cipher.doFinal(plaintext);

            // Update the chain value.
            for (int j = 0; j < finalChainValue.length; j++) {
                finalChainValue[j] ^= ciphertext[j];
            }

            // Update the plaintext.
            plaintext = ciphertext;
        }

        // Convert the final chain value to hex.
        String finalChainValueHex = bytesToHex(finalChainValue);

        // Print the first 6 hex values of the final chain value.
        System.out.println(finalChainValueHex.substring(0, 6)); // 99aaf4
    }

    private static byte[] hexToBytes(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((Character.digit(hexString.charAt(2 * i), 16) << 4)
                    | Character.digit(hexString.charAt(2 * i + 1), 16));
        }
        return bytes;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}