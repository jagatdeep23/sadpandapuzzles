package gui;

import io.FileIO;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import puzzle.Puzzle;

/**
 * Controls all actions performed on the puzzle and word list
 * @author Sad Panda Software
 * @version 3.0
 */
public class Controller {
  
  /** the model */
  private Puzzle             puzzle;
  
  /** the list of words in the word list */
  private ArrayList <String> words;
  
  /** the File IO manager */
  private FileIO             fileManager;
  
  /** Default Constructor for the Controller */
  public Controller () {
    puzzle = null;
    words = new ArrayList <String> ();
    fileManager = new FileIO ();
  }
  
  /** Adds a word to the word list
   * @param word a word */
  public void addWord (String word) {
    word = word.toUpperCase ();
    if (word.length () > 1) {
      String s = validateWord (word);
      if (!s.equals ("")) {
        JOptionPane.showMessageDialog (null, "The word you have entered contained invalid characters\nInvalid characters are: " + s, "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }
      checkEasterEgg (word);
      words.add (word);
      Components.wordList.getContents ().addElement (word);
    } else {
      JOptionPane.showMessageDialog (null, "The word you have entered does not meet the minimum requirement length of 2", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /** Removes the selected word from the word list */
  public void removeWord () {
    int index = Components.wordList.getSelectedIndex ();
    if (index >= 0) {
      String word = (String) Components.wordList.getSelectedValue ();
      words.remove (word);
      Components.wordList.getContents ().remove (index);
      Components.wordList.setSelectedIndex ((index > 0) ? index - 1 : index);
    }
  }
  
  /** Clears the current wordlist */
  public void clearWordList () {
    words.clear ();
    Components.wordList.getContents ().removeAllElements ();
  }
  
  /** Initiates the puzzle creation depending on the name of the type that is passed in
   * @param type a string that refers to the type of puzzle */
  public void buildPuzzle (String type) {
    try {
      puzzle = Puzzle.getConstructor (type);
      puzzle.setList (words);
      puzzle.generate ();
      int height = Components.getScrollPanel ().getHeight ();
      int width = Components.getScrollPanel ().getWidth ();
      Components.getScrollPanel ().setSize (width + 1, height);
      Components.getScrollPanel ().setSize (width, height);
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog (null, "A Critical Error Has Occurred", "Error!", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  /** Returns the puzzle associated with the controller
   * @return puzzle the puzzle */
  public Puzzle getPuzzle () {
    return puzzle;
  }
  
  /** Returns the list of words in the Wordlist
   * @return ArrayList<String> - List of words in the puzzle */
  public ArrayList <String> getWordList () {
    return words;
  }
  
  /** loads a puzzle */
  public void loadPuzzle () {
    puzzle = fileManager.loadPuzzle ();
    if (puzzle != null) {
      for (String s : puzzle.getWordList ()) {
        addWord (s);
      }
    }
  }
  
  /** loads a word list */
  public void loadList () {
    ArrayList<String> list = fileManager.loadWordList();
    if (list != null) {
      for (String s : list) {
        addWord(s);
      }
    }
  }
  
  /** saves a puzzle or word list */
  public void save () {
    String[] options = {"Word List", "Puzzle", "Cancel"};
    int result = JOptionPane.showOptionDialog (null, "What Do You Want To Save?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    if (result == 0) {
      fileManager.saveWords (words);
    } else if (result == 1){
      fileManager.savePuzzle (puzzle);
    }
  }
  
  /** Initiates the export puzzle functionality */
  public void exportPuzzle () {
    fileManager.exportPuzzle (puzzle);
  }
  
  /** Sets the current puzzle
   * @param p Puzzle object which will bet set */
  public void setPuzzle (Puzzle p) {
    puzzle = p;
  }
  
  /** checks to see if any invalid characters are in the word
   * @param word a word
   * @return chars a string specifying invalid characters in the string */
  private String validateWord (String word) {
    String chars = "";
    for (int i = 0; i < word.length (); ++i) {
      if (!Character.isLetter (word.charAt (i))) {
        chars += "\"" + word.charAt (i) + "\" ";
      }
    }
    return chars;
  }
  
  /** checks the word to see if it is "special"
   * @param word a word */
  private void checkEasterEgg (String word) {
    if (word.startsWith ("IMAC") || word.equals ("APPLE") || word.equals ("KATZ") || word.startsWith ("IPOD") || word.startsWith ("IPHONE")
        || word.startsWith ("MAC") && !word.startsWith ("MACR") && !word.startsWith ("MACE")) {
      JOptionPane.showMessageDialog (null, "The word you have entered cannot be recognized\nSince we are nice, we will add it for you anyways.",
          "Woah There!", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
