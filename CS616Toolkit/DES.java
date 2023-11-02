package CS616Toolkit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DES {
    public static void main(String[] args) {
        try {
            String plainText = "jan11"; // in ques
            String cipherText = "NuhXLH6RVLs="; // in ques
            //AA:AA:AA:AA:AA:AA, appended 2 00:00 at the end because key fragment length is 8
            byte[] desByteArr = new byte[]{
                    (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0x00, (byte) 0x0e,
            };
            //Getting the key fragment length to append the dummy characters at the end above,
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            byte[] decryptedValue = Base64.getDecoder().decode(cipherText.getBytes(StandardCharsets.UTF_8));
            System.out.println("Key Fragment Length " + decryptedValue.length);

            for (int i = 0; i <= 255; i++) {
                for (int j = 0; j <= 255; j++) {
                    desByteArr[desByteArr.length - 1] = (byte) i;
                    desByteArr[desByteArr.length - 2] = (byte) j;


                    //Passing the DESFragment Array to set the secret key object
                    SecretKey secretKey = new SecretKeySpec(desByteArr, "DES");
                    //Initializing the Cipher Text object
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    //Encoding plain Text to get the cipher text in Base64 format
                    String cipherTextBruteForce = new String(Base64.getEncoder().encode(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);


                    if (cipherTextBruteForce.equals(cipherText)) {
                        System.out.println(Integer.toHexString(j));
                        System.out.println(Integer.toHexString(i));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
