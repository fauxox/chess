package chess.event;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import chess.controller.ChessBoardControllerINF;
import chess.view.ChessButton;

public class ToggleAction extends AbstractAction {

	private ChessBoardControllerINF mover;

	private ChessButton lastToggle;

	public ToggleAction(ChessBoardControllerINF mover) {
		super();
		this.mover = mover;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ChessButton chessButton = (ChessButton) e.getSource();
		mover.selectionChanged(lastToggle, chessButton);
		lastToggle = chessButton;
	}

}
