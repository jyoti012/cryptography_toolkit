from sympy import mod_inverse

# Define the parameters of the elliptic curve
A = 1431
C = 2457
p = 5333

# Define the point P
Px, Py = 2094, 2409

# Function to add two points on the elliptic curve
def ecc_add(x1, y1, x2, y2):
		if x1 == float('inf') or x2 == float('inf'):
				# If one of the points is the point at infinity, return the other point
				return x2, y2 if x1 == float('inf') else x1, y1

		if x1 == x2 and y1 == y2:
				# Point doubling, but need to check if y1 is 0 (point at infinity)
				if y1 == 0:
						return float('inf'), float('inf')
				# Slope of the tangent at (x1, y1)
				m = ((3 * x1**2 + A) * mod_inverse(2 * y1, p)) % p
		else:
				# Point addition
				if x1 == x2:
						# Points are additive inverses, return point at infinity
						return float('inf'), float('inf')
				# Slope of the line through (x1, y1) and (x2, y2)
				m = ((y2 - y1) * mod_inverse(x2 - x1, p)) % p

		# Calculate the coordinates of the new point
		x3 = (m**2 - x1 - x2) % p
		y3 = (m * (x1 - x3) - y1) % p
		return x3, y3

# Function to perform scalar multiplication
def ecc_multiply(x, y, k):
		if k == 0:  # Multiplying by 0 gives the point at infinity
				return float('inf'), float('inf')
		result_x, result_y = x, y
		k -= 1
		while k > 0:
				result_x, result_y = ecc_add(result_x, result_y, x, y)
				k -= 1
		return result_x, result_y

# Function to find the order of a point
def find_order(x, y):
		order = 1
		qx, qy = x, y
		while True:
				qx, qy = ecc_add(x, y, qx, qy)
				order += 1
				if qx == float('inf') and qy == float('inf'):
						break
		return order

# Calculate 1001P
P_1001x, P_1001y = ecc_multiply(Px, Py, 1001)

# Find the order of P
order_P = find_order(Px, Py)

# Output the results
print(f"1001P: ({P_1001x}, {P_1001y})")
print(f"Order of P: {order_P}")
