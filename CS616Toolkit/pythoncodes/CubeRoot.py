def cube_root_mod_p(y, p):
    """Finds the cube root of y mod p.

    Args:
        y: An integer.
        p: A prime number.

    Returns:
        The cube root of y mod p.
    """

    # Convert the hex numbers to decimal.
    y = int(y, 16)
    p = int(p, 16)

    # Find the cube root of y mod p.
    root = pow(y, (2 * p - 1) // 3, p)

    # Convert the cube root back to hex.
    root_hex = hex(root)[2:]

    # Return the first five hex characters of the cube root.
    return root_hex[:5]


if __name__ == '__main__':
    y = 'd'
    p = 'c22de96c9b208d75a0143753e4155daa58e4ffe8c754b306a6f98370234d161d'

    root_hex = cube_root_mod_p(y, p)

    print(root_hex)