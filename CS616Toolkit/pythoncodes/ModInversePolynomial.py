"""
BINARY POLYNOMIAL ARITHMETIC
These functions operate on binary polynomials (Z/2Z[x]), expressed as coefficient bitmasks, etc:
	0b100111 -> x^5 + x^2 + x + 1
As an implied precondition, parameters are assumed to be *nonnegative* integers unless otherwise noted.
This code is time-sensitive and thus NOT safe to use for online cryptography.
"""

from typing import Tuple
from sympy import Symbol, Poly, invert

# descriptive aliases (assumed not to be negative)
Natural = int
BPolynomial = int


def p_mul(a: BPolynomial, b: BPolynomial) -> BPolynomial:
  """ Binary polynomial multiplication (peasant). """
  result = 0
  while a and b:
    if a & 1: result ^= b
    a >>= 1
    b <<= 1
  return result


def p_mod(a: BPolynomial, b: BPolynomial) -> BPolynomial:
  """ Binary polynomial remainder / modulus.
				Divides a by b and returns resulting remainder polynomial.
				Precondition: b != 0 """
  bl = b.bit_length()
  while True:
    shift = a.bit_length() - bl
    if shift < 0: return a
    a ^= b << shift


def p_divmod(a: BPolynomial,
             b: BPolynomial) -> Tuple[BPolynomial, BPolynomial]:
  """ Binary polynomial division.
				Divides a by b and returns resulting (quotient, remainder) polynomials.
				Precondition: b != 0 """
  q = 0
  bl = b.bit_length()
  while True:
    shift = a.bit_length() - bl
    if shift < 0: return (q, a)
    q ^= 1 << shift
    a ^= b << shift


def p_mod_mul(a: BPolynomial, b: BPolynomial,
              modulus: BPolynomial) -> BPolynomial:
  """ Binary polynomial modular multiplication (peasant).
				Returns p_mod(p_mul(a, b), modulus)
				Precondition: modulus != 0 and b < modulus """
  result = 0
  deg = p_degree(modulus)
  assert p_degree(b) < deg
  while a and b:
    if a & 1: result ^= b
    a >>= 1
    b <<= 1
    if (b >> deg) & 1: b ^= modulus
  return result


def p_exp(a: BPolynomial, exponent: Natural) -> BPolynomial:
  """ Binary polynomial exponentiation by squaring (iterative).
				Returns polynomial `a` multiplied by itself `exponent` times.
				Precondition: not (x == 0 and exponent == 0) """
  factor = a
  result = 1
  while exponent:
    if exponent & 1: result = p_mul(result, factor)
    factor = p_mul(factor, factor)
    exponent >>= 1
  return result


def p_gcd(a: BPolynomial, b: BPolynomial) -> BPolynomial:
  """ Binary polynomial euclidean algorithm (iterative).
				Returns the Greatest Common Divisor of polynomials a and b. """
  while b:
    a, b = b, p_mod(a, b)
  return a


def p_egcd(a: BPolynomial,
           b: BPolynomial) -> tuple[BPolynomial, BPolynomial, BPolynomial]:
  """ Binary polynomial Extended Euclidean algorithm (iterative).
				Returns (d, x, y) where d is the Greatest Common Divisor of polynomials a and b.
				x, y are polynomials that satisfy: p_mul(a,x) ^ p_mul(b,y) = d
				Precondition: b != 0
				Postcondition: x <= p_div(b,d) and y <= p_div(a,d) """
  a = (a, 1, 0)
  b = (b, 0, 1)
  while True:
    q, r = p_divmod(a[0], b[0])
    if not r: return b
    a, b = b, (r, a[1] ^ p_mul(q, b[1]), a[2] ^ p_mul(q, b[2]))


def p_mod_inv(a: BPolynomial, modulus: BPolynomial) -> BPolynomial:
  """ Binary polynomial modular multiplicative inverse.
				Returns b so that: p_mod(p_mul(a, b), modulus) == 1
				Precondition: modulus != 0 and p_coprime(a, modulus)
				Postcondition: b < modulus """
  d, x, y = p_egcd(a, modulus)
  assert d == 1  # inverse exists
  return x


def p_mod_pow(x: BPolynomial, exponent: Natural,
              modulus: BPolynomial) -> BPolynomial:
  """ Binary polynomial modular exponentiation by squaring (iterative).
				Returns: p_mod(p_exp(x, exponent), modulus)
				Precondition: modulus > 0
				Precondition: not (x == 0 and exponent == 0) """
  factor = x = p_mod(x, modulus)
  result = 1
  while exponent:
    if exponent & 1:
      result = p_mod_mul(result, factor, modulus)
    factor = p_mod_mul(factor, factor, modulus)
    exponent >>= 1
  return result


def p_degree(a: BPolynomial) -> int:
  """ Returns degree of a. """
  return a.bit_length() - 1


def p_congruent(a: BPolynomial, b: BPolynomial, modulus: BPolynomial) -> bool:
  """ Checks if a is congruent with b under modulus.
				Precondition: modulus > 0 """
  return p_mod(a ^ b, modulus) == 0


def p_coprime(a: BPolynomial, b: BPolynomial) -> bool:
  """ Checks if a and b are coprime. """
  return p_gcd(a, b) == 1


