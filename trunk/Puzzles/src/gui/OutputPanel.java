package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

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
  }
}
