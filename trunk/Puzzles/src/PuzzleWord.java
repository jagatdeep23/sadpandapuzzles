public class PuzzleWord {
	String word;
	int row, col;
	Constants.Direction dir;
	
	public int getColumn() {
		return col;
	}
	public void setColumn(int col) {
		this.col = col;
	}
	public Constants.Direction getDirection() {
		return dir;
	}
	public void setDirection(Constants.Direction dir) {
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
