import java.util.ArrayList;

//Edited some typo's in the comments -Kyle

public class Constants {
	//Direction - Defines the direction in which a word is oriented. 
	public static enum Direction {N, NE, E, SE, S, SW, W, NW};
	
	/**
	 * Generates a puzzle object, which is used to make crosswords and word searches.
	 * @param wordList - an array of words used in the puzzle;
	 * @return Puzzle - a puzzle object.
	 */
	public static Puzzle genWordSearch(ArrayList<String> wordList) {
	    int length = wordList.get(0).length()*3/2;
		int colSize = length;
	    int rowSize = length;
	    int numWords = wordList.size();
	    ArrayList<PuzzleWord> puzzleWords = new ArrayList<PuzzleWord>();
	    boolean isValid;
	    
	    PuzzleCell[][] matrix = generateMatrix(colSize, rowSize);
	    for(String word: wordList) {
	        isValid = false;
	        while (!isValid) {
	            Direction dir = generateDirection();
	            int [] point = generatePosition(word.length(), colSize, rowSize);
	            PuzzleWord pWord = new PuzzleWord ();
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
		int i = 0;
		while(i < w.length()){
			if(!matrix[col][row].add(w.charAt(i))){
				for(int j = i; j > 0; j--){
					row -= dR;
					col -= dC;
					matrix[col][row].remove();
				}
				return false;
			}
			i++;
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
	public static int[] generatePosition(int length, int colSize, int rowSize) {
		int col = colSize - length;
		int row = rowSize - length;
		int[] point = new int[2];
		point[0] = (int)(col * Math.random());
		point[1] = (int)(row * Math.random());
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
