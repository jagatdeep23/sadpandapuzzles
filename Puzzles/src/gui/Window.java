package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * The "View" component of the MVC design pattern. What the user sees on the
 * screen
 * 
 * @author Sad Panda Software
 * @version 3.0
 */

@SuppressWarnings("serial")
public class Window extends JPanel {
  
  /**
   * the graphical controller that mediates between the model and view
   */
  private Controller controller;
  
  /**
   * Window Contructor
   * 
   * @param controller
   *          the controller that will mediate between the model and view
   */
  public Window (Controller controller) {
    this.controller = controller;
    EventListener listener = new EventListener ();
    setLayout (new BorderLayout ());
    add (Components.buildToolbar (), BorderLayout.NORTH);
    JSplitPane windowPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, Components.buildSidebar (), new JScrollPane (Components.getOutputPanel (),
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
    windowPane.getLeftComponent ().setPreferredSize (new Dimension (150, 50));
    add (windowPane, BorderLayout.CENTER);
    Components.Buttons.addActionListener (listener);
  }
  
  /**
   * The Listener for all of the GUI components
   * 
   * @author Sad Panda Software
   * @version 2.0
   */
  public class EventListener implements ActionListener {
    private boolean helpIsOpen = false;
    /**
     * the action that is performed based on the event passed
     * 
     * @param event
     *          the event that triggers actions
     */
    public void actionPerformed (ActionEvent event) {
      Object obj = event.getSource ();
      if (obj.equals (Components.Buttons.newButton)) {
        if (Components.wordList.getContents ().size () == 0 || save ("New")) {
          controller.clearWordList ();
          controller.setPuzzle (null);
          Components.getOutputPanel ().repaint ();
          Components.wordList.removeAll ();
        }
      } else if (obj.equals (Components.Buttons.openButton)) {
        if (Components.wordList.getContents ().size () == 0 || save ("Open")) {
          controller.clearWordList ();
          controller.loadPuzzle ();
          Components.getOutputPanel ().repaint ();
          Components.wordList.removeAll ();
        }
      } else if (obj.equals (Components.Buttons.saveButton)) {
        String[] options = {"Word List", "Puzzle", "Cancel"};
        int result = JOptionPane.showOptionDialog (null, "What Do You Want To Save?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == 0) {
          controller.saveWordList ();
        } else if (result == 1){
          controller.savePuzzle ();
        }
      } else if (obj.equals (Components.Buttons.exportButton)) {
        controller.exportPuzzle ();
      } else if (obj.equals (Components.Buttons.quitButton)) {
        if (Components.wordList.getContents ().size () == 0 || save ("Quit")) {
          System.exit (0);
        }
      } else if (obj.equals (Components.Buttons.helpButton)) {
        if (!helpIsOpen) {
          helpIsOpen = true;
          showHelp();
        }
      } else if (obj.equals (Components.Buttons.generateButton)) {
        controller.buildPuzzle (Components.getSelectedPuzzleOption ());
        Components.getOutputPanel ().repaint ();
      } else if (obj.equals (Components.Buttons.addWordToList)) {
        controller.addWord (Components.getWordFieldText ());
        Components.wordField.setText ("");
      } else if (obj.equals (Components.Buttons.removeWordFromList)) {
        controller.removeWord ();
      } else if (obj.equals (Components.Buttons.clearList)) {
        controller.setPuzzle (null);
        controller.clearWordList ();
        Components.wordList.getContents ().clear ();
        Components.getOutputPanel ().repaint ();
      } else if (obj.equals (Components.wordField)) {
        controller.addWord (Components.getWordFieldText ());
        Components.wordField.setText ("");
      } else if (obj.equals (Components.Buttons.loadList)) {
        controller.loadList();
      }
    }
    
    /**
     * Prompts to save the current puzzle
     * 
     * @param title
     *          the text to display as the title
     * @return true if save was yes or no; false if save was cancelled
     */
    private boolean save (String title) {
      if (title == "Save") {
        controller.savePuzzle ();
        return true;
      } else {
        int result = JOptionPane.showConfirmDialog (null, "Would you like to save the current puzzle?", title, JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
          return (false);
        } else {
          if (result == JOptionPane.YES_OPTION) {
            controller.savePuzzle ();
          }
          return (true);
        }
      }
    }
    
    private void showHelp () {
      JDialog popup = new JDialog(controller.getFrame (), "I can has help?");
      popup.addWindowListener (new WindowListener() {
        public void windowActivated (WindowEvent e) { }
        public void windowClosed (WindowEvent e) { }
        public void windowClosing (WindowEvent e) { helpIsOpen = false; }
        public void windowDeactivated (WindowEvent e) { }
        public void windowDeiconified (WindowEvent e) { }
        public void windowIconified (WindowEvent e) { }
        public void windowOpened (WindowEvent e) { }
      });
      Container c = new Container ();
      c.setLayout (new BorderLayout(5, 5));
      Container d = new Container();
      d.setLayout (new GridLayout (9, 2, 0, 5));
      d.add (new JLabel ("1. Clears all words and puzzles"));
      d.add (new JLabel ("9. Word List area"));
      d.add (new JLabel ("2. Prompts user to open a file"));
      d.add (new JLabel ("10. Text field for entering words"));
      d.add (new JLabel ("3. Prompts user to save a file"));
      d.add (new JLabel ("11. Adds a word to the word list"));
      d.add (new JLabel ("4. Prompts user to export to HTML"));
      d.add (new JLabel ("12. Removes the selected word from the word list"));
      d.add (new JLabel ("5. Exits the program"));
      d.add (new JLabel ("13. Clears the word list"));
      d.add (new JLabel ("6. How did you get here?"));
      d.add (new JLabel ("14. The puzzle output area"));
      d.add (new JLabel ("7. Selects puzzle type"));
      d.add (new JLabel ("15. LMB - Exports a puzzle (generates if no puzzle)"));
      d.add (new JLabel ("8. Generates a puzzle of the selected type"));
      d.add (new JLabel ("16. RMB - Generates a puzzle"));
      d.add (new JLabel());
      d.add(new JLabel("17. Adds word to the list from a file"));
      
      c.add (new JLabel (new ImageIcon("images/helpImage.PNG")), BorderLayout.CENTER);
      c.add (d, BorderLayout.SOUTH);
      popup.add (c);
      popup.pack ();
      popup.setVisible (true);
    }
  }
}
