package Crypto_Code;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES {
    public static void main(String[] args) {
        try {
            String plainText = "codebreaker"; // Given in ques
            String cipherText = "5XWvgqrISJZ+DCXEryChjQ=="; // Given in ques
            byte[] aesByteArr = new byte[]{
                    (byte) 0xc0, (byte) 0xc1, (byte) 0xc7, (byte) 0xca, (byte) 0x60, (byte) 0x31, (byte) 0x7b, (byte) 0xd8, (byte) 0x27, (byte) 0xfb, (byte) 0x1b,
                    (byte) 0xee, (byte) 0x01, (byte) 0x30, (byte) 0x00, (byte) 0x00
            };
            /* We will write the given key fragment
             C0:C1:C7:CA:60:31:7B:D8:27:FB:1B:EE:01:30
             to the byte array as (byt) 0xc0, 0xc1, 0xc7 etc and here we have only 14 bytes
             to make it 16 we will add two more bytes as 0 bytes , 0x00 and 0x00
            */
            Cipher cipher = Cipher.getInstance("AES");
            for (int i = 0; i <= 255; i++) {
                for (int j = 0; j <= 255; j++) {
                    aesByteArr[aesByteArr.length - 1] = (byte) i;
                    aesByteArr[aesByteArr.length - 2] = (byte) j;
                    //Passing the AESFragment Array to set the secret key object
                    SecretKey secretKey = new SecretKeySpec(aesByteArr, "AES");
                    //Initializing the Cipher Text object
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    //Encoding Plain Text to get the cipher text in Base64 format
                    String cipherTextBruteForce = Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
                    if (cipherTextBruteForce.equals(cipherText)) {
                        System.out.println(Integer.toHexString(j));
                        System.out.println(Integer.toHexString(i));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
