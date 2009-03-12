package gui;

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
 * @version 1.0 */
public class Controller {
  
	/** the model */
  InstanceModel model;
  
	/** Default Constructor for the Controller */
	public Controller() {
	  model = new InstanceModel();
	  Components.setOutputPanel (new OutputPanel(this));
		buildWindow();
	}
	
	public InstanceModel getModel () {
	  return model;
	}
	
	public void savePuzzle () {
	  
	}
	
  public void addWord (String word) {
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
  }
  
  public void clearWordList () {
    model.clearWordList();
    Components.wordList.getContents().removeAllElements();
    Components.getOutputPanel ().removeAll ();
  }

  public void removeWord () {
    int index = Components.wordList.getSelectedIndex ();
    if (index >= 0) {
      String word = (String)Components.wordList.getSelectedValue ();
      model.removeWord (word);
      Components.wordList.getContents().remove (index);
      Components.wordList.setSelectedIndex ((index > 0) ? index - 1 : index);
    }
  }
  
  public void buildPuzzle (String type) {
    model.buildPuzzle (type);
  }
  
  public Puzzle getPuzzle() {
    return model.getPuzzle ();
  }
  
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
