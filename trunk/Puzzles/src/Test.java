import java.util.*;
import java.io.*;

public class Test {
	
	public static void main(String[] args) throws IOException
	{
		
		ArrayList<String> words = Input.getFile();
		
		Algorithms.prepGenerator();
		Puzzle P = Algorithms.genWordSearch(words);
		
		System.out.println(P.toString());
		
		System.out.println();
		
		for (String word : words) {		//Josh, Java has a nifty for-each loop that 
			System.out.println (word);  //eliminates the need for the Iterator object.
		}								//In fact, the for-each loop only works with
										//things that implement Iterable
		//Input.saveWordsAs(words);
		
		//System.out.print("RAWR");
	}
}
