package gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * Allows items to be added and removed from the program menu
 * 
 * @author Sad Panda Software
 * @version 2.0
 */
public class MutableList extends JList {
  private static final long serialVersionUID = 1L;
  
  MutableList () {
    super (new DefaultListModel ());
  }
  
  DefaultListModel getContents () {
    return (DefaultListModel) getModel ();
  }
}