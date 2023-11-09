// Solution for COT 5937 Programming Assignment #1: Performs AES (128 bit
// key) encryption and decryption on files.
// Date: 6/23/04
// Arup Guha & Hua Zhang

// Note: writeOutput and writePlainFile should be combined into one method
//       that makes use of the mode variable.
 
import java.io.*;
import java.util.*;

public class AES {

  // Constants to identify the current status of the message.
  final static boolean PLAIN = false;
  final static boolean CIPHER = true;

  private int[] message; // Stores the message in ints.
  private String inputfile; // Stores the name of the input file.
  private String outputfile; // Stores the name of the output file.
  private int[] key; // Stores the key.
  private boolean msgstatus; // Stores whether message is currently the
                             // plain text or cipher text.

  private int msglength; // Stores the actual message length in bytes.
  private int[] sbox; // Stores all the sbox values.
  private int[] inv_sbox; // Stores all the inverse sbox values.
  private boolean mode; // Either PLAIN or CIPHER

  public static void main(String[] args) throws IOException {

    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    // Determine whether the user is encrypting or decrypting.
    boolean mode;
    System.out.println("Would you like to (E)ncrypt or (D)ecrypt?");
    char ans = (stdin.readLine()).charAt(0);    
    if  (ans == 'D') 
      mode = CIPHER;
    else
      mode = PLAIN;

    // Read in input and output files.
    System.out.println("Enter the input file.");
    String input = stdin.readLine();
    System.out.println("Enter where you would like the output stored.");
    String output = stdin.readLine();

    // Create the new AES object.
    AES test = new AES(input, output, "sbox.txt","invsbox.txt", mode);
 
    // Read in the key and message into the object.
    System.out.println("Please enter the key in HEX.");
    String key = stdin.readLine();
    test.setKey(key);
    test.keyExpansion();
    test.readMessage();

    // Encrypt or Decrypt.
    if (mode == PLAIN) {
      test.encrypt();
      test.writeOutput();
    } 
    else {
      test.decrypt();
      test.writePlainFile(output);
    }
  }

  // Creates a new AES object.
  public AES(String inp, String out, String sboxfile,
             String invsboxfile, boolean m) throws IOException {

    inputfile = inp;
    outputfile = out;
    mode = m;

    // Divides by 4 since an int stores 4 bytes.
    message = new int[getMessageLength()/4];

    // Read in the sbox and inverse sbox from files.
    readSbox(sboxfile);
    readInvSbox(invsboxfile);
  }

  // Reads in the S-box from a file.
  private void readSbox(String file) throws IOException {

    BufferedReader fin = new BufferedReader(new FileReader(file));
    sbox = new int[256]; // Allocate space for the sbox.

    // Loop through each line.
    for (int i=0; i<16; i++) {
      String line = fin.readLine();
      StringTokenizer tok = new StringTokenizer(line);

      // Separate out all 16 values on a line.
      for (int j=0; j<16; j++) {

	// Store corresponding decimal value of the HEX string.
        String temp = tok.nextToken();
        sbox[16*i+j] = 16*hexVal(temp.charAt(0))+hexVal(temp.charAt(1));
      }
    }
    fin.close();
  }

  // Read in the inverse S-box.
  private void readInvSbox(String file) throws IOException {

    BufferedReader fin = new BufferedReader(new FileReader(file));
    inv_sbox = new int[256]; // Allocate space for the inverse sbox.

    // Loop through each line.
    for (int i=0; i<16; i++) {
      String line = fin.readLine();
      StringTokenizer tok = new StringTokenizer(line);

      // Separate out all 16 values on a line.
      for (int j=0; j<16; j++) {

	// Store the corresponding decimal value for the HEX string.
        String temp = tok.nextToken();
        inv_sbox[16*i+j] = 16*hexVal(temp.charAt(0))+hexVal(temp.charAt(1));
      }
    }
    fin.close();
  }

  // Determine the length of the message in bytes.
  private int getMessageLength() throws IOException {

    BufferedReader fin = new BufferedReader(new FileReader(inputfile));
    int bytecount = 0;

    // For reading a plaintext file.
    if (mode == PLAIN) {
 
      // Count the number of characters in the plain text.
      while (fin.ready()) {
        int c = fin.read(); // Each char is one byte.
        bytecount++;
      }
    }
    else {

      // For reading a ciphertext file.
      while (fin.ready()) {

	// Only could HEX chars.
        int c = fin.read();
        if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')) 
          bytecount++;
      }
      bytecount /= 2; // Account for a Hex char being 1/2 a byte.
    }
    fin.close();

    msglength = bytecount;

    // Rounds up to the nearest multiple of 16 bytes, to account for the
    // block size.
    return 16*((bytecount+15)/16);
  }

