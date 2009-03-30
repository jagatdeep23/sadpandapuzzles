package puzzle;

import java.util.ArrayList;

import puzzle.Puzzle.Direction;


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
   * the list of directions associated with this Puzzle Cell
   */
  private ArrayList<Direction> dirs;
  
  /**
   * Default constructor. 
   */
  public PuzzleCell () {
    numWords = 0;
    setCharacter('\0');
    dirs = new ArrayList<Direction>();
  }
  
  /**
   * Attempts to set the character value of this cell.
   * @param c - the next character to be added.
   * @return boolean - whether or not the add was successful
   */
  public boolean add (char c) {
    if(c == getCharacter() || getCharacter() == '\0'){
      numWords++;
      setCharacter(c);
      return true;
    }
    return false;
    
  }
  
  public boolean add (char c, Direction dir) {
    if(c == getCharacter() || getCharacter() == '\0'){
      numWords++;
      setCharacter(c);
      dirs.add (dir);
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
    }
  }
  
  public void remove (Direction dir) {
    dirs.remove (dir);
    if (--numWords == 0) {
      dirs.clear ();
      setCharacter('\0');
    }
  }
  
  /**
   * Returns the contents of the puzzle cell as a string.
   * @return string - Returns the contents of the puzzle cell as a string.
   */
  public String toString () {
    return ((getCharacter() + "").toUpperCase());
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
  
  public int getNumWords() {
    return numWords;
  }
  
  public void setNumWords(int i) {
    numWords = i;
  }
  
  public boolean hasDirection (Direction dir) {
    return (dirs.contains (dir));
  }
  
  public boolean hasNoDirection () {
    return (dirs.isEmpty ());
  }
  
  public boolean hasDirection () {
    return (!hasNoDirection());
  }
  
  public boolean hasCharacter () {
    return (character != '\0');
  }
  
  public boolean hasCharacter (char c) {
    return (character == c);
  }
  
  public boolean isEmpty () {
    return (character == '\0');
  }
}
