import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AESFinalChainValue {

    public static byte[] aesECBEncrypt(byte[] plaintext, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plaintext);
    }

    public static byte[] findFinalChainValue(byte[] plaintext, byte[] initialChainValue, int chainLength) throws Exception {
        byte[] finalChainValue = Arrays.copyOf(initialChainValue, initialChainValue.length);
        for (int i = 0; i < chainLength; i++) {
            byte[] ciphertext = aesECBEncrypt(finalChainValue, finalChainValue);
            for (int j = 0; j < finalChainValue.length; j++) {
                finalChainValue[j] ^= ciphertext[j];
            }
        }
        return finalChainValue;
    }

    public static void main(String[] args) throws Exception {
        byte[] plaintext = hexStringToByteArray("11111111111111111111111111111111");
        byte[] initialChainValue = hexStringToByteArray("99aaf4b8e24df5dca79ead5ee8441afac138c2aabcf6f177aa0ae84d9f5f0c0a");
        int chainLength = 120;

        byte[] finalChainValue = findFinalChainValue(plaintext, initialChainValue, chainLength);

        System.out.println(bytesToHex(finalChainValue).substring(0, 12));
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    | Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
