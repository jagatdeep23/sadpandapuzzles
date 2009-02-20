package puzzle;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Puzzle stores the list of words and the location of them in a PuzzleCell matrix
 * @author Sad Panda Software
 * @version 1.0
 */
public class Puzzle {

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
	 * the two-dimensional array used to store arranged letters from PuzzleWords
	 */
	PuzzleCell[][] matrix;

	/**
	 * the word list of PuzzleWords that are in the Puzzle
	 */
	ArrayList<PuzzleWord> wordList;

		/**
		 * the number of words in the puzzle
		 */
	int numWords,
		/**
		 * the height and width of the puzzle
		 */
		arraySize;
	
	public Puzzle() {
		matrix = null;
		wordList = null;
		numWords = 0;
		arraySize = 0;
	}
	
	/**
	 * Generates a word search puzzle based on a list of words.
	 * @param wordList - a list of words
	 * @return a generated puzzle in the form of a word search
	 */
	public void genWordSearch(ArrayList<String> wordList) {
	    int length = generateDimension(wordList);
	    
	    System.out.println ("Puzzle is " + length + " square.");
	    
	    ArrayList<PuzzleWord> puzzleWords = new ArrayList<PuzzleWord>();
	    
	    boolean isValid;
	    
	    PuzzleCell[][] matrix = new PuzzleCell[length][length];
	    
	    //Fills the PuzzleCell matrix with default PuzzleCells
		for (int r = 0; r < length; r ++)
			for (int c = 0; c < length; c ++)
				matrix[r][c] = new PuzzleCell();
		
		//Adds words to the puzzle
	    for(String word: wordList) {
	        isValid = false;
	        while (!isValid) {
	            Direction dir = generateDirection();
	            System.out.println(word + "; " + dir.name() + " size: " + word.length());
	            int [] point = generatePosition(word.length(), length, length, dir);
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
	    
	    //Fills in the remaining cells with random characters
	    for (int r = 0; r < length; r ++)
			for (int c = 0; c < length; c ++)
				if (matrix[r][c].getCharacter() == '\0')
					matrix[r][c].addRandomChar();
	    
	    //assigns values to this puzzle object
	    this.arraySize = length;
	    this.numWords = puzzleWords.size();
	    this.matrix = matrix;
	    this.wordList =  puzzleWords;
	}
	
	/**
	 * Generates a crossword puzzle
	 * @param wordList
	 */
	public void genCrossword (ArrayList<String> wordList) {
		/**
		 * TODO
		 */
	}
	
	/**
	 * Adds and word and validates to ensure that it will fit into the grid
	 * @param word - puzzleword to be added.
	 * @param matrix - our current puzzle grid.
	 * @return boolean - Whether the add was a success or not.
	 */
	private boolean addAndValidate(PuzzleWord word, PuzzleCell[][] matrix){
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
	private Direction generateDirection() {
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
	private int[] generatePosition(int length, int colSize, int rowSize, Direction dir) {
		int[] point = new int[2];
		switch(dir){
		case N:
			point[0] = getNumberGenerator().nextInt(colSize);
			point[1] = length - 1 + getNumberGenerator().nextInt(rowSize - length);
			break;
		case NE:
			point[0] = getNumberGenerator().nextInt(colSize - length);
			point[1] = length - 1 + getNumberGenerator().nextInt(rowSize - length);
			break;
		case E:
			point[0] = getNumberGenerator().nextInt(colSize - length);
			point[1] = getNumberGenerator().nextInt(rowSize);
			break;
		case SE:
			point[0] = getNumberGenerator().nextInt(colSize - length);
			point[1] = getNumberGenerator().nextInt(rowSize - length);
			break;
		case S:
			point[0] = getNumberGenerator().nextInt(colSize);
			point[1] = getNumberGenerator().nextInt(rowSize - length);
			break;
		case SW:
			point[0] = length - 1 + getNumberGenerator().nextInt(colSize - length);
			point[1] = getNumberGenerator().nextInt(rowSize - length);
			break;
		case W:
			point[0] = length - 1 + getNumberGenerator().nextInt(colSize - length);
			point[1] = getNumberGenerator().nextInt(rowSize);
			break;
		case NW:
			point[0] = length - 1 + getNumberGenerator().nextInt(colSize - length);
			point[1] = length - 1 + getNumberGenerator().nextInt(rowSize - length);
			break;	
		}
		return (point);
	}
	
	/**
	 * Generates the dimension to be used in the word search matrix
	 * @param list
	 * @return an integer specifying the dimension to be used by the Puzzle
	 */
	private int generateDimension (ArrayList<String> list) {
		int sum = 0;
		int max = 0;
		for (String s : list) {
			sum += s.length();
			max = Math.max(max, s.length());
		}
		sum = (int)(Math.ceil(Math.sqrt(sum * 3)));
		if (sum < max) {
			sum = max * 3 / 2;
		}
		return (++sum);
	}
	
	/**
	 * Returns the size of the matrix.
	 * @return arraySize - the dimension of the matrix
	 */
	public int getArraySize() {
		return arraySize;
	}

	/**
	 * Return the puzzle's matrix.
	 * @return matrix - a 2D array of PuzzleCells
	 */
	public PuzzleCell[][] getMatrix() {
		return matrix;
	}

	/**
	 * Returns the number of words in the puzzle.
	 * @return numWords - number of words in the puzzle
	 */
	public int getNumWords() {
		return numWords;
	}

	/**
	 * Returns an array of puzzle words.
	 * @return wordList - a list of PuzzleWords
	 */
	public ArrayList<PuzzleWord> getWordList() {
		return wordList;
	}
	
	/**
	 * A Singleton object used by any instance of puzzle as a number generator
	 * @return a random number generator
	 */
	public Random getNumberGenerator () {
		if (gen == null) {
			gen = new Random();
		}
		return gen;
	}
	
	/**
	 * Gets the puzzle as a string
	 * @return s - Returns the puzzle as a string
	 */
	public String toString() {
		String s = "";
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				s += matrix[r][c] + " ";
			}
			s += "\n";
		}
		return s;
	}
}
