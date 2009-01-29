import java.util.*;
import java.io.*;

public class Test {
	
	public static void main(String[] args) throws IOException
	{
		
		ArrayList<String> words = Input.getFile();
		
		// Iterator<String> iter = words.iterator();
		// while(iter.hasNext())
		// {
		//  	String word = iter.next();
		//  	System.out.println(word);
		// }
		for (String word : words) {		//Josh, Java has a nifty for-each loop that 
			System.out.println (word);  //eliminates the need for the Iterator object.
		}								//In fact, the for-each loop only works with
										//things that implement Iterable
		Input.saveWordsAs(words);
	}
}
