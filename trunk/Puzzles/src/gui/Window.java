package gui;

import io.FileIO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * The "View" component of the MVC design pattern. What the User sees on the
 * screen
 * 
 * @author Sad Panda Software
 * @version 3.0
 */

@SuppressWarnings("serial")
public class Window extends JPanel {
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
    JSplitPane windowPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, Components.buildSidebar (), new JScrollPane (Components.getOutputPanel (), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
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
    
    /**
     * the action that is performed based on the event passed
     * 
     * @param event
     *          the event that triggers actions
     */
    public void actionPerformed (ActionEvent event) {
      Object obj = event.getSource ();
      if (obj.equals (Components.Buttons.newButton)) {
        System.out.println ("new");
        if (save ("New")) {
          controller.clearWordList ();
          controller.setPuzzle (null);
          Components.getOutputPanel ().repaint ();
          Components.wordList.removeAll ();
        }
      } else if (obj.equals (Components.Buttons.openButton)) {
        System.out.println ("open");
        if (save ("Open")) {
          controller.clearWordList ();
          controller.setPuzzle (FileIO.loadPuzzle ());
          Components.getOutputPanel ().repaint ();
          Components.wordList.removeAll ();
          for (String s : controller.getWordList ()) {
            Components.wordList.getContents ().addElement (s);
          }
        }
      } else if (obj.equals (Components.Buttons.saveButton)) {
        save ("Save");
      } else if (obj.equals (Components.Buttons.exportButton)) {
        System.out.println ("export");
        FileIO.exportPuzzle (controller.getPuzzle ());
      } else if (obj.equals (Components.Buttons.quitButton)) {
        if (save ("Quit")) {
          System.exit (0);
        }
      } else if (obj.equals (Components.Buttons.helpButton)) {
        System.out.println ("help");
      } else if (obj.equals (Components.Buttons.generateButton)) {
        System.out.println ("generate");
        controller.buildPuzzle (Components.getSelectedPuzzleOption ());
        Components.getOutputPanel ().repaint ();
      } else if (obj.equals (Components.Buttons.addWordToList)) {
        System.out.println ("add");
        controller.addWord (Components.getWordFieldText ());
        Components.wordField.setText ("");
      } else if (obj.equals (Components.Buttons.removeWordFromList)) {
        System.out.println ("remove");
        controller.removeWord ();
      } else if (obj.equals (Components.Buttons.clearList)) {
        System.out.println ("clear");
        controller.clearWordList ();
        controller.getModel ().clearPuzzle ();
        Components.getOutputPanel ().repaint ();
      } else if (obj.equals (Components.wordField)) {
        controller.addWord (Components.getWordFieldText ());
        Components.wordField.setText ("");
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
        int result = JOptionPane.showConfirmDialog (null, "Would you like to save the current word list?", title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
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
  }
}
