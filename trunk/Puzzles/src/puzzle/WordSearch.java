package puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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
  public void draw (Graphics2D g) {
    g.setColor (Color.WHITE);
    g.fillRect (0, 0, 5000, 5000);
    g.setColor (Color.BLACK);
    g.setFont (new Font ("Courier", Font.BOLD, 18))
;    for (int r = 0; r < getMatrixHeight (); r++) {
      for (int c = 0; c < getMatrixWidth (); c++) {
        if (getMatrix ()[r][c].getNumWords () > 0)
          g.setColor (Color.RED);
        else
          g.setColor (Color.BLACK);
        g.drawString (getMatrix ()[r][c].toString (), 30 + 24 * r, 30 + 24 * c);
      }
    }
  }
  
  /**
   * generates a Word Search puzzle
   */
  public void generate () {
    long total = 0;
    if (getWordList ().size () > 0) {
      Collections.sort (getWordList (), new shared.Algorithms.SortByLineLength ());
      int length = generateDimension (getWordList ());
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      boolean isValid;
      setMatrix (new PuzzleCell [length] [length]);
      fillMatrix (length, true);
      for (String word : getWordList ()) {
        isValid = false;
        while (!isValid) {
          ++total;
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
      setMatrixWidth (length);
      setMatrixHeight (length);
      setNumWords (puzzleWords.size ());
      setWordList (puzzleWords);
    }
  }
  
  /**
   * Gets the puzzle as a string
   * 
   * @return s the puzzle represented as a string
   */
  public String toString () {
    String s = "";
    for (int r = 0; r < getMatrixHeight (); r++) {
      for (int c = 0; c < getMatrixWidth (); c++) {
        s += getMatrix ()[r][c] + " ";
      }
      s += "\n";
    }
    return s;
  }

  /**
   * Adds and word and validates to ensure that it will fit into the grid
   * 
   * @param word
   *          puzzleword to be added.
   * @return boolean Whether the add was a success or not.
   */
  protected boolean addAndValidate (PuzzleWord word) {
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
    int row = word.getRow (), oldRow = word.getRow ();
    int col = word.getColumn (), oldCol = word.getColumn ();
    String w = word.getWord ();
    for (int i = 0; i < w.length (); i++) {
      try {
        char test = getMatrix ()[col][row].toString().charAt (0);
        if (test != '?' && test != w.charAt (i))
          return false;
      } catch (ArrayIndexOutOfBoundsException e) {
        return false;
      }
      row += dR;
      col += dC;
    }
    row = oldRow;
    col = oldCol;
    for (int i = 0; i < w.length (); ++i) {
      getMatrix()[col][row].add (w.charAt (i));
      col += dC;
      row += dR;
    }
    return (true);
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
    sum = (int) (Math.ceil (Math.sqrt (sum)));
    if (sum < list.get (0).length ()) {
      sum = list.get (0).length ();
    }
    return (++sum);
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
    int [] point = {0, 0};
    switch (dir) {
      case NORTH:
        point[0] = getNumberGenerator ().nextInt (colSize);
        point[1] = length - 1;
        try {
           point[1] =+ getNumberGenerator ().nextInt (rowSize - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case NORTHEAST:
        point[1] = length - 1;
        try {
          point[0] = getNumberGenerator ().nextInt (colSize - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] += getNumberGenerator ().nextInt (rowSize - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case EAST:
        try {
          point[0] = getNumberGenerator ().nextInt (colSize - length);
        } catch (IllegalArgumentException e) {
        }
        point[1] = getNumberGenerator ().nextInt (rowSize);
        break;
      case SOUTHEAST:
        try {
          point[0] = getNumberGenerator ().nextInt (colSize - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] = getNumberGenerator ().nextInt (rowSize - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case SOUTH:
        point[0] = getNumberGenerator ().nextInt (colSize);
        try {
          point[1] = getNumberGenerator ().nextInt (rowSize - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case SOUTHWEST:
        point[0] = length - 1;
        try {
          point[0] += getNumberGenerator ().nextInt (colSize - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] = getNumberGenerator ().nextInt (rowSize - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case WEST:
        point[0] = length - 1;
        try {
          point[0] += getNumberGenerator ().nextInt (colSize - length);
        } catch (IllegalArgumentException e) {
        }
        point[1] = getNumberGenerator ().nextInt (rowSize);
        break;
      case NORTHWEST:
        point[0] = length - 1;
        point[1] = length - 1;
        try {
          point[0] += getNumberGenerator ().nextInt (colSize - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] += getNumberGenerator ().nextInt (rowSize - length);
        } catch (IllegalArgumentException e) {
        }
        break;
    }
    return (point);
  }
}
