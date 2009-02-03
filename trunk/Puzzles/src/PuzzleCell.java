
public class PuzzleCell {
	int numWords;
	char character;
	
	public PuzzleCell () {
		numWords = 0;
		character = '\0';
	}
	
	public boolean add (char c) {
		if(c == character || character == '\0'){
			numWords++;
			character = c;
			return true;
		}
		return false;
		
	}
	
	public void addRandomChar () {
		character = Character.toChars(97 + (int)(Math.random()*26))[0];
	}
	
	public void remove () {
	
		if (--numWords == 0) {
			character = '\0';
			numWords--;
		}else{
			numWords--;
		}
	}
	
	public String toString () {
		return (character + "");
	}
}
