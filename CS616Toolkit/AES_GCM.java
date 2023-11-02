package CS616Toolkit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES_GCM {
    public static final String IVStr = "65U3jGXzxm84mVzT";
    public static final byte[] aesByteArr = new byte[]{
            (byte) 0x71, (byte) 0xde, (byte) 0x54, (byte) 0x0c, (byte) 0x34, (byte) 0x95, (byte) 0xa3, (byte) 0xc6,
            (byte) 0xc1, (byte) 0x41, (byte) 0x48, (byte) 0xc5, (byte) 0x38, (byte) 0xcb, (byte) 0xfe, (byte) 0xa5,
            (byte) 0x44, (byte) 0x08, (byte) 0x37, (byte) 0x68, (byte) 0xcd, (byte) 0x2c, (byte) 0xed, (byte) 0x60,
            (byte) 0x48, (byte) 0xfb, (byte) 0x8c, (byte) 0x37, (byte) 0x29, (byte) 0x2e, (byte) 0xdd, (byte) 0x70,
    };
    public static final byte[] aadArr = new byte[]{
            (byte) 0x61, (byte) 0x62, (byte) 0x00
            /* We know first two values a and b are given , 'a' in ASCII is 65 and
            * in Hex it is 61 so we write that as (byte)0x62 and
            *   'b' in ASCII is 66 and in Hex it is 62, so we write that as (byte)0x62
             */
    };
    public static final String cipherText = "wv9bPRIQhm5C5uCHpHg7HruTpJfb"; // Given in ques
    public static final int GCM_TAG_LENGTH = 16; // given in ques is 128 bits  we provide in bytes

    public static void main(String[] args) throws Exception {

        // Generate Key
        SecretKey secretKey = new SecretKeySpec(aesByteArr, "AES");
        byte[] IV = Base64.getDecoder().decode(IVStr.getBytes(StandardCharsets.UTF_8));

        /*System.out.println("Original Text : " + plainText);

        byte[] cipherText = encrypt(plainText.getBytes(), secretKey, IV);
        System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(cipherText));*/

        String decryptedText = decrypt(Base64.getDecoder().decode(cipherText), secretKey, IV);
        System.out.println("Decrypted Text : " + decryptedText);
    }

    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);

        return cipherText;
    }

    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV) throws Exception {
        String plainText = null;
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        for (int i = 0; i <= 255; i++) {
            aadArr[aadArr.length - 1] = (byte) i;
            cipher.updateAAD(aadArr);
            // Perform Decryption
            try {
                byte[] plainTextArr = cipher.doFinal(cipherText);
                plainText = new String(plainTextArr);
                System.out.println("Missing AAD Character: "+(char) i);
            } catch (Exception e) {
                //Multiple Exceptions come, just ignoring them
            }
        }
        return plainText;
    }
}