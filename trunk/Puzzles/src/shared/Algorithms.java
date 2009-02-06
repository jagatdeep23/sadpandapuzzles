package shared;

import java.util.ArrayList;
import java.util.Random;

import puzzle.Puzzle;
import puzzle.PuzzleCell;
import puzzle.PuzzleWord;


//Edited some typo's in the comments -Kyle

public class Algorithms {
	//Direction - Defines the direction in which a word is oriented. 
	public static enum Direction {N, NE, E, SE, S, SW, W, NW};
	private static Random gen;
	/**
	 * Generates a puzzle object, which is used to make crosswords and word searches.
	 * @param wordList - an array of words used in the puzzle;
	 * @return Puzzle - a puzzle object.
	 */
	public static void prepGenerator() {
		gen = new Random();
	}
	
	public static Puzzle genWordSearch(ArrayList<String> wordList) {
	    int length = wordList.get(0).length() * 3 / 2;
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
	            int [] point = generatePosition(word.length(), colSize, rowSize, dir);
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
	    
	    
	    Puzzle p = new Puzzle(puzzleWords, matrix);
	    return p;
	}
	
	/**
	 * 
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
		return true;
		
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
		return point;
	}
	
	/**
	 * 
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
		return matrix;
	}
}
