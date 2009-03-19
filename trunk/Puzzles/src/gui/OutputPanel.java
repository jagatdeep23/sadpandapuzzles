package gui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OutputPanel extends JPanel {
  protected Controller controller;
  
  public OutputPanel (Controller controller) {
    this.controller = controller;
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor (Color.BLACK);
    
    if (controller.getPuzzle () != null) {
      System.out.println ("Drawing Puzzle");
      controller.getPuzzle ().draw (g2d);
    }
  }
}
