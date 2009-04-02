package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import puzzle.Crossword;
import puzzle.WordSearch;

/**
 * Panel to which buttons and contols will be added
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
@SuppressWarnings("serial")
public class OutputPanel extends JPanel {
  private Controller controller;
  
  /**
   * Sets the controller for the output panel
   * 
   * @param controller
   */
  public OutputPanel (Controller controller) {
    this.controller = controller;
  }
  
  @Override
  public Dimension getPreferredSize() {
    Dimension d = new Dimension ();
    if (controller.getPuzzle () != null) {
      if (controller.getPuzzle () instanceof WordSearch) {
        d.setSize (controller.getPuzzle ().getMatrixWidth () * 20 + 75, controller.getPuzzle ().getMatrixHeight () * 20 + 60);
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
    
    if (controller.getPuzzle () != null) {
      System.out.println ("Drawing Puzzle");
      controller.getPuzzle ().draw (g2d);
    }
    
    super.setPreferredSize (getPreferredSize());
    setAutoscrolls (true);
  }
}
