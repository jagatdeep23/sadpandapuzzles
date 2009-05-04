package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import puzzle.Puzzle;

/** The components of the GUI
 * @author Sad Panda Software
 * @version 3.0 */
public class Components {
  
  /** The drop down menu */
  public static JComboBox    dropDown;

  /** Area for storing the word list */
  public static MutableList   wordList;

  /** The output panel */
  private static OutputPanel  outputPanel;

  /** Text area for inputting words */
  private static JTextField    wordField;
  
  /** the output panel scroll pane */
  private static JScrollPane scrollArea;
  
  /** An empty JLabel */
  private static final JLabel EMPTY_LABEL = new JLabel (" ");
  
  /** New String*/
  private static final String NEW         = "New";
  
  /** Open String*/
  private static final String OPEN        = "Open";
  
  /** Save String*/
  private static final String SAVE        = "Save";
  
  /** Export String*/
  private static final String EXPORT      = "Export";
  
  /** Generate String*/
  private static final String GENERATE    = "Generate";
  
  /** Builds the side panel
   * @return JPanel */
  public static JPanel buildSidebar () {
    wordField = new JTextField ();
    Buttons.addWordToList = new JButton ("Add New Word");
    Buttons.removeWordFromList = new JButton ("Remove Selected Word");
    Buttons.clearList = new JButton ("Clear Word List");
    Buttons.loadList = new JButton ("Load Word List");
    JLabel wordListLabel = new JLabel ("Word List");
    wordListLabel.setHorizontalAlignment (JLabel.CENTER);
    Buttons.addWordToList.setMnemonic ('a');
    Buttons.removeWordFromList.setMnemonic ('r');
    Buttons.clearList.setMnemonic ('c');
    Buttons.loadList.setMnemonic ('l');
    JPanel buttonPanel = new JPanel (new GridLayout (5, 1, 5, 5));
    buttonPanel.add (wordField);
    buttonPanel.add (Buttons.addWordToList);
    buttonPanel.add (Buttons.removeWordFromList);
    buttonPanel.add (Buttons.clearList);
    buttonPanel.add (Buttons.loadList);
    wordList = new MutableList ();
    wordList.setFocusable (false);
    scrollArea = new JScrollPane (wordList);
    JPanel sidebarPanel = new JPanel (new BorderLayout (5, 5));
    sidebarPanel.add (wordListLabel, BorderLayout.NORTH);
    sidebarPanel.add (buttonPanel, BorderLayout.SOUTH);
    sidebarPanel.add (scrollArea, BorderLayout.CENTER);
    return sidebarPanel;
  }

  /** Builds the toolbar
   * @return JToolBar the toolbar at the top of the screen */
  public static JToolBar buildToolbar () {
    JToolBar toolBar = new JToolBar ();
    JToolBar leftBar = new JToolBar ();
    JToolBar rightBar = new JToolBar ();
    toolBar.setLayout (new BorderLayout ());
    leftBar.setLayout (new GridLayout (1, 4));
    rightBar.setLayout (new BorderLayout ());
    leftBar.setFloatable (false);
    rightBar.setFloatable (false);
    leftBar.setRollover (true);
    rightBar.setRollover (true);
    String [] s = Puzzle.getPuzzleTypes ();
    dropDown = new JComboBox (s);
    dropDown.setFocusable (false);
    JPanel dropDownPanel = new JPanel (new GridLayout (3, 1));
    dropDownPanel.add (EMPTY_LABEL);
    dropDownPanel.add (EMPTY_LABEL);
    dropDownPanel.add (dropDown);
    leftBar.add (generateButton (NEW));
    leftBar.add (generateButton (OPEN));
    leftBar.add (generateButton (SAVE));
    leftBar.add (generateButton (EXPORT));
    rightBar.add (dropDownPanel, BorderLayout.CENTER);
    rightBar.add (generateButton (GENERATE), BorderLayout.EAST);
    toolBar.add (leftBar, BorderLayout.WEST);
    toolBar.add (rightBar, BorderLayout.EAST);
    toolBar.setSize (toolBar.getWidth (), toolBar.getHeight () * 2);
    toolBar.setFloatable (false);
    toolBar.setRollover (true);
    return toolBar;
  }

