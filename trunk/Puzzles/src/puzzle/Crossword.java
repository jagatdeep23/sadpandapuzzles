package puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 * A Crossword puzzle is a specialized Puzzle. It is a rectangular shape that
 * consists of words that must intersect at least one time. Words may never be
 * directly parallel. It is also known as a free-form crossword puzzle.
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class Crossword extends Puzzle {
  
  /** If first word in crossword */
  private boolean firstWord = true;
  
  /** default constructor */
  public Crossword () {
    reset();
  }
  
  /** draws the crossword puzzle */
  public void draw (Graphics2D g) {
    g.setColor (Color.BLACK);
    g.setFont (new Font ("Courier", Font.BOLD, 18));
    g.setStroke (new BasicStroke (3));
    g.fillRect (0, 0, 5000, 5000);
    for (int r = 0; r < getMatrixHeight (); r++) {
      for (int c = 0; c < getMatrixWidth (); c++) {
        if (getMatrixElement (r, c).hasCharacter ()) {
          g.setColor (Color.WHITE);
          g.fillRect (30 + 24 * r, 30 + 24 * c, 24, 24);
          g.setColor (Color.BLACK);
          g.drawRect (30 + 24 * r, 30 + 24 * c, 24, 24);
          g.drawString (getMatrixElement(r,c).toString (), 30 + 24 * r + 9, 30 + 24 * c + 18);
        }
      }
    }
  }
  
  /** generates a crossword puzzle */
  public void generate () {
    if (getWordList ().size () > 0) {
      Collections.sort (getWordList (), new shared.Algorithms.SortByLineLength ());
      int length = generateDimension (getWordList ()), attempts = 0;
      initializeMatrix(length);
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      ArrayList <String> list = new ArrayList<String>(getWordList());
      boolean isValid;
      firstWord = true;
      int limit = list.size() * list.size() / 2;
      int test = 0;
      int words = 0;
      JDialog popup = new JDialog();
      JProgressBar bar = new JProgressBar(0, list.size ());
      buildPopup (popup, bar, "Generating Crossword");
      long time = System.currentTimeMillis ();
      while (list.size () != 0) {
        isValid = false;
        attempts = 0;
        while (!isValid) {
          String word = list.get (0);
          Direction dir = generateDirection (2);
          int [] point = generatePosition (word.length (), getMatrixHeight (), getMatrixWidth (), dir);
          PuzzleWord pWord = new PuzzleWord (point, dir, word);
          isValid = addAndValidate (pWord);
          if (isValid) {
            list.remove (0);
            puzzleWords.add (pWord);
            words++;
            updateProgressBar (bar, words, null);
            
          } else if (++attempts >= 5000) {
            
            attempts = 0;
            list.remove (0);
            list.add (word);
            
            if (++test >= limit) {
              
              words --;
              PuzzleWord pw = puzzleWords.get (words);
              puzzleWords.remove (words);
              removeWordFromPuzzle(pw);
              list.add (pw.getWord ());
              test = 0;
              
              updateProgressBar (bar, words, "We might not be able to get through this...");
            }
            if ((System.currentTimeMillis () - time) >= 4000 || list.size() == 1) {
              popup.dispose ();
              JOptionPane.showMessageDialog (null, "This program cannot create a puzzle from your input!\nPlease remove word(s) and try again.", "Oh No!",
                  JOptionPane.ERROR_MESSAGE);
              reset();
              return;
            }
          }
        }
      }
      trim (puzzleWords);
      popup.dispose ();
    }
    firstWord = true;
  }

  /**
   * Gets the puzzle as a string
   * 
   * @return s - Returns the puzzle as a string
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
    boolean isCrossed = false;
    if (firstWord) {
      isCrossed = true;
      firstWord = false;
    } else if (checkParallel(word)) {
        isCrossed = checkCrossed (word);
    }
    if (isCrossed) {
      Direction dir = word.getDirection ();
      String w = word.getWord ();
      int dC = getColumnChange (dir);
      int dR = getRowChange (dir);
      int row = word.getRow ();
      int col = word.getColumn ();
      int length = w.length ();
      for (int i = 0; i < length; ++i, col += dC, row += dR) {
        getMatrixElement (row, col).add (w.charAt (i), dir);
      }
    }
    return isCrossed;
  }

  /**
   * Checks to see if a word crosses any existing words in the puzzle
   * @param word the word to check
   * @return true if the word crosses at least one other word
   */
  protected boolean checkCrossed(PuzzleWord word) {
    String w = word.getWord ();
    Direction dir = word.getDirection ();
    int col = word.getColumn ();
    int row = word.getRow ();
    int dC = getColumnChange (dir);
    int dR = getRowChange (dir);
    boolean valid = false;
    for (int i = 0; i < w.length (); i++, col += dC, row += dR) {
      char character = w.charAt (i);
      if (!getMatrixElement (row, col).isEmpty ()) {
        if (getMatrixElement (row, col).hasDirection (dir)) {
          return false;
        } else if (getMatrixElement (row, col).getCharacter () == character) {  
          valid = true;
        } else if (getMatrixElement (row, col).getCharacter () != character) {
          return false;
        }
      }
    }
    return valid;
  }

  /**
   * Checks to see if a word is parallel any existing words in the puzzle
   * @param word the word to check
   * @return true if the word can be placed in the puzzle
   */
  protected boolean checkParallel(PuzzleWord word) {
    String w = word.getWord ();
    Direction dir = word.getDirection ();
    int col = word.getColumn ();
    int row = word.getRow ();
    int length = w.length ();
    int dC = getColumnChange (dir);
    int dR = getRowChange (dir);
    try {
      if (getMatrixElement (row - dR, col - dC).hasCharacter ()) {
        return false;
      }
    } catch (ArrayIndexOutOfBoundsException e) {
    }
    try {
      if (getMatrixElement (row + dR * length, col + dC * length).hasCharacter ()) {
        return false;
      }
    } catch (ArrayIndexOutOfBoundsException e) {
    }
    for (int i = 0; i < w.length (); i++, col += dC, row += dR) {
      if (getMatrixElement (row, col).isEmpty ()) {
        try {
          if (getMatrixElement (row - dC, col - dR).hasCharacter ()) {
            return false;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
          if (getMatrixElement (row + dC, col + dR).hasCharacter ()) {
            return false;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
      }
    }
    return true;
  }

  /**
   * Generates the dimension to be used in the word search matrix
   * 
   * @param list
   * @return an integer specifying the dimension to be used by the Puzzle
   */
  protected int generateDimension (ArrayList <String> list) {
    int max = 0, temp = 0;
    for (String s : list) {
      if (temp++ <= (list.size () + 1) / 2)
        max += s.length ();
    }
    return (max);
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

  protected void removeWordFromPuzzle (PuzzleWord pw) {
    int r = pw.getRow ();
    int c = pw.getColumn ();
    int dr = (pw.getDirection () == Direction.SOUTH) ? 1 : 0;
    int dc = (pw.getDirection () == Direction.SOUTH) ? 0 : 1;
    for (int k = 0; k < pw.getWord ().length (); ++k, r += dr, c += dc) {
      getMatrixElement (r, c).remove (pw.getDirection ());
    }
  }

  /**
   * trims the matrix to size
   * @param puzzleWords the list of puzzleWords
   */
  protected void trim(ArrayList<PuzzleWord> puzzleWords) {
    int minWidth = 999, maxWidth = -1, minHeight = 999, maxHeight = -1;
    for (PuzzleWord word : puzzleWords) {
      int dC = getColumnChange(word.getDirection ());
      int dR = getRowChange(word.getDirection ());
      minWidth = Math.min (minWidth, word.getColumn ());
      maxWidth = Math.max (maxWidth, word.getColumn () + dC * word.getWord ().length ());
      minHeight = Math.min (minHeight, word.getRow ());
      maxHeight = Math.max (maxHeight, word.getRow () + dR * word.getWord ().length ());
    }
    
    setMatrixWidth (maxWidth - minWidth + 1);
    setMatrixHeight (maxHeight - minHeight + 1);
    PuzzleCell [][] newMatrix = new PuzzleCell [getMatrixHeight ()] [getMatrixWidth ()];
    for (int r = 0; r < getMatrixHeight (); ++r) {
      for (int c = 0; c < getMatrixWidth (); ++c) {
        newMatrix[r][c] = getMatrixElement (r + minHeight, c + minWidth);
      }
    }
    for (int i = 0; i < puzzleWords.size (); ++i) {
      PuzzleWord p = puzzleWords.get (i);
      p.setColumn (p.getColumn () - minWidth);
      p.setRow (p.getRow () - minHeight);
      puzzleWords.set (i, p);
    }
    setMatrix (newMatrix);
    setNumWords (puzzleWords.size ());
    setWordList (puzzleWords);
  }
}
