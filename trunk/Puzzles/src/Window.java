import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;


public class Window extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JMenuBar menuBar;
	JMenu fileMenu, optionsMenu, helpMenu;
	JMenuItem newItem, loadItem, saveItem, exportItem, exitItem,
	          helpItem, aboutItem;
	JCheckBoxMenuItem wordsearchItem, crosswordItem;
	JTextArea wordListArea, outputArea;
	JButton generateButton;
	JLabel wordListLabel;
	
	public Window () {
		newItem = new JMenuItem ("New");
		loadItem = new JMenuItem ("Load");
		saveItem = new JMenuItem ("Save");
		exportItem = new JMenuItem ("Export");
		exitItem = new JMenuItem ("Exit");
		wordsearchItem = new JCheckBoxMenuItem ("Word Search");
		crosswordItem = new JCheckBoxMenuItem ("Crossword");
		helpItem = new JMenuItem ("Help!");
		aboutItem = new JMenuItem ("About?");
		
		wordListLabel = new JLabel ("Word List");
		wordListArea = new JTextArea ("");
		outputArea = new JTextArea ("");
		generateButton = new JButton ("Generate Puzzle(s)");
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu ("File");
		optionsMenu = new JMenu ("Options");
		helpMenu = new JMenu ("Help");
		
		addToMenu (fileMenu, newItem, loadItem, saveItem, exportItem, exitItem);
		addToMenu (optionsMenu, wordsearchItem, crosswordItem);
		addToMenu (helpMenu, helpItem, aboutItem);
	
		menuBar.add (fileMenu);
		menuBar.add (optionsMenu);
		menuBar.add (helpMenu);
		
		this.setLayout (new BorderLayout());
		this.add (menuBar, BorderLayout.NORTH);
		
		Container wordListPane = new Container();
		wordListPane.setLayout(new BorderLayout());
		wordListPane.add(wordListLabel, BorderLayout.NORTH);
		wordListPane.add(new JScrollPane (wordListArea), BorderLayout.CENTER);
		wordListPane.add(generateButton, BorderLayout.SOUTH);
		
		this.add(wordListPane, BorderLayout.WEST);
		outputArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		this.add( new JScrollPane (outputArea), BorderLayout.CENTER);
		
	}
	
	private static void addToMenu (JMenu menu, JMenuItem ... items) {
		for (JMenuItem i: items) //Will being lazy
			menu.add(i);
	}
}
