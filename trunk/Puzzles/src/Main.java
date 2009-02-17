import gui.Window;
import javax.swing.JFrame;

import shared.Controller;
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
		
		Controller controller = new Controller();
		JFrame frame = new JFrame("Program Name");
		frame.getContentPane().add(new Window(controller));
		frame.pack();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
