package chess.event;

import chess.model.ChessBoardSquare;

public class ChessBoardModelEvent {

	private int row, col;

	private ChessBoardSquare chessBoardSquare;

	public ChessBoardModelEvent(int row, int col, ChessBoardSquare c) {
		this.row = row;
		this.col = col;
		this.chessBoardSquare = c;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int x) {
		this.row = x;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int y) {
		this.col = y;
	}

	public ChessBoardSquare getChessBoardSquare() {
		return chessBoardSquare;
	}

	public void setChessBoardSquare(ChessBoardSquare chessBoardSquare) {
		this.chessBoardSquare = chessBoardSquare;
	}

}
