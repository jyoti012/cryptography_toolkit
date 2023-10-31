package Crypto_Code;

import java.math.BigInteger;

/*
 * https://www.eecis.udel.edu/~amer/CISC651/ASCII-Conversion-Chart.pdf
 */

public class ASCIIToHex {
  public static void main(String[] args) {
    // Input large decimal number
    String decimalNumberStr = "4949494949496565656565657070";

    // Convert large decimal to hexadecimal
    BigInteger decimalNumber = new BigInteger(decimalNumberStr);
    String hexString = decimalToHex(decimalNumber).toUpperCase();

    System.out.println("Decimal: " + decimalNumberStr);
    System.out.println("Hexadecimal: " + hexString);
  }

  public static String decimalToHex(BigInteger decimalNumber) {
    return decimalNumber.toString(16);
  }
}
