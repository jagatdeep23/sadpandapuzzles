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
import javax.swing.JFrame;
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
   * the frame
   */
  private JFrame frame;
  
  /**
   * Window Constructor
   * 
   * @param controller
   *          the controller that will mediate between the model and view
   */
  public Window (Controller controller) {
    this.controller = controller;
    EventListener listener = new EventListener ();
    setLayout (new BorderLayout ());
    add (Components.buildToolbar (), BorderLayout.NORTH);
    Components.outputPanel = new OutputPanel (controller);
    JSplitPane windowPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, Components.buildSidebar (), new JScrollPane (Components.outputPanel,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    windowPane.getLeftComponent ().setPreferredSize (new Dimension (150, 50));
    add (windowPane, BorderLayout.CENTER);
    Components.Buttons.addActionListener (listener);
    buildFrame (listener);
    
  }
  
  /**
   * The Listener for all of the GUI components
   * 
   * @author Sad Panda Software
   * @version 3.0
   */
  private class EventListener implements ActionListener, WindowListener {
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
          Components.outputPanel.repaint ();
          Components.wordList.removeAll ();
        }
      } else if (obj.equals (Components.Buttons.openButton)) {
        if (Components.wordList.getContents ().size () == 0 || save ("Open")) {
          controller.clearWordList ();
          controller.loadPuzzle ();
          Components.outputPanel.repaint ();
          Components.wordList.removeAll ();
        }
      } else if (obj.equals (Components.Buttons.saveButton)) {
        controller.save ();
      } else if (obj.equals (Components.Buttons.exportButton)) {
        controller.exportPuzzle ();
      } else if (obj.equals (Components.Buttons.helpButton)) {
        showHelp();
      } else if (obj.equals (Components.Buttons.generateButton)) {
        controller.buildPuzzle (Components.getSelectedPuzzleOption ());
        Components.outputPanel.repaint ();
      } else if (obj.equals (Components.Buttons.addWordToList)) {
        controller.addWord (Components.getWordFieldText ());
        Components.wordField.setText ("");
      } else if (obj.equals (Components.Buttons.removeWordFromList)) {
        controller.removeWord ();
      } else if (obj.equals (Components.Buttons.clearList)) {
        controller.setPuzzle (null);
        controller.clearWordList ();
        Components.wordList.getContents ().clear();
        Components.outputPanel.repaint();
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
        int result = JOptionPane.showConfirmDialog (frame, "Would you like to save what you are working on?", title, JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        switch (result) {
          case JOptionPane.CANCEL_OPTION:
            return false;
          case JOptionPane.YES_OPTION:
            controller.save ();
          default:
            return true;
        }
      }
    
    private void showHelp () {
      if (!helpIsOpen) {
        helpIsOpen = true;
        JDialog popup = new JDialog(frame, "I can has help?");
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
        popup.setResizable (false);
      }
    }
    public void windowActivated (WindowEvent arg0) {}
    public void windowClosed (WindowEvent arg0) {}
    public void windowClosing (WindowEvent arg0) {
      if (Components.wordList.getContents ().size () == 0) {
        System.exit (0);
      }
      int result = JOptionPane.showConfirmDialog (frame, "Would you like to save?", "Exit", JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE);
      switch (result) {
        case JOptionPane.YES_OPTION:
          controller.save();
        default:
          System.exit(0);
      }
    }
    public void windowDeactivated (WindowEvent arg0) {}
    public void windowDeiconified (WindowEvent arg0) {}
    public void windowIconified (WindowEvent arg0) {}
    public void windowOpened (WindowEvent arg0) {}
  }
  
  private void buildFrame (WindowListener listener) {
    frame = new JFrame ("Puzzle Generator 3.0 - Sad Panda Software");
    frame.setMinimumSize (new Dimension (480, 400));
    frame.getContentPane ().add (this);
    frame.pack ();
    frame.setSize (800, 600);
    frame.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
    frame.setVisible (true);
    frame.addWindowListener (listener);
  }
}
