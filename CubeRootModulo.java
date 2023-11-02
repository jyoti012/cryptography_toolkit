public class CubeRootModulo {

  public static long modPow(long base, long exponent, long modulus) {
    if (modulus == 1)
      return 0;
    long result = 1;
    base = base % modulus;
    while (exponent > 0) {
      if (exponent % 2 == 1) {
        result = (result * base) % modulus;
      }
      base = (base * base) % modulus;
      exponent = exponent / 2;
    }
    return result;
  }

  public static long findCubeRootModulo(long n, long p) {
    long x = modPow(n, (2 * p - 1) / 3, p); // Calculate the cube root modulo p
    return x;
  }

  public static void main(String[] args) {
    long n = 999;
    long p = 10007;

    long cubeRoot = findCubeRootModulo(n, p);
    System.out.println("Cube root of " + n + " modulo " + p + " is " + cubeRoot);
  }
}
