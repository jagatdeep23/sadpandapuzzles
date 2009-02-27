package puzzle;


import java.util.ArrayList;

import shared.ProgramConstants;


public class InstanceModel {
  
  private Puzzle puzzle;
  
  private ArrayList<String> wordList;
  
  public InstanceModel() {
    wordList = new ArrayList<String>();
  }
  
  public void addWord (String word) {
    wordList.add (word);
  }
  
  public void clearWordList () {
    wordList.clear ();
  }

  public void removeWord (String word) {
    wordList.remove(word);
  }
  
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
  
  public void clearPuzzle () {
    puzzle = null;
    wordList.clear ();
  }
  
  public Puzzle getPuzzle() {
    return puzzle;
  }
  
  public ArrayList<String> getWordList () {
    return wordList;
  }
  
}
