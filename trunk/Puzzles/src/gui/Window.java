package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Collections;

import javax.swing.*;

import io.FileIO;

import shared.Algorithms;
import shared.Controller;

/**
 * The main GUI component. It is used to be a container for all of the GUI items
 * 
 * @author Sad Panda Software
 * @version 1.0
 */
public class Window extends JPanel {
	private Controller controller;

	private static final long serialVersionUID = 1L;

	/**
	 * constructor for the window
	 */
	public Window(Controller c) {

		controller = c;
		ActionListener listener = new ItemListener();

		WindowItems.wordListLabel = new JLabel("Word List");
		WindowItems.wordListArea = new JTextArea("");
		WindowItems.outputArea = new JTextArea("");
		WindowItems.generateButton = new JButton("Generate Puzzle(s)");

		WindowItems.wordListArea.setFont(new Font ("Courier New", Font.PLAIN, 16));
		WindowItems.outputArea.setFont(new Font("Courier New", Font.PLAIN, 22));
		WindowItems.outputArea.setEditable(false);

		WindowItems.menuBar = new MenuBar(listener);
		WindowItems.generateButton.addActionListener(listener);

		this.setLayout(new BorderLayout());
		this.add(WindowItems.menuBar, BorderLayout.NORTH);

		WindowItems.wordsearchItem.setSelected(true);
		controller.toggleWordSearchOption();
		
		Container wordListPane = new Container();
		wordListPane.setLayout(new BorderLayout());
		wordListPane.add(WindowItems.wordListLabel, BorderLayout.NORTH);
		wordListPane.add(new JScrollPane(WindowItems.wordListArea),
				BorderLayout.CENTER);
		wordListPane.add(WindowItems.generateButton, BorderLayout.SOUTH);

		this.add(wordListPane, BorderLayout.WEST);
		WindowItems.outputArea.setBorder(BorderFactory.createEmptyBorder(10,
				10, 10, 10));
		this.add(new JScrollPane(WindowItems.outputArea), BorderLayout.CENTER);

	}

	private class ItemListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			Object o = arg0.getSource();

			if (o.equals(WindowItems.newItem)) {
				// Save the current puzzle?
				// yes / no / cancel
				int result = JOptionPane.showConfirmDialog(null,
						"Would you like to save the current word list?", "New",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.CANCEL_OPTION) {
					// Do nothing
				} else {
					if (result == JOptionPane.YES_OPTION) {
						FileIO.saveWords (controller.getWordList());
					} else if (result == JOptionPane.NO_OPTION) {
						// Do nothing
					}
					controller.getWordList().clear();
					WindowItems.outputArea.setText("");
					WindowItems.wordListArea.setText("");
				}
			}
			if (o.equals(WindowItems.loadItem)) {
				// Save the current puzzle?
				// yes / no / cancel
				int result = JOptionPane.showConfirmDialog(null,
						"Would you like to save the current word list?",
						"Load", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.CANCEL_OPTION) {
					// Do nothing
				} else {
					if (result == JOptionPane.YES_OPTION) {
						FileIO.saveWords (controller.getWordList());
					} else if (result == JOptionPane.NO_OPTION) {
						// Do nothing
					}
					// clear contents
					controller.getWordList().clear();
					WindowItems.outputArea.setText("");
					
					// load contents
					controller.setWordList(FileIO.getFile());
					WindowItems.wordListArea.setText(Algorithms.arrayToString(controller.getWordList()));
				}
			}
			if (o.equals(WindowItems.saveItem)) {
				// save item
				FileIO.saveWords (controller.getWordList());
			}
			if (o.equals(WindowItems.exportItem)) {
				// disabled
			}
			if (o.equals(WindowItems.exitItem)) {
				// Save the current puzzle?
				// yes / no / cancel
				int result = JOptionPane.showConfirmDialog(null,
						"Would you like to save the current word list?",
						"Quit", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.CANCEL_OPTION) {
					// Do nothing
				} else {
					if (result == JOptionPane.YES_OPTION) {
						// Prompt to save
					} else if (result == JOptionPane.NO_OPTION) {
						// Do nothing
					}
					System.exit(0);
				}
			}

			if (o.equals(WindowItems.wordsearchItem)) {
				controller.toggleWordSearchOption();
			}
			if (o.equals(WindowItems.crosswordItem)) {
				controller.toggleCrosswordOption();
				// disabled
			}

			if (o.equals(WindowItems.helpItem)) {

			}
			if (o.equals(WindowItems.aboutItem)) {

			}
			if (o.equals(WindowItems.generateButton)) {
				controller.setWordList(Algorithms.stringToArray (WindowItems.wordListArea.getText()));
				Collections.sort(controller.getWordList(), new Algorithms.SortByLineLength());
				
				if (controller.getDoWordSearch() || controller.getDoCrossword()) {
					WindowItems.outputArea.setText("");
					if (controller.getDoWordSearch()) {
						controller.generateWordSearchPuzzle();
						WindowItems.outputArea.append(controller.getWordSearch().toString() + "\n\n");
					}
					
					if (controller.getDoCrossword()) {
						
					}
					
					WindowItems.outputArea.append(Algorithms.arrayToString(controller.getWordList()));
				}
			}
		}
	}
}
