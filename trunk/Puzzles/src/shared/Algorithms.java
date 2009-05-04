package shared;

/** Algorithms used in various places throughout the program
 * @author Sad Panda Software
 * @version 3.0 */
public class Algorithms {
  
  /** This is the Comparator class used to sort words by length
   * @author Sad Panda Software
   * @version 3.0 */
  public static class SortByLineLength implements java.util.Comparator <String> {
    
    /** compares two strings
     * @param one the first string
     * @param two the second string
     * @return true if two's length is greater than one's length; false otherwise */
    public int compare (String one, String two) {
      return (two.length () - one.length ());
    }
  }
}
