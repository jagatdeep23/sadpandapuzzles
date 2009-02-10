package gui;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

/**
 * All of the GUI items
 * @author Sad Panda Software
 * @version 1.0
 */
public class WindowItems {
	/**
	 * the menu bar
	 */
	public static JMenuBar menuBar;
	
						/**
						 * the File menu
						 */
	public static JMenu fileMenu,
						/**
						 * the Options menu
						 */
						optionsMenu,
						/**
						 * the Help menu
						 */
						helpMenu;
							/**
							 * the New menu item
							 */
	public static JMenuItem newItem,
							/**
							 * the Load menu item
							 */
							loadItem,
							/**
							 * the Save menu item
							 */
							saveItem,
							/**
							 * the Export menu item
							 */
							exportItem,
							/**
							 * the Exit menu item
							 */
							exitItem,
							/**
							 * The Help menu item
							 */
							helpItem,
							/**
							 * The About menu item
							 */
							aboutItem;
									/**
									 * Check box item used to determine if a word search puzzle should be generated
									 */
	public static JCheckBoxMenuItem wordsearchItem,
									/**
									 * Check box item used to determine if a crossword puzzle should be generated
									 */
									crosswordItem;
							/**
							 * Area where the word list is loaded and can be modified
							 */
	public static JTextArea wordListArea,
							/**
							 * Output area for the generated puzzles
							 */
							outputArea;
							/**
							 * Button used to generate puzzles
							 */
	public static JButton generateButton;
							/**
							 * The label for the word list on the left panel
							 */
	public static JLabel wordListLabel;
	
}
