from sympy import symbols, gcdex

def polynomial_to_binary(polynomial):
		"""Convert a polynomial to its binary representation."""
		binary_number = 0
		for term in polynomial.as_ordered_terms():
				coeff, power = term.as_coeff_exponent(x)
				if coeff != 0:
						binary_number += 1 << int(power)
		return binary_number

def polynomial_to_hex(polynomial):
		"""Convert a polynomial to its hexadecimal representation."""
		binary_number = polynomial_to_binary(polynomial)
		return hex(binary_number)

# Define the variable and polynomials
x = symbols('x')
polynomial_1 = x**4 + x**3 + x**2 + x + 1
polynomial_2 = x**7 + x + 1

# Find P1 and P2 using Extended Euclidean Algorithm
P1, P2, _ = gcdex(polynomial_1, polynomial_2)

# Convert P1 and P2 to hexadecimal
hex_P1 = polynomial_to_hex(P1)
hex_P2 = polynomial_to_hex(P2)


# Remove 0x from the answers
hex_P1 = hex_P1[2:]
hex_P2 = hex_P2[2:]

print(f"Hex equivalent of P1: {hex_P1}") # 6E
print(f"Hex equivalent of P2: {hex_P2}") # 9 -> 09
