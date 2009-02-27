package puzzle;

import java.awt.Graphics;
import java.util.ArrayList;

public interface Puzzle {
  
  public static enum Direction { EAST, SOUTH, WEST, NORTH, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};
  
  public void generate ();
  public void draw (Graphics g);
  public void setList (ArrayList<String> list);
  
  public void addWordToList (String word);
  public void removeWordFromList (String word);
  public void clearWordList();
  
  public ArrayList <PuzzleWord> getWordList ();
  
  public int getNumWords ();
  public int getMatrixHeight();
  public int getMatrixWidth();
   
}
