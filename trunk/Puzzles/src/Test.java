import io.FileIO;

import java.util.*;
import java.io.*;

import puzzle.Puzzle;
import shared.Algorithms;


public class Test {
	
	public static void main(String[] args) throws IOException
	{
		
		ArrayList<String> words = FileIO.getFile();
		
		Algorithms.prepGenerator();
		
		Puzzle P = Algorithms.genWordSearch(words);
		
		System.out.println(P.toString());
		System.out.println();
		
		for (String word : words) {
			System.out.println (word);
		}
	}
}
