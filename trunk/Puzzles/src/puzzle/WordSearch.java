package puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
    reset();
  }
  
  public WordSearch (Scanner scan) {
    load(scan);
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
  
  @Override
  public String export (boolean isPuzzle) {
    String s = "<html><body><h1>Sad Panda Software Word Search</h1>\n";
    s += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
    for (int r = -1; r <= getMatrixHeight(); r++) {
      s += "<tr>";
      for (int c = -1; c <= getMatrixWidth(); c++) {
        try {
          if (getMatrixElement(r,c).getNumWords () > 0 || isPuzzle) {
            s += "<td><center><tt><big>" + getMatrixElement(r,c) + "</big></tt></center></td>";
          } else {
            s += "<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>";
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          s += "<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>";
        }
      }
      s += "</tr>\n";
    }
    s += "</table>\n<br><br>\n";
    
    s += "<table border=\"0\">\n";
    if (!isPuzzle) {
      s += "<tr><td><b>Word</b></td><td><b>Direction</b></td><td><b>Row</b></td><td><b>Col</b></td></tr>\n";
    }
    for (PuzzleWord word : getPuzzleWordList ()) {
      s += "<tr>";
      s += "<td>" + word.getWord ().toLowerCase () + "</td>";
      if (!isPuzzle) {
        s += "<td>" + word.getDirection ().name ().toLowerCase () + "</td><td>" + word.getRow () + "</td><td>" + word.getColumn () + "</td>";
      }
      s += "</tr>\n";
    }
    s += "</body></html>";
    return s;
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
  
  @Override
  public void load (Scanner scan) {
    reset();
    Scanner scan2 = new Scanner (scan.nextLine ());
    processFileHeader (scan, scan2);
    for (int r = 0; r < getMatrixHeight (); r++) {
      scan2 = new Scanner (scan.nextLine ());
      for (int c = 0; c < getMatrixWidth (); c++) {
        PuzzleCell cell = new PuzzleCell();
        cell.setCharacter (scan2.next ().charAt (0));
        cell.setNumWords (scan2.nextInt ());
        setMatrixElement (r, c, cell);
      }
    }
  }

  /**
   * generates a string for saving the word search puzzle
   */
  public String save () {
    String s = "wordsearch\n";
    s += getNumWords () + "\n";
    s += getMatrixHeight () + "\n";
    s += getMatrixWidth () + "\n";
    for (PuzzleWord word : getPuzzleWordList ()) {
      s += word.getWord () + " " + word.getRow () + " " + word.getColumn () + " " + word.getDirection ().ordinal () + "\n";
    }
    for (int r = 0; r < getMatrixHeight (); r++) {
      for (int c = 0; c < getMatrixWidth (); c++) {
        s += getMatrixElement(r, c) + " " + getMatrixElement(r,c).getNumWords () + " ";
      }
      s += "\n";
    }
    return s;
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
           point[1] -= super.getNumberGenerator ().nextInt (col - length);
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
          point[1] -= super.getNumberGenerator ().nextInt (col - length);
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
          point[0] -= super.getNumberGenerator ().nextInt (row - length);
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
          point[0] -= super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        point[1] = super.getNumberGenerator ().nextInt (col);
        break;
      case NORTHWEST:
        point[0] = length - 1;
        point[1] = length - 1;
        try {
          point[0] -= super.getNumberGenerator ().nextInt (row - length);
        } catch (IllegalArgumentException e) {
        }
        try {
          point[1] -= super.getNumberGenerator ().nextInt (col - length);
        } catch (IllegalArgumentException e) {
        }
        break;
    }
    return (point);
  }
}
