package EulerTotientAndPrimes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Asymmetric {

	public static void main(String[] args) {

		BigInteger[] result = extendedEuclidean(new BigInteger("973"), new BigInteger("301"));
		System.out.println(result[0]); // gcd ��
		System.out.println(result[1]); // s ��
		System.out.println(result[2]); // t ��

		System.out.println(phi(12));
		//To Verify given m = 12 a = 5
		// a^phi(m) = 1 mod 12 
		
		MillerRabin(new BigInteger("37"));
		
		DH();
	}

	/*
	 * Euclidean Algorithm Greatest Common Divisor: GCD(r0,r1) of two integer r0,r1
	 * The gcd is the product of all common prime factors 84 = 2^2 * 3 * 7 = 2 * 2 *
	 * 3 * 7 30 = 2 * 3 * 5
	 * 
	 * gcd(84,30) = 2 * 3 = 6
	 */

	public static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	/*
	 * Extended Euclidean Algorithm (EEA)
	 * 
	 * gcd(973,301) = s(973) + t(301)
	 * 
	 */
	public static BigInteger[] extendedEuclidean(BigInteger p, BigInteger q) {
		BigInteger[] val = new BigInteger[3];

		if (q.equals(BigInteger.ZERO)) {
			val[0] = p;
			val[1] = BigInteger.ONE;
			val[2] = BigInteger.ZERO;
		} else {
			BigInteger[] val2 = extendedEuclidean(q, p.mod(q));
			val[0] = val2[0];
			val[1] = val2[2];
			val[2] = val2[1].subtract(p.divide(q).multiply(val2[2]));
		}
		return val;
	}

	/*
	 * Euler's Phi Function
	 * 
	 */
	public static int phi(int n) {
		int result = 1;
		for (int i = 2; i < n; i++)
			if (gcd(i, n) == 1)
				result++;
		return result;
	}

	// Prime Factorization for Euler Phi Function
	public static List<Integer> primeFactors(int number) {
		int n = number;
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}
		return factors;
	}
	
	/*
	 * Miller Rabin Test 
	 * 
	 */
	
	public static void MillerRabin(BigInteger n) {
		BigInteger nSub1 = n.subtract(BigInteger.ONE);
		
		// compute nSub1 / 2^ m is mod != 0
		BigInteger two = BigInteger.TWO;
		BigInteger MONE = new BigInteger("-1");
		BigInteger m = nSub1;
		int count = 1;
		// System.out.println(nSub1.divide(two.pow(count)));
		while (nSub1.mod(two.pow(count)).equals(BigInteger.ZERO)) {
			m = nSub1.divide(two.pow(count));
			count++;
		}
		//System.out.println(count-1);
		count = count-1;
	//	System.out.println(m);
		BigInteger b0 = two.modPow(m, n); // T a^m mod n
	//	System.out.println(b0);
		int state = 0;
		if (b0.equals(BigInteger.ONE) || b0.equals(n.subtract(BigInteger.ONE))) {
			System.out.println("Inconclusive, Probably Prime");
			state =1;
		} else {
			
			for (int i = 0; i < count - 1; i++) {
				b0 = b0.modPow(BigInteger.TWO, n);

				if (b0.remainder(n).equals(n.subtract(BigInteger.ONE))) {
					System.out.println("Inconclusive, Probably Prime");
					state =1;
					break;
				}
			}
			if(state == 0)
			{
				System.out.println("Not a Prime");
			}
			
		}
	}
	
	/*
	 *  Diffie Hellman Key Agreement 
	 * 	Publicly Known Parameter (alpha,p)
	 * 
	 * Alice,Bob Choose Private Key 
	 * Choose Random Private Key = KpubA,B = alpha^a(mod p) switch 
	 * 
	 * B^a (mod 29) 
	 * 
	 */
	public static void DH()
	{
	BigInteger p = new BigInteger("341769842231234673709819975074677605139");
	BigInteger g = new BigInteger("37186859139075205179672162892481226795");
	BigInteger aX = new BigInteger("83986164647417479907629397738411168307");
	BigInteger bX = new BigInteger("140479748264028247931575653178988397140");
	
	BigInteger pubA = g.modPow(aX, p);
	BigInteger pubB = g.modPow(bX,p);
	
	
	//switch 
	
	BigInteger common = pubB.modPow(aX,p);
	BigInteger common1 = pubA.modPow(bX, p);
	
	System.out.println(common.toString(16));
	System.out.println(common1.toString(16));
	}
}