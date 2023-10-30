/*
  The extended Euclidean algorithm is an extension of the Euclidean algorithm, 
  which is used to find the GCD of two numbers. 
  It finds h and k such that they satisfy Bézout's identity, 
  which is the equation : ha + kb = 1 
  
  maths ref: https://youtu.be/6KmhCKxFWOs?si=7RITqautKC0zrhzz
 */ 

import java.util.Scanner;

public class ExtendedEuclideanAlgorithm {
  public static void extendedEuclidean(int a, int b) {
    int h0 = 1, h1 = 0, k0 = 0, k1 = 1;

    while (b != 0) {
      int quotient = a / b;
      int temp;

      temp = a;
      a = b;
      b = temp % b;

      temp = h0;
      h0 = h1;
      h1 = temp - quotient * h1;

      temp = k0;
      k0 = k1;
      k1 = temp - quotient * k1;
    }

    System.out.println("s = " + h0);
    System.out.println("t = " + k0);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the value of a: ");
    int a = scanner.nextInt();

    System.out.print("Enter the value of b: ");
    int b = scanner.nextInt();

    scanner.close();

    extendedEuclidean(a, b);
  }
}
