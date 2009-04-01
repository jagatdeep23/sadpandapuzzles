package puzzle;

import java.util.ArrayList;
import shared.ProgramConstants;

/**
 * The "Model" component of the MVC design pattern
 * 
 * @author Sad Panda Software
 * @version 2.0
 */
public class InstanceModel {
  
  /** the puzzle associated with the puzzle */
  private Puzzle             puzzle;
  
  /** the word list associated with the model */
  private ArrayList <String> wordList;
  
  /** contructor for the model */
  public InstanceModel () {
    wordList = new ArrayList <String> ();
  }
  
  /**
   * adds a word to the model's word list
   * 
   * @param word
   *          the word to add to the model's word list
   */
  public void addWord (String word) {
    wordList.add (word);
  }
  
  /** clears the word list associated with the model */
  public void clearWordList () {
    wordList.clear ();
  }
  
  /**
   * removes a word from the model's word list
   * 
   * @param word -
   *          the word to remove
   */
  public void removeWord (String word) {
    wordList.remove (word);
  }
  
  /**
   * Builds a Puzzle and generates from the list of words
   * 
   * @param type
   *          the type of puzzle to Build
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
    puzzle.setList (wordList);
    puzzle.generate ();
  }
  
  /** clears the model's puzzle */
  public void clearPuzzle () {
    puzzle = null;
    wordList.clear ();
  }
  
  /**
   * gets the model's puzzle
   * 
   * @return puzzle the model's puzzle
   */
  public Puzzle getPuzzle () {
    return puzzle;
  }
  
  /**
   * sets the model's puzzle
   * 
   * @param p
   *          a Puzzle
   */
  public void setPuzzle (Puzzle p) {
    puzzle = p;
    wordList.clear ();
    try {
      for (PuzzleWord w : puzzle.getWordList ()) {
        wordList.add (w.getWord ());
      }
    } catch (NullPointerException e) {
    }
  }
  
  /**
   * Gets the word list
   * 
   * @return wordList the list of words
   */
  public ArrayList <String> getWordList () {
    return wordList;
  }
  
}
