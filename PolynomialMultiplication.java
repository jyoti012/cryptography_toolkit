public class PolynomialMultiplication {
  public static int[] multiplyPolynomials(int[] poly1, int[] poly2) {
    int m = poly1.length, n = poly2.length;
    int[] result = new int[m + n - 1];

    for (int i = 0; i < m; i++)
      for (int j = 0; j < n; j++)
        result[i + j] += poly1[i] * poly2[j];

    return result;
  }

  public static void main(String[] args) {
    int[] poly1 = { 1, 2, 3 };
    int[] poly2 = { 2, 1 };

    int[] result = multiplyPolynomials(poly1, poly2);

    System.out.println("Polynomial 1: " + java.util.Arrays.toString(poly1));
    System.out.println("Polynomial 2: " + java.util.Arrays.toString(poly2));
    System.out.println("Result of multiplication: " + java.util.Arrays.toString(result));
  }
}
