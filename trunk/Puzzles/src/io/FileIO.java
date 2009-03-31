package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;

import puzzle.Crossword;
import puzzle.Puzzle;
import puzzle.PuzzleCell;
import puzzle.PuzzleWord;
import puzzle.WordSearch;
import puzzle.Puzzle.Direction;


/**
 * This class contains all of the File Input and Output methods.
 * 
 * @author Sad Panda Software
 * @version 2.0
 */
public class FileIO {
  
  /** A file chooser */
  private static JFileChooser chooser;
  
  /** The file that is to be loaded */
  private static File file;
  
  /** The array list of words */
  private static Vector <String> words = new Vector <String> ();
  
  /** Returns a list of words sorted by length (longest first)
   * @return ArrayList<String> - A list of words */
  public static Vector <String> getFile () {
    int status;
    status = getFileChooser().showOpenDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      file = getFileChooser().getSelectedFile ();
      System.out.println ("File selected to open: " + file.getName ());
      System.out.println ("Full path name: " + file.getAbsolutePath ());
    } else {
      words.clear ();
      return words;
    }
    
    try {
      getWords (file);
    } catch (IOException e) {
      System.out.println ("Error: IO Exception was thrown:" + e);
    }
    return words;
  }
  
  /** Processes a list of words from a file.
   * @param input a File
   * @throws IOException */
  private static void getWords (File input) throws IOException {
    FileReader fileReader = new FileReader (input);
    BufferedReader buffer = new BufferedReader (fileReader);
    String temp;
    
    while ((temp = buffer.readLine ()) != null)
      words.add (temp);
    buffer.close ();
  }
  
  /** Will call the appropriate save function based on whether or not a file has
   * been associated yet. If one has not, a new file will be created.
   * @param list Vector<String>
   * @throws IOException */
  public static void saveWords (ArrayList <String> list) {
    if (file != null) {
      try {
        save (list, file);
      } catch (IOException e) {
        System.out.println ("Error: IO Exception was thrown:" + e);
      }
    } else
      saveWordsAs (list);
  }
  
  /** Creates a new file and calls the save function to save the list of words to the new file.
   * @param list ArrayList<String>
   * @throws IOException */
  private static void saveWordsAs (ArrayList <String> list) {
    int status;
    File newFile = new File ("empty");
    status = getFileChooser().showSaveDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      newFile = getFileChooser().getSelectedFile ();
      System.out.println ("File chosen to save to: " + newFile.getName ());
      System.out.println ("Full path to file: " + newFile.getAbsolutePath ());
      try {
        save (list, newFile);
      } catch (IOException e) {
        System.out.println ("Error: IO Exception was thrown:" + e);
      }
    }
  }
  
  /** Will perform the actual save of the word list to the location provided.
   * @param list ArrayList<String>
   * @param location Location of file
   * @throws IOException */
  private static void save (ArrayList <String> list, File location) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    for (String word : list) {
      buffer.write (word + "\n");
    }
    buffer.close ();
  }
  /** Creates a new JFileChooser
   * 
   * @return JFileChooser
   */
  private static JFileChooser getFileChooser() {
    if (chooser == null) {
      chooser = new JFileChooser();
    }
    return (chooser);
  }
  
  /** Initiates the save functions to save the state of the current puzzle
   * 
   * @param puzzle
   */
  public static void savePuzzle (Puzzle puzzle) {
    int status;
    File newFile = new File ("empty");
    status = getFileChooser().showSaveDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      newFile = getFileChooser().getSelectedFile ();
      System.out.println ("File chosen to save to: " + newFile.getName ());
      System.out.println ("Full path to file: " + newFile.getAbsolutePath ());
      if(puzzle instanceof WordSearch)
      {
        try {
          saveWordSearch (puzzle, newFile);
        } catch (IOException e) {
          System.out.println ("Error: IO Exception was thrown:" + e);
        }
      }
      else if(puzzle instanceof Crossword)
      {
        try {
          saveCrossword (puzzle, newFile);
        } catch (IOException e) {
          System.out.println ("Error: IO Exception was thrown:" + e);
        }
      }
      else
      {
        System.out.println("No Puzzle to save.");
      }
    }
  }
  
  /**
   * Saves the state of the current Word Search puzzle to a file
   * @param puzzle
   * @param location
   * @throws IOException
   */
  private static void saveWordSearch (Puzzle puzzle, File location) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    ArrayList <PuzzleWord> list = puzzle.getWordList ();
    PuzzleCell[][] matrix = puzzle.getMatrix ();
    buffer.write ("wordsearch\n");
    buffer.write (puzzle.getNumWords () + "\n");
    buffer.write (puzzle.getMatrixHeight () + "\n");
    buffer.write (puzzle.getMatrixWidth () + "\n");
    for (PuzzleWord word : list) {
      buffer.write (word.getWord() + " " + word.getRow () + " " + word.getColumn() + " " + word.getDirection ().ordinal () + "\n");
    }
    String s = "";
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        s += matrix[r][c] + " " + matrix[r][c].getNumWords() + " ";
      }
      s += "\n";
    }
    buffer.write (s);
    buffer.close ();
  }
  
  /**
   * Saves the state of the current Crossword Puzzle to a file
   * @param puzzle
   * @param location
   * @throws IOException
   */
  private static void saveCrossword (Puzzle puzzle, File location) throws IOException {
    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
    ArrayList <PuzzleWord> list = puzzle.getWordList ();
    buffer.write ("crossword\n");
    buffer.write (puzzle.getNumWords () + "\n");
    buffer.write (puzzle.getMatrixHeight () + "\n");
    buffer.write (puzzle.getMatrixWidth () + "\n");
    for (PuzzleWord word : list) {
      buffer.write (word.getWord() + " " + word.getRow () + " " + word.getColumn() + " " + word.getDirection ().ordinal () + "\n");
    }
    buffer.close ();
  }

  /**
   * Initiates the load puzzle functions to load a puzzle and its state from a file
   * @return puzzle - The puzzle that is loaded
   */
  public static Puzzle loadPuzzle () {
    int status;
    status = getFileChooser().showOpenDialog (null);
    Puzzle puzzle = null;
    Scanner scan = null;
    String type = "";
    
    if (status == JFileChooser.APPROVE_OPTION) {
      file = getFileChooser().getSelectedFile ();
      System.out.println ("File selected to open: " + file.getName ());
      System.out.println ("Full path name: " + file.getAbsolutePath ());
    } else {
      return puzzle;
    }
    try{
      scan = new Scanner(file);
      type = scan.nextLine();
    }
    catch (FileNotFoundException e){
      System.out.println ("Error: File Not Found Exception was thrown:" + e);
    }
    
    if(type.equals ("wordsearch"))
      {
        puzzle = loadWordSearch (scan);
        return puzzle;
      }
    else if(type.equals ("crossword"))
    {
        puzzle = loadCrossword (scan);
        return puzzle;
    }
    else
    {
      System.out.println("Invalid File");
      return puzzle;
    }
  }
  
  /**
   * Loads a Word Search puzzle and its state from a file
   * @param scan
   * @return Puzzle - a Word Search puzzle
   */
  private static Puzzle loadWordSearch(Scanner scan)
  {
    Puzzle puzzle = new WordSearch();
    
    int height, width;
    Scanner scan2 = new Scanner(scan.nextLine());
    puzzle.setNumWords (scan2.nextInt ());
    scan2 = new Scanner(scan.nextLine());
    height = scan2.nextInt ();
    puzzle.setMatrixHeight(height);
    scan2 = new Scanner(scan.nextLine());
    width = scan2.nextInt ();
    ArrayList<PuzzleWord> words = new ArrayList<PuzzleWord>();
    for(int i = 0; i < puzzle.getNumWords(); i++) {
      PuzzleWord word = new PuzzleWord();
      scan2 = new Scanner(scan.nextLine());
      word.setWord (scan2.next ());
      word.setRow (scan2.nextInt ());
      word.setColumn (scan2.nextInt ());
      word.setDirection (Direction.values()[scan2.nextInt()]);
      words.add (word);
    }
    puzzle.setWordList(words);
    PuzzleCell[][] matrix = new PuzzleCell[height][width];
    for (int r = 0; r < matrix.length; r++) {
      scan2 = new Scanner(scan.nextLine());
      for (int c = 0; c < matrix[0].length; c++) {
        matrix[r][c] = new PuzzleCell();
        matrix[r][c].setCharacter (scan2.next ().charAt (0));
        matrix[r][c].setNumWords(scan2.nextInt ());
      }
    }
    puzzle.setMatrix (matrix);
    return puzzle;
  }
  
  /**
   * Loads a Crossword puzzle and its state
   * @param scan
   * @return Puzzle - a Crossword puzzle
   */
  private static Puzzle loadCrossword(Scanner scan)
  {
    Puzzle puzzle = new Crossword();
    
    int height, width;
    Scanner scan2 = new Scanner(scan.nextLine());
    puzzle.setNumWords (scan2.nextInt ());
    scan2 = new Scanner(scan.nextLine());
    height = scan2.nextInt ();
    puzzle.setMatrixHeight(height);
    scan2 = new Scanner(scan.nextLine());
    width = scan2.nextInt ();
    puzzle.setMatrixWidth (width);
    PuzzleCell[][] matrix = new PuzzleCell[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        matrix[r][c] = new PuzzleCell();
      }
    }
    puzzle.setMatrix(matrix);
    ArrayList<PuzzleWord> words = new ArrayList<PuzzleWord>();
    for(int i = 0; i < puzzle.getNumWords(); i++) {
      PuzzleWord word = new PuzzleWord();
      scan2 = new Scanner(scan.nextLine());
      word.setWord (scan2.next ());
      word.setRow (scan2.nextInt ());
      word.setColumn (scan2.nextInt ());
      word.setDirection (Direction.values()[scan2.nextInt()]);
      words.add (word);
      puzzle.addAndValidate(word);
    }
    puzzle.setWordList(words);
    return puzzle;
  }
  
  /**
   * Initiates the export to HTML process to export a puzzle and its state to an HTML file
   * @param puzzle
   */
  public static void exportPuzzle (Puzzle puzzle) {
    int status;
    File newFile = new File ("empty");
    status = getFileChooser().showSaveDialog (null);
    
    if (status == JFileChooser.APPROVE_OPTION) {
      newFile = getFileChooser().getSelectedFile ();
      File actualFile = new File(newFile.toString() + ".html");
      System.out.println ("File chosen to save to: " + newFile.getName ());
      System.out.println ("Full path to file: " + newFile.getAbsolutePath ());
      if(puzzle instanceof WordSearch)
      {
        try {
          saveSearchHTML(puzzle, actualFile);
        } catch (IOException e) {
          System.out.println ("Error: IO Exception was thrown:" + e);
        }
      }
      else if(puzzle instanceof Crossword)
      {
        try {
          saveCrossHTML(puzzle, actualFile);
        } catch (IOException e) {
          System.out.println ("Error: IO Exception was thrown:" + e);
        }
      }
      else
      {
        System.out.println("No Puzzle to export.");
      }
    }   
  }
  
  /**
   * Exports the current Crossword puzzle and its state to an HTML file
   * @param puzzle
   * @param location
   * @throws IOException
   */
  private static void saveCrossHTML(Puzzle puzzle, File location)throws IOException {
	    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
	    ArrayList <PuzzleWord> list = puzzle.getWordList ();
	    PuzzleCell[][] matrix = puzzle.getMatrix ();
      
	    String s = "";
	    s += "<html><body><h1>Sad Panda Software Crossword</h1><table border=\"0\" bordercolor=\"ffffff\" cellpadding=\"0\" cellspacing=\"0\">";
	    for (int r = 0; r < matrix[0].length; r++) {
        for (int c = 0; c < matrix.length; c++) {
	        if('\0' == matrix[c][r].getCharacter ()){
	        	s += "<td> <center><tt> </tt></center>";  
	        }else {
	        	s += "<td> <table border=\"1\" cellpadding=\"3\" cellspacing=\"5\">  <td><tt>"
	        		+ " " + "<tt></td>  </table>";
	        }
	      }
	      s += "<tr>";
	    }
	    s += "</table><br><br>";
	    buffer.write (s);
	    
      buffer.write ("<b>East</b><br>");
	    for (PuzzleWord word : list) {
		    if (word.getDirection ().name ().toLowerCase ().equals ("east")) {
		      buffer.write (word.getWord () + "<br>");
        }
      }
      buffer.write ("<br>");
      
      buffer.write ("<b>South</b><br>");
      for (PuzzleWord word : list) {
        if (word.getDirection ().name ().toLowerCase ().equals ("south")) {
          buffer.write (word.getWord () + "<br>");
        }
      }
      buffer.write ("<br>");
      
	    buffer.close ();

  }
  
  /**
   * Exports the current Word Search puzzle and its state to an HTML file
   * @param puzzle
   * @param location
   * @throws IOException
   */
  private static void saveSearchHTML(Puzzle puzzle, File location)throws IOException {
	    BufferedWriter buffer = new BufferedWriter (new FileWriter (location));
	    ArrayList <PuzzleWord> list = puzzle.getWordList ();
	    PuzzleCell[][] matrix = puzzle.getMatrix ();
	    
	    String s = "<html><body><pre>";
	    for (int r = 0; r < matrix.length; r++) {
	      for (int c = 0; c < matrix[0].length; c++) {
	        s += matrix[r][c] + " ";
	      }
	      s += "\n";
	    }
	    
	    
	    for (PuzzleWord word : list) {
		      s += "\n" + word.getWord();
		    }
	    s += "</pre></body></html>";
	    buffer.write (s);
	    buffer.close ();
  }
}
