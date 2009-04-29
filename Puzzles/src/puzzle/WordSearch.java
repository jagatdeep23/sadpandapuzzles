package puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

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
    g.setFont (new Font ("Courier", Font.BOLD, 18));
    for (int c = 0; c < getMatrixWidth (); c++) {
      for (int r = 0; r < getMatrixHeight (); r++) {
        if (getMatrixElement (c, r).getNumWords () > 0)
          g.setColor (Color.RED);
        else
          g.setColor (Color.BLACK);
        g.drawString (getMatrixElement (c, r).toString (), 30 + 24 * r, 30 + 24 * c);
      }
    }
  }
  
  /**
   * generates a Word Search puzzle
   */
  public void generate () {
    long total = 0;
    if (getWordList ().size () > 0) {
      int progress = 0;
      
      JDialog popup = new JDialog();
      JProgressBar bar = new JProgressBar(0, getWordList().size ());
      super.buildPopup (popup, bar);

      Collections.sort (getWordList (), new shared.Algorithms.SortByLineLength ());
      int length = generateDimension (getWordList ());
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      
      super.initializeMatrix (length);
      
      boolean isValid;
      for (String word : getWordList ()) {
        isValid = false;
        PuzzleWord pWord = new PuzzleWord();
        while (!isValid) {
          ++total;
          Direction dir = super.generateDirection (8);
          int [] point = generatePosition (word.length (), length, length, dir);
          pWord.setPoint (point);
          pWord.setDirection (dir);
          pWord.setWord (word);
          isValid = addAndValidate (pWord);
        }
        bar.setValue (++progress);
        puzzleWords.add (pWord);
      }
      super.fillMatrix (FillRandom);
      setMatrixWidth (length);
      setMatrixHeight (length);
      setNumWords (puzzleWords.size ());
      setWordList (puzzleWords);
      popup.dispose ();
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
        s += getMatrixElement (r, c) + " ";
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
    int dC = super.getColumnChange (word.getDirection ());
    int dR = super.getRowChange (word.getDirection ());
    int row = word.getRow (), oldRow = word.getRow ();
    int col = word.getColumn (), oldCol = word.getColumn ();
    String w = word.getWord ();
    for (int i = 0; i < w.length (); i++) {
      try {
        char test = getMatrixElement (row,col).getCharacter ();
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
      getMatrixElement (row, col).add (w.charAt (i));
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
    sum = (int) (Math.ceil (Math.sqrt (sum) * 5 / 4));
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
   * @param row
   *          number of columns.
   * @param col
   *          number of rows.
   * @return int[] - [0] is the x value, and [1] is the y value.
   */
  protected int [] generatePosition (int length, int col, int row, Direction dir) {
    int [] point = {0, 0};
    switch (dir) {
      case NORTH:
        point[0] = super.getNumberGenerator ().nextInt (row);
        point[1] = length - 1;
        try {
           point[1] =+ super.getNumberGenerator ().nextInt (col - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case NORTHEAST:
        point[1] = length - 1;
        try {
          point[0] = super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] += super.getNumberGenerator ().nextInt (col - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case EAST:
        try {
          point[0] = super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        point[1] = super.getNumberGenerator ().nextInt (col);
        break;
      case SOUTHEAST:
        try {
          point[0] = super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] = super.getNumberGenerator ().nextInt (col - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case SOUTH:
        point[0] = super.getNumberGenerator ().nextInt (row);
        try {
          point[1] = super.getNumberGenerator ().nextInt (col - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case SOUTHWEST:
        point[0] = length - 1;
        try {
          point[0] += super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] = super.getNumberGenerator ().nextInt (col - length);
        } catch (IllegalArgumentException e) {
        }
        break;
      case WEST:
        point[0] = length - 1;
        try {
          point[0] += super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        point[1] = super.getNumberGenerator ().nextInt (col);
        break;
      case NORTHWEST:
        point[0] = length - 1;
        point[1] = length - 1;
        try {
          point[0] += super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] += super.getNumberGenerator ().nextInt (col - length);
        } catch (IllegalArgumentException e) {
        }
        break;
    }
    return (point);
  }
}
