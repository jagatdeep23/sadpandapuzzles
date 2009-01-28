import java.util.*;
import java.io.*;

public class Test {
	
	public static void main(String[] args) throws IOException
	{
		
		ArrayList<String> words = Input.getFile();
		
		Iterator<String> iter = words.iterator();
		while(iter.hasNext())
		{
			String word = iter.next();
			System.out.println(word);
		}
		
		Input.saveWords(words);
	}
}
