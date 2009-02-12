package puzzle;
import java.util.ArrayList;

/**
 * A Puzzle stores the list of words and the location of them in a PuzzleCell matrix
 * @author Sad Panda Software
 * @version 1.0
 */
public class Puzzle {

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

	/**
	 * The constructor for declaring a Puzzle Object
	 * @param list -
	 *            list of words that will be in the puzzle
	 * @param matrix -
	 *            a matrix that holds the actual puzzle
	 */
	public Puzzle(ArrayList<PuzzleWord> list, PuzzleCell[][] matrix) {
		wordList = list;
		numWords = list.size();

		/*
		 * Kyle here, Added these two lines to actually create a matrix in
		 * puzzle for us to move values into. arraySize =
		 * list.get(0).getWord().length()*3/2; this.matrix = new
		 * PuzzleCell[arraySize][arraySize];
		 */
		arraySize = list.get(0).getWord().length() * 3 / 2;
		this.matrix = new PuzzleCell[arraySize][arraySize];

		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				this.matrix[r][c] = matrix[r][c];
			}
		}
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
	 * Gets the puzzle as a string
	 * @return s - Returns the puzzle as a string
	 */
	public String toString() {
		String s = "";
		for (int r = 0; r < matrix.length; r++) {
			s += "\n";
			for (int c = 0; c < matrix[0].length; c++) {
				s += matrix[r][c] + " ";
			}
		}
		return s;
	}
}
