/**
 * 
 */
package test;

import junit.framework.TestCase;
import puzzle.Puzzle.Direction;
import puzzle.PuzzleWord;

/**
 * This is a JUnit test that verifies the integrity of our PuzzleWord class
 * @author Sad Panda Software
 */
public class PuzzleWordTest extends TestCase {
  
  PuzzleWord word;
  
  /**
   * @param arg0
   */
  public PuzzleWordTest (String arg0) {
    super (arg0);
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#PuzzleWord()}.
   */
  public void testPuzzleWord () {
    word = new PuzzleWord();
    System.out.println ("Testing default constructor");
    assertEquals ("Row should be zero", word.getRow(), 0);
    assertEquals ("Column should be zero", word.getColumn(), 0);
    assertEquals ("Direction should be East", word.getDirection(), Direction.EAST);
    assertEquals ("Word should be \"\"", word.getWord(), "");
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#getColumn()}.
   */
  public void testGetColumn () {
    word = new PuzzleWord();
    System.out.println ("Testing getColumn");
    assertEquals ("Column should be 0", word.getColumn(), 0);
    word.setColumn (5);
    assertEquals ("Column should be 5", word.getColumn(), 5);
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#setColumn(int)}.
   */
  public void testSetColumn () {
    word = new PuzzleWord();
    System.out.println ("Testing setColumn");
    word.setColumn (10);
    assertEquals ("Column should be 10", word.getColumn(), 10);
    word.setColumn (5);
    assertEquals ("Column should be 5", word.getColumn(), 5);
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#getDirection()}.
   */
  public void testGetDirection () {
    word = new PuzzleWord();
    System.out.println ("Testing getDirection");
    assertEquals ("Direction should be East", word.getDirection(), Direction.EAST);
    word.setDirection (Direction.NORTH);
    assertEquals ("Direction should be North", word.getDirection(), Direction.NORTH);
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#setDirection(puzzle.Puzzle.Direction)}.
   */
  public void testSetDirection () {
    word = new PuzzleWord();
    System.out.println ("Testing setDirection");
    word.setColumn (10);
    assertEquals ("Column should be 10", word.getColumn(), 10);
    word.setColumn (5);
    assertEquals ("Column should be 5", word.getColumn(), 5);
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#getRow()}.
   */
  public void testGetRow () {
    word = new PuzzleWord();
    System.out.println ("Testing getRow");
    assertEquals ("Row should be 0", word.getRow(), 0);
    word.setRow (5);
    assertEquals ("Row should be 5", word.getRow(), 5);
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#setRow(int)}.
   */
  public void testSetRow () {
    word = new PuzzleWord();
    System.out.println ("Testing setRow");
    word.setRow (10);
    assertEquals ("Row should be 10", word.getRow(), 10);
    word.setRow (5);
    assertEquals ("Row should be 5", word.getRow(), 5);
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#getWord()}.
   */
  public void testGetWord () {
    word = new PuzzleWord();
    System.out.println ("Testing getWord");
    assertEquals ("Word should be \"\"", word.getWord(), "");
    word.setWord ("PLAYER");
    assertEquals ("Word should be PLAYER", word.getWord(), "PLAYER");
  }
  
  /**
   * Test method for {@link puzzle.PuzzleWord#setWord(java.lang.String)}.
   */
  public void testSetWord () {
    word = new PuzzleWord();
    System.out.println ("Testing setWord");
    word.setWord ("CHICKEN");
    assertEquals ("Word should be CHICKEN", word.getWord(), "CHICKEN");
    word.setWord ("EGG");
    assertEquals ("Word should be EGG", word.getWord(), "EGG");
  }
  
}
