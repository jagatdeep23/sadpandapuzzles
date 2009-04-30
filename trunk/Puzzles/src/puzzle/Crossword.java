package puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
    super.reset();
  }
  
  public Crossword (Scanner scan) {
    load(scan);
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
  
  @Override
  public String export (boolean isPuzzle) {
    String s = "<html>\n<body>\n<h1>Sad Panda Software Crossword</h1>\n<table border=\"1\" bordercolor=\"000000\" borderstyle=\"solid\" cellpadding=\"0\" cellspacing=\"0\">";
    for (int r = -1; r <= getMatrixHeight (); r++) {
      s += "<tr>";
      for (int c = -1; c <= getMatrixWidth (); c++) {
        try {
          if (getMatrixElement(r, c).isEmpty ()) {
            s += "<td bgcolor=\"black\">&nbsp&nbsp&nbsp&nbsp&nbsp</td>";
          } else {
            s += "<td bgcolor=\"white\"> ";
            if (isPuzzle) {
              s += "&nbsp&nbsp&nbsp&nbsp&nbsp";
            } else {
              s += "<center><b>" + getMatrixElement(r,c) + "</b></center>";
            }
            s += " </td>";
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          s += "<td bgcolor=\"black\">&nbsp&nbsp&nbsp&nbsp&nbsp</td>";
        }
      }
      s += "</tr>\n";
    }
    s += "</table>\n<br><br>\n";
    if (isPuzzle) {
      s += "<b>South</b><br>\n";
      for (PuzzleWord word : getPuzzleWordList ()) {
        if (word.getDirection ().name ().toLowerCase ().equals ("east")) {
          s += word.getWord ().toLowerCase () + "<br>\n";
        }
      }
      s += "<br>\n<b>East</b><br>\n";
      for (PuzzleWord word : getPuzzleWordList ()) {
        if (word.getDirection ().name ().toLowerCase ().equals ("south")) {
          s += word.getWord ().toLowerCase () + "<br>\n";
        }
      }
      s += "<br>\n";
    }
    return s;
  }

  /** generates a crossword puzzle */
  public void generate () {
    if (getWordList ().size () > 0) {
      Collections.sort (getWordList (), new shared.Algorithms.SortByLineLength ());
      int length = generateDimension (getWordList ()), attempts = 0;
      super.initializeMatrix(length);
      ArrayList <PuzzleWord> puzzleWords = new ArrayList <PuzzleWord> ();
      ArrayList <String> list = new ArrayList<String>(getWordList());
      boolean isValid;
      firstWord = true;
      int limit = list.size() * list.size() / 2;
      int test = 0;
      int words = 0;
      JDialog popup = new JDialog();
      JProgressBar bar = new JProgressBar(0, list.size ());
      super.buildPopup (popup, bar);
      long time = System.currentTimeMillis ();
      while (list.size () != 0) {
        isValid = false;
        attempts = 0;
        while (!isValid) {
          String word = list.get (0);
          Direction dir = super.generateDirection (2);
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
              
              super.updateProgressBar (bar, words, "We might not be able to get through this...");
            }
            if ((System.currentTimeMillis () - time) >= 10000 || list.size() == 1) {
              popup.dispose ();
              JOptionPane.showMessageDialog (null, "This program cannot create a puzzle from your input!\nPlease remove word(s) and try again.", "Oh No!",
                  JOptionPane.ERROR_MESSAGE);
              super.reset();
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

  @Override
  public void load (Scanner scan){
    reset();
    Scanner scan2 = new Scanner (scan.nextLine ());
    processFileHeader (scan, scan2);
    for (int r = 0; r < getMatrixHeight (); r++) {
      scan2 = new Scanner (scan.nextLine ());
      for (int c = 0; c < getMatrixWidth (); c++) {
        setMatrixElement (r, c, new PuzzleCell());
        char t = scan2.next ().charAt (0);
        if (t != '?') {
          getMatrixElement(r,c).setCharacter (t);
        }
      }
    }
  }

  /**
   * generates a string for saving the crossword puzzle
   */
  public String save () {
    String s = "crossword\n";
    s += getNumWords () + "\n";
    s += getMatrixHeight () + "\n";
    s += getMatrixWidth () + "\n";
    for (PuzzleWord word : getPuzzleWordList ()) {
      s += word.getWord () + " " + word.getRow () + " " + word.getColumn () + " " + word.getDirection ().ordinal () + "\n";
    }
    for (int r = 0; r < getMatrixHeight (); r++) {
      for (int c = 0; c < getMatrixWidth (); c++) {
        s += getMatrixElement(r, c) + " ";
      }
      s += "\n";
    }
    return s;
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
      int dC = super.getColumnChange (dir);
      int dR = super.getRowChange (dir);
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
    int dC = super.getColumnChange (dir);
    int dR = super.getRowChange (dir);
    boolean valid = false;
    for (int i = 0; i < w.length (); i++, col += dC, row += dR) {
      char character = w.charAt (i);
      if (!getMatrixElement (row, col).isEmpty ()) {
        if (getMatrixElement (row, col).hasDirection (dir)) {
          return false;
        } else {
          if (getMatrixElement (row, col).getCharacter () == character) {  
            valid = true;
          } else {
            return false;
          }
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
    int dC = super.getColumnChange (dir);
    int dR = super.getRowChange (dir);
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
          point[0] = super.getNumberGenerator ().nextInt (colSize - length);
          point[1] = super.getNumberGenerator ().nextInt (rowSize);
        }
        break;
      case SOUTH:
        if (firstWord) {
          point[0] = colSize / 2;
          point[1] = (rowSize / 2) - (length / 2);
        } else {
          point[0] = super.getNumberGenerator ().nextInt (colSize);
          point[1] = super.getNumberGenerator ().nextInt (rowSize - length);
        }
        break;
    }
    return (point);
  }

  /**
   * 
   * @param pw the puzzle word to remove
   */
  protected void removeWordFromPuzzle (PuzzleWord pw) {
    int r = pw.getRow ();
    int c = pw.getColumn ();
    int dr = super.getRowChange (pw.getDirection());
    int dc = super.getColumnChange (pw.getDirection());
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
      int dC = super.getColumnChange(word.getDirection ());
      int dR = super.getRowChange(word.getDirection ());
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
