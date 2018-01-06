package chess.event;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import chess.controller.ChessBoardControllerINF;

/**
 * This is a bit hacky, but shows how to use custom actions.
 * 
 * @author John T. Langton
 *
 */
@SuppressWarnings("serial")
public class NewGameAction extends AbstractAction {

	private ChessBoardControllerINF mover;

	public NewGameAction(ChessBoardControllerINF mover) {
		super("New Game");
		this.mover = mover;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mover.newGame();
	}

}
