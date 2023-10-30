package Crypto_Code;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES_ECB {
    public static void main(String[] args) {
        try {
            String cipherText = "L4JwZr1hziltX2EqVzQeWPjGxgaAuMKWPI6LhNCKszztIn+ekxR9DQCleZS77VJ0";
            byte[] aesByteArr = new byte[]{
                    (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee,
                    (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee,
                    (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa,
                    (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee, (byte) 0xee
            };
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //Passing the AESFragment Array to set the secret key object
            SecretKey secretKey = new SecretKeySpec(aesByteArr, "AES");
            //Initializing the Cipher Text object
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //Decoding Cipher Text to get the plain text
            byte[] cipherByteArray = Base64.getDecoder().decode(cipherText.getBytes(StandardCharsets.UTF_8));
            byte[] plainTextArr = cipher.doFinal(cipherByteArray);
            System.out.println(new String(plainTextArr));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
