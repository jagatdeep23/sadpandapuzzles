package puzzle;

import java.awt.Graphics;
import java.util.ArrayList;

public interface Puzzle {
  
  public static enum Direction { EAST, SOUTH, WEST, NORTH, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};
  
  public void generate ();
  public void draw (Graphics g);
  public void setList (ArrayList<String> list);
  public boolean addAndValidate(PuzzleWord word);
  
  public void addWordToList (String word);
  public void removeWordFromList (String word);
  public void clearWordList();
  
  public ArrayList <PuzzleWord> getWordList ();
  
  public int getNumWords ();
  public int getMatrixHeight();
  public int getMatrixWidth();
  public PuzzleCell[][] getMatrix();
  
  public void setNumWords (int i);
  public void setMatrixHeight(int i);
  public void setMatrixWidth(int i);
  public void setMatrix(PuzzleCell[][] matrix);
  public void setWordList(ArrayList<PuzzleWord> words);
   
}
