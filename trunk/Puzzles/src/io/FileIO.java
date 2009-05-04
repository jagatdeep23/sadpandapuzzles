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

import puzzle.Puzzle;

/**
 * This class contains all of the File Input and Output methods.
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class FileIO {
  
  /** A file chooser */
  private static JFileChooser chooser;
  
  /** Default Constructor */
  public FileIO () {
    chooser = new JFileChooser ();
  }
  
  /**
   * Initiates the export to HTML process to export a puzzle and its state to an
   * HTML file
   */
  public void exportPuzzle (Puzzle puzzle) {
    if (puzzle != null && puzzle.getNumWords () > 0) {
        if (chooser.showSaveDialog (null) == JFileChooser.APPROVE_OPTION) {
          File newFile = chooser.getSelectedFile ();
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
   * @return ArrayList<String> words list of words
   */
  public ArrayList <String> loadWordList () {
    ArrayList <String> words = new ArrayList <String> ();
    File file;
    if (chooser.showOpenDialog (null) == JFileChooser.APPROVE_OPTION) {
      file = chooser.getSelectedFile ();
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
   * Initiates the load puzzle functions to load a puzzle and its state from a file
   * @return puzzle - The puzzle that is loaded
   */
  public Puzzle loadPuzzle () {
    Puzzle puzzle = null;
    File file;
    if (chooser.showOpenDialog (null) == JFileChooser.APPROVE_OPTION) {
      file = chooser.getSelectedFile ();
      if (file != null) {
        try {
          Scanner scan = new Scanner (file);
          String type = scan.nextLine ();
          puzzle = Puzzle.getConstructor (type);
          puzzle.load (scan);
        } catch (FileNotFoundException e) {
          JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog (null, "A Critical Error Has Occurred", "Error!", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    return puzzle;
  }
  
  /**
   * Will call the appropriate save function based on whether or not a file has
   * been associated yet. If one has not, a new file will be created.
   * @param list Vector<String>
   * @throws IOException
   */
  public void saveWords (ArrayList <String> list) {
    if (chooser.showSaveDialog (null) == JFileChooser.APPROVE_OPTION) {
      File newFile = chooser.getSelectedFile ();
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
   * @param puzzle
   */
  public void savePuzzle (Puzzle puzzle) {
    int status;
    File newFile = new File ("empty");
    status = chooser.showSaveDialog (null);
    if (status == JFileChooser.APPROVE_OPTION) {
      try {
      newFile = chooser.getSelectedFile ();
      BufferedWriter buffer = new BufferedWriter (new FileWriter (newFile));
      String s = puzzle.save ();
      buffer.write (s);
      buffer.close ();
      } catch (IOException e) {
        JOptionPane.showMessageDialog (null, "File IO Exception\n" + e.getLocalizedMessage (), "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
