package puzzle;


/**
 * A PuzzleCell is used as a specialized container for a character used in the generated puzzle
 * @author Sad Panda Software
 * @version 1.0
 * 
 */
public class PuzzleCell {
	/**
	 * number of words associated with this PuzzleCell
	 */
	int numWords;
	/**
	 * the character associated with this PuzzleCell
	 */
	private char character;
	
	/**
	 * Default constructor. 
	 */
	public PuzzleCell () {
		numWords = 0;
		setCharacter('\0');
	}
	
	/**
	 * Attepmts to set the character value of this cell.
	 * @param c - the next character to be added.
	 * @return boolean - weither or not the add was successful
	 */
	public boolean add (char c) {
		if(c == getCharacter() || getCharacter() == '\0'){
			numWords++;
			setCharacter(c);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Adds a random character to the puzzleCell
	 */
	public void addRandomChar () {
		setCharacter(Character.toChars(97 + (int)(Math.random()*26))[0]);
	}
	
	/**
	 * If a puzzle cell contains more then one word, this decrements the number of words
	 * associated with this cell. If there is only one word, it decrements the number of words
	 * then removes the character from the puzzle cell.
	 */
	public void remove () {
	
		if (--numWords == 0) {
			setCharacter('\0');
			numWords--;
		}else{
			numWords--;
		}
	}
	
	/**
	 * Returns the contents of the puzzle cell as a string.
	 * @return string - Returns the contents of the puzzle cell as a string.
	 */
	public String toString () {
		return (getCharacter() + "");
	}

	/**
	 * Sets the char the puzzle cell contains.
	 * @param character - Sets the char the puzzle cell contains.
	 */
	public void setCharacter(char character) {
		this.character = character;
	}

	/**
	 * The character in the puzzle cell.
	 * @return character - The character in the puzzle cell.
	 */
	public char getCharacter() {
		return character;
	}
}
