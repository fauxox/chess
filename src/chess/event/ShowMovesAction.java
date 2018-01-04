package chess.event;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;

import chess.controller.ChessBoardControllerINF;

/**
 * This is a bit hacky, but shows how to use custom actions.
 * 
 * @author John T. Langton
 *
 */
public class ShowMovesAction extends AbstractAction {

	private ChessBoardControllerINF mover;

	public ShowMovesAction(ChessBoardControllerINF mover) {
		super("Show Moves");
		this.mover = mover;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean showMoves = ((JCheckBoxMenuItem) e.getSource()).isSelected();
		mover.showMoves(showMoves);
	}

}
