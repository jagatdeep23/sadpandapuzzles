package puzzle;

import java.util.ArrayList;
import java.util.Random;

import puzzle.Puzzle.Direction;

/**
 * A template for different Puzzles to use as the core components
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class PuzzleTemplate {
  
  /** A random number generator */
  private static Random            gen;
  
  /** the word list of PuzzleWords that are in the Puzzle */
  protected ArrayList <PuzzleWord> wordList;
  
  /** the list of words to be added to the puzzle */
  protected ArrayList <String>     words;
  
  /** the number of words in the puzzle */
  protected int                    numWords;
  
  /** the height of the puzzle */
  protected int                    height;
  
  /** the width of the puzzle */
  protected int                    width;
  
  /** the two-dimensional array used to store arranged letters from PuzzleWords */
  protected PuzzleCell [][]        matrix;
  
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
  public ArrayList <PuzzleWord> getWordList () {
    return wordList;
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
    int i = cells.length, j = cells[0].length;
    matrix = new PuzzleCell [i] [j];
    for (int r = 0; r < i; r++) {
      for (int c = 0; c < j; c++) {
        matrix[r][c] = cells[r][c];
      }
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
   * @return Direction - any of the 7 directions a word can be oriented.
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
