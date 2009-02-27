package gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class MutableList extends JList {
  private static final long serialVersionUID = 1L;
  
  MutableList () {
    super (new DefaultListModel ());
  }
  
  DefaultListModel getContents () {
    return (DefaultListModel) getModel ();
  }
}