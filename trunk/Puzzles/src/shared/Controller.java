package shared;

import java.util.ArrayList;

import puzzle.Puzzle;

public class Controller {
	/**
	 * a word search puzzle
	 */
	private Puzzle wordSearchPuzzle;
	/**
	 * a crossword puzzle
	 */
	private Puzzle crosswordPuzzle;
	
	/**
	 * variable to determine whether or not a word search puzzle should be generated
	 */
	private boolean doWordSearch;
	/**
	 * variable to determine whether or not a crossword puzzle should be generated
	 */
	private boolean doCrossword;
	/**
	 * the word list currently in the "state" of the program
	 */
	private ArrayList<String> wordList;
	
	public Controller() {
		wordSearchPuzzle = null;
		crosswordPuzzle = null;
		doWordSearch = false;
		doCrossword = false;
		wordList = new ArrayList<String>();
	}
	
	public void toggleWordSearchOption () {
		doWordSearch = !doWordSearch;
	}
	
	public void toggleCrosswordOption () {
		doCrossword = !doCrossword;
	}
	 
	public void setWordSearchPuzzle (Puzzle p) {
		wordSearchPuzzle = p;
	}
	
	public void setCrosswordPuzzle (Puzzle p) {
		crosswordPuzzle = p;
	}
	
	public boolean getDoWordSearch () {
		return doWordSearch;
	}
	
	public boolean getDoCrossword () {
		return doCrossword;
	}
	
	public void setWordList (ArrayList<String> list) {
		wordList = list;
	}
	
	public ArrayList<String> getWordList () {
		return wordList;
	}
	
	public Puzzle getCrosswordPuzzle () {
		return crosswordPuzzle;
	}
	
	public Puzzle getWordSearch () {
		return wordSearchPuzzle;
	}
}
