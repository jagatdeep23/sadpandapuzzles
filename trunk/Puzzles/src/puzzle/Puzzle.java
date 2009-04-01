package puzzle;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * The Puzzle interface used throughout the program. This includes prototyped
 * methods that are defined in classes that implement this Puzzle interface.
 * 
 * @author Sad Panda Software
 * @version 2.0
 */
public interface Puzzle {
  
  /**
   * The list of all possible directions
   * 
   * @author Sad Panda Software
   * @version 2.0
   */
  public static enum Direction {
    /** East */
    EAST,
    /** South */
    SOUTH,
    /** West */
    WEST,
    /** North */
    NORTH,
    /** Northeast */
    NORTHEAST,
    /** Southeast */
    SOUTHEAST,
    /** Southwest */
    SOUTHWEST,
    /** Northwest */
    NORTHWEST
  };
  
  /**
   * adds a word to the puzzle
   * 
   * @param word
   *          a PuzzleWord to add to the puzzle
   * @return true if the word was added, false if the word was not added
   */
  public boolean addAndValidate (PuzzleWord word);

  /**
   * draws a puzzle
   * 
   * @param g
   *          the graphics to draw to
   */
  public void draw (Graphics g);

  /** generates a puzzle */
  public void generate ();
  
  /**
   * gets the matrix of the puzzle
   * 
   * @return the matrix of PuzzleCells
   */
  public PuzzleCell [][] getMatrix ();

  /**
   * gets the matrix height of the puzzle
   * 
   * @return matrix height
   */
  public int getMatrixHeight ();
  
  /**
   * gets the matrix width of the puzzle
   * 
   * @return matrix width
   */
  public int getMatrixWidth ();

  /**
   * gets the number of words in the puzzle
   * 
   * @return the number of words in the puzzle
   */
  public int getNumWords ();

  /**
   * gets the list of words in the puzzle
   * 
   * @return the list of words in the puzzle
   */
  public ArrayList <PuzzleWord> getWordList ();

  /**
   * sets the list of available words to the puzzles
   * 
   * @param list
   *          a list of words
   */
  public void setList (ArrayList <String> list);

  /**
   * set the matrix of PuzzleCells
   * 
   * @param matrix
   *          the new matrix
   */
  public void setMatrix (PuzzleCell [][] matrix);

  /**
   * sets the matrix height
   * 
   * @param i
   *          new height
   */
  public void setMatrixHeight (int i);
  
  /**
   * sets the number of words
   * 
   * @param i
   *          new number of words
   */
  public void setNumWords (int i);

  /**
   * set the matrix width
   * 
   * @param i
   *          new width
   */
  public void setMatrixWidth (int i);
  
  /**
   * sets the word list
   * 
   * @param words
   *          list of words
   */
  public void setWordList (ArrayList <PuzzleWord> words);
  
}
