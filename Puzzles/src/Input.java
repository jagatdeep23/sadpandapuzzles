import java.util.*;
import java.io.*;
import javax.swing.*;

public class Input {
	private static JFileChooser chooser;

	private static File file;

	private static int status;

	private static ArrayList<String> words = new ArrayList<String>();

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

	private static void getWords(File input) throws IOException {
		FileReader fileReader = new FileReader(input);
		BufferedReader buffer = new BufferedReader(fileReader);
		String temp;

		while ((temp = buffer.readLine()) != null)
			words.add(temp);
		buffer.close();

		Collections.sort(words, new byLineLength());
	}

	public static void saveWords(ArrayList<String> list) throws IOException {
		if (file != null)
			save(list, file);
		else
			saveWordsAs(list);
	}

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

	public static void save(ArrayList<String> list, File location)
			throws IOException {
		FileWriter fileWriter = new FileWriter(location);
		BufferedWriter buffer = new BufferedWriter(fileWriter);

		for (String word : list)
			buffer.write(word + "\n");
		buffer.close();
	}

	public static class byLineLength implements java.util.Comparator<String> {

		public int compare(String one, String two) {
			return (two.length() - one.length());
		}
	}

}
