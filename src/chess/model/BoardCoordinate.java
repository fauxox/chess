package chess.model;

/**
 * Super, compact class for holding a coordinate of chess board.
 * 
 * @author John T. Langton
 *
 */
public class BoardCoordinate {

	/**
	 * I use bytes since we'll only have values between 0 and 8.
	 */
	private byte x, y;

	public BoardCoordinate(byte x, byte y) {
		this.x = x;
		this.y = y;
	}

	public byte getX() {
		return x;
	}

	public void setX(byte x) {
		this.x = x;
	}

	public byte getY() {
		return y;
	}

	public void setY(byte y) {
		this.y = y;
	}

}
