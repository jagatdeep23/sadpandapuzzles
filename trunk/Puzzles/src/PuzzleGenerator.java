import javax.swing.JFrame;
import javax.swing.UIManager;

import gui.Controller;
import gui.Window;

/**
 * Main class to execute program
 * @author Sad Panda Software
 * @version 3.0
 */
public class PuzzleGenerator {
  public static void main () {
    try {
      UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ());
    } catch (Exception e) {}
    JFrame.setDefaultLookAndFeelDecorated (true);
    
    Controller controller = new Controller();
    new Window (controller);
  }
}
