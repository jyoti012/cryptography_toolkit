# Code 2

# from Crypto.Cipher import AES
# from Crypto.Util.Padding import pad
# import binascii

# def aes_ecb_encrypt(key, plaintext):
#     """ Encrypts the plaintext using AES ECB mode with the given key. """
#     cipher = AES.new(key, AES.MODE_ECB)
#     return cipher.encrypt(plaintext)

# # def reduction_function_aes(data, block_size=32):
# #     """ A simple reduction function for AES TMTO chain.
# #         It truncates or pads the data to fit the block size.
# #     """
# #     return data[:block_size//2] + (b'\x00' * (block_size//2 - len(data[:block_size//2])))

# def reduction_function_aes_256(data, block_size=32):
# 	""" A reduction function for AES-256 TMTO chain.
# 			It truncates or pads the data to fit a 256-bit (32 bytes) block size.
# 	"""
# 	return data[:block_size] + (b'\x00' * (block_size - len(data[:block_size])))

# # Re-initialize the current value with the initial chain value
# current_value = initial_chain_value

# for i in range(chain_length):
# 	# Encrypt using the current value as the key (32 bytes required for AES-256)
# 	encrypted = aes_ecb_encrypt(current_value[:32], pad(plaintext, AES.block_size))
# 	# Apply reduction function
# 	current_value = reduction_function_aes_256(encrypted)

# # Final chain value in hexadecimal
# final_chain_value_hex = binascii.hexlify(current_value).decode()[:6]
# final_chain_value_hex

from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import binascii

def aes_ecb_encrypt(key, plaintext):
  """ Encrypts the plaintext using AES ECB mode with the given key. """
  cipher = AES.new(key, AES.MODE_ECB)
  return cipher.encrypt(plaintext)


def reduction_function_aes_256(data, block_size=32):
  """ A reduction function for AES-256 TMTO chain.
        It truncates or pads the data to fit a 256-bit (32 bytes) block size.
    """
  return data[:block_size] + (b'\x00' * (block_size - len(data[:block_size])))


# Initial data
plaintext = "11111111111111111111111111111111".encode()
initial_chain_value = binascii.unhexlify(
    "99aaf4b8e24df5dca79ead5ee8441afac138c2aabcf6f177aa0ae84d9f5f0c0a")
chain_length = 120

# Starting the chain with the initial value
current_value = initial_chain_value

for i in range(chain_length):
  # Encrypt using the current value as the key (32 bytes required for AES-256)
  encrypted = aes_ecb_encrypt(current_value[:32], pad(plaintext,
                                                      AES.block_size))
  # Apply reduction function
  current_value = reduction_function_aes_256(encrypted)

# Final chain value in hexadecimal
final_chain_value_hex = binascii.hexlify(current_value).decode()[:6]
print(final_chain_value_hex)
