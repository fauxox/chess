package mvc;

public class ChessPiece {

	public enum ChessPieceType {King, Queen, Bishop, Knight, Rook, Pawn, None }
	
	public enum ColorType {Black, White, None}
	
	private ChessPieceType cpt;
	private ColorType ct;
	
	ChessPiece(ChessPieceType cpt, ColorType ct){
		this.cpt = cpt;
		this.ct = ct;
	}

	public ChessPieceType getCpt() {
		return cpt;
	}

	public void setCpt(ChessPieceType cpt) {
		this.cpt = cpt;
	}

	public ColorType getCt() {
		return ct;
	}

	public void setCt(ColorType ct) {
		this.ct = ct;
	}
	
	public String toString() {
		return ct.toString()+":"+cpt.toString();
	}
}
