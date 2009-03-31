package puzzle;

// Will is working on this.... please do not edit

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
    for (int c = 0; c < width; c++) {
      for (int r = 0; r < height; r++) {
        if (matrix[c][r].hasCharacter ()) {
          g.drawRect (30 + 24 * r, 30 + 24 * c, 24, 24);
          g.drawString (matrix[c][r].toString (), 30 + 24 * r + 8, 30 + 24 * c + 15);
        }
      }
    }
  }
  
  public void generate () {
    if (words.size () > 0 && toUpdate) {
      int length = generateDimension (words);
      height = width = length;
      Collections.sort (words, new shared.Algorithms.SortByLineLength ());
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      boolean isValid;
      firstWord = true;
      matrix = new PuzzleCell [height] [width];
      
      //Fills the PuzzleCell matrix with default PuzzleCells
      fillMatrix (length);
      
      //Adds words to the puzzle
      for (String word : words) {
        isValid = false;
        System.out.println (word + " " + word.length () + " " + height + " x " + width);
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
        }
      }
      
      
      int minWidth = -1, maxWidth = -1, minHeight = -1 , maxHeight = -1;
      for (int c = 0; c < matrix.length; ++c) {
        for (int r = 0; r < matrix[0].length; ++r) {
          if (matrix[c][r].hasCharacter ()) {
            if (minWidth == -1 || minWidth > c)
              minWidth = c;
            if (maxWidth == -1 || maxWidth < c)
              maxWidth = c;
            if (minHeight == -1 || minHeight > r)
              minHeight = r;
            if (maxHeight == -1 || maxHeight < r)
              maxHeight = r;
          }
        }
      }
      
      width = maxWidth - minWidth + 1;
      height = maxHeight - minHeight + 1;
      
      PuzzleCell[][] newMatrix = new PuzzleCell[width][height];
      for (int c = 0; c < width; ++c) {
        for (int r = 0; r < height; ++r) {
          newMatrix[c][r] = matrix[c + minWidth][r + minHeight];
        }
      }
      matrix = newMatrix;
      
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
    for (int c = 0; c < matrix.length; c++) {
      for (int r = 0; r < matrix[0].length; r++) {
        s += matrix[c][r] + " ";
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
    int max = 0,temp = 0;
    for (String s : list) {
      if ( temp++ <= (list.size () + 1 ) / 2)
        max += s.length ();
    }
    return (max);
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
          point[0] = (colSize / 2) - (length / 2);
          point[1] = rowSize / 2;
        } else {
          point[0] = getNumberGenerator ().nextInt (colSize - length);
          point[1] = getNumberGenerator ().nextInt (rowSize);
        }
        break;
      case SOUTH:
        if (firstWord) {
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
  public boolean addAndValidate (PuzzleWord word) {
    Direction dir = word.getDirection ();
    String w = word.getWord ();
    int dC = (dir == Direction.EAST) ? 1 : 0;
    int dR = (dir == Direction.SOUTH) ? 1 : 0;
    int row = word.getRow ();
    int col = word.getColumn ();
    int length = w.length ();
    int oldRow = row;
    int oldCol = col;
    boolean isCrossed = false, valid = true;
    char character;
    if (firstWord) {
      isCrossed = true;
      firstWord = false;
    } else {
      
      try { //PRE letter is not blank
        if (matrix [col - dC][row - dR].hasCharacter ()){
          return false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {}
      try { //POST letter is not blank
        if (matrix [col + dC * length ][row + dR * length].hasCharacter ()) {
          return false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {}
      
      
      for (int i = 0; valid && i < w.length (); i++, col += dC, row += dR) {
        
        character = w.charAt (i);
        
        //If character cell is empty
        if (matrix [col][row].isEmpty ()) {
          
          try { //PREV letter is not blank
            if (matrix [col - dR][row - dC].hasCharacter ()){
              return false;
            }
          } catch (ArrayIndexOutOfBoundsException e) {}
          
          try { //NEXT letter is not blank
            if (matrix [col + dR][row + dC].hasCharacter ()){
              return false;
            }
          } catch (ArrayIndexOutOfBoundsException e) {}
          
          //If character cell is not empty
        } else {
          
          //If characters do not match
          if (!matrix[col][row].hasCharacter (character)) {
            return false;
          
          //If characters do match
          } else {
            
            //If cell has matching direction
            if (matrix[col][row].hasDirection (dir)) {
              return false;
            }
            
            //If cell has a direction
            if (!isCrossed) {
              if (matrix[col][row].getNumWords () > 0) {
                isCrossed = true;
              }
            }
          }
        }
      }
    }
    row = oldRow;
    col = oldCol;
    
    if (isCrossed) {
      for (int i = 0; i < length; ++i, col += dC, row += dR) {
        matrix[col][row].add (w.charAt (i));
      }
    }
    return isCrossed;
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
