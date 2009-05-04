package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/** What the user sees on the screen. The "View" component of the MVC design pattern.
 * @author Sad Panda Software
 * @version 3.0 */

public class Window extends JPanel {
  
  /** the serialized version ID */
  private static final long serialVersionUID = -5087749268470966093L;

  /** the graphical controller that mediates between the model and view */
  private Controller controller;
  
  /** the frame */
  private JFrame frame;
  
  /** Window Constructor
   * @param controller the controller that will mediate between the model and view */
  public Window (Controller controller) {
    this.controller = controller;
    EventListener listener = new EventListener ();
    setLayout (new BorderLayout ());
    add (Components.buildToolbar (), BorderLayout.NORTH);
    Components.setOutputPanel (new OutputPanel (controller));
    JSplitPane windowPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, Components.buildSidebar (), new JScrollPane (Components.getOutputPanel(),
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    windowPane.getLeftComponent ().setPreferredSize (new Dimension (150, 50));
    add (windowPane, BorderLayout.CENTER);
    Components.Buttons.addActionListener (listener);
    buildFrame (listener);
  }
  
  /** The Listener for all of the GUI components
   * @author Sad Panda Software
   * @version 3.0 */
  private class EventListener implements ActionListener, WindowListener {
    
    /** the action that is performed based on the event passed
     * @param event the event that triggers actions */
    public void actionPerformed (ActionEvent event) {
      Object obj = event.getSource ();
      if (obj.equals (Components.Buttons.newButton)) {
        if (Components.wordList.getContents ().size () == 0 && controller.getPuzzle () == null || save ("New")) {
          controller.clearWordList ();
          controller.setPuzzle (null);
          Components.getOutputPanel().repaint ();
          Components.wordList.removeAll ();
        }
      } else if (obj.equals (Components.Buttons.openButton)) {
        if (Components.wordList.getContents ().size () == 0 && controller.getPuzzle () == null || save ("Open")) {
          controller.clearWordList ();
          controller.loadPuzzle ();
          Components.getOutputPanel().repaint ();
          Components.wordList.removeAll ();
        }
      } else if (obj.equals (Components.Buttons.saveButton)) {
        controller.save ();
      } else if (obj.equals (Components.Buttons.exportButton)) {
        controller.exportPuzzle ();
      } else if (obj.equals (Components.Buttons.generateButton)) {
        String type = Components.getSelectedPuzzleOption ();
        controller.buildPuzzle (type);
        Components.getOutputPanel().repaint ();
      } else if (obj.equals (Components.Buttons.addWordToList)) {
        controller.addWord (Components.getWordFieldText ());
        Components.setWordFieldText ("");
      } else if (obj.equals (Components.Buttons.removeWordFromList)) {
        controller.removeWord ();
      } else if (obj.equals (Components.Buttons.clearList)) {
        controller.setPuzzle (null);
        controller.clearWordList ();
        Components.wordList.getContents ().clear();
        Components.getOutputPanel().repaint();
      } else if (obj.equals (Components.getWordField())) {
        controller.addWord (Components.getWordFieldText ());
        Components.setWordFieldText ("");
      } else if (obj.equals (Components.Buttons.loadList)) {
        controller.loadList();
      }
    }
    
    /** Prompts to save the current puzzle
     * @param title the text to display as the title
     * @return true if save was yes or no; false if save was canceled. */
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
  
  /** builds the main frame for the Window */
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
