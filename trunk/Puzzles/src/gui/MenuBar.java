package gui;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar{

	private static final long serialVersionUID = 1L;

	public MenuBar() {
		WindowItems.newItem = new JMenuItem ("New");
		WindowItems.loadItem = new JMenuItem ("Load");
		WindowItems.saveItem = new JMenuItem ("Save");
		WindowItems.exportItem = new JMenuItem ("Export");
		WindowItems.exitItem = new JMenuItem ("Exit");
		WindowItems.wordsearchItem = new JCheckBoxMenuItem ("Word Search");
		WindowItems.crosswordItem = new JCheckBoxMenuItem ("Crossword");
		WindowItems.helpItem = new JMenuItem ("Help!");
		WindowItems.aboutItem = new JMenuItem ("About?");
		
		WindowItems.fileMenu = new JMenu ("File");
		WindowItems.optionsMenu = new JMenu ("Options");
		WindowItems.helpMenu = new JMenu ("Help");
		
		addToMenu (WindowItems.fileMenu, WindowItems.newItem, WindowItems.loadItem, WindowItems.saveItem, WindowItems.exportItem, WindowItems.exitItem);
		addToMenu (WindowItems.optionsMenu, WindowItems.wordsearchItem, WindowItems.crosswordItem);
		addToMenu (WindowItems.helpMenu, WindowItems.helpItem, WindowItems.aboutItem);
	
		this.add (WindowItems.fileMenu);
		this.add (WindowItems.optionsMenu);
		this.add (WindowItems.helpMenu);
	}
	
	private static void addToMenu (JMenu menu, JMenuItem ... items) {
		for (JMenuItem i: items) //Will being lazy
			menu.add(i);
	}
}
