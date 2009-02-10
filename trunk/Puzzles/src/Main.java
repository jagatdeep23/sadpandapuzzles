import gui.Window;
import javax.swing.JFrame;
/**
 * Main class to execute program
 * @author Sad Panda Software
 * @version 1.0
 */
public class Main {
	/**
	 * Sets up the GUI and starts the program
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Program Name");
		frame.getContentPane().add(new Window());
		frame.pack();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
