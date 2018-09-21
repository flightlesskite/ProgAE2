/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	private char [][] vcipher;
	private int len;
       
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		len=keyword.length();
		
	    // create alphabet array
		alphabet= new char [SIZE];
		
		for (int i=0; i< SIZE; i++)
			alphabet[i]=(char)('A'+i);
		
		// find length of keyword for 1st dimension of array
		// second dimension is the alphabet length (26)
		
		vcipher=new char[len][SIZE];
		
		// create VCipher array
		for (int i=0; i<len; i++)	{
			int count=0;
			// indexing the letters of keyword
			char key=keyword.charAt(i);
			for (int j=0; j< SIZE; j++) {
				// create first part of VCipher 
				// first dimension of array= letters of the keyword
				// second dimension of array= keyword char+1 to Z
				if (j<'Z'-key+1) {
					vcipher[i][j]= (char)(keyword.charAt(i)+j);
				}
				// create second part of VCipher
				// remainder of the cipher= from A up to keyword char-1
				else {
					vcipher[i][j]=(char)('A'+count);
					count++;
				}
			}	
		}
		
		// print test for the vcipher array
		for (int i=0; i<len; i++)	{
			for (int j=0; j<SIZE; j++) 	{
				System.out.print (vcipher[i][j]);
			}
			System.out.println();
		}
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch, int counter)
	{
	    for (int i=0; i<SIZE; i++) {
	    		if (ch== alphabet[i])	{
	    			return vcipher[counter%len][i];
	    		}
	    }	
		return ch;  
	}
	
	
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch, int counter)
	{
	    for (int i=0; i<SIZE; i++) {
	    		if (ch== vcipher[counter%len][i])	{
	    			return alphabet[i];
	    		}
	    }	
		return ch;  
	}
}
