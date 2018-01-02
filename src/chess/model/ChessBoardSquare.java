package chess.model;

public class ChessBoardSquare {

	// In general you wouldn't have anything related to the view in the model. I'm
	// storing this in here for showing potential moves and emphasizing decoupling
	// of m/v/c with listeners. Some may take issue with this.
	private boolean highlighted = false;

	private ChessPiece chessPiece = null;

	public ChessBoardSquare() {
	}

	public ChessBoardSquare(ChessPiece cp) {
		this.chessPiece = cp;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public ChessPiece getChessPiece() {
		return chessPiece;
	}

	/**
	 * 
	 * @param chessPiece
	 *            can be null to remove piece
	 */
	public void setChessPiece(ChessPiece chessPiece) {
		this.chessPiece = chessPiece;
	}

}
