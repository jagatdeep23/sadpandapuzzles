package puzzle;

/**
 * A word that is used in a Puzzle
 * @author Sad Panda Software
 * @version 1.0
 */
public class PuzzleWord {
	/**
	 * the word string
	 */
	String word;
	/**
	 * the row of the location of the word
	 */
	int row,
	/**
	 * the column of the location of the word
	 */
		col;
	/**
	 * the direction associated with the word
	 */
	Puzzle.Direction dir;
	
	/**
	 * Default constructor.
	 */
	public PuzzleWord()
	{
		row = 0;
		col = 0;
		dir = Puzzle.Direction.E;
		word = "";
	}
	
	/**
	 * Gets the number of columns
	 * @return col - the number of columns
	 */
	public int getColumn() {
		return col;
	}
	
	/**
	 * Sets the number of columns
	 * @param col - sets the number of columns
	 */
	public void setColumn(int col) {
		this.col = col;
	}
	
	/**
	 * Returns the direction the word is traveling.
	 * @return dir - returns the direction the word is traveling.
	 */
	public Puzzle.Direction getDirection() {
		return dir;
	}
	
	/**
	 * Sets the direction the word is traveling.
	 * @param dir - Sets the direction the word is traveling.
	 */
	public void setDirection(Puzzle.Direction dir) {
		this.dir = dir;
	}
	
	/**
	 * Returns the number of rows.
	 * @return row - returns the number of rows.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Sets the number of rows.
	 * @param row - Sets the number of rows.
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Returns the word as a string.
	 * @return word - Returns the word as a string.
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Sets the word.
	 * @param word - Sets the word.
	 */
	public void setWord(String word) {
		this.word = word;
	}
	
}
