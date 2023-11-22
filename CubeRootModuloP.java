import java.math.BigInteger;

public class CubeRootModuloP {
    public static BigInteger cubeRootMod(BigInteger y, BigInteger p) {
        // Find the cube root of y mod p.
        BigInteger root = pow(y, p.subtract(BigInteger.ONE).multiply(BigInteger.valueOf(2)).divide(BigInteger.valueOf(3)), p);

        // Return the first five hex characters of the cube root.
        String rootHex = root.toString(16);
        return new BigInteger(rootHex.substring(0, Math.min(5, rootHex.length())), 16);
    }

    // Modular exponentiation function.
    private static BigInteger pow(BigInteger base, BigInteger exponent, BigInteger modulus) {
        BigInteger result = BigInteger.ONE;
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.testBit(0)) {
                result = result.multiply(base).mod(modulus);
            }
            base = base.multiply(base).mod(modulus);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    public static void main(String[] args) {
        BigInteger y = new BigInteger("d22de96c9b208d75a0143753e4155daa58e4ffe8c754b306a6f98370234d161d", 16);
        BigInteger p = new BigInteger("c22de96c9b208d75a0143753e4155daa58e4ffe8c754b306a6f98370234d161d", 16);

        BigInteger root = cubeRootMod(y, p);

        System.out.println("The cube root of y mod p is: " + root.toString(16)); // Print in hex format
    }
}
