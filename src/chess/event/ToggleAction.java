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
		//System.out.println("in event and x is " + chessButton.getI() + " and y is " + chessButton.getJ());
		mover.selectionChanged(lastToggle, chessButton);
		lastToggle = chessButton;
	}

}