  // Reads in a the input file into message, storing the input file in
  // hexadecimal characters.
  public void readMessage() throws IOException {

    BufferedReader fin = new BufferedReader(new FileReader(inputfile));
 
    int icnt, bytecnt, linecnt, hexcnt;

    // Initialize the message buffer.
    for (icnt=0; icnt<message.length; icnt++)    
      message[icnt] = 0;

    // For plaintext files.
    if (mode == PLAIN) {

      // Read in each actual character of the plain text.
      for (bytecnt=0; bytecnt<msglength; bytecnt++) {
        int c = fin.read();
        message[bytecnt/4] = c + (message[bytecnt/4] << 8);
      }

      // Pad the plaintext with spaces to fill the block length.
      for (bytecnt=msglength; bytecnt<4*message.length; bytecnt++)
        message[bytecnt/4] = (message[bytecnt/4] << 8) + (int)(' ');
    }
    else {
      
      // Read in cipher text file one line at a time.
      for (linecnt=0; linecnt<(msglength+16)/32; linecnt++) {
        String line = fin.readLine();

        // Fill in each hex character one by one.
        for (hexcnt=0; hexcnt<line.length(); hexcnt++)
          message[8*linecnt+hexcnt/8] = hexVal(line.charAt(hexcnt)) +
                              (message[8*linecnt+hexcnt/8] << 4);
      }
    }
    fin.close();
  }

  // Sets the key to the HEX string passed in.
  public boolean setKey(String the_key) {
    int i;

    // Check that the key length is correct.
    if (the_key.length() != 32)
      return false;

    key = new int[44]; // allocate space for WHOLE key schedule
    for (i=0; i<4; i++) 
      key[i] = 0;

    // Fills key[0] through key[3], the key for round 0.
    // Reads in each character into the appropriate array slot and shifts
    // by 4 bits.
    for (i=0; i<the_key.length(); i++)
      key[i/8] = (key[i/8] << 4) + hexVal(the_key.charAt(i));

    return true;
  }

  // Fills in the keys for rounds 1 through 10.
  public void keyExpansion() {
  	
    int i;
    int temp;
    int Rcon[] = {0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0x1B,0x36};
  	
    //Rcon[j]=(RC[j],0,0,0)
    for (i=0;i<10;i++) Rcon[i] = (Rcon[i] << 24);
  	
    for (i=4;i<44;i++) {
  		
      temp = key[i-1];
  		
       if ((i%4)==0) {
  			
         // Rotates the word, the extra & 000000FF at the end is due to
  	 // a signing issue of the >> operator.
  	 temp = (temp << 8) + (((temp & 0xFF000000) >> 24) & 0x000000FF);

  	//Substitute for each byte as necessary.
  	temp = (sbox[unsign((temp & 0xFF000000) >> 24)] << 24) +
	       (sbox[unsign((temp & 0x00FF0000) >> 16)] << 16) +
	       (sbox[unsign((temp & 0x0000FF00) >> 8)] << 8) +
	       (sbox[unsign(temp & 0x000000FF)]);

  	//xor with Rcon
  	temp = temp ^ Rcon[i/4 - 1];
      }
  		
      key[i] = key[i-4]^temp; // Final xor.
    }
  }

  // Converts an integer(0-15) to the appropriate HEX character.
  public static char convToHex(int d) {
    if (d < 10)
      return (char)(d+'0');
    else
      return (char)(d-10+'A');
  }

  // Returns the integer value of a given HEX character.
  public static int hexVal(char c) {
    if (c >= '0' && c <= '9')
      return (int)(c-'0');
    else
      return (int)(c-'A'+10);
  }

  // Should only be called if the message is in CIPHER status. This
  // method writes out the ciphertext to the output file, writing 64
  // hex characters per line. This corresponds to 4 blocks of ciphertext
  // per line.
  public void writeOutput() throws IOException {

    BufferedWriter fout = new BufferedWriter(new FileWriter(outputfile));
    for (int i=0; i<message.length; i++) {
      char[] hex = new char[8];
      int temp = message[i];

      // Convert one int into 8 HEX chars.
      for (int j=7; j>=0; j--) {
        hex[j] = convToHex(temp & 15); 
        temp = temp >> 4;       
      }

      // Write out those HEX chars
      for (int j=0; j<8; j++)
        fout.write(hex[j]);

      // Advance to the next line after 64 chars have been written.
      if (i%8 == 7)
        fout.write('\n');
    }
    fout.close();
  }

