package puzzle;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Crossword implements Puzzle {
  /** A random number generator */
  private static Random  gen;
  
  /** the two-dimensional array used to store arranged letters from PuzzleWords */
  PuzzleCell [][]        matrix;
  
  /** the word list of PuzzleWords that are in the Puzzle */
  ArrayList <PuzzleWord> wordList;
  
  /** the list of words to be added to the puzzle */
  ArrayList <String> words;
  
  /** the number of words in the puzzle */
  int numWords;
  
  /** the height of the puzzle */
  int height;
  
  /** the width of the puzzle */
  int width;
  
  public Crossword () {
    matrix = null;
    wordList = null;
    numWords = 0;
    height = 0;
    width = 0;
  }
  
  public void addWordToList (String word) {
    // TODO Auto-generated method stub
    
  }

  public void clearWordList () {
    // TODO Auto-generated method stub
    
  }

  public void removeWordFromList (String word) {
    // TODO Auto-generated method stub
    
  }

  public void draw (Graphics g) {
    // TODO Auto-generated method stub
    
  }

  public void generate () {
    // TODO Auto-generated method stub
    
  }

  public void setList (ArrayList <String> list) {
    words = list;
  }

  /** Returns the height of the matrix.
   * @return arraySize the dimension of the matrix */
  public int getMatrixHeight () {
    return height;
  }
  
  /** Returns the width of the matrix.
   * @return arraySize the dimension of the matrix */
  public int getMatrixWidth () {
    return width;
  }
  
  
  /** Returns the number of words in the puzzle.
   * @return numWords - number of words in the puzzle */
  public int getNumWords () {
    return numWords;
  }
  
  /** Returns an array of puzzle words.
   * @return wordList - a list of PuzzleWords */
  public ArrayList <PuzzleWord> getWordList () {
    return wordList;
  }
  
  /** A Singleton object used by any instance of puzzle as a number generator
   * @return a random number generator */
  protected Random getNumberGenerator () {
    if (gen == null) {
      gen = new Random ();
    }
    return gen;
  }
  
  /** Gets the puzzle as a string
   * @return s - Returns the puzzle as a string */
  public String toString () {
    String s = "";
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        s += matrix[r][c] + " ";
      }
      s += "\n";
    }
    return s;
  }
  
}
