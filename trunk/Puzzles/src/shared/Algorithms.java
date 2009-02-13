package shared;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import puzzle.Puzzle;
import puzzle.PuzzleCell;
import puzzle.PuzzleWord;


//Edited some typo's in the comments -Kyle
/**
 * Algorithms used in various places throughout the program
 * @author Sad Panda Software
 * @version 1.0
 */
public class Algorithms {
	/**
	 * Direction - Defines the direction in which a word is oriented. 
	 * @author Sad Panda Software
	 */
	public static enum Direction {
		/**
		 * North Direction
		 */
		N,
		
		/**
		 * North-East Direction
		 */
		NE,
		
		/**
		 * East Direction
		 */
		E,
		
		/**
		 * South-East Direction
		 */
		SE,
		
		/**
		 * South Direction
		 */
		S,
		
		/**
		 * South-West Direction
		 */
		SW,
		
		/**
		 * West Direction
		 */
		W,
		
		/**
		 * North-West Direction
		 */
		NW
	}
	
	/**
	 * A random number generator
	 */
	private static Random gen;
	
	/**
	 * prepares the random number generator for use
	 */
	public static void prepGenerator() {
		gen = new Random();
	}
	
	/**
	 * Generates a word search puzzle based on a list of words.
	 * @param wordList - a list of words
	 * @return a generated puzzle in the form of a word search
	 */
	public static Puzzle genWordSearch(ArrayList<String> wordList) {
	    int length = wordList.get(0).length() * 2;
		int colSize = length;
	    int rowSize = length;
	    
	    System.out.println ("Puzzle is " + length + " square.");
	    
	    ArrayList<PuzzleWord> puzzleWords = new ArrayList<PuzzleWord>();
	    
	    boolean isValid;
	    
	    PuzzleCell[][] matrix = generateMatrix(colSize, rowSize);
	    for(String word: wordList) {
	        isValid = false;
	        while (!isValid) {
	            Direction dir = generateDirection();
	            System.out.println(word + "; " + dir.name() + " size: " + word.length());
	            int [] point = generatePosition(word.length(),
	            		colSize,
	            		rowSize,
	            		dir);
	            PuzzleWord pWord = new PuzzleWord ();
	            System.out.println(point[0] + ", " + point[1]);
                pWord.setColumn(point[0]);
                pWord.setRow(point[1]);
                pWord.setDirection(dir);
                pWord.setWord(word);
                isValid = addAndValidate(pWord, matrix);
	            if (isValid){
	                puzzleWords.add(pWord);
	            }
	        }
	    }
	    for (int r = 0; r < colSize; r ++) {
			for (int c = 0; c < rowSize; c ++) {
				if (matrix[r][c].getCharacter() == '\0') {
					matrix[r][c].addRandomChar();
				}
			}
		}
	    
	    return (new Puzzle(puzzleWords, matrix));
	}
	
	/**
	 * Adds and word and validates to ensure that it will fit into the grid
	 * @param word - puzzleword to be added.
	 * @param matrix - our current puzzle grid.
	 * @return boolean - Whether the add was a success or not.
	 */
	public static boolean addAndValidate(PuzzleWord word, PuzzleCell[][] matrix){
		int dC = 0;
		int dR = 0;
		switch(word.getDirection()){
		case N:
			dR = -1;
			break;
		case NE:
			dR = -1;
			dC = 1;
			break;
		case E:
			dC = 1;
			break;
		case SE:
			dR = 1;
			dC = 1;
			break;
		case S:
			dR = 1;
			break;
		case SW:
			dR = 1;
			dC = -1;
			break;
		case W:
			dC = -1;
			break;
		case NW:
			dR = -1;
			dC = -1;
			break;	
		}
		int row = word.getRow();
		int col = word.getColumn();
		String w = word.getWord();
		for (int i = 0; i < w.length(); i++){
			System.out.println("Col " + col + ", Row " + row);
			PuzzleCell cell = matrix[col][row];
			char character = w.charAt(i);
			if(!cell.add(character)){
				for(int j = i-1; j >= 0; j--){
					row -= dR;
					col -= dC;
					matrix[col][row].remove();
				}
				return false;
			}
			row += dR;
			col += dC;
		}
		return (true);
		
	}
	
	/**
	 * Generates a random direction.
	 * @return Direction - any of the 7 directions a word can be oriented. 
	 */
	public static Direction generateDirection() {
		int num = (int)(8 * Math.random());
		return (Direction.values()[num]);
	}
	
	/**
	 * returns a valid start point for a word by length. Does not check intersections. 
	 * @param length - length of the word.
	 * @param colSize - number of columns.
	 * @param rowSize - number of rows.
	 * @return int[] - [0] is the x value, and [1] is the y value.
	 */
	public static int[] generatePosition(int length, int colSize, int rowSize, Direction dir) {
		int[] point = new int[2];
		switch(dir){
		case N:
			point[0] = gen.nextInt(colSize);
			point[1] = length - 1 + gen.nextInt(rowSize - length);
			break;
		case NE:
			point[0] = gen.nextInt(colSize - length);
			point[1] = length - 1 + gen.nextInt(rowSize - length);
			break;
		case E:
			point[0] = gen.nextInt(colSize - length);
			point[1] = gen.nextInt(rowSize);
			break;
		case SE:
			point[0] = gen.nextInt(colSize - length);
			point[1] = gen.nextInt(rowSize - length);
			break;
		case S:
			point[0] = gen.nextInt(colSize);
			point[1] = gen.nextInt(rowSize - length);
			break;
		case SW:
			point[0] = length - 1 + gen.nextInt(colSize - length);
			point[1] = gen.nextInt(rowSize - length);
			break;
		case W:
			point[0] = length - 1 + gen.nextInt(colSize - length);
			point[1] = gen.nextInt(rowSize);
			break;
		case NW:
			point[0] = length - 1 + gen.nextInt(colSize - length);
			point[1] = length - 1 + gen.nextInt(rowSize - length);
			break;	
		}
		return (point);
	}
	
	/**
	 * Generates an empty two-dimensional array used for generating Puzzles
	 * @param colSize - number of columns.
	 * @param rowSize - number of rows.
	 * @return puzzleCell[][] - this matrix will contain the puzzle.
	 */
	public static PuzzleCell[][] generateMatrix(int colSize, int rowSize) {
		PuzzleCell[][] matrix;
		matrix = new PuzzleCell[colSize][rowSize];
		
		for (int r = 0; r < colSize; r ++) {
			for (int c = 0; c < rowSize; c ++) {
				matrix[r][c] = new PuzzleCell();
			}
		}
		return (matrix);
	}
	
	public static String arrayToString (ArrayList<String> a) {
		String s = "";
		for (String word: a) {
			s += word + "\n";
		}
		return (s);
	}
	
	public static ArrayList<String> stringToArray (String s) {
		ArrayList<String> list = new ArrayList<String>();
		Scanner parse = new Scanner (s);
		parse.useDelimiter("\n");
		while (parse.hasNext()) {
			String next = parse.next();
			System.out.println (next);
			if (next.length() > 0){ 
				list.add(next);
			}
		}
		return (list);
	}
	
	
	public static class SortByLineLength implements java.util.Comparator<String> {

		public int compare(String one, String two) {
			return (two.length() - one.length());
		}
	}
}
