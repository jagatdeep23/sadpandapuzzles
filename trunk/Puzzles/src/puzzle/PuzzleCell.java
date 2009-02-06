package puzzle;

public class PuzzleCell {
	int numWords;
	private char character;
	
	public PuzzleCell () {
		numWords = 0;
		setCharacter('\0');
	}
	
	public boolean add (char c) {
		if(c == getCharacter() || getCharacter() == '\0'){
			numWords++;
			setCharacter(c);
			return true;
		}
		return false;
		
	}
	
	public void addRandomChar () {
		setCharacter(Character.toChars(97 + (int)(Math.random()*26))[0]);
	}
	
	public void remove () {
	
		if (--numWords == 0) {
			setCharacter('\0');
			numWords--;
		}else{
			numWords--;
		}
	}
	
	public String toString () {
		return (getCharacter() + "");
	}

	/**
	 * @param character the character to set
	 */
	public void setCharacter(char character) {
		this.character = character;
	}

	/**
	 * @return character - the character
	 */
	public char getCharacter() {
		return character;
	}
}