  /** Gets the output panel
   * @return panel the panel where puzzles are displayed */
  public static OutputPanel getOutputPanel () {
    return outputPanel;
  }
  
  /** Gets the scroll pane
   * @return the scroll pane that contains the output panel */
  public static JScrollPane getScrollPanel() {
    return scrollArea;
  }
  
  /** Gets the name of the currently drop-down item
   * @return selected drop-down item name */
  public static String getSelectedPuzzleOption () {
    return ((String) (dropDown.getSelectedItem ()));
  }

  /** gets the input word field
   * @return wordField the word field */
  public static JTextField getWordField() {
    return wordField;
  }
  
  /** Sets the word field text to the passed string
   * @param s the text */
  public static void setWordFieldText (String s) {
    wordField.setText (s);
  }
  
  /** Returns the currently inputed word in upper-case
   * @return text text of the word field*/
  public static String getWordFieldText () {
    return (wordField.getText ().toUpperCase ());
  }

  /** Sets the output panel
   * @param p an OutputPanel to draw puzzles */
  public static void setOutputPanel (OutputPanel p) {
    outputPanel = p;
  }

  /** Generates a button given its name
   * @param name the name of the Button
   * @return a created JButton */
  private static JButton generateButton (String name) {
    JButton button = new JButton (name, new ImageIcon ("images/" + name.toLowerCase () + ".png"));
    button.setVerticalTextPosition (SwingConstants.BOTTOM);
    button.setHorizontalTextPosition (SwingConstants.CENTER);
    button.setRolloverEnabled (true);
    button.setFocusable (false);
    button.setDisplayedMnemonicIndex (0);
    if (name.equals (NEW)) {
      Buttons.newButton = button;
      Buttons.newButton.setMnemonic ('N');
    } else if (name.equals (OPEN)) {
      Buttons.openButton = button;
      Buttons.openButton.setMnemonic ('O');
    } else if (name.equals (SAVE)) {
      Buttons.saveButton = button;
      Buttons.saveButton.setMnemonic ('S');
    } else if (name.equals (EXPORT)) {
      Buttons.exportButton = button;
      Buttons.exportButton.setMnemonic ('E');
    } else if (name.equals (GENERATE)) {
      Buttons.generateButton = button;
      Buttons.generateButton.setMnemonic ('G');
    }  
    return (button);
  }
  
  /** Buttons used in the interface
   * @author Sad Panda Software
   * @version 3.0 */
  public static final class Buttons {
    
    /** the new button */
    public static JButton newButton;
    
    /** the open button */
    public static JButton openButton;
    
    /** the save button */
    public static JButton saveButton;
    
    /** the export button */
    public static JButton exportButton;
    
    /** the generate button */
    public static JButton generateButton;
    
    /** the add to list button */
    public static JButton addWordToList;
    
    /** the remove from list button */
    public static JButton removeWordFromList;
    
    /** the clear list button */
    public static JButton clearList;
    
    /** the load list button */
    public static JButton loadList;
    
    /** Adds action listeners to the button
     * @param listener - an ActionListener */
    public static void addActionListener (ActionListener listener) {
      newButton.addActionListener (listener);
      openButton.addActionListener (listener);
      saveButton.addActionListener (listener);
      exportButton.addActionListener (listener);
      generateButton.addActionListener (listener);
      addWordToList.addActionListener (listener);
      removeWordFromList.addActionListener (listener);
      clearList.addActionListener (listener);
      loadList.addActionListener (listener);
      wordField.addActionListener (listener);
    }
  }
}
