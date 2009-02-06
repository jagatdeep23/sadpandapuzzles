public class PuzzleWord {
	String word;
	int row, col;
	Algorithms.Direction dir;
	
	public PuzzleWord()
	{
		row = 0;
		col = 0;
		dir = Algorithms.Direction.E;
		word = "";
	}
	
	public int getColumn() {
		return col;
	}
	public void setColumn(int col) {
		this.col = col;
	}
	public Algorithms.Direction getDirection() {
		return dir;
	}
	public void setDirection(Algorithms.Direction dir) {
		this.dir = dir;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
}
