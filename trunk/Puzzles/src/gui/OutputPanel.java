package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import puzzle.Crossword;
import puzzle.WordSearch;

/**
 * Panel that the puzzle prints to
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
    this.addMouseListener (new MyMouseListener());
  }
  
  /**
   * gets the preferred size of the panel
   */
  @Override
  public Dimension getPreferredSize () {
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
    
    if (controller.getPuzzle() != null) {
      controller.getPuzzle().draw (g2d);
    }
    
    setPreferredSize (getPreferredSize ());
  }
  
  /**
   * 
   * The MouseListener that gets added to the output panel
   * 
   * @author Sad Panda Software
   * @version 3.0
   *
   */
  private class MyMouseListener implements MouseListener {

    /**
     * This method checks to see what mouse button was clicked and
     * determines what action gets performed based on the input.
     * 
     * @param m the mouse event passed
     */
    public void mouseClicked (MouseEvent m) {
      if (m.getButton () == MouseEvent.BUTTON2) {
        controller.buildPuzzle (Components.getSelectedPuzzleOption ());
        repaint();
      }
      if (m.getButton () == MouseEvent.BUTTON1 && m.getClickCount () == 2) {
        if (controller.getPuzzle () == null) {
          controller.buildPuzzle (Components.getSelectedPuzzleOption ());
          repaint();
        } else {
          io.FileIO.exportPuzzle (controller.getPuzzle ());
        }
      }
    }

    public void mouseEntered (MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    public void mouseExited (MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    public void mousePressed (MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    public void mouseReleased (MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
    
  }
}
