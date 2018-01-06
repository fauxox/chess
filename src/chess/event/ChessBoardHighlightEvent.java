package chess.event;

import java.util.ArrayList;
import chess.model.ChessBoardSquare;

public class ChessBoardHighlightEvent {

	private ArrayList<ChessBoardSquare> squares;

	public ChessBoardHighlightEvent(ArrayList<ChessBoardSquare> squares) {
		this.squares = squares;
	}

	public ArrayList<ChessBoardSquare> getSquares() {
		return squares;
	}

	public void setSquares(ArrayList<ChessBoardSquare> squares) {
		this.squares = squares;
	}

}
