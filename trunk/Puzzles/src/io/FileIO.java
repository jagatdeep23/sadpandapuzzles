package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import puzzle.Crossword;
import puzzle.Puzzle;
import puzzle.PuzzleCell;
import puzzle.PuzzleWord;
import puzzle.WordSearch;
import puzzle.Direction;

/**
 * This class contains all of the File Input and Output methods.
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class FileIO {
  
  /** A file chooser */
  private static JFileChooser chooser;
  
  /** The file that is to be loaded */
  private static File         file;
  
  /** the puzzle */
  private Puzzle              puzzle;
  
  /**
   * Default Constructor
   */
  public FileIO () {
    this (null);
  }
  
  /**
   * Creates a new FileIO manager with a puzzle
   */
  public FileIO (Puzzle puzzle) {
    setPuzzle (puzzle);
  }
  
  /**
   * sets the puzzle of the FileIO manager
   */
  public void setPuzzle (Puzzle p) {
    puzzle = p;
  }
  
  /**
   * Initiates the export to HTML process to export a puzzle and its state to an
   * HTML file
   */
  public void exportPuzzle () {
    if (puzzle != null && puzzle.getNumWords () > 0) {
        if (getFileChooser ().showSaveDialog (null) == JFileChooser.APPROVE_OPTION) {
          File newFile = getFileChooser ().getSelectedFile ();
          try {
            FileWriter writer = new FileWriter (newFile + " puzzle.html");
            writer.write (puzzle.export (true));
            writer.close ();
            
            writer = new FileWriter (newFile + " solution.html");
            writer.write (puzzle.export (false));
            writer.close ();
          } catch (IOException e) {
            JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
          }
        }
    } else {
      JOptionPane.showMessageDialog (null, "Please Generate a Puzzle before Exporting", "Error!", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  /**
   * Returns a list of words sorted by length (longest first)
   * 
   * @return ArrayList<String> - A list of words
   */
  public ArrayList <String> loadWordList () {
    ArrayList <String> words = new ArrayList <String> ();
    if (getFileChooser ().showOpenDialog (null) == JFileChooser.APPROVE_OPTION) {
      file = getFileChooser ().getSelectedFile ();
    } else {
      return words;
    }
    if (file != null) {
      try {
        FileReader fileReader = new FileReader (file);
        BufferedReader buffer = new BufferedReader (fileReader);
        String temp;
        while ((temp = buffer.readLine ()) != null)
          words.add (temp);
        buffer.close ();
        return words;
      } catch (IOException e) {
        JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
    return words;
  }
  
  /**
   * grabs the puzzle saved in the state of the manager
   * 
   * @return a puzzle
   */
  public Puzzle getPuzzle () {
    return puzzle;
  }
  
  /**
   * Initiates the load puzzle functions to load a puzzle and its state from a
   * file
   * 
   * @return puzzle - The puzzle that is loaded
   */
  public void loadPuzzle () {
    int status;
    status = getFileChooser ().showOpenDialog (null);
    Scanner scan = null;
    String type = "";
    if (status == JFileChooser.APPROVE_OPTION) {
      file = getFileChooser ().getSelectedFile ();
      if (file != null) {
        try {
          scan = new Scanner (file);
          type = scan.nextLine ();
        } catch (FileNotFoundException e) {
          JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
        }
        if (type.equals ("wordsearch")) {
          loadWordSearch (scan);
        } else if (type.equals ("crossword")) {
          loadCrossword (scan);
        } else {
          JOptionPane.showMessageDialog (null, "The file you have loaded cannot be recognized", "Oh Noes!", JOptionPane.ERROR_MESSAGE);
        }
      }
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
  public void saveWords (ArrayList <String> list) {
    if (getFileChooser ().showSaveDialog (null) == JFileChooser.APPROVE_OPTION) {
      File newFile = getFileChooser ().getSelectedFile ();
      try {
        BufferedWriter buffer = new BufferedWriter (new FileWriter (newFile));
        for (String word : list) {
          buffer.write (word + "\n");
        }
        buffer.close ();
      } catch (IOException e) {
        JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  /**
   * Initiates the save functions to save the state of the current puzzle
   * 
   * @param puzzle
   */
  public void savePuzzle () {
    int status;
    File newFile = new File ("empty");
    status = getFileChooser ().showSaveDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      try {
      newFile = getFileChooser ().getSelectedFile ();
      BufferedWriter buffer = new BufferedWriter (new FileWriter (newFile));
      String s = puzzle.save ();
      buffer.write (s);
      buffer.close ();
      } catch (IOException e) {
        
      }
    }
  }
  
  /**
   * Creates a new JFileChooser
   * 
   * @return JFileChooser
   */
  private JFileChooser getFileChooser () {
    if (chooser == null) {
      chooser = new JFileChooser ();
    }
    return (chooser);
  }
  
  /**
   * Loads a Crossword puzzle and its state
   * 
   * @param scan
   *          a scanner
   * @return Puzzle a Crossword puzzle
   */
  private void loadCrossword (Scanner scan) {
    puzzle = new Crossword ();
    int height, width;
    Scanner scan2 = new Scanner (scan.nextLine ());
    puzzle.setNumWords (scan2.nextInt ());
    scan2 = new Scanner (scan.nextLine ());
    height = scan2.nextInt ();
    puzzle.setMatrixHeight (height);
    scan2 = new Scanner (scan.nextLine ());
    width = scan2.nextInt ();
    puzzle.setMatrixWidth (width);
    puzzle.buildMatrix ();
    ArrayList <PuzzleWord> words = new ArrayList <PuzzleWord> ();
    ArrayList <String> w = new ArrayList <String> ();
    for (int i = 0; i < puzzle.getNumWords (); i++) {
      scan2 = new Scanner (scan.nextLine ());
      String s = scan2.next ();
      w.add (s);
      words.add (new PuzzleWord (scan2.nextInt (), scan2.nextInt (), Direction.values ()[scan2.nextInt ()], s));
    }
    puzzle.setWordList (words);
    puzzle.setList (w);
    for (int r = 0; r < puzzle.getMatrixHeight (); r++) {
      scan2 = new Scanner (scan.nextLine ());
      for (int c = 0; c < puzzle.getMatrixWidth (); c++) {
        puzzle.setMatrixElement (r, c, new PuzzleCell());
        char t = scan2.next ().charAt (0);
        if (t != '?') {
          puzzle.getMatrixElement(r,c).setCharacter (t);
        }
      }
    }
  }
  
  /**
   * Loads a Word Search puzzle and its state from a file
   * 
   * @param scan
   *          a scanner
   * @return Puzzle a Word Search puzzle
   */
  private void loadWordSearch (Scanner scan) {
    puzzle = new WordSearch ();
    Scanner scan2 = new Scanner (scan.nextLine ());
    puzzle.setNumWords (scan2.nextInt ());
    scan2 = new Scanner (scan.nextLine ());
    puzzle.setMatrixHeight (scan2.nextInt ());
    scan2 = new Scanner (scan.nextLine ());
    puzzle.setMatrixWidth (scan2.nextInt ());
    ArrayList <PuzzleWord> words = new ArrayList <PuzzleWord> ();
    ArrayList <String> w = new ArrayList <String> ();
    for (int i = 0; i < puzzle.getNumWords (); i++) {
      scan2 = new Scanner (scan.nextLine ());
      String s = scan2.next ();
      w.add (s);
      words.add (new PuzzleWord (scan2.nextInt (), scan2.nextInt (), Direction.values ()[scan2.nextInt ()], s));
    }
    puzzle.setWordList (words);
    puzzle.setList (w);
    puzzle.buildMatrix ();
    for (int r = 0; r < puzzle.getMatrixHeight (); r++) {
      scan2 = new Scanner (scan.nextLine ());
      for (int c = 0; c < puzzle.getMatrixWidth (); c++) {
        PuzzleCell cell = new PuzzleCell();
        cell.setCharacter (scan2.next ().charAt (0));
        cell.setNumWords (scan2.nextInt ());
        puzzle.setMatrixElement (r, c, cell);
      }
    }
  }
}
