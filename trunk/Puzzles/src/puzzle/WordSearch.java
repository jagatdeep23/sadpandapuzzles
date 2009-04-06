package puzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A WordSearch puzzle is a specialized Puzzle. It is a square that consists of
 * randomly placed words that can intersect.
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class WordSearch extends Puzzle {
  
  /** default constructor */
  public WordSearch () {
    setMatrix (null);
    setWordList (null);
    setNumWords (0);
    setMatrixHeight (0);
    setMatrixWidth (0);
  }
  
  /**
   * draws the WordSearch puzzle
   * 
   * @param g
   *          the graphics to draw to
   */
  public void draw (Graphics g) {
    g.setColor (Color.WHITE);
    g.drawRect (0, 0, 5000, 5000);
    g.setColor (Color.BLACK);
    for (int r = 0; r < getMatrixHeight(); r++) {
      for (int c = 0; c < getMatrixWidth(); c++) {
        if (getMatrix()[r][c].getNumWords () > 0)
          g.setColor (Color.RED);
        else
          g.setColor (Color.BLACK);
        g.drawString (getMatrix()[r][c].toString (), 30 + 24 * r, 30 + 24 * c);
      }
    }
  }
  
  /**
   * Adds and word and validates to ensure that it will fit into the grid
   * 
   * @param word
   *          puzzleword to be added.
   * @return boolean Whether the add was a success or not.
   */
  public boolean addAndValidate (PuzzleWord word) {
    int dC = 0, dR = 0;
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
      PuzzleCell cell = getMatrix()[col][row];
      char character = w.charAt (i);
      if (!cell.add (character)) {
        for (int j = i - 1; j >= 0; j--) {
          row -= dR;
          col -= dC;
          getMatrix()[col][row].remove ();
        }
        return false;
      }
      row += dR;
      col += dC;
    }
    return (true);
  }
  
  /**
   * generates a Word Search puzzle
   */
  public void generate () {
    if (getWordList().size () > 0) {
      int length = generateDimension (getWordList());
      Collections.sort (getWordList(), new shared.Algorithms.SortByLineLength ());
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      boolean isValid;
      setMatrix (new PuzzleCell [length] [length]);
      fillMatrix (length, true);
      for (String word : getWordList()) {
        isValid = false;
        while (!isValid) {
          Direction dir = generateDirection (8);
          int [] point = generatePosition (word.length (), length, length, dir);
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
      fillMatrix (length, false);
      setMatrixWidth(length);
      setMatrixHeight(length);
      setNumWords(puzzleWords.size ());
      setWordList(puzzleWords);
    }
  }
  
  /**
   * Gets the puzzle as a string
   * 
   * @return s - Returns the puzzle as a string
   */
  public String toString () {
    String s = "";
    for (int r = 0; r < getMatrixHeight(); r++) {
      for (int c = 0; c < getMatrixWidth(); c++) {
        s += getMatrix()[r][c] + " ";
      }
      s += "\n";
    }
    return s;
  }
  
  /**
   * Generates the dimension to be used in the word search matrix
   * 
   * @param list
   * @return an integer specifying the dimension to be used by the Puzzle
   */
  protected int generateDimension (ArrayList <String> list) {
    int sum = 0;
    for (String s : list) {
      sum += s.length ();
    }
    sum = (int) (Math.ceil (Math.sqrt (sum * 3 / 2)));
    if (sum < list.get (0).length ()) {
      sum = list.get (0).length () + 2;
    } else {
      ++sum;
    }
    return (sum);
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
  protected int [] generatePosition (int length, int colSize, int rowSize, Direction dir) {
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
}
