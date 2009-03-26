package puzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Crossword implements Puzzle {
  /** A random number generator */
  private static Random  gen;
  
  /** the two-dimensional array used to store arranged letters from PuzzleWords */
  PuzzleCell [][]        matrix;
  
  /** the word list of PuzzleWords that are in the Puzzle */
  ArrayList <PuzzleWord> wordList;
  
  /** the list of words to be added to the puzzle */
  ArrayList <String>     words;
  
  /** the number of words in the puzzle */
  int                    numWords;
  
  /** the height of the puzzle */
  int                    height;
  
  /** the width of the puzzle */
  int                    width;
  
  boolean                toUpdate  = true;
  
  /** If first word in crossword */
  boolean                firstWord = true;
  
  public Crossword () {
    matrix = null;
    wordList = null;
    numWords = 0;
    height = 0;
    width = 0;
    toUpdate = true;
  }
  
  public void addWordToList (String word) {
    toUpdate = true;
    words.add (word);
  }
  
  public void removeWordFromList (String word) {
    toUpdate = true;
    words.remove (word);
  }
  
  public void clearWordList () {
    toUpdate = true;
    words.clear ();
  }
  
  public void draw (Graphics g) {
    if (!toUpdate)
      generate ();
    g.setColor (Color.WHITE);
    g.drawRect (0, 0, 5000, 5000);
    g.setColor (Color.BLACK);
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        if (matrix[r][c].numWords > 0) {
          g.drawRect (30 + 24 * r, 30 + 24 * c, 24, 24);
          g.drawString (matrix[r][c].toString (), 30 + 24 * r + 8, 30 + 24 * c + 15);
        }
      }
    }
  }
  
  public void generate () {
    int crazy = 0;
    if (words.size () > 0 && toUpdate) {
      int length = generateDimension (words);
      height = width = length;
      Collections.sort (words, new shared.Algorithms.SortByLineLength ());
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      boolean isValid;
      matrix = new PuzzleCell [height] [width];
      
      //Fills the PuzzleCell matrix with default PuzzleCells
      fillMatrix (length);
      
      //Adds words to the puzzle
      for (String word : words) {
        isValid = false;
        while (!isValid) {
          Direction dir = generateDirection ();
          int [] point = generatePosition (word.length (), height, width, dir);
          PuzzleWord pWord = new PuzzleWord ();
          pWord.setColumn (point[0]);
          pWord.setRow (point[1]);
          pWord.setDirection (dir);
          pWord.setWord (word);
          isValid = addAndValidate (pWord);
          if (isValid) {
            puzzleWords.add (pWord);
          }
          crazy++;
          //if (crazy > 100)
          //break;
        }
      }
      
      //assigns values to this puzzle object
      this.numWords = puzzleWords.size ();
      this.wordList = puzzleWords;
    }
    firstWord = true;
  }
  
  public void setList (ArrayList <String> list) {
    words = list;
  }
  
  public int getMatrixHeight () {
    return height;
  }
  
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
  
  /**
   * Gets the puzzle as a string
   * 
   * @return s - Returns the puzzle as a string
   */
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
  
  private void fillMatrix (int length) {
    for (int r = 0; r < length; r++) {
      for (int c = 0; c < length; c++) {
        matrix[r][c] = new PuzzleCell ();
      }
    }
  }
  
  /**
   * Generates the dimension to be used in the word search matrix
   * 
   * @param list
   * @return an integer specifying the dimension to be used by the Puzzle
   */
  private int generateDimension (ArrayList <String> list) {
    int max = 0;
    for (String s : list) {
      max = Math.max (s.length (), max);
    }
    return (2 * max);
  }
  
  /**
   * Generates a random direction.
   * 
   * @return Direction - any of the 7 directions a word can be oriented.
   */
  private Direction generateDirection () {
    int num = (int) (2 * Math.random ());
    return (Direction.values ()[num]);
  }
  
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
  private int [] generatePosition (int length, int colSize, int rowSize, Direction dir) {
    int [] point = new int [2];
    switch (dir) {
      case EAST:
        if (firstWord) {
          System.out.println ("here1\n");
          point[0] = (colSize / 2) - (length / 2);
          point[1] = rowSize / 2;
        } else {
          point[0] = getNumberGenerator ().nextInt (colSize - length);
          point[1] = getNumberGenerator ().nextInt (rowSize);
        }
        break;
      case SOUTH:
        if (firstWord) {
          System.out.println ("here2\n");
          point[0] = colSize / 2;
          point[1] = (rowSize / 2) - (length / 2);
        } else {
          point[0] = getNumberGenerator ().nextInt (colSize);
          point[1] = getNumberGenerator ().nextInt (rowSize - length);
        }
        break;
    }
    return (point);
  }
  
  /**
   * Adds and word and validates to ensure that it will fit into the grid
   * 
   * @param word
   *          puzzleword to be added.
   * @param matrix
   *          our current puzzle grid.
   * @return boolean Whether the add was a success or not.
   */
  private boolean addAndValidate (PuzzleWord word) {
    int dC = 0;
    int dR = 0;
    switch (word.getDirection ()) {
      case EAST:
        dC = 1;
        break;
      case SOUTH:
        dR = 1;
        break;
    }
    int row = word.getRow ();
    int col = word.getColumn ();
    String w = word.getWord ();
    boolean crossed = false, parallel = false;
    
    for (int i = 0; i < w.length (); i++) {
      PuzzleCell cell = matrix[col][row];
      char character = w.charAt (i);
      
      if (!cell.add (character, word.getDirection ())) {
        for (int j = i - 1; j >= 0; j--) {
          row -= dR;
          col -= dC;
          matrix[col][row].remove (word.getDirection ());
        }
        return false;
      }
      
      if (!crossed) {
        crossed = (cell.getNumWords () > 1);
      }
      
      if (!firstWord) {
        parallel = checkParallel (row, col, word.getDirection (), (i == 0 || i == w.length () - 1));
      }
        
      System.out.println ("Testing - " + parallel + "\n");
      row += dR;
      col += dC;
    }
    
    if (!firstWord && (!crossed || parallel)) {
      for (int i = 0; i < w.length (); i++) {
        row -= dR;
        col -= dC;
        matrix[col][row].remove (word.getDirection ());
      }
      return false;
    }
    
    System.out.println (w + " added to puzzle\n");
    firstWord = false;
    return true;
  }
  
  private boolean checkParallel (int row, int col, Direction dir, boolean firstOrLast) {
    boolean parallel = false;
    switch (dir) {
      case EAST:
        parallel = matrix[row][col].hasDirection (Direction.EAST);
        if (!firstOrLast && !parallel){
          try {
            parallel = parallel || matrix[row + 1][col].hasDirection (Direction.EAST);
          } catch (ArrayIndexOutOfBoundsException e) { System.err.println ("Array Out of Bounds"); }
          try {
            parallel = parallel || matrix[row - 1][col].hasDirection (Direction.EAST);
          } catch (ArrayIndexOutOfBoundsException e) { System.err.println ("Array Out of Bounds"); }
        }
        break;
      case SOUTH:
        parallel = matrix[row][col].hasDirection (Direction.SOUTH);
        if (!firstOrLast){
          try {
            parallel = parallel || matrix[row][col + 1].hasDirection (Direction.SOUTH);
          } catch (ArrayIndexOutOfBoundsException e) { System.err.println ("Array Out of Bounds"); }
          try {
            parallel = parallel || matrix[row][col + 1].hasDirection (Direction.SOUTH);
          } catch (ArrayIndexOutOfBoundsException e) { System.err.println ("Array Out of Bounds"); }
        }
        break;
    }
    return parallel;
  }
  
  public PuzzleCell [][] getMatrix () {
    return matrix;
  }
  
  public void setNumWords (int words) {
    numWords = words;
  }
  
  public void setMatrixWidth (int i) {
    width = i;
  }
  
  public void setMatrixHeight (int i) {
    height = i;
  }
  
  public void setWordList (ArrayList <PuzzleWord> words) {
    wordList = words;
  }
  
  public void setMatrix (PuzzleCell [][] cells) {
    int i = cells.length, j = cells[0].length;
    matrix = new PuzzleCell [i] [j];
    for (int r = 0; r < i; r++) {
      for (int c = 0; c < j; c++) {
        matrix[r][c] = cells[r][c];
      }
    }
  }
}
