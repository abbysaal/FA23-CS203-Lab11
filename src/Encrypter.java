import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Encrypter {

    private int shift;
    private String encrypted;

    /**
     * Default Constructor
     */
    public Encrypter() {
        this.shift = 1;
        this.encrypted = "";
    }

    /**
     * Non-default Constructor
     * @param s - custom shift amount
     */
    public Encrypter(int s) {
        this.shift = s;
        this.encrypted = "";
    }

    /**
     * Encrypts the content of a file and writes the result to another file.
     *
     * @param inputFilePath      the path to the file containing the text to be encrypted
     * @param encryptedFilePath the path to the file where the encrypted text will be written
     * @throws Exception if an error occurs while reading or writing the files
     */
    public void encrypt(String inputFilePath, String encryptedFilePath) throws Exception {
    	String message = readFile(inputFilePath);
    	
    	String newMessage = "";
    	
    	String alphabet1 = "abcdefghijklmnopqrstuvwxyz";
    	String alphabet2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	
    	for (int i = 0; i < message.length(); i++) {
    		
    		int position = 0;
    		
    		if (alphabet1.indexOf(message.charAt(i)) == -1 && alphabet2.indexOf(message.charAt(i)) == -1)
    		{
    			newMessage += message.charAt(i);
    		}
    		else if (message.charAt(i) == (message.toLowerCase()).charAt(i))
    		{
    			position = alphabet1.indexOf(message.charAt(i));
    			int key = (shift + position) % 26;
    			char newLetter = (char)alphabet1.charAt(key);
    			newMessage += newLetter;
    		}
    		else if (message.charAt(i) == (message.toUpperCase()).charAt(i))
    		{
    			position = alphabet2.indexOf(message.charAt(i));
    			int key = (shift + position) % 26;
    			char newLetter = (char)alphabet2.charAt(key);
    			newMessage += newLetter;
    		}
    		else
    		{
    			newMessage += message.charAt(i);
    		}
    	}
    	
    	writeFile(newMessage, encryptedFilePath);
    }

    /**
     * Decrypts the content of an encrypted file and writes the result to another file.
     *
     * @param messageFilePath    the path to the file containing the encrypted text
     * @param decryptedFilePath the path to the file where the decrypted text will be written
     * @throws Exception if an error occurs while reading or writing the files
     */
    public void decrypt(String messageFilePath, String decryptedFilePath) throws Exception {
    	String message = readFile(messageFilePath);
    	
    	String alphabet1 = "abcdefghijklmnopqrstuvwxyz";
    	String alphabet2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	
    	String newMessage = "";
    	
    	for (int i = 0; i < message.length(); i++)
    	{
    		int position = 0;
    		
    		if (alphabet1.indexOf(message.charAt(i)) == -1 && alphabet2.indexOf(message.charAt(i)) == -1)
    		{
    			newMessage += message.charAt(i);
    		}
    		else if (message.charAt(i) == (message.toLowerCase()).charAt(i))
    		{
    			position = alphabet1.indexOf(message.charAt(i));
    			int key = (position - shift) % 26;
    			
    			if (key < 0)
    			{
    				key += alphabet1.length();
    			}
    			
    			char newLetter = (char)alphabet1.charAt(key);
    			
    			newMessage += newLetter;
    		}
    		else if (message.charAt(i) == (message.toUpperCase()).charAt(i))
    		{
    			position = alphabet2.indexOf(message.charAt(i));
    			
    			int key = (position - shift) % 26;
    			
    			if (key < 0)
    			{
    				key += alphabet2.length();
    			}
    			
    			char newLetter = (char)alphabet2.charAt(key);
    			
    			newMessage += newLetter;
    		}
    		else
    		{
    			newMessage += message.charAt(i);
    		}
    	}
    	
    	writeFile(newMessage, decryptedFilePath);
    }

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param filePath the path to the file to be read
     * @return the content of the file as a string
     * @throws Exception if an error occurs while reading the file
     */
    private static String readFile(String filePath) throws Exception {
        String message = "";

        try(Scanner fileScanner = new Scanner(Paths.get(filePath)))
    	{
    		while(fileScanner.hasNextLine()) {
    			message += ("\n"+ fileScanner.nextLine());
    		}
    		fileScanner.close();
    	}catch (Exception e)
    	{
    		System.out.println("Error: " + e.toString());
    	}
        
        return message;
    }

    /**
     * Writes data to a file.
     *
     * @param data     the data to be written to the file
     * @param filePath the path to the file where the data will be written
     */
    private static void writeFile(String data, String filePath) {
    	
    	try(PrintWriter output = new PrintWriter(filePath)){
    		output.println(data);
    		
    		output.close();
    	}catch (Exception e)
    	{
    		System.out.println("Error: " + e.toString());
    	}
    }

    /**
     * Returns a string representation of the encrypted text.
     *
     * @return the encrypted text
     */
    @Override
    public String toString() {
        return encrypted;
    }
}
