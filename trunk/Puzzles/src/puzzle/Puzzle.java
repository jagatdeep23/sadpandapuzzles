package puzzle;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Puzzle interface used throughout the program. This includes prototyped
 * methods that are defined in classes that implement this Puzzle interface.
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public abstract class Puzzle {
  
  /** A random number generator */
  private static Random          gen;
  
  /** the word list of PuzzleWords that are in the Puzzle */
  private ArrayList <PuzzleWord> wordList;
  
  /** the list of words to be added to the puzzle */
  private ArrayList <String>     words;
  
  /** the number of words in the puzzle */
  private int                    numWords;
  
  /** the height of the puzzle */
  private int                    height;
  
  /** the width of the puzzle */
  private int                    width;
  
  /** the two-dimensional array used to store arranged letters from PuzzleWords */
  private PuzzleCell [][]        matrix;
  
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
  protected abstract boolean addAndValidate (PuzzleWord word);
  
  /**
   * draws a puzzle
   * 
   * @param g
   *          the graphics to draw to
   */
  public abstract void draw (Graphics g);
  
  /** generates a puzzle */
  public abstract void generate ();
  
  /**
   * Generates the dimension to be used in the word search matrix
   * 
   * @param list
   * @return an integer specifying the dimension to be used by the Puzzle
   */
  protected abstract int generateDimension (ArrayList <String> list);
  
  /**
   * returns a valid start point for a word by length. Does not check
   * intersections.
   * 
   * @param length
   *          length of the word.
   * @param colSize
   *          number of columns.
   * @param rowSize
   *          number of rows.
   * @return int[] - [0] is the x value, and [1] is the y value.
   */
  protected abstract int [] generatePosition (int length, int colSize, int rowSize, Direction dir);
  
  /**
   * adds a word to the list associated
   * 
   * @param word
   *          a word
   */
  public void addWordToList (String word) {
    words.add (word);
  }
  
  /**
   * removes a word from the list associated
   * 
   * @param word
   *          a word
   */
  public void removeWordFromList (String word) {
    words.remove (word);
  }
  
  /**
   * clears the words from the list associated
   */
  public void clearWordList () {
    words.clear ();
  }
  
  /**
   * gets the matrix
   * 
   * @return matrix the matrix of PuzzleCells
   */
  public PuzzleCell [][] getMatrix () {
    return matrix;
  }
  
  /**
   * gets the matrix height
   * 
   * @return height of the matrix
   */
  public int getMatrixHeight () {
    return height;
  }
  
  /**
   * gets the matrix height
   * 
   * @return height of the matrix
   */
  public int getMatrixWidth () {
    return width;
  }
  
  /**
   * Returns the number of words in the puzzle.
   * 
   * @return numWords - number of words in the puzzle
   */
  public int getNumWords () {
    return numWords;
  }
  
  /**
   * Returns an array of puzzle words.
   * 
   * @return wordList - a list of PuzzleWords
   */
  public ArrayList <PuzzleWord> getPuzzleWordList () {
    return wordList;
  }
  
  /**
   * Returns an array of words.
   * 
   * @return words - a list of PuzzleWords
   */
  public ArrayList <String> getWordList () {
    return words;
  }
  
  /**
   * sets the list of words associated
   * 
   * @param list
   *          the list to set
   */
  public void setList (ArrayList <String> list) {
    words = list;
  }
  
  /**
   * sets the number of words
   * 
   * @param words
   *          the number of words
   */
  public void setNumWords (int words) {
    numWords = words;
  }
  
  /**
   * sets the matrix width of the puzzle
   * 
   * @param i
   *          the hwidth to set
   */
  public void setMatrixWidth (int i) {
    width = i;
  }
  
  /**
   * sets the matrix height of the puzzle
   * 
   * @param i
   *          the height to set
   */
  public void setMatrixHeight (int i) {
    height = i;
  }
  
  /**
   * sets the PuzzleCell matrix
   * 
   * @param cells
   *          the matrix to set
   */
  public void setMatrix (PuzzleCell [][] cells) {
    if (cells != null) {
      int i = cells.length, j = cells[0].length;
      matrix = new PuzzleCell [i] [j];
      for (int r = 0; r < i; r++) {
        for (int c = 0; c < j; c++) {
          matrix[r][c] = cells[r][c];
        }
      }
    } else {
      matrix = null;
    }
  }
  
  /**
   * sets the word list
   * 
   * @param words
   *          the list of words to set
   */
  public void setWordList (ArrayList <PuzzleWord> words) {
    wordList = words;
  }
  
  /**
   * fills the matrix with random characters or spaces
   * 
   * @param length
   * @param fillBlank
   *          flag; true if blanks are desired, false if random chars are
   *          desired
   */
  protected void fillMatrix (int length, boolean fillBlank) {
    for (int r = 0; r < length; r++) {
      for (int c = 0; c < length; c++) {
        if (fillBlank) {
          matrix[r][c] = new PuzzleCell ();
        } else if (!matrix[r][c].hasCharacter ()) {
          matrix[r][c].addRandomChar ();
        }
      }
    }
  }
  
  /**
   * Generates a random direction.
   * 
   * @return Direction - any of the 8 directions a word can be oriented.
   */
  protected Direction generateDirection (int max) {
    int num = (int) (max * Math.random ());
    return (Direction.values ()[num]);
  }
  
  /**
   * A Singleton object used by any instance of puzzle as a number generator
   * 
   * @return a random number generator
   */
  protected Random getNumberGenerator () {
    if (gen == null) {
      gen = new Random ();
    }
    return gen;
  }
  
}
