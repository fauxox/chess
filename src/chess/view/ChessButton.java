package chess.view;

import javax.swing.JToggleButton;

import chess.event.ToggleAction;

public class ChessButton extends JToggleButton {

	private final byte i;
	private final byte j;

	/**
	 * A chess button represents a square on a chess board. Different pieces may be
	 * placed onto the button but the button itself will never move. Thus the
	 * position information for it is final.
	 * 
	 * @param toggleAction
	 * 
	 * @param x
	 * @param y
	 */
	public ChessButton(ToggleAction toggleAction, final byte i, final byte j) {
		// Here's an example where we want the super method to do whatever it does with
		// AbstractAction objects, so we call it. Thus we don't have to figure out
		// whatever book keeping it does there and do it ourselves.
		super(toggleAction);
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
