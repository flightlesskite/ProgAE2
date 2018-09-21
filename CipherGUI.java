import java.awt.*;
import javax.swing.*;

import jdk.nashorn.internal.parser.Token;

import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;
	
	
	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{

		String keyword=keyField.getText();
		String fileName=messageField.getText();
	
		if (getKeyword(keyword)==true && processFileName(fileName)==true)	{
			boolean vigenere=true;
			
			if (e.getSource()==monoButton)	{
			vigenere=false;
			}
			
			if (e.getSource()==vigenereButton)	{
			vigenere=true;
			}	

			processFile(vigenere);				}	
		
		}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword(String keyword)
	{
		if (keyword.isEmpty()) 	{
			JOptionPane.showMessageDialog(null, "Please enter a keyword to proceed.", "Warning", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			messageField.setText("");
			return false;
		}
		String[] key=keyword.split("");
		Arrays.sort(key);
		for (int i=1;i<key.length; i++)
			if (key[i].equals(key[i-1]) || Character.isLowerCase(keyword.charAt(i))) {
				JOptionPane.showMessageDialog(null, "Invalid keyword: Must be CAPS and no duplicate letters.", "Warning", JOptionPane.ERROR_MESSAGE);
				keyField.setText("");
				messageField.setText("");
				return false;	
			}
		return true;  // replace with your code
	}
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName(String fileName)
	{
		if (fileName.isEmpty()) 	{
			JOptionPane.showMessageDialog(null, "Please enter a file name to proceed.", "Warning", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			messageField.setText("");
			return false;
		}
		
		char lastChar = messageField.getText().charAt(messageField.getText().length() - 1);
		if (lastChar== 'P'|| lastChar=='C'|| lastChar=='D')	{
			return true; 
		}	
		else
		JOptionPane.showMessageDialog(null, "Invalid file name. Must end in P or C.", "Warning", JOptionPane.ERROR_MESSAGE);
		keyField.setText("");
		messageField.setText("");
		return false;
		 
	}
	
	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{	
		String fileName= messageField.getText();
		FileReader reader= null;
		FileWriter writer= null;
		char lastChar = fileName.charAt(fileName.length() - 1);
		
		if (vigenere==false)	{
			processMono(fileName, reader, writer, lastChar);
			
		}	
		
		if (vigenere==true)
			processVigenere(fileName, reader, writer, lastChar);
			
		return true;  
	}
	
	
	/** 
	 * processing MonoCipher
	 * creates an instance of MonoCipher class
	 * @param file name, reader, writer and last character
	 */
	private void processMono (String fileName, FileReader reader, FileWriter writer, char lastChar) {
		
		mcipher=new MonoCipher(keyField.getText());
		try {
			try {
				reader= new FileReader (fileName+".txt");
				
				// processes encoding if file name ends with the character 'P'
				// produces output file of encoded message filename+"C"
				// produces output file of frequency table
				if (lastChar== 'P')	{
					
				writer= new FileWriter (fileName.substring(0,fileName.length()-1)+"C.txt");
				LetterFrequencies lf= new LetterFrequencies();
				
				char c=' ';
				boolean done= false;
				
				while (!done)	{
					int next= reader.read();
					if (next==-1)
						done=true;
					else {
						c=(char) next;
						writer.write(mcipher.encode(c));
						lf.addChar(mcipher.encode(c));
					}
				}
				writer.close();
				writeFreq(lf.getReport());
				}
				
				// processes decoding if file name ends with the character 'C'
				// produces output file of decoded message filename+"D"
				// produces output file of frequency table
				if (lastChar== 'C')	{
					
				writer= new FileWriter (fileName.substring(0,fileName.length()-1)+"D.txt");
				LetterFrequencies lf= new LetterFrequencies();
				
				char c=' ';
				boolean done= false;
				
				while (!done)	{
					int next= reader.read();
					if (next==-1)
						done=true;
					else {
						c=(char) next;
						writer.write(mcipher.decode(c));
						lf.addChar(mcipher.decode(c));
					}
				}
				writer.close();
				writeFreq(lf.getReport());
				System.exit(0);
				}
			}
			
			finally {
				if (reader != null) reader.close();
			}
		}
			
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "The File does not exist. Please try again.", "Warning", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			messageField.setText("");
		}
	}
	
	private void processVigenere (String fileName, FileReader reader, FileWriter writer, char lastChar)	{
		
		vcipher=new VCipher(keyField.getText());
		try {
			try {
				reader= new FileReader (fileName+".txt");
				
				// processes encoding if file name ends with the character 'P'
				// produces output file of encoded message filename+"C"
				// produces output file of frequency table
				if (lastChar== 'P')	{
					
				writer= new FileWriter (fileName.substring(0,fileName.length()-1)+"C.txt");
				LetterFrequencies lf= new LetterFrequencies();
				
				char c=' ';
				boolean done= false;
				int counter=0;
				
				while (!done)	{
					int next= reader.read();
					if (next==-1)
						done=true;
					else if (next<'A' || next>'Z') {
						writer.write(next);
						}
					else	 {
						c=(char) next;
						writer.write(vcipher.encode(c, counter));
						lf.addChar(vcipher.encode(c, counter));
						counter++;
					}
				}
				writer.close();
				writeFreq(lf.getReport());
				}
			
			
			// processes decoding if file name ends with the character 'C'
			// produces output file of decoded message filename+"D"
			// produces output file of frequency table
			if (lastChar== 'C')	{
				
			writer= new FileWriter (fileName.substring(0,fileName.length()-1)+"D.txt");
			LetterFrequencies lf= new LetterFrequencies();
			
			char c=' ';
			boolean done= false;
			int counter=0;
			
			while (!done)	{
				int next= reader.read();
				if (next==-1)
					done=true;
				else if (next<'A' || next>'Z') {
					writer.write(next);
					}
				else	 {
					c=(char) next;
					writer.write(vcipher.decode(c, counter));
					lf.addChar(vcipher.decode(c, counter));
					counter++;
				}
			}
			writer.close();
			writeFreq(lf.getReport());
			System.exit(0);
			}
		}
			
			finally {
				if (reader != null) reader.close();
			}
		}
			
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The File does not exist. Please try again.", "Warning", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			messageField.setText("");
		}
	}
	
	
	/** 
	 * processes frequency report
	 * method will be called in processMono 
	 * @param report generated from LetterFrequencies clasP
	 */
	private void writeFreq (String report)	{
		
		String fileName= messageField.getText();
		fileName=fileName.substring(0,fileName.length()-1)+"F.txt";
		
		try {
			
			FileWriter fw= new FileWriter(fileName);
			fw.write(report);
			fw.close();
		}
		
		catch (IOException e)	{
			JOptionPane.showMessageDialog(null, "Unable to produce frequency table", "Warning", JOptionPane.ERROR_MESSAGE);
		}	
		
	}
}
