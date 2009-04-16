package gui;

import io.FileIO;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import puzzle.Crossword;
import puzzle.Puzzle;
import puzzle.WordSearch;
import shared.ProgramConstants;

/**
 * The controller class controls the model of this project as well as the GUI
 * setup.
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class Controller {
  
  /** the model */
  private Puzzle             puzzle;
  
  /** the list of words in the word list */
  private ArrayList <String> words;
  
  /** the window frame */
  private JFrame frame;
  
  /** Default Constructor for the Controller */
  public Controller () {
    puzzle = null;
    words = new ArrayList <String> ();
    Components.setOutputPanel (new OutputPanel (this));
    buildWindow ();
  }
  
  /**
   * Adds a word to the wordlist which will be added to the puzzle next time
   * generate is pushed
   * 
   * @param word
   */
  public void addWord (String word) {
    if (word.length () > 1) {
      word = word.toUpperCase ();
      int count = 0;
      String s = "";
      for (int i = 0; i < word.length (); ++i) {
        if (word.charAt (i) < 'A' || word.charAt (i) > 'Z') {
          s += "\"" + word.charAt (i) + "\" ";
          count++;
        }
      }
      if (count > 0) {
        JOptionPane.showMessageDialog (null, "The word you have entered contained invalid characters\nInvalid characters are: " + s, "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (word.startsWith ("IMAC") || word.equals ("APPLE") || word.equals ("KATZ") || word.startsWith ("IPOD") || word.startsWith ("IPHONE")
          || word.startsWith ("MAC") && !word.startsWith ("MACR") && !word.startsWith ("MACE")) {
        JOptionPane.showMessageDialog (null, "The word you have entered cannot be recognized\nSince we are nice, we will add it for you anyways.",
            "Woah There!", JOptionPane.INFORMATION_MESSAGE);
      }
      words.add (word);
      Components.wordList.getContents ().addElement (word);
    } else {
      JOptionPane.showMessageDialog (null, "The word you have entered does not meet the minimum requirement length of 2", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  /**
   * Initiates the puzzle creation depending on the name of the type that is
   * passed in
   * 
   * @param type
   *          a string that refers to the type
   */
  public void buildPuzzle (String type) {
    if (type.equals (ProgramConstants.WORD_SEARCH)) {
      if (puzzle == null || puzzle instanceof Crossword) {
        puzzle = new WordSearch ();
      }
    } else if (type.equals (ProgramConstants.CROSSWORD)) {
      if (puzzle == null || puzzle instanceof WordSearch) {
        puzzle = new Crossword ();
      }
    }
    puzzle.setList (words);
    puzzle.generate ();
  }
  
  /**
   * Clears the current wordlist
   * 
   */
  public void clearWordList () {
    words.clear ();
    // puzzle.clearWordList ();
    Components.wordList.getContents ().removeAllElements ();
    Components.getOutputPanel ().removeAll ();
  }
  
  /**
   * Returns the Instance Model used by the program
   * 
   * @return InstanceModel
   */
  public Puzzle getPuzzle () {
    return puzzle;
  }
  
  /**
   * Initiates the save puzzle functionality when the save button is pushed
   * 
   */
  public void savePuzzle () {
    FileIO.savePuzzle (puzzle);
  }
  
  /**
   * Removes the selected word from the word list
   * 
   */
  public void removeWord () {
    int index = Components.wordList.getSelectedIndex ();
    if (index >= 0) {
      String word = (String) Components.wordList.getSelectedValue ();
      words.remove (word);
      Components.wordList.getContents ().remove (index);
      Components.wordList.setSelectedIndex ((index > 0) ? index - 1 : index);
    }
  }
  
  /**
   * Sets the current puzzle
   * 
   * @param p
   *          Puzzle object which will bet set
   */
  public void setPuzzle (Puzzle p) {
    puzzle = p;
  }
  
  /**
   * Returns the list of words in the Wordlist
   * 
   * @return ArrayList<String> - List of words in the puzzle
   */
  public ArrayList <String> getWordList () {
    return words;
  }
  
  /**
   * Builds the main GUI window
   */
  private void buildWindow () {
    if (System.getProperty ("mrj.version") != null) {
      System.setProperty ("apple.laf.useScreenMenuBar", "true");
    }
    try {
      UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ());
    } catch (ClassNotFoundException e) {
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    } catch (UnsupportedLookAndFeelException e) {
    }
    
    JFrame.setDefaultLookAndFeelDecorated (true);
    frame = new JFrame ("Puzzle Generator 3.0 - Sad Panda Software");
    frame.setMinimumSize (new Dimension (480, 400));
    frame.getContentPane ().add (new Window (this));
    frame.pack ();
    frame.setSize (800, 600);
    frame.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
    frame.setVisible (true);
  }
  
  /**
   * gets the window frame
   * @return the window frame
   */
  public JFrame getFrame () {
    return frame;
  }
}
