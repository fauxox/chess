package chess.view;

import javax.swing.JToggleButton;

import chess.event.ToggleAction;

public class ChessButton extends JToggleButton {

	private final byte row;
	private final byte col;

	/**
	 * A chess button represents a square on a chess board. Different pieces may be
	 * placed onto the button but the button itself will never move. Thus the
	 * position information for it is final. Note we don't use x and y because all
	 * JComponents already have x and y values used for rendering them on the
	 * screen.
	 * 
	 * @param toggleAction
	 * 
	 * @param row
	 *            we don't use X because that's a screen coordinate used for
	 *            rendering JComponents
	 * @param chessYwe
	 *            don't use Y because that's a screen coordinate used for rendering
	 *            JComponents
	 */
	public ChessButton(ToggleAction toggleAction, final byte row, final byte col) {
		// Here's an example where we want the super method to do whatever it does with
		// AbstractAction objects, so we call it. Thus we don't have to figure out
		// whatever book keeping it does there and do it ourselves.
		super(toggleAction);
		this.row = row;
		this.col = col;
	}

	public byte getRow() {
		return row;
	}

	public byte getCol() {
		return col;
	}

}
