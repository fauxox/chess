package chess.model;

public class ChessBoardSquare {

	// In general you wouldn't have anything related to the view in the model. I'm
	// storing this in here for showing potential moves and emphasizing decoupling
	// of m/v/c with listeners. Some may take issue with this.
	private boolean highlighted = false;

	private ChessPiece chessPiece = null;

	private final int row, col;

	public ChessBoardSquare(final int row, final int col) {
		this.row = row;
		this.col = col;
	}

	public ChessBoardSquare(ChessPiece cp, final int row, final int col) {
		this.chessPiece = cp;
		this.row = row;
		this.col = col;
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

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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
