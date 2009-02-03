import java.util.ArrayList;


public class Puzzle {
	PuzzleCell[][] matrix;
	ArrayList<PuzzleWord> wordList;
	int numWords;
	
	//  Puzzle p = new Puzzle(list);
	
	public Puzzle (ArrayList<PuzzleWord> list, PuzzleCell[][] matrix) {
		wordList = list;
		numWords = list.size();
		
		for (int r = 0; r < matrix.length; r ++) {
			for (int c = 0; c < matrix[0].length; c ++) {
				this.matrix[r][c] = matrix[r][c];
			}
		}
	}
}
