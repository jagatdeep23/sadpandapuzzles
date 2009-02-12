package io;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * This class contains all of the File Input and Output methods.
 * @author Sad Panda Software
 * @version 1.0
 */
public class FileIO {
	private static JFileChooser chooser;

	private static File file;

	private static int status;

	private static ArrayList<String> words = new ArrayList<String>();

	/**
	 * Returns a list of words sorted by length (longest first)
	 * 
	 * @return  ArrayList<String> - A list of words
	 */
	public static ArrayList<String> getFile() {
		chooser = new JFileChooser();
		status = chooser.showOpenDialog(null);

		if (status == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			System.out.println("File selected to open: " + file.getName());
			System.out.println("Full path name: " + file.getAbsolutePath());
		}

		try {
			getWords(file);
		} catch (IOException e) {
			System.out.println("Error: IO Exception was thrown:" + e);
		}

		return words;
	}

	/**
	 * Processes a list of wrds from a file.
	 * 
	 * @param input a File
	 * @throws IOException
	 */
	private static void getWords(File input) throws IOException {
		FileReader fileReader = new FileReader(input);
		BufferedReader buffer = new BufferedReader(fileReader);
		String temp;

		while ((temp = buffer.readLine()) != null)
			words.add(temp);
		buffer.close();

		Collections.sort(words, new byLineLength());
	}

	/**
	 * Will call the appropriate save function based on whether or not a file has
	 * been associated yet.  If one has not, a new file will be created.
	 * 
	 * @param list ArrayList<String>
	 * @throws IOException
	 */
	public static void saveWords(ArrayList<String> list) throws IOException {
		if (file != null)
			save(list, file);
		else
			saveWordsAs(list);
	}
	
	/**
	 * Creates a new file and calls the save function to save the list of words to the
	 * new file.
	 * 
	 * @param list ArrayList<String>
	 * @throws IOException
	 */
	public static void saveWordsAs(ArrayList<String> list) throws IOException {
		File newFile = new File("empty");
		chooser = new JFileChooser();
		status = chooser.showSaveDialog(null);

		if (status == JFileChooser.APPROVE_OPTION) {
			newFile = chooser.getSelectedFile();
			System.out.println("File chosen to save to: " + newFile.getName());
			System.out.println("Full path to file: " + newFile.getAbsolutePath());
		}
		save(list, newFile);
	}

	/**
	 * Will perform the actual save of the word list to the location provided.
	 * 
	 * @param list ArrayList<String>
	 * @param location Location of file
	 * @throws IOException
	 */
	public static void save(ArrayList<String> list, File location)
			throws IOException {
		FileWriter fileWriter = new FileWriter(location);
		BufferedWriter buffer = new BufferedWriter(fileWriter);

		for (String word : list)
			buffer.write(word + "\n");
		buffer.close();
	}

	/**
	 * This class performs the comparison of words based on word length.
	 * @author Sad Panda Software
	 * @version 1.0
	 */
	public static class byLineLength implements java.util.Comparator<String> {

		public int compare(String one, String two) {
			return (two.length() - one.length());
		}
	}

}