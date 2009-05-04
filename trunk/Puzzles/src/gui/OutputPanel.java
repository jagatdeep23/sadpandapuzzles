package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import puzzle.Crossword;
import puzzle.WordSearch;

/**
 * Panel that the puzzle prints to
 * @author Sad Panda Software
 * @version 3.0
 */
@SuppressWarnings("serial")
public class OutputPanel extends JPanel {
  
  /** the controller that maintains the puzzle */
  private Controller controller;
  
  /**
   * Sets the controller for the output panel
   * @param controller
   */
  public OutputPanel (Controller controller) {
    this.controller = controller;
  }
  
  /**
   * gets the preferred size of the panel
   */
  @Override
  public Dimension getPreferredSize () {
    Dimension d = new Dimension ();
    if (controller.getPuzzle () != null) {
      if (controller.getPuzzle () instanceof WordSearch) {
        d.setSize (controller.getPuzzle ().getMatrixWidth () * 24 + 48, controller.getPuzzle ().getMatrixHeight () * 24 + 48);
      }
      if (controller.getPuzzle () instanceof Crossword) {
        d.setSize (controller.getPuzzle ().getMatrixHeight () * 24 + 37, controller.getPuzzle ().getMatrixWidth () * 24 + 32);
      }
      return d;
    }
    return super.getSize ();
  }
  
  /**
   * Draws everything on the output panel
   * 
   * @param g
   *          Graphics
   */
  public void paintComponent (Graphics g) {
    super.paintComponent (g);
    
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor (Color.BLACK);
    
    if (controller.getPuzzle() != null) {
      controller.getPuzzle().draw (g2d);
    }
    
    setPreferredSize (getPreferredSize ());
    revalidate();
  }
}
