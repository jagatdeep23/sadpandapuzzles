package shared;

import gui.Window;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import puzzle.Puzzle;

/**
 * The controller class controls the model of this project as well as the GUI setup.
 * @author Sad Panda Software
 * @version 1.0
 */
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
	
	/**
	 * Default Constructor for the Controller
	 *
	 */
	public Controller() {
		wordSearchPuzzle = new Puzzle();
		crosswordPuzzle = null;
		doWordSearch = false;
		doCrossword = false;
		wordList = new ArrayList<String>();
		buildWindow();
	}
	
	/**
	 * Toggles the wordsearch option for generation
	 */
	public void toggleWordSearchOption () {
		doWordSearch = !doWordSearch;
	}
	
	/**
	 * Toggles the crossword option for generation
	 */
	public void toggleCrosswordOption () {
		doCrossword = !doCrossword;
	}
	
	/**
	 * Tells the model to generate a word search puzzle
	 */
	public void generateWordSearchPuzzle () {
		wordSearchPuzzle.genWordSearch(wordList);
	}
	
	/**
	 * Tells the model to generate a crossword puzzle
	 */
	public void generateCrosswordPuzzle () {
		crosswordPuzzle.genCrossword(wordList);
	}
	
	/**
	 * Returns true if there is a flag to generate a word search puzzle
	 * @return a boolean value determining if a word search puzzle should be generated
	 */
	public boolean getDoWordSearch () {
		return doWordSearch;
	}
	
	/**
	 * Returns true if there is a flag to generate a crossword puzzle
	 * @return a boolean value determining if a crossword puzzle should be generated
	 */
	public boolean getDoCrossword () {
		return doCrossword;
	}
	
	/**
	 * Sets the model's word list
	 * @param list a list of words
	 */
	public void setWordList (ArrayList<String> list) {
		wordList = list;
	}
	
	/**
	 * Gets the model's word list
	 * @return a list of words
	 */
	public ArrayList<String> getWordList () {
		return wordList;
	}
	
	/**
	 * Gets the model's crossword puzzle.  It will be null if a crossword puzzle was never generated
	 * @return a generated crossword puzzle
	 */
	public Puzzle getCrosswordPuzzle () {
		return crosswordPuzzle;
	}
	
	/**
	 * Gets the model's word search puzzle.  It will be null if a word search puzzle was never generated
	 * @return a generated word search puzzle
	 */
	public Puzzle getWordSearch () {
		return wordSearchPuzzle;
	}
	
	/**
	 * Builds the main GUI window
	 */
	private void buildWindow () {
		if (System.getProperty("mrj.version") != null) {
			System.setProperty("apple.laf.useScreenMenuBar","true");
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Puzzle Generator 1.0 - Sad Panda Software");
		frame.getContentPane().add(new Window(this));
		frame.pack();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
