package chess.model;

public class ChessPiece {

	public enum ChessPieceType {
		King, Queen, Bishop, Knight, Rook, Pawn
	}

	private final ChessPieceType cpt;
	private final boolean isBlack;

	ChessPiece(ChessPieceType cpt, boolean isBlack) {
		this.cpt = cpt;
		this.isBlack = isBlack;
	}

	public ChessPieceType getChessPieceType() {
		return cpt;
	}

	public boolean isBlack() {
		return isBlack;
	}

}
