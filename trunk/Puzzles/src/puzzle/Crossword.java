package puzzle;

import java.awt.Color;
import java.awt.Graphics;
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
    matrix = null;
    wordList = null;
    numWords = 0;
    height = 0;
    width = 0;
  }
  
  /**
   * Adds and word and validates to ensure that it will fit into the grid
   * 
   * @param word
   *          puzzleword to be added.
   * @return boolean Whether the add was a success or not.
   */
  public boolean addAndValidate (PuzzleWord word) {
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
        if (matrix[row - dR][col - dC].hasCharacter ()) {
          return false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {
      }
      try {
        if (matrix[row + dR * length][col + dC * length].hasCharacter ()) {
          return false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {
      }
      for (int i = 0; valid && i < w.length (); i++, col += dC, row += dR) {
        character = w.charAt (i);
        if (matrix[row][col].isEmpty ()) {
          try {
            if (matrix[row - dC][col - dR].hasCharacter ()) {
              return false;
            }
          } catch (ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (matrix[row + dC][col + dR].hasCharacter ()) {
              return false;
            }
          } catch (ArrayIndexOutOfBoundsException e) {
          }
        } else {
          if (!matrix[row][col].hasCharacter (character)) {
            return false;
          } else {
            if (matrix[row][col].hasDirection (dir)) {
              return false;
            }
            if (!isCrossed && matrix[row][col].getNumWords () > 0) {
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
        matrix[row][col].add (w.charAt (i));
      }
    }
    return isCrossed;
  }
  
  /** draws the crossword puzzle */
  public void draw (Graphics g) {
    g.setColor (Color.WHITE);
    g.drawRect (0, 0, 5000, 5000);
    g.setColor (Color.BLACK);
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        if (matrix[r][c].hasCharacter ()) {
          g.drawRect (30 + 24 * r, 30 + 24 * c, 24, 24);
          g.drawString (matrix[r][c].toString (), 30 + 24 * r + 8, 30 + 24 * c + 15);
        }
      }
    }
  }
  
  /** generates a crossword puzzle */
  public void generate () {
    if (words.size () > 0) {
      int length = generateDimension (words), crazy = 0;
      height = width = length;
      Collections.sort (words, new shared.Algorithms.SortByLineLength ());
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      boolean isValid;
      firstWord = true;
      matrix = new PuzzleCell [height] [width];
      fillMatrix (length, true);
      for (String word : words) {
        isValid = false;
        crazy = 0;
        System.out.println (word + " " + word.length () + " " + height + " x " + width);
        while (!isValid) {
          Direction dir = generateDirection (2);
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
          if (++crazy == 200000) {
            JOptionPane.showMessageDialog (null, "This program cannot create a puzzle from your input!\nPlease remove word(s) and try again.", "Oh No!",
                JOptionPane.ERROR_MESSAGE);
            matrix = null;
            wordList = null;
            numWords = 0;
            height = 0;
            width = 0;
            return;
          }
        }
      }
      int minWidth = -1, maxWidth = -1, minHeight = -1, maxHeight = -1;
      for (int r = 0; r < matrix.length; ++r) {
        for (int c = 0; c < matrix[0].length; ++c) {
          if (matrix[r][c].hasCharacter ()) {
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
      PuzzleCell [][] newMatrix = new PuzzleCell [height][width];
      for (int r = 0; r < height; ++r) {
        for (int c = 0; c < width; ++c) {
          newMatrix[r][c] = matrix[r + minHeight][c + minWidth];
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
      matrix = newMatrix;
      this.numWords = temp.size ();
      this.wordList = temp;
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
    for (int c = 0; c < matrix.length; c++) {
      for (int r = 0; r < matrix[0].length; r++) {
        s += matrix[c][r] + " ";
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
