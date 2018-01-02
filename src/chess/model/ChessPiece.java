package chess.model;

public class ChessPiece {

	public enum ChessPieceType {
		King, Queen, Bishop, Knight, Rook, Pawn
	}

	private ChessPieceType cpt;
	private boolean isBlack;

	ChessPiece(ChessPieceType cpt, boolean isBlack) {
		this.cpt = cpt;
		this.isBlack = isBlack;
	}

	public ChessPieceType getChessPieceType() {
		return cpt;
	}

	public void setChessPieceType(ChessPieceType cpt) {
		this.cpt = cpt;
	}

	public boolean isBlack() {
		return isBlack;
	}

	public void setBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}

}
