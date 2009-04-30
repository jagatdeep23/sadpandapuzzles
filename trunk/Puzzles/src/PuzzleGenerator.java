import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.Controller;
import gui.Window;

/**
 * Main class to execute program
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class PuzzleGenerator {
  /**
   * Sets up the GUI and starts the program
   * 
   * @param args
   */
  public static void main (String [] args) {
    if (System.getProperty ("mrj.version") != null) {
      System.setProperty ("apple.laf.useScreenMenuBar", "true");
    }
    try {
      UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ());
    } catch (ClassNotFoundException e) {
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    } catch (UnsupportedLookAndFeelException e) {
    }
    
    JFrame.setDefaultLookAndFeelDecorated (true);
    Controller controller = new Controller();
    new Window (controller);
  }
}