  // This method should only be called in PLAIN mode. It writes out the
  // plaintext in NORMAL text mode instead in HEX to the file passed into
  // the method.
  public void writePlainFile(String plain) throws IOException {

    BufferedWriter fout = new BufferedWriter(new FileWriter(plain));    
    for (int i=0; i<message.length; i++) {
      int temp = message[i];
      char[] letters = new char[4];

      // Convert an int into 4 chars.
      for (int j=3; j>=0; j--) {
        letters[j] = (char)(temp & 255);
        temp = temp >> 8;
      }

      // Write out the four chars.
      for (int j=0; j<4; j++)
        fout.write(letters[j]); 
    }
    fout.close();
  }

  // Runs AES encryption on the message, assuming that the mode is PLAIN.
  // After completion, the mode is changed to CIPHER.
  public void encrypt() {

    addRoundKey(0);

    // Do rounds 1 - 9.
    for (int i=1; i<10; i++) {
      subBytes();
      shiftRows();
      mixCols();
      addRoundKey(i);
    }

    // Do shortened 10th round.
    subBytes();
    shiftRows();
    addRoundKey(10);

    mode = CIPHER; // Change the mode.
  }

  // Assumes rounds for the key are labelled 0 through 10.
  private void addRoundKey(int which_round) {

    // xors the message with the appropriate parts of the key.
    for (int i=0; i<message.length; i++)
      message[i] = message[i] ^ key[4*which_round+i%4];
  }
  
  // Unsigns a byte that is stored in an integer.
  private int unsign(int c) {
    if (c >=0)
      return c;
    else
      return 256+c;
  }

  // Performs the S-box substitution.
  private void subBytes() {

    // Each int stores 4 bytes, so you substitute for each and shift them
    // accordingly. Notice the use of unsign.
    for (int i=0; i<message.length; i++) 
      message[i] = 
        (sbox[unsign((message[i] & 0xFF000000) >> 24)] << 24) +
        (sbox[unsign((message[i] & 0x00FF0000) >> 16)] << 16) +
        (sbox[unsign((message[i] & 0x0000FF00) >> 8)] << 8) +
        (sbox[unsign(message[i] & 0x000000FF)]);
                 

  }

  // Does the same exact thing as subBytes, but in reverse.
  private void subInvBytes() {

    // Uses the inverse lookup table to do the substitutions.
    for (int i=0; i<message.length; i++) {

      message[i] = 
        (inv_sbox[unsign((message[i] & 0xFF000000) >> 24)] << 24) +
        (inv_sbox[unsign((message[i] & 0x00FF0000) >> 16)] << 16) +
        (inv_sbox[unsign((message[i] & 0x0000FF00) >> 8)] << 8) +
        (inv_sbox[unsign(message[i] & 0x000000FF)]);
                 
    }
  }

  // Shifts the rows in the message.
  private void shiftRows() {

    // Process one block at a time.
    for (int block=0; block<message.length/4; block++) {

      // Create an AESmatrix for the current block.
      AESmatrix temp = new AESmatrix(message, 4*block);

      // Shift the rows and then write the result to the message.
      temp = temp.shiftRows();
      temp.writeAns(message, 4*block);
    }    
  }

  // Does the inverse operation of shiftRows.
  private void invShiftRows() {

    // Go through each block.
    for (int block=0; block<message.length/4; block++) {

      // Create a new AESmatrix for the current block.
      AESmatrix temp = new AESmatrix(message, 4*block);

      // Do the inverse shift and write the result to the message.
      temp = temp.invShiftRows();
      temp.writeAns(message, 4*block);
    }    
  }

  // Performs the Mix Columns portion of the cipher.
  private void mixCols() {

    // Loop through each block.
    for (int block=0; block<message.length/4; block++) {

      // Create an AESmatrix object for the current block.
      AESmatrix temp = new AESmatrix(message, 4*block);

      // Mix the Columns and then write the answer to the message.
      temp = temp.mixCols();
      temp.writeAns(message, 4*block);
    }        
  }

  // Does the inverse of mixCols.
  private void invMixCols() {

    // Loop through each block.
    for (int block=0; block<message.length/4; block++) {
      AESmatrix temp = new AESmatrix(message, 4*block);

      // Invert the Mix Columns and write the answer to the message.
      temp = temp.invMixCols();
      temp.writeAns(message, 4*block);
    }        
  }

  // Runs AES decryption on the message, assuming that the mode is CIPHER.
  // After completion, the mode is changed to PLAIN.
  public void decrypt() {

    addRoundKey(10);

    // Decrypt Rounds 9 through 1
    for (int i=9; i>0; i--) {
      invShiftRows();
      subInvBytes();
      addRoundKey(i);
      invMixCols();
    }

    // Decrypt a partial round 0.
    invShiftRows();
    subInvBytes();
    addRoundKey(0);

    mode = PLAIN; // Change the mode.
  }

}