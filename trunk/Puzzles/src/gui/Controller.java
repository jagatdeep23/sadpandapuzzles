package gui;

import io.FileIO;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import puzzle.InstanceModel;
import puzzle.Puzzle;

/** The controller class controls the model of this project as well as the GUI setup.
 * @author Sad Panda Software
 * @version 2.0 */
public class Controller {
  
	/** the model */
  InstanceModel model;
  
	/** Default Constructor for the Controller */
	public Controller() {
	  model = new InstanceModel();
	  Components.setOutputPanel (new OutputPanel(this));
		buildWindow();
	}
	
  /**
   * Returns the Instance Model used by the program
   * @return InstanceModel
   */
	public InstanceModel getModel () {
	  return model;
	}
	
  /**
   * Initiates the save puzzle functionality when the save button is pushed
   *
   */
	public void savePuzzle () {
	  FileIO.savePuzzle(model.getPuzzle ());
	}
	
  /**
   * Adds a word to the wordlist which will be added to the puzzle next time generate is pushed
   * @param word
   */
  public void addWord (String word) {
    if (word.length() > 1) {
      word = word.toUpperCase ();
      int count = 0;
      String s = "";
      for (int i = 0; i < word.length (); ++i) {
        if (word.charAt (i) < 'A' || word.charAt (i) > 'Z') {
          s += "\"" + word.charAt (i) + "\" ";
          count ++;
        }
      }
      if (count > 0) {
        JOptionPane.showMessageDialog (null, "The word you have entered contained invalid characters\nInvalid characters are: " + s, "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      model.addWord (word);
      Components.wordList.getContents().addElement (word);
    } else {
      JOptionPane.showMessageDialog (null, "The word you have entered does not meet the minimum requirement length of 2", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  /**
   * Clears the current wordlist
   *
   */
  public void clearWordList () {
    model.clearWordList();
    Components.wordList.getContents().removeAllElements();
    Components.getOutputPanel ().removeAll ();
  }

  /**
   * Removes the selected word from the word list
   *
   */
  public void removeWord () {
    int index = Components.wordList.getSelectedIndex ();
    if (index >= 0) {
      String word = (String)Components.wordList.getSelectedValue ();
      model.removeWord (word);
      Components.wordList.getContents().remove (index);
      Components.wordList.setSelectedIndex ((index > 0) ? index - 1 : index);
    }
  }
  
  /**
   * Initiates the puzzle creation depending on the name of the type that is passed in
   * @param type - "Word Search" or "Crossword"
   */
  public void buildPuzzle (String type) {
    model.buildPuzzle (type);
  }
  
  /** 
   * Returns the current puzzle
   * @return Puzzle
   */
  public Puzzle getPuzzle() {
    return model.getPuzzle ();
  }
  
  /**
   * Sets the current puzzle
   * @param p - Puzzle object which will bet set
   */
  public void setPuzzle(Puzzle p) {
    model.setPuzzle (p);
  }
  
  /**
   * Returns the list of words in the Wordlist
   * @return ArrayList<String> - List of words in the puzzle
   */
  public ArrayList<String> getWordList () {
    return model.getWordList ();
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
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		
    JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Puzzle Generator 2.0 - Sad Panda Software");
		frame.setMinimumSize(new Dimension(460, 400));
		frame.getContentPane().add(new Window(this));
		frame.pack();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
