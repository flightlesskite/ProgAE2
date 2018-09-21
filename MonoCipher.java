/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;
	

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		// create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)	{
			alphabet[i] = (char)('A' + i);
		}
		
		// removes duplicate letters in the cipher alphabet
		String alpha= new String (alphabet);
		String [] k=keyword.split("");
		
		for (int i=0; i<k.length;i++)
		 {
			alpha=alpha.replaceAll(k[i],"");
		 }
		
		// adds keyword to the beginning of cipher alphabet and flips it
		StringBuilder sb= new StringBuilder(alpha);
		String ci= keyword+sb.reverse();
		
		// create cipher array using created cipher alphabet
		cipher= new char [SIZE];
		for (int i=0; i<SIZE;i++)	{
			cipher[i]=(char)(ci.charAt(i));
			System.out.print(cipher[i]);
		}
		System.out.println();
		
		// create first part of cipher from keyword
		// create remainder of cipher from the remaining characters of the alphabet
		// print cipher array for testing and tutors
	}
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		for (int i=0; i<SIZE; i++) {
			// only encodes if character is A-Z (in caps)
			if (ch==alphabet[i])
				return cipher[i];
		}	
		// returns character without encoding if not A-Z (in caps)
		return ch;
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		for (int i=0; i<SIZE; i++) {
			// only decodes if character is A-Z (in caps)
			if (ch==cipher[i]) 
				return alphabet[i];
		}
		// returns character without encoding if not A-Z (in caps)
		return ch; 
	}
}
