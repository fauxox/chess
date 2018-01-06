package chess.model;

/**
 * Compact class for holding a coordinate of chess board.
 * 
 * @author John T. Langton
 *
 */
public class BoardCoordinate {

	private int row, col;

	public BoardCoordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
