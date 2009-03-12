package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFileChooser;

import puzzle.Puzzle;


/**
 * This class contains all of the File Input and Output methods.
 * 
 * @author Sad Panda Software
 * @version 1.0
 */
public class FileIO {
  
  /** A file chooser */
  private static JFileChooser chooser;
  
  /** The file that is to be loaded */
  private static File file;
  
  /** The array list of words */
  private static Vector <String> words = new Vector <String> ();
  
  /** Returns a list of words sorted by length (longest first)
   * @return ArrayList<String> - A list of words */
  public static Vector <String> getFile () {
    int status;
    status = getFileChooser().showOpenDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      file = getFileChooser().getSelectedFile ();
      System.out.println ("File selected to open: " + file.getName ());
      System.out.println ("Full path name: " + file.getAbsolutePath ());
    } else {
      words.clear ();
      return words;
    }
    
    try {
      getWords (file);
    } catch (IOException e) {
      System.out.println ("Error: IO Exception was thrown:" + e);
    }
    return words;
  }
  
  /** Processes a list of words from a file.
   * @param input a File
   * @throws IOException */
  private static void getWords (File input) throws IOException {
    FileReader fileReader = new FileReader (input);
    BufferedReader buffer = new BufferedReader (fileReader);
    String temp;
    
    while ((temp = buffer.readLine ()) != null)
      words.add (temp);
    buffer.close ();
  }
  
  /** Will call the appropriate save function based on whether or not a file has
   * been associated yet. If one has not, a new file will be created.
   * @param list Vector<String>
   * @throws IOException */
  public static void saveWords (ArrayList <String> list) {
    if (file != null) {
      try {
        save (list, file);
      } catch (IOException e) {
        System.out.println ("Error: IO Exception was thrown:" + e);
      }
    } else
      saveWordsAs (list);
  }
  
  /** Creates a new file and calls the save function to save the list of words to the new file.
   * @param list ArrayList<String>
   * @throws IOException */
  private static void saveWordsAs (ArrayList <String> list) {
    int status;
    File newFile = new File ("empty");
    status = getFileChooser().showSaveDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      newFile = getFileChooser().getSelectedFile ();
      System.out.println ("File chosen to save to: " + newFile.getName ());
      System.out.println ("Full path to file: " + newFile.getAbsolutePath ());
      try {
        save (list, newFile);
      } catch (IOException e) {
        System.out.println ("Error: IO Exception was thrown:" + e);
      }
    }
  }
  
  /** Will perform the actual save of the word list to the location provided.
   * @param list ArrayList<String>
   * @param location Location of file
   * @throws IOException */
  private static void save (ArrayList <String> list, File location) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    for (String word : list) {
      buffer.write (word + "\n");
    }
    buffer.close ();
  }
  
  private static JFileChooser getFileChooser() {
    if (chooser == null) {
      chooser = new JFileChooser();
    }
    return (chooser);
  }
  
  public static void exportHTML (Puzzle puzzle) {
    
  }
  
  public static void savePuzzle (Puzzle puzzle) {
    
  }
  
  public static Puzzle loadPuzzle () {
    return null;
  }
}
