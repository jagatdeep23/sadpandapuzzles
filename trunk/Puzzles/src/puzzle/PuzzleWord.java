package puzzle;

import puzzle.Direction;

/**
 * A word that is used in a Puzzle
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class PuzzleWord {
  /** the word string */
  private String    word;
  
  /** the row of the location of the word */
  private int       row;
  
  /** the column of the location of the word */
  private int       col;
  
  /** the direction associated with the word */
  private Direction dir;
  
  /** Default constructor */
  public PuzzleWord () {
    row = 0;
    col = 0;
    dir = Direction.EAST;
    word = "";
  }
  
  /**
   * Creates a new PuzzleWord
   * @param row the row
   * @param col the column
   * @param dir the direction
   * @param word the word
   */
  public PuzzleWord (int row, int col, Direction dir, String word) {
    setColumn(col);
    setRow(row);
    setDirection(dir);
    setWord(word);
  }
  
  /**
   * Creates a new PuzzleWord
   * @param point the point corresponding the row and column
   * @param dir the direction
   * @param word the word
   */
  public PuzzleWord (int[] point, Direction dir, String word) {
    setColumn(point[0]);
    setRow(point[1]);
    setDirection(dir);
    setWord(word);
  }
  
  /**
   * Gets the number of columns
   * 
   * @return col the number of columns
   */
  public int getColumn () {
    return col;
  }
  
  /**
   * Returns the direction the word is traveling.
   * 
   * @return dir returns the direction the word is traveling.
   */
  public Direction getDirection () {
    return dir;
  }

  /**
   * Returns the number of rows.
   * 
   * @return row - returns the number of rows.
   */
  public int getRow () {
    return row;
  }

  /**
   * Returns the word as a string.
   * 
   * @return word the word as a string.
   */
  public String getWord () {
    return word;
  }

  /**
   * Sets the number of columns
   * 
   * @param col
   *          sets the number of columns
   */
  public void setColumn (int col) {
    this.col = col;
  }

  /**
   * Sets the direction the word is traveling.
   * 
   * @param dir
   *          Sets the direction the word is traveling.
   */
  public void setDirection (Direction dir) {
    this.dir = dir;
  }

  /**
   * Sets the row and column to the passed point
   * @param point the ordinal pair corresponding to a row and column
   */
  public void setPoint (int[] point) {
    col = point[0];
    row = point[1];
  }

  /**
   * Sets the number of rows.
   * 
   * @param row
   *          Sets the number of rows.
   */
  public void setRow (int row) {
    this.row = row;
  }
  
  /**
   * Sets the word.
   * 
   * @param word
   *          Sets the word.
   */
  public void setWord (String word) {
    this.word = word;
  }
  
}
