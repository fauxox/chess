package chess.event;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import chess.controller.ChessBoardControllerINF;
import chess.view.ChessButton;

public class ToggleAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChessBoardControllerINF mover;

	private int lastRow = -1, lastCol = -1;

	public ToggleAction(ChessBoardControllerINF mover) {
		super();
		this.mover = mover;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ChessButton chessButton = (ChessButton) e.getSource();
		int row = chessButton.getRow(), col = chessButton.getCol();
		mover.selectionChanged(lastRow, lastCol, row, col);
		lastRow = row;
		lastCol = col;
	}

}
