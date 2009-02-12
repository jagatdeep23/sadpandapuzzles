package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The main GUI component.  It is used to be a container for all of the GUI items
 * @author Sad Panda Software
 * @version 1.0
 */
public class Window extends JPanel{
	private static final long serialVersionUID = 1L;
	/**
	 * constructor for the window
	 */
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
	
	private class ItemListener implements ActionListener {
		

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}
	
	}
}