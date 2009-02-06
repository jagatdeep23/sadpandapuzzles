import java.util.ArrayList;

public class Puzzle {
	PuzzleCell[][] matrix;

	ArrayList<PuzzleWord> wordList;

	int numWords, arraySize;

	/**
	 * 
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
	 * 
	 * @return arraySize - the dimension of the matrix
	 */
	public int getArraySize() {
		return arraySize;
	}

	/**
	 * 
	 * @return matrix - a 2D array of PuzzleCells
	 */
	public PuzzleCell[][] getMatrix() {
		return matrix;
	}

	/**
	 * 
	 * @return numWords - number of words in the puzzle
	 */
	public int getNumWords() {
		return numWords;
	}

	/**
	 * 
	 * @return wordList - a list of PuzzleWords
	 */
	public ArrayList<PuzzleWord> getWordList() {
		return wordList;
	}

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
