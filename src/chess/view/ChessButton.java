package chess.view;

import javax.swing.JToggleButton;

public class ChessButton extends JToggleButton {

	private final byte i;
	private final byte j;

	/**
	 * A chess button represents a square on a chess board. Different pieces may be
	 * placed onto the button but the button itself will never move. Thus the
	 * position information for it is final.
	 * 
	 * @param x
	 * @param y
	 */
	public ChessButton(final byte i, final byte j) {
		super();
		this.i = i;
		this.j = j;
	}

	public byte getI() {
		return i;
	}

	public byte getJ() {
		return j;
	}

}
