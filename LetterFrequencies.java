/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)	{
			alphabet[i] = (char)('A' + i);
		}
		
		alphaCounts = new int [SIZE];
		for (int count=0; count<SIZE; count++)
			alphaCounts[count]=0;
		
		totChars=0;
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		int index=0;
		for (int i=0; i<SIZE; i++)
			if (ch==alphabet[i])	{
				index=i;
				alphaCounts[index]++;
			}
	}	
	
	/**
	 * Gets the total number of characters read
	 * @return the total characters read
	 */
	private int getTotChars()	
	{
		for (int i=0; i<alphaCounts.length; i++)	{
			totChars+= alphaCounts[i];
		}	
		return totChars;
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
    {
		int max=alphaCounts[0];
		for (int i=0; i<SIZE; i++)	{
			if (max<=alphaCounts[i])	{
				max=alphaCounts[i];
				maxCh=alphabet[i];
			}	
		}
		double maxValue=((double)max/(double)getTotChars()*100.0);
	    return maxValue;  
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{	
		// initialise column variables 
		String alpha, freq, freqP, avgFreq, diff="";
		String table="";
		
		// create title
		String title= String.format("%3s%5s%7s%9s%6s%n", "Letter", "Freq", "Freq%", "AvgFreq%", "Diff");
		
		// create 'most frequent letter'/max character message
		String.format("%1s", maxCh);
		String maxPC= String.format("%.1f", getMaxPC());
		String maxFreq="The most frequent letter is "+ maxCh+" at "+maxPC+"%";
		
		// format each column variable
		// calculate values for columns Freq% and Diff
		// concatenate all rows from A-Z into a table format
		for (int i=0; i<SIZE; i++)
		{
			alpha= String.format("%3s", alphabet[i]);
			freq=String.format ("%7d", alphaCounts[i]);
			freqP=String.format("%8.1f", (alphaCounts[i]/(double)totChars*100));
			avgFreq=String.format("%7.1f", avgCounts[i]);
			diff=String.format("%8.1f%n", ((alphaCounts[i]/(double)totChars)*100)-avgCounts[i]);
			
			table=table+alpha+freq+freqP+avgFreq+diff;
		} 
		
		// add title and message of 'most frequent letter' to table 
		table=title+table+maxFreq;
		
		return table;  // replace with your code
	}
}


 