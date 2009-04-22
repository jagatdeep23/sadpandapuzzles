package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import puzzle.Crossword;
import puzzle.Puzzle;
import puzzle.PuzzleCell;
import puzzle.PuzzleWord;
import puzzle.WordSearch;
import puzzle.Puzzle.Direction;

/**
 * This class contains all of the File Input and Output methods.
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class FileIO {
  
  /** A file chooser */
  private static JFileChooser    chooser;
  
  /**
   * The array list of words
   */
  private static ArrayList <String> words = new ArrayList <String> ();
  
  /** The file that is to be loaded */
  private static File            file;
  
  /**
   * Initiates the export to HTML process to export a puzzle and its state to an
   * HTML file
   * 
   * @param puzzle
   *          a puzzle
   */
  public static void exportPuzzle (Puzzle puzzle) {
    try {
      //boolean type = showExportType();
      int status;
      File newFile = new File ("empty");
      status = getFileChooser ().showSaveDialog (null);

      if (status == JFileChooser.APPROVE_OPTION) {
        newFile = getFileChooser ().getSelectedFile ();
        if (puzzle instanceof WordSearch) {
          try {
            exportWordSearchHTML (puzzle, new File (newFile.toString () + " puzzle.html"), true);
            exportWordSearchHTML (puzzle, new File (newFile.toString () + " solution.html"), false);
          } catch (IOException e) {
            JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
          }
        } else if (puzzle instanceof Crossword) {
          try {
            exportCrosswordHTML (puzzle, new File (newFile.toString () + " puzzle.html"), true);
            exportCrosswordHTML (puzzle, new File (newFile.toString () + " solution.html"), false);
          } catch (IOException e) {
            JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
          }
        } else {
          
        }
      }
    } catch (IllegalStateException e) {}
    
  }

  /**
   * Returns a list of words sorted by length (longest first)
   * 
   * @return ArrayList<String> - A list of words
   */
  public static ArrayList <String> getFile () {
    words.clear ();
    int status;
    status = getFileChooser ().showOpenDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      file = getFileChooser ().getSelectedFile ();
    } else {
      return words;
    }
    try {
      getWords (file);
    } catch (IOException e) {
      JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
    }
    return words;
  }
  
  /**
   * Initiates the load puzzle functions to load a puzzle and its state from a
   * file
   * 
   * @return puzzle - The puzzle that is loaded
   */
  public static Puzzle loadPuzzle () {
    int status;
    status = getFileChooser ().showOpenDialog (null);
    Puzzle puzzle = null;
    Scanner scan = null;
    String type = "";
    
    if (status == JFileChooser.APPROVE_OPTION) {
      file = getFileChooser ().getSelectedFile ();
    } else {
      return puzzle;
    }
    try {
      scan = new Scanner (file);
      type = scan.nextLine ();
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
    }
    
    if (type.equals ("wordsearch")) {
      puzzle = loadWordSearch (scan);
      return puzzle;
    } else if (type.equals ("crossword")) {
      puzzle = loadCrossword (scan);
      return puzzle;
    } else {
      JOptionPane.showMessageDialog (null, "The file you have loaded cannot be recognized", "Oh Noes!", JOptionPane.ERROR_MESSAGE);
      return puzzle;
    }
  }

  /**
   * Will call the appropriate save function based on whether or not a file has
   * been associated yet. If one has not, a new file will be created.
   * 
   * @param list
   *          Vector<String>
   * @throws IOException
   */
  public static void saveWords (ArrayList <String> list) {
    if (file != null) {
      try {
        save (list, file);
      } catch (IOException e) {
      }
    } else
      saveWordsAs (list);
  }

  /**
   * Initiates the save functions to save the state of the current puzzle
   * 
   * @param puzzle
   */
  public static void savePuzzle (Puzzle puzzle) {
    int status;
    File newFile = new File ("empty");
    status = getFileChooser ().showSaveDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      newFile = getFileChooser ().getSelectedFile ();
      if (puzzle instanceof WordSearch) {
        try {
          saveWordSearch (puzzle, newFile);
        } catch (IOException e) {
          JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
        }
      } else if (puzzle instanceof Crossword) {
        try {
          saveCrossword (puzzle, newFile);
        } catch (IOException e) {
        }
      } else {
        JOptionPane.showMessageDialog (null, "Please generate a puzzle before saving, then try again", "Oh Noes!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Exports the current Crossword puzzle and its state to an HTML file
   * 
   * @param puzzle
   *          a puzzle
   * @param location
   *          a file
   * @param isPuzzle
   *          boolean flag for export type
   * @throws IOException
   */
  private static void exportCrosswordHTML (Puzzle puzzle, File location, boolean isPuzzle) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    ArrayList <PuzzleWord> list = puzzle.getPuzzleWordList ();
    Collections.shuffle (list);
    PuzzleCell [][] matrix = puzzle.getMatrix ();
    String s = "";
    s += "<html>\n<body>\n<h1>Sad Panda Software Crossword</h1>\n<table border=\"1\" bordercolor=\"000000\" borderstyle=\"solid\" cellpadding=\"0\" cellspacing=\"0\">";
    for (int r = -1; r <= matrix[0].length; r++) {
      s += "<tr>";
      for (int c = -1; c <= matrix.length; c++) {
        try {
          if (matrix[c][r].isEmpty ()) {
            s += "<td bgcolor=\"black\">&nbsp&nbsp&nbsp&nbsp&nbsp</td>";
          } else {
            s += "<td bgcolor=\"white\"> ";
            if (isPuzzle) {
              s += "&nbsp&nbsp&nbsp&nbsp&nbsp";
            } else {
              s += "<center><b>" + matrix[c][r]+ "</b></center>";
            }
            s += " </td>";
          }
        }catch (ArrayIndexOutOfBoundsException e) {
          s += "<td bgcolor=\"black\">&nbsp&nbsp&nbsp&nbsp&nbsp</td>";
        }
      }
      s += "</tr>\n";
    }
    s += "</table>\n<br><br>\n";
    buffer.write (s);
    
    if (isPuzzle) {
      buffer.write ("<b>South</b><br>\n");
      for (PuzzleWord word : list) {
        if (word.getDirection ().name ().toLowerCase ().equals ("east")) {
          buffer.write (word.getWord ().toLowerCase () + "<br>\n");
        }
      }
      buffer.write ("<br>\n");
      
      buffer.write ("<b>East</b><br>\n");
      for (PuzzleWord word : list) {
        if (word.getDirection ().name ().toLowerCase ().equals ("south")) {
          buffer.write (word.getWord ().toLowerCase () + "<br>\n");
        }
      }
      buffer.write ("<br>\n");
    }
    buffer.close ();
    
  }

  /**
   * Exports the current Word Search puzzle and its state to an HTML file
   * 
   * @param puzzle
   *          a puzzle
   * @param location
   *          a file
   * @param isPuzzle
   *          boolean flag for export type
   * @throws IOException
   */
  private static void exportWordSearchHTML (Puzzle puzzle, File location, boolean isPuzzle) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    ArrayList <PuzzleWord> list = puzzle.getPuzzleWordList ();
    Collections.shuffle (list);
    PuzzleCell [][] matrix = puzzle.getMatrix ();
    
    String s = "<html><body><h1>Sad Panda Software Word Search</h1>\n";
    
    s += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
    for (int r = -1; r <= matrix[0].length; r++) {
      s += "<tr>";
      for (int c = -1; c <= matrix.length; c++) {
        try {
          if (matrix[c][r].getNumWords () > 0 || isPuzzle) {
          s += "<td><center><tt><big>" + matrix[c][r]+ "</big></tt></center></td>";
          } else {
            s += "<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>";
          }
        }catch (ArrayIndexOutOfBoundsException e) {
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
    for (PuzzleWord word : list) {
      s += "<tr>";
      s += "<td>" + word.getWord ().toLowerCase() + "</td>";
      if (!isPuzzle) {
        s+= "<td>" + word.getDirection ().name ().toLowerCase () + "</td><td>" + word.getRow () + "</td><td>" + word.getColumn () + "</td>";
      }
      s += "</tr>\n";
    }
    s += "</body></html>";
    buffer.write (s);
    buffer.close ();
  }

  /**
   * Creates a new JFileChooser
   * 
   * @return JFileChooser
   */
  private static JFileChooser getFileChooser () {
    if (chooser == null) {
      chooser = new JFileChooser ();
    }
    return (chooser);
  }
  
  /**
   * Processes a list of words from a file.
   * 
   * @param input
   *          a File
   * @throws IOException
   */
  private static void getWords (File input) throws IOException {
    FileReader fileReader = new FileReader (input);
    BufferedReader buffer = new BufferedReader (fileReader);
    String temp;
    
    while ((temp = buffer.readLine ()) != null)
      words.add (temp);
    buffer.close ();
  }
  
  /**
   * Loads a Crossword puzzle and its state
   * 
   * @param scan
   *          a scanner
   * @return Puzzle a Crossword puzzle
   */
  private static Puzzle loadCrossword (Scanner scan) {
    Puzzle puzzle = new Crossword ();
    int height, width;
    Scanner scan2 = new Scanner (scan.nextLine ());
    puzzle.setNumWords (scan2.nextInt ());
    scan2 = new Scanner (scan.nextLine ());
    height = scan2.nextInt ();
    puzzle.setMatrixHeight (height);
    scan2 = new Scanner (scan.nextLine ());
    width = scan2.nextInt ();
    puzzle.setMatrixWidth (width);
    PuzzleCell [][] matrix = new PuzzleCell [height] [width];
    puzzle.setMatrix (matrix);
    ArrayList <PuzzleWord> words = new ArrayList <PuzzleWord> ();
    ArrayList <String> w = new ArrayList <String>();
    for (int i = 0; i < puzzle.getNumWords (); i++) {
      PuzzleWord word = new PuzzleWord ();
      scan2 = new Scanner (scan.nextLine ());
      String s = scan2.next ();
      word.setWord (s);
      w.add (s);
      word.setRow (scan2.nextInt ());
      word.setColumn (scan2.nextInt ());
      word.setDirection (Direction.values ()[scan2.nextInt ()]);
      words.add (word);
      
    }
    puzzle.setWordList (words);
    puzzle.setList (w);
    for (int r = 0; r < matrix.length; r++) {
      scan2 = new Scanner (scan.nextLine ());
      for (int c = 0; c < matrix[0].length; c++) {
        matrix[r][c] = new PuzzleCell ();
        char t = scan2.next ().charAt (0);
        if (t != '?') {
          matrix[r][c].setCharacter (t);
        }
      }
    }
    puzzle.setMatrix (matrix);
    return puzzle;
  }

  /**
   * Loads a Word Search puzzle and its state from a file
   * 
   * @param scan
   *          a scanner
   * @return Puzzle a Word Search puzzle
   */
  private static Puzzle loadWordSearch (Scanner scan) {
    Puzzle puzzle = new WordSearch ();
    
    int height, width;
    Scanner scan2 = new Scanner (scan.nextLine ());
    puzzle.setNumWords (scan2.nextInt ());
    scan2 = new Scanner (scan.nextLine ());
    height = scan2.nextInt ();
    puzzle.setMatrixHeight (height);
    scan2 = new Scanner (scan.nextLine ());
    width = scan2.nextInt ();
    puzzle.setMatrixWidth (width);
    ArrayList <PuzzleWord> words = new ArrayList <PuzzleWord> ();
    ArrayList <String> w = new ArrayList <String>();
    for (int i = 0; i < puzzle.getNumWords (); i++) {
      PuzzleWord word = new PuzzleWord ();
      scan2 = new Scanner (scan.nextLine ());
      String s = scan2.next ();
      word.setWord (s);
      w.add (s);
      word.setRow (scan2.nextInt ());
      word.setColumn (scan2.nextInt ());
      word.setDirection (Direction.values ()[scan2.nextInt ()]);
      words.add (word);
    }
    puzzle.setWordList (words);
    puzzle.setList (w);
    PuzzleCell [][] matrix = new PuzzleCell [height] [width];
    for (int r = 0; r < matrix.length; r++) {
      scan2 = new Scanner (scan.nextLine ());
      for (int c = 0; c < matrix[0].length; c++) {
        matrix[r][c] = new PuzzleCell ();
        matrix[r][c].setCharacter (scan2.next().charAt (0));
        matrix[r][c].setNumWords (scan2.nextInt ());
      }
    }
    puzzle.setMatrix (matrix);
    return puzzle;
  }

  /**
   * Will perform the actual save of the word list to the location provided.
   * 
   * @param list
   *          ArrayList<String>
   * @param location
   *          Location of file
   * @throws IOException
   */
  private static void save (ArrayList <String> list, File location) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    for (String word : list) {
      buffer.write (word + "\n");
    }
    buffer.close ();
  }
  
  /**
   * Saves the state of the current Crossword Puzzle to a file
   * 
   * @param puzzle
   * @param location
   * @throws IOException
   */
  private static void saveCrossword (Puzzle puzzle, File location) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    ArrayList <PuzzleWord> list = puzzle.getPuzzleWordList ();
    PuzzleCell [][] matrix = puzzle.getMatrix ();
    buffer.write ("crossword\n");
    buffer.write (puzzle.getNumWords () + "\n");
    buffer.write (puzzle.getMatrixHeight () + "\n");
    buffer.write (puzzle.getMatrixWidth () + "\n");
    for (PuzzleWord word : list) {
      buffer.write (word.getWord () + " " + word.getRow () + " " + word.getColumn () + " " + word.getDirection ().ordinal () + "\n");
    }
    String s = "";
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        s += matrix[r][c] + " ";
      }
      s += "\n";
    }
    buffer.write (s);
    buffer.close ();
  }

  /**
   * Saves the state of the current Word Search puzzle to a file
   * 
   * @param puzzle
   * @param location
   * @throws IOException
   */
  private static void saveWordSearch (Puzzle puzzle, File location) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    ArrayList <PuzzleWord> list = puzzle.getPuzzleWordList ();
    PuzzleCell [][] matrix = puzzle.getMatrix ();
    buffer.write ("wordsearch\n");
    buffer.write (puzzle.getNumWords () + "\n");
    buffer.write (puzzle.getMatrixHeight () + "\n");
    buffer.write (puzzle.getMatrixWidth () + "\n");
    for (PuzzleWord word : list) {
      buffer.write (word.getWord () + " " + word.getRow () + " " + word.getColumn () + " " + word.getDirection ().ordinal () + "\n");
    }
    String s = "";
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        s += matrix[r][c] + " " + matrix[r][c].getNumWords () + " ";
      }
      s += "\n";
    }
    buffer.write (s);
    buffer.close ();
  }
  
  /**
   * Creates a new file and calls the save function to save the list of words to
   * the new file.
   * 
   * @param list
   *          ArrayList<String>
   * @throws IOException
   */
  private static void saveWordsAs (ArrayList <String> list) {
    int status;
    File newFile = new File ("empty");
    status = getFileChooser ().showSaveDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      newFile = getFileChooser ().getSelectedFile ();
      try {
        save (list, newFile);
      } catch (IOException e) {
        JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
