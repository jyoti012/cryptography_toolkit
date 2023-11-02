package CS616Toolkit;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class MAC {
    public static void main(String[] args) {
        try {
            String plainText = "This is the message to Keccak-512 MAC!abc";
            String MAC = "6P9c8GDP31jtJ7dAW3E2YRKa+ntW3YQd+dL55bOMu4Qa8LhA0GF4LMZqtzWQlSDxqrn1gacjC3lIUurKUeWm8w==";
            MessageDigest md = MessageDigest.getInstance("SHA3-512"); // Enter the Hash Code mentioned in ques
            /*  We will run three for loop, and we will try every combination of ASCII Character
            * from 1 to 50 , for example if i = 1 , j=1 and k = 1 ascii will be
            * SOH SOH SOH and similarly next iteration will be
            * i =1, j=1 and k =2
            * and so on till we get the symbols
            *
            * */
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    for (int k = 0; k < 50; k++) {
                        StringBuilder sb = new StringBuilder(plainText);
                        /*The setCharAt(int index, char ch) method of StringBuilder
                          class is used to set the character at the position index passed as ch.
                          We typecast the integer value to char explicitly.
                          */
                        sb.setCharAt(plainText.length() - 1, (char) k);
                        sb.setCharAt(plainText.length() - 2, (char) j);
                        sb.setCharAt(plainText.length() - 3, (char) i);
                        String modifiedPlainText = sb.toString();
                        byte[] encryptedText = md.digest(modifiedPlainText.getBytes(StandardCharsets.UTF_8));
                        String encryptedStr = Base64.getEncoder().encodeToString(encryptedText);
                        if (MAC.equals(encryptedStr)) {
                            System.out.println((char) i);
                            System.out.println((char) j);
                            System.out.println((char) k);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
