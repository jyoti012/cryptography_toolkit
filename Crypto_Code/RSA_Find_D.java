package Crypto_Code;

import java.math.BigInteger;

public class RSA_Find_D {
    public static void main(String args[])
    {
        int i;
        BigInteger p,q,n,z,e,mod;

        BigInteger d = new BigInteger("0");
        // The number to be encrypted and decrypted
        String plaintext = "127458934542242345565645767";
        System.out.println("Original Message is: "+plaintext);
        BigInteger msg = new BigInteger(plaintext);

        // The number after decryption
        BigInteger msgback;


        // Holds the values to calculate gcd
        String input1 = "10092003300140014003";
        String input2 = "975319753197531975319";
        // Step 1 : Select two prime numbers p and q  or generate them using Rabin-Miller algo
        // 1st prime number p
        p = new BigInteger(input1);
        // 2nd prime number q
        q = new BigInteger(input2);

        BigInteger one = new BigInteger("1");

        // Step 2: calculate n = p* q
        n = p.multiply(q);

        //Step 3: Compute ϕ(n) = (p – 1) * (q – 1), here z is representing ϕ(n)
        z = (p.subtract(one)).multiply(q.subtract(one));
        System.out.println("the value of z = " + z);// Printing ϕ(n) value

        //Step 4: Choose e such gcd(e , ϕ(n) ) = 1, calculating gcd of e and z
        /* We will start with e=2, and will keep incrementing till e<z,
         * we wil break if we find any value of e where GCD(E and z) is equal to 1. */
        for (e = BigInteger.valueOf(2); e.compareTo(z)<=0; e=e.add(BigInteger.ONE)) {


            String e_value = e.toString();
            String z_value = z.toString();
            int GCD_VALUE = find_gcd(e_value,z_value);// calling function to calculate GCD of e and z

            if (GCD_VALUE == 1) {
                break; // we will break the loop once we find the value of e, where gcd of e and z is 1
            }
        }
        // e is for public key exponent
        System.out.println("the value of e = " + e.toString());

        //Step 5: Calculate private key d parameter, modular inverse of e
        // d*e modϕ(n) = 1 or d*e mod z = 1
        String number_one = "1";

        //Calculate d such e*d mod ϕ(n) = 1
        for (i = 0; i <= 9; i++) {
            BigInteger a = new BigInteger(number_one);// assigning a = 1;
            BigInteger k = BigInteger.valueOf(i);// converting i to Big Integer k to perform i*z
            //BigInteger x = 1 + (z.multiply(i));
            // Next two lines are performing  x = 1 + (i * z);
            BigInteger b = z.multiply(k); //(i * z)
            BigInteger x = a.add(b);//  1 + (i * z)
            mod = x.mod(e);

            // d is for private key exponent
            if (mod.intValue() == 0) {
                d=x.divide(e); // performing d=x/a
                break;
            }
        }


        System.out.println("the value of d = " + d.toString());

        // Cipher text C = P^e mod n where P = plaintext
        BigInteger c = msg.modPow(e,n); // Using Big integer Power Modulo method
        //c = (Math.pow(msg,e)) % n;
        System.out.println("Encrypted message is : " + c.toString());
        //------------------- Decryption -------------//

        //For Decryption D = Dd mod n where D will refund the plaintext

        // PlainText = cipher_text^d mod n;
        msgback = c.modPow(d,n);

        System.out.println("Decrypted message is : "
                + msgback);
    }

    /*
    static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }
*/
    // FInd GCD
    public static int find_gcd(String num1, String num2)
    {

        // BigInteger object to store the result
        BigInteger result;

        // Holds the values to calculate gcd
        String input1 = num1; // We have value in String and later will convert them to BigInteger
        String input2 = num2;

        // Creating two BigInteger objects
        BigInteger a
                = new BigInteger(num1);
        BigInteger b
                = new BigInteger(num2);

        // Calculate gcd
        result = a.gcd(b);

        return result.intValue(); // returning integer value of Big Integer
    }
}

// Java Program to Implement the RSA Algorithm




