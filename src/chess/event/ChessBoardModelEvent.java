package chess.event;

import chess.model.ChessBoardSquare;

public class ChessBoardModelEvent {

	private int x, y;

	private ChessBoardSquare chessBoardSquare;

	public ChessBoardModelEvent(int x, int y, ChessBoardSquare c) {
		this.x = x;
		this.y = y;
		this.chessBoardSquare = c;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ChessBoardSquare getChessBoardSquare() {
		return chessBoardSquare;
	}

	public void setChessBoardSquare(ChessBoardSquare chessBoardSquare) {
		this.chessBoardSquare = chessBoardSquare;
	}

}
