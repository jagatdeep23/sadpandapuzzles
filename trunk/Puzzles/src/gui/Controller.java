package gui;

import io.FileIO;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
  private JFrame             frame;
  
  /** the File IO manager */
  private FileIO             fileManager;
  
  /** Default Constructor for the Controller */
  public Controller () {
    puzzle = null;
    words = new ArrayList <String> ();
    fileManager = new FileIO ();
    Components.setOutputPanel (new OutputPanel (this));
    buildWindow ();
  }
  
  /**
   * Adds a word to the word list
   * 
   * @param word
   *          a word
   */
  public void addWord (String word) {
    word = word.toUpperCase ();
    if (word.length () > 1) {
      String s = validateWord (word);
      if (!s.equals ("")) {
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
   * Removes the selected word from the word list
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
   * Clears the current wordlist
   * 
   */
  public void clearWordList () {
    words.clear ();
    Components.wordList.getContents ().removeAllElements ();
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
    int height = Components.getScrollPanel ().getHeight ();
    int width = Components.getScrollPanel ().getWidth ();
    Components.getScrollPanel ().setSize (width + 1, height);
    Components.getScrollPanel ().setSize (width, height);
  }
  
  /**
   * gets the window frame
   * 
   * @return the window frame
   */
  public JFrame getFrame () {
    return frame;
  }
  
  /**
   * Returns the puzzle associated with the controller
   * 
   * @return puzzle the puzzle
   */
  public Puzzle getPuzzle () {
    return puzzle;
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
   * loads a puzzle
   */
  public void loadPuzzle () {
    fileManager.loadPuzzle ();
    puzzle = fileManager.getPuzzle();
    if (puzzle != null) {
      for (String s : puzzle.getWordList ()) {
        addWord (s);
      }
    }
  }
  
  public void loadList () {
    ArrayList<String> list = fileManager.loadWordList();
    if (list != null) {
      for (String s : list) {
        addWord(s);
      }
    }
  }
  
  /**
   * Initiates the save puzzle functionality
   */
  public void savePuzzle () {
    fileManager.setPuzzle (puzzle);
    fileManager.savePuzzle ();
  }
  
  /**
   * Initiates the save word list functionality
   */
  public void saveWordList () {
    fileManager.saveWords (words);
  }
  
  /**
   * Initiates the export puzzle functionality
   */
  public void exportPuzzle () {
    fileManager.setPuzzle (puzzle);
    fileManager.exportPuzzle ();
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
    frame.addWindowListener (new WindowListener () {
      public void windowActivated (WindowEvent arg0) {
      }
      
      public void windowClosed (WindowEvent arg0) {
      }
      
      public void windowClosing (WindowEvent arg0) {
        if (Components.wordList.getContents ().size () == 0 || save ("Quit")) {
          System.exit (0);
        }
      }
      
      public void windowDeactivated (WindowEvent arg0) {
      }
      
      public void windowDeiconified (WindowEvent arg0) {
      }
      
      public void windowIconified (WindowEvent arg0) {
      }
      
      public void windowOpened (WindowEvent arg0) {
      }
      
      private boolean save (String title) {
        int result = JOptionPane.showConfirmDialog (null, "Would you like to save the current puzzle?", title, JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        switch (result) {
          case JOptionPane.CANCEL_OPTION:
            return false;
          case JOptionPane.YES_OPTION:
            savePuzzle ();
          case JOptionPane.NO_OPTION:
            return true;
        }
        return true;
      }
    });
  }
  
  /**
   * checks to see if any invalid characters are in the word
   * 
   * @param word
   *          a word
   * @return chars a string specifying invalid characters in the string;
   */
  private String validateWord (String word) {
    String chars = "";
    for (int i = 0; i < word.length (); ++i) {
      if (!Character.isLetter (word.charAt (i))) {
        chars += "\"" + word.charAt (i) + "\" ";
      }
    }
    return chars;
  }
}
