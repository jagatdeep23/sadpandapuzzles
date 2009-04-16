package puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 * A Crossword puzzle is a specialized Puzzle. It is a retangular shape that
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
    setMatrix (null);
    setWordList (null);
    setNumWords (0);
    setMatrixHeight (0);
    setMatrixWidth (0);
  }
  
  /** draws the crossword puzzle */
  public void draw (Graphics2D g) {
    g.setColor (Color.BLACK);
    g.setFont (new Font ("Courier", Font.BOLD, 18));
    g.setStroke (new BasicStroke (3));
    g.fillRect (0, 0, 5000, 5000);
    for (int r = 0; r < getMatrixHeight (); r++) {
      for (int c = 0; c < getMatrixWidth (); c++) {
        if (getMatrix ()[r][c].hasCharacter ()) {
          g.setColor (Color.WHITE);
          g.fillRect (30 + 24 * r, 30 + 24 * c, 24, 24);
          g.setColor (Color.BLACK);
          g.drawRect (30 + 24 * r, 30 + 24 * c, 24, 24);
          g.drawString (getMatrix ()[r][c].toString (), 30 + 24 * r + 9, 30 + 24 * c + 18);
        }
      }
    }
  }
  
  /** generates a crossword puzzle */
  public void generate () {
    long total = 0;
    
    if (getWordList ().size () > 0) {
      
      Collections.sort (getWordList (), new shared.Algorithms.SortByLineLength ());
      
      int length = generateDimension (getWordList ()), crazy = 0;
      setMatrixWidth (length);
      setMatrixHeight (length);
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      ArrayList <String> list = getWordList();
      boolean isValid;
      firstWord = true;
      setMatrix (new PuzzleCell [getMatrixHeight ()] [getMatrixWidth ()]);
      fillMatrix (length, true);
      int limit = (int)(Math.pow (list.size(), 2)) / 2;
      int test = 0;
      
      long time = System.currentTimeMillis ();
      for (int i = 0; i < list.size (); ++i) {
        String word = list.get (i);
        isValid = false;
        crazy = 0;
        while (!isValid) {
          Direction dir = generateDirection (2);
          int [] point = generatePosition (word.length (), getMatrixHeight (), getMatrixWidth (), dir);
          PuzzleWord pWord = new PuzzleWord ();
          pWord.setColumn (point[0]);
          pWord.setRow (point[1]);
          pWord.setDirection (dir);
          pWord.setWord (word);
          ++total;
          isValid = addAndValidate (pWord);
          if (isValid) {
            puzzleWords.add (pWord);
          } else if (++crazy == 5000) {
            list.remove (i);
            list.add (word);
            --i;
            isValid = true;
            if (++test == limit) {
              PuzzleWord pw = puzzleWords.get (puzzleWords.size () - 1);
              puzzleWords.remove (puzzleWords.size () - 1);
              int r = pw.getRow ();
              int c = pw.getColumn ();
              int dr = (pw.getDirection () == Direction.EAST) ? 0 : 1;
              int dc = (pw.getDirection () == Direction.SOUTH) ? 1 : 0;
              for (int k = 0; k < pw.getWord ().length (); ++k, r += dr, c += dc) {
                getMatrix()[r][c].remove (pw.getDirection ());
              }
              String wd = list.get (i);
              list.remove (i);
              list.add (wd);
            }
              
            if ((System.currentTimeMillis () - time) >= 3000) {
              System.out.println ("Took " + total + " passes.");
              JOptionPane.showMessageDialog (null, "This program cannot create a puzzle from your input!\nPlease remove word(s) and try again.", "Oh No!",
                  JOptionPane.ERROR_MESSAGE);
              setMatrix (null);
              setWordList (null);
              setNumWords (0);
              setMatrixHeight (0);
              setMatrixWidth (0);
              return;
            }
          }
        }
      }
      int minWidth = -1, maxWidth = -1, minHeight = -1, maxHeight = -1;
      for (int r = 0; r < getMatrixHeight (); ++r) {
        for (int c = 0; c < getMatrixWidth (); ++c) {
          if (getMatrix ()[r][c].hasCharacter ()) {
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
      setMatrixWidth (maxWidth - minWidth + 1);
      setMatrixHeight (maxHeight - minHeight + 1);
      PuzzleCell [][] newMatrix = new PuzzleCell [getMatrixHeight ()] [getMatrixWidth ()];
      for (int r = 0; r < getMatrixHeight (); ++r) {
        for (int c = 0; c < getMatrixWidth (); ++c) {
          newMatrix[r][c] = getMatrix ()[r + minHeight][c + minWidth];
        }
      }
      ArrayList <PuzzleWord> temp = new ArrayList <PuzzleWord> ();
      for (PuzzleWord w : puzzleWords) {
        PuzzleWord p = new PuzzleWord ();
        p.setColumn (w.getColumn () - minWidth);
        p.setRow (w.getRow () - minHeight);
        p.setDirection (w.getDirection ());
        p.setWord (w.getWord ());
        temp.add (p);
      }
      setMatrix (newMatrix);
      setNumWords (temp.size ());
      setWordList (temp);
      Collections.shuffle (getWordList ());
    }
    firstWord = true;
    System.out.println ("Took " + total + " passes.");
  }

  /**
   * Gets the puzzle as a string
   * 
   * @return s - Returns the puzzle as a string
   */
  public String toString () {
    String s = "";
    for (int c = 0; c < getMatrixHeight (); c++) {
      for (int r = 0; r < getMatrixWidth (); r++) {
        s += getMatrix ()[c][r] + " ";
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
    Direction dir = word.getDirection ();
    String w = word.getWord ();
    int dC = (dir == Direction.EAST) ? 1 : 0, dR = (dir == Direction.SOUTH) ? 1 : 0;
    int row = word.getRow (), col = word.getColumn (), length = w.length (), oldRow = row, oldCol = col;
    boolean isCrossed = false, valid = true;
    char character;
    if (firstWord) {
      isCrossed = true;
      firstWord = false;
    } else {
      try {
        if (getMatrix ()[row - dR][col - dC].hasCharacter ()) {
          return false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {
      }
      try {
        if (getMatrix ()[row + dR * length][col + dC * length].hasCharacter ()) {
          return false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {
      }
      for (int i = 0; valid && i < w.length (); i++, col += dC, row += dR) {
        character = w.charAt (i);
        if (getMatrix ()[row][col].isEmpty ()) {
          try {
            if (getMatrix ()[row - dC][col - dR].hasCharacter ()) {
              return false;
            }
          } catch (ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (getMatrix ()[row + dC][col + dR].hasCharacter ()) {
              return false;
            }
          } catch (ArrayIndexOutOfBoundsException e) {
          }
        } else {
          if (!getMatrix ()[row][col].hasCharacter (character)) {
            return false;
          } else {
            if (getMatrix ()[row][col].hasDirection (dir)) {
              return false;
            }
            if (!isCrossed && getMatrix ()[row][col].getNumWords () > 0) {
              isCrossed = true;
            }
          }
        }
      }
    }
    row = oldRow;
    col = oldCol;
    if (isCrossed) {
      for (int i = 0; i < length; ++i, col += dC, row += dR) {
        getMatrix ()[row][col].add (w.charAt (i), dir);
      }
    }
    return isCrossed;
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
}
