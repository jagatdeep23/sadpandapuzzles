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
          Direction dir = generateDirection (8);
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
    
    int dC = super.getColumnChange (dir);
    int dR = super.getRowChange (dir);
    int row = word.getRow ();
    int col = word.getColumn ();
    int length = w.length ();
    int oldRow = row;
    int oldCol = col;
    
    boolean isCrossed = false;
    boolean valid = true;
    
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

  private void removeWordFromPuzzle (PuzzleWord pw) {
    int r = pw.getRow ();
    int c = pw.getColumn ();
    int dr = (pw.getDirection () == Direction.SOUTH) ? 1 : 0;
    int dc = (pw.getDirection () == Direction.SOUTH) ? 0 : 1;
    for (int k = 0; k < pw.getWord ().length (); ++k, r += dr, c += dc) {
      getMatrix()[r][c].remove (pw.getDirection ());
    }
  }

  /**
   * trims the matrix to size
   * @param puzzleWords the list of puzzleWords
   */
  private void trim(ArrayList<PuzzleWord> puzzleWords) {
    int minWidth = 999, maxWidth = -1, minHeight = 999, maxHeight = -1;
    for (PuzzleWord word : puzzleWords) {
      int dC = getColumnChange(word.getDirection ());
      int dR = getRowChange(word.getDirection ());
      minWidth = Math.min (minWidth, word.getColumn ());
      maxWidth = Math.max (maxWidth, word.getColumn () + dC);
      minHeight = Math.min (minHeight, word.getRow ());
      maxHeight = Math.max (maxHeight, word.getRow () + dR);
    }
    
    setMatrixWidth (maxWidth - minWidth + 1);
    setMatrixHeight (maxHeight - minHeight + 1);
    PuzzleCell [][] newMatrix = new PuzzleCell [getMatrixHeight ()] [getMatrixWidth ()];
    for (int r = 0; r < getMatrixHeight (); ++r) {
      for (int c = 0; c < getMatrixWidth (); ++c) {
        newMatrix[r][c] = getMatrix ()[r + minHeight][c + minWidth];
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
