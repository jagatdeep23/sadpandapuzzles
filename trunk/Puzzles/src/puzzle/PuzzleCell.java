package puzzle;

import java.util.ArrayList;

import puzzle.Puzzle.Direction;

/**
 * A PuzzleCell is used as a specialized container for a character used in the
 * generated puzzle
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class PuzzleCell {
  /**
   * number of words associated with this PuzzleCell
   */
  private int                   numWords;
  /**
   * the character associated with this PuzzleCell
   */
  private char                  character;
  
  /**
   * the list of directions associated with this Puzzle Cell
   */
  private ArrayList <Direction> dirs;
  
  /**
   * Default constructor.
   */
  public PuzzleCell () {
    numWords = 0;
    setCharacter ('\0');
    dirs = new ArrayList <Direction> ();
  }
  
  /**
   * Attempts to set the character value of this cell.
   * 
   * @param c
   *          the next character to be added.
   * @return boolean whether or not the add was successful
   */
  public boolean add (char c) {
    if (c == character || character == '\0') {
      numWords++;
      setCharacter (c);
      return true;
    }
    return false;
  }
  
  /**
   * Attempts to set the character value of this cell.
   * 
   * @param c
   *          the next character to be added.
   * @param dir
   *          the direction of the word to add.
   * @return boolean whether or not the add was successful
   */
  public boolean add (char c, Direction dir) {
    if (c == character || character == '\0') {
      numWords++;
      setCharacter (c);
      dirs.add (dir);
      return true;
    }
    return false;
  }
  
  /**
   * Adds a random character to the puzzleCell
   */
  public void addRandomChar () {
    setCharacter (Character.toChars (97 + (int) (Math.random () * 26))[0]);
  }
  
  /**
   * If a puzzle cell contains more then one word, this decrements the number of
   * words associated with this cell. If there is only one word, it decrements
   * the number of words then removes the character from the puzzle cell.
   */
  public void remove () {
    if (--numWords == 0) {
      setCharacter ('\0');
    }
  }
  
  /**
   * If a puzzle cell contains more then one word, this decrements the number of
   * words associated with this cell. If there is only one word, it decrements
   * the number of words then removes the character from the puzzle cell. It
   * will also remove the associated direction
   * 
   * @param dir
   *          direction to remove
   */
  public void remove (Direction dir) {
    dirs.remove (dir);
    if (--numWords == 0) {
      dirs.clear ();
      setCharacter ('\0');
    }
  }
  
  /**
   * Sets the char the puzzle cell contains.
   * 
   * @param character -
   *          Sets the char the puzzle cell contains.
   */
  public void setCharacter (char character) {
    this.character = character;
  }
  
  public void setDirs (ArrayList <Direction> dirs) {
    this.dirs = dirs;
  }
  
  /**
   * sets the number of words associated with the PuzzleCell
   * 
   * @param i
   *          number of words to set
   */
  public void setNumWords (int i) {
    numWords = i;
  }
  
  /**
   * gets the number of words associated with the PuzzleCell
   * 
   * @return number of words
   */
  public int getNumWords () {
    return numWords;
  }
  
  /**
   * checks to see if any directions are associated with the PuzzleCell
   * 
   * @return true if the PuzzleCell has any direction; false otherwise
   */
  public boolean hasDirection () {
    return (!hasNoDirection ());
  }
  
  /**
   * checks to see if the passed direction is in PuzzleCell
   * 
   * @param dir
   *          a direction
   * @return true if the PuzzleCell has the direction; false otherwise
   */
  public boolean hasDirection (Direction dir) {
    return (dirs.contains (dir));
  }
  
  /**
   * checks to see if no directions are associated with the PuzzleCell
   * 
   * @return true if the PuzzleCell has no direction; false otherwise
   */
  public boolean hasNoDirection () {
    return (dirs.isEmpty ());
  }
  
  /**
   * checks to see if the PuzzleCell has any character
   * 
   * @return true if the PuzzleCell has a character; false otherwise
   */
  public boolean hasCharacter () {
    return (character != '\0');
  }
  
  /**
   * checks to see if the PuzzleCell has a specified character
   * 
   * @param c
   *          a character
   * @return true if the PuzzleCell has <tt>c</tt> as the character
   */
  public boolean hasCharacter (char c) {
    return (character == c);
  }
  
  /**
   * checks to see if the PuzzleCell is empty
   * 
   * @return true if the PuzzleCell is empty; false otherwise
   */
  public boolean isEmpty () {
    return (character == '\0');
  }
  
  public ArrayList <Direction> getDirList () {
    return dirs;
  }
  
  /**
   * Returns the contents of the puzzle cell as a string.
   * 
   * @return contents of the puzzle cell as a string.
   */
  public String toString () {
    if (isEmpty ())
      return "?";
    return (character + "").toUpperCase ();
  }
}
