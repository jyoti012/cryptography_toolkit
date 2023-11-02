package CS616Toolkit;

public class FastExponentiationModulo {
	public static long fastExponentiationModulo(long base, long exponent, long modulo) {
		if (exponent == 0) {
			return 1 % modulo; // Anything raised to the power of 0 is 1 (mod modulo)
		} else if (exponent % 2 == 0) {
			// If the exponent is even, use the property a^b = (a^(b/2))^2
			long result = fastExponentiationModulo(base, exponent / 2, modulo);
			return (result * result) % modulo;
		} else {
			// If the exponent is odd, use the property a^b = a * (a^(b-1))
			return (base * fastExponentiationModulo(base, exponent - 1, modulo)) % modulo;
		}
	}

	public static void main(String[] args) {
		long base = 42;
		long exponent = 101;
		long modulo = 21781;

		long result = fastExponentiationModulo(base, exponent, modulo);
		System.out.println(base + "^" + exponent + " mod " + modulo + " = " + result);
	}
}
