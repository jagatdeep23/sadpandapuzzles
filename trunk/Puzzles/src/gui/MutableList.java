package gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * Allows items to be added and removed from the program menu
 * 
 * @author Sad Panda Software
 * @version 3.0
 */
public class MutableList extends JList {
  private static final long serialVersionUID = 1L;
  
  /** default constructor */
  MutableList () {
    super (new DefaultListModel ());
  }
  
  /**
   * gets the contents of the list
   * 
   * @return contents of the list
   */
  public DefaultListModel getContents () {
    return (DefaultListModel) getModel ();
  }
}
