package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;


public class Window extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public Window () {
		
		
		WindowItems.wordListLabel = new JLabel ("Word List");
		WindowItems.wordListArea = new JTextArea ("");
		WindowItems.outputArea = new JTextArea ("");
		WindowItems.generateButton = new JButton ("Generate Puzzle(s)");
		
		WindowItems.menuBar = new MenuBar();
		
		this.setLayout (new BorderLayout());
		this.add (WindowItems.menuBar, BorderLayout.NORTH);
		
		Container wordListPane = new Container();
		wordListPane.setLayout(new BorderLayout());
		wordListPane.add(WindowItems.wordListLabel, BorderLayout.NORTH);
		wordListPane.add(new JScrollPane (WindowItems.wordListArea), BorderLayout.CENTER);
		wordListPane.add(WindowItems.generateButton, BorderLayout.SOUTH);
		
		this.add(wordListPane, BorderLayout.WEST);
		WindowItems.outputArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		this.add( new JScrollPane (WindowItems.outputArea), BorderLayout.CENTER);
		
	}
}