def poly_multiply_mod_p(p1, p2, p3_coefficients):
  result = [0] * (len(p1) + len(p2) - 1)

  for i in range(len(p1)):
    for j in range(len(p2)):
      result[i + j] = (
          result[i + j] + p1[i] *
          p2[j]) % 2**64  # Use a sufficiently large number to avoid overflow

  # Perform polynomial long division to find the remainder
  while len(result) >= len(p3_coefficients):
    lead = result[-len(p3_coefficients)] // p3_coefficients[0]

    for i in range(len(p3_coefficients)):
      result[-len(p3_coefficients) + i] = (result[-len(p3_coefficients) + i] -
                                           lead * p3_coefficients[i]) % 2**64

    while len(result) > 1 and result[-1] == 0:
      result.pop()

  return result


def create_polynomial(coefficients, powers):
  # Find the maximum power in the list
  max_power = max(powers)

  # Initialize the coefficients list with zeros
  polynomial = [0] * (max_power + 1)

  # Set the coefficients based on the input lists
  for coeff, power in zip(coefficients, powers):
    polynomial[power] = coeff

  return polynomial


def listToBinary(binary_list):
  return int(''.join(map(str, binary_list)), 2)


# Example usage: x^128 + x^8 + x^6 + x^5 + x^4 + x + 1
coefficients_list = [1, 1, 1, 1, 1, 1, 0]
powers_list = [128, 8, 6, 5, 4, 0, 1]

p1 = create_polynomial(coefficients_list, powers_list)
# p2 = listToBinary(p1)
# print(create_polynomial(coefficients_list, powers_list))
# print(p2)

# coefficients_list = [1, 1, 0]
# powers_list = [254, 1, 0]
# p2 = create_polynomial(coefficients_list, powers_list)
# p2 = listToBinary(p2)

# coefficients_list = [1, 1]
# powers_list = [256, 0]
# p3 = create_polynomial(coefficients_list, powers_list)
# p3 = listToBinary(p3)

# print(p_mod_mul(p1, p2, p3))

a = 0b10011001011010000100010110111010110000100001010001001111110101011011111011100100111100111001011010100011111111101111011100
# modulus = 0b100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000101110011

modulus = 0b100011101000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001

gcd = p_gcd(a, modulus)
# If the GCD is not 1, then p_mod_inv will rightly fail because an inverse does not exist.

# bin(gcd) does not print 0b1, then the polynomials are not coprime
# print(bin(gcd))  # 0b1


def hex_to_binary(hex_string):
  # Convert the hexadecimal string to an integer
  integer_value = int(hex_string, 16)

  # Convert the integer to a binary string and remove the '0b' prefix
  binary_string = bin(integer_value)[2:]

  return binary_string


# Example usage
hex_number = "265a116eb08513f56fb93ce5a8ffbdc"
binary_number = hex_to_binary(hex_number)
print("hex to binary", binary_number)


def binary_list_to_string(binary_list):
  # Convert each binary digit in the list to a string and concatenate them
  binary_string = ''.join(str(bit) for bit in binary_list)
  return binary_string


# Example usage
# binary_list = [
#     1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
#     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
#     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
#     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
#     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
#     0, 0, 0, 1
# ]
binary_string = binary_list_to_string(p1)
print('polynomial binary value = ', binary_string)


def binary_to_hex(binary_str):
  # Convert binary string to an integer
  binary_int = int(binary_str, 2)

  # Convert the integer to a hexadecimal string
  hex_str = hex(binary_int)

  return hex_str[2:]  # Remove the '0x' prefix


# binary_input = input("Enter a binary number: ")
# hex_output = binary_to_hex(binary_input)
# print(f"The hexadecimal equivalent is: {hex_output}")

def polynomial_to_binary(polynomial):
	# Get the coefficients of the polynomial
	coefficients = Poly(polynomial).all_coeffs()

	# Convert each coefficient to its binary representation
	binary_coefficients = [bin(int(coefficient))[2:] for coefficient in coefficients]

	# Join the binary coefficients into a single binary string
	binary_representation = ''.join(binary_coefficients)

	return binary_representation

def hexadecimal_to_binary(hexadecimal_number):
	# Convert the hexadecimal number to its binary representation
	binary_representation = bin(int(hexadecimal_number, 16))[2:]
	return binary_representation

# Define the variable x
x = Symbol('x')

# Define the polynomial x^128 + x^8 + x^6 + x^5 + x^4 + x + 1
polynomial = x**128 + x**8 + x**6 + x**5 + x**4 + x + 1

# Example usage:
hexadecimal_number = "265a116eb08513f56fb93ce5a8ffbdc"
binary_polynomial = polynomial_to_binary(polynomial)
binary_hexadecimal = hexadecimal_to_binary(hexadecimal_number)


print("Binary Polynomial:", binary_polynomial)
print("Binary Hexadecimal:", binary_hexadecimal)

decimal_number = p_mod_inv(0b10011001011010000100010110111010110000100001010001001111110101011011111011100100111100111001011010100011111111101111011100, 0b100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000101110011)
print('Decimal - ', p_mod_inv(0b10011001011010000100010110111010110000100001010001001111110101011011111011100100111100111001011010100011111111101111011100, 0b100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000101110011))
print('hexadecimal_representation - ', hex(decimal_number))
print('hexadecimal rep without 0x - ', hex(decimal_number)[2:]) # final answer



