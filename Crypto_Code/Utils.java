package Crypto_Code;

import java.math.BigInteger;

public class Utils {
  public static String byteArrayToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02X", b));
    }
    return sb.toString();
  }

  public static BigInteger cubeRoot(BigInteger n) {
		double root = Math.pow(n.doubleValue(), 1.0 / 3.0);
		return BigInteger.valueOf((long) root);
	}
}
