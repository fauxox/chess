package chess.event;

import chess.model.ChessPiece;

public class ChessBoardModelEvent {

	private int x, y;
	
	private ChessPiece cp;
	
	public ChessBoardModelEvent(int x, int y, ChessPiece cp){
		this.x = x;
		this.y = y;
		this.cp = cp;
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

	public ChessPiece getCp() {
		return cp;
	}

	public void setCp(ChessPiece cp) {
		this.cp = cp;
	}
	
}
