package Crypto_Code;

import java.security.MessageDigest;
import java.util.Base64;

public class PasswordBasedDecryption {
    public static void main(String[] args) {
        try {
            String[] passwords = {"Cork", "Dublin", "Wexford", "Kerry", "Kildare", "Kilkenny", "Mayo", "Galway", "Waterford", "Meath", "Limerick"};

            //String hash64 = "2PTGEi98+px2aL7vd0MEE5oRfdjliw/ISSvLEl1QB6VRY63zi5z/kTUfTX4bJ6Z7KfOr9Ehpj1a8q4WSp7q5UQ==";
            String hash64 = "JEP0tlZdFoB1eP1aFElEZH6ZD32w2KrBrrEPzHsr5UoyXyojU0/FtKMgsUkTZxIVpz+XWoC7WwM1TisLyOMtxA==";
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            byte[] hash = Base64.getDecoder().decode(hash64);
            //System.out.println(hash.length);

            for (String p : passwords) {
                int salt = 0;
                for (salt = 0; salt <= 0xffff; salt++) { // bruteforce all the possibilities of 2 byte salt 0x0000 to 0xffff
                    byte[] b = {(byte) ((salt >> 8) & 0x000000ff), (byte) (salt & 0x000000ff)};// seperate the salt to byte array

                    sha.update(b); // add salt before digest
                    byte[] newHash = sha.digest(p.getBytes());

                    String newHash64 = Base64.getEncoder().encodeToString(newHash);
                    if (newHash64.equals(hash64)) {
                        System.out.println(p + ", Salt: " + String.format("%x", b[0]) +
                                String.format("%x", b[1]));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
