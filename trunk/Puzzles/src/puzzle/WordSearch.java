package puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WordSearch implements Puzzle {
  
  /** A random number generator */
  private static Random gen;
  
  /** the two-dimensional array used to store arranged letters from PuzzleWords */
  PuzzleCell [][] matrix;
  
  /** the word list of PuzzleWords that are in the Puzzle */
  ArrayList <PuzzleWord> wordList;
  
  /** the list of words to be added to the puzzle */
  ArrayList <String> words;
  
  /** the number of words in the puzzle */
  int numWords;
  
  /** the height and width of the puzzle */
  int arraySize;
  
  boolean toUpdate = true;
  
  public WordSearch () {
    matrix = null;
    wordList = null;
    numWords = 0;
    arraySize = 0;
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
      generate();
    g.setColor (Color.WHITE);
    g.drawRect (0, 0, 5000, 5000);
    g.setColor (Color.BLACK);
    for (int r = 0; r < arraySize; r ++) {
      for (int c = 0; c < arraySize; c ++) {
        if (matrix[r][c].numWords > 0)
          g.setColor (Color.RED);
        else
          g.setColor (Color.BLACK);
        g.drawString (matrix[r][c].toString (), 30 + 24 * r, 30 + 24 * c);
      }
    }
  }

  public void generate () {
    if (words.size () > 0 && toUpdate) {
      int length = generateDimension (words);
      Collections.sort (words, new shared.Algorithms.SortByLineLength());
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      boolean isValid;
      matrix = new PuzzleCell [length] [length];
      
      //Fills the PuzzleCell matrix with default PuzzleCells
      fillMatrix (length, true);
      
      //Adds words to the puzzle
      for (String word : words) {
        isValid = false;
        while (!isValid) {
          Direction dir = generateDirection ();
          int [] point = generatePosition (word.length (), length, length, dir);
          PuzzleWord pWord = new PuzzleWord ();
          pWord.setColumn (point[0]);
          pWord.setRow (point[1]);
          pWord.setDirection (dir);
          pWord.setWord (word);
          isValid = addAndValidate (pWord, matrix);
          if (isValid) {
            puzzleWords.add (pWord);
          }
        }
      }
      
      //Fills in the remaining cells with random characters
      fillMatrix (length, false);
      
      //assigns values to this puzzle object
      this.arraySize = length;
      this.numWords = puzzleWords.size ();
      this.wordList = puzzleWords;
    }
  }

  /** Returns the size of the matrix.
   * @return arraySize the dimension of the matrix */
  public int getArraySize () {
    return arraySize;
  }
  
  public int getMatrixHeight () {
    return arraySize;
  }

  public int getMatrixWidth () {
    return arraySize;
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
  
  public void setList (ArrayList <String> list) {
    words = list;
  }

  /** Adds and word and validates to ensure that it will fit into the grid
   * @param word puzzleword to be added.
   * @param matrix our current puzzle grid.
   * @return boolean Whether the add was a success or not. */
  private boolean addAndValidate (PuzzleWord word, PuzzleCell [][] matrix) {
    int dC = 0;
    int dR = 0;
    switch (word.getDirection ()) {
      case NORTH:
        dR = -1;
        break;
      case NORTHEAST:
        dR = -1;
        dC = 1;
        break;
      case EAST:
        dC = 1;
        break;
      case SOUTHEAST:
        dR = 1;
        dC = 1;
        break;
      case SOUTH:
        dR = 1;
        break;
      case SOUTHWEST:
        dR = 1;
        dC = -1;
        break;
      case WEST:
        dC = -1;
        break;
      case NORTHWEST:
        dR = -1;
        dC = -1;
        break;
    }
    int row = word.getRow ();
    int col = word.getColumn ();
    String w = word.getWord ();
    for (int i = 0; i < w.length (); i++) {
      PuzzleCell cell = matrix[col][row];
      char character = w.charAt (i);
      if (!cell.add (character)) {
        for (int j = i - 1; j >= 0; j--) {
          row -= dR;
          col -= dC;
          matrix[col][row].remove ();
        }
        return false;
      }
      row += dR;
      col += dC;
    }
    return (true);
    
  }
  
  
  private void fillMatrix (int length, boolean fillBlank) {
    for (int r = 0; r < length; r++) {
      for (int c = 0; c < length; c++) {
        if (fillBlank) {
          matrix[r][c] = new PuzzleCell();
        } else if (matrix[r][c].getCharacter () == '\0') {
            matrix[r][c].addRandomChar ();
        }
      }
    }
  }

  /** Generates a random direction.
   * @return Direction - any of the 7 directions a word can be oriented. */
  private Direction generateDirection () {
    int num = (int) (8 * Math.random ());
    return (Direction.values ()[num]);
  }
  
  /** returns a valid start point for a word by length. Does not check intersections.
   * @param length length of the word.
   * @param colSize number of columns.
   * @param rowSize number of rows.
   * @return int[] - [0] is the x value, and [1] is the y value. */
  private int [] generatePosition (int length, int colSize, int rowSize, Direction dir) {
    int [] point = new int [2];
    switch (dir) {
      case NORTH:
        point[0] = getNumberGenerator ().nextInt (colSize);
        point[1] = length - 1 + getNumberGenerator ().nextInt (rowSize - length);
        break;
      case NORTHEAST:
        point[0] = getNumberGenerator ().nextInt (colSize - length);
        point[1] = length - 1 + getNumberGenerator ().nextInt (rowSize - length);
        break;
      case EAST:
        point[0] = getNumberGenerator ().nextInt (colSize - length);
        point[1] = getNumberGenerator ().nextInt (rowSize);
        break;
      case SOUTHEAST:
        point[0] = getNumberGenerator ().nextInt (colSize - length);
        point[1] = getNumberGenerator ().nextInt (rowSize - length);
        break;
      case SOUTH:
        point[0] = getNumberGenerator ().nextInt (colSize);
        point[1] = getNumberGenerator ().nextInt (rowSize - length);
        break;
      case SOUTHWEST:
        point[0] = length - 1 + getNumberGenerator ().nextInt (colSize - length);
        point[1] = getNumberGenerator ().nextInt (rowSize - length);
        break;
      case WEST:
        point[0] = length - 1 + getNumberGenerator ().nextInt (colSize - length);
        point[1] = getNumberGenerator ().nextInt (rowSize);
        break;
      case NORTHWEST:
        point[0] = length - 1 + getNumberGenerator ().nextInt (colSize - length);
        point[1] = length - 1 + getNumberGenerator ().nextInt (rowSize - length);
        break;
    }
    return (point);
  }
  
  /** Generates the dimension to be used in the word search matrix
   * @param list
   * @return an integer specifying the dimension to be used by the Puzzle */
  private int generateDimension (ArrayList <String> list) {
    int sum = 0;
    for (String s : list) {
      sum += s.length ();
    }
    sum = (int) (Math.ceil (Math.sqrt (sum * 3 / 2)));
    if (sum < list.get(0).length ()) {
      sum = list.get(0).length () + 2;
    } else {
      sum ++;
    }
    return (sum);
  }

  /** A Singleton object used by any instance of puzzle as a number generator
   * @return a random number generator */
  private Random getNumberGenerator () {
    if (gen == null) {
      gen = new Random ();
    }
    return gen;
  }
}
