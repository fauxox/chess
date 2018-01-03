package chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chess.model.ChessBoardModel;
import chess.model.ChessPiece;
import chess.view.ChessButton;

public class ChessBoardController implements ActionListener {

	private ChessBoardModel model;
	private boolean showMoves = false;

	// normally I'd say hanging onto a view component is
	// bad, but this is a simple hack and not too egregious
	private ChessButton lastToggle;

	public ChessBoardController(ChessBoardModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// The only thing we'll get ActionEvents from
		// at this point are buttons on the board
		ChessButton targetButton = (ChessButton) e.getSource();
		int newX = targetButton.getI();
		int newY = targetButton.getJ();
		ChessPiece targetPiece = model.getChessBoardSquare(newX, newY).getChessPiece();

		// if nothing was selected (note if the first part of the OR is true, then the
		// second part won't be evaluated and therefore won't throw a null pointer
		// exception), and...
		if (lastToggle == null
				|| model.getChessBoardSquare(lastToggle.getI(), lastToggle.getJ()).getChessPiece() == null) {
			// you select another blank space
			if (targetPiece == null) {
				// then don't do anything
				return;
			}
			// you select a piece of the player whose turn it is NOT
			else if (targetPiece.isBlack() != model.getTurn()) {
				// then do nothing
				return;
			}
			// otherwise the piece is not null and it's one of your pieces
			else {
				// so track it and show moves if necessary
				updateToggle(targetButton);
				return;
			}
		}

		// if we're down here then lastToggle was not null, and it's
		// ChessPiece was not null - so it had a piece, so let's grab it
		int lastX = lastToggle.getI();
		int lastY = lastToggle.getJ();
		ChessPiece lastPiece = model.getChessBoardSquare(lastX, lastY).getChessPiece();

		// Based on the total logic in here, if we get down here, lastPiece should be
		// the turn taker's piece. So we'll assume that from here.

		// If they're selecting a blank location or one of the other players pieces
		// we'll assume they're trying to move there. Note that if the first part of the
		// OR condition is true the second part won't be evaluated, thus it won't throw
		// a null pointer (which it would if it was evaluated and the first part was
		// true). This is an optimization because, since it's an OR, as soon as one
		// clause it true, the result is true, you don't need to know the other one.
		if (targetPiece == null || targetPiece.isBlack() != model.getTurn()) {
			move(targetButton, lastX, lastY, newX, newY);
			return;
		}
		// if you're just selecting another one of your own pieces
		else if (targetPiece.isBlack() == model.getTurn()) {
			updateToggle(targetButton);
			return;
		}

		// Because of the return statements in the last if and else if, we should never
		// get down here unless I forgot something that should have been in an else.
		try {
			throw new Exception("Uncle Johnny messed up.");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method differs from the move in the model. This is where the logic
	 * validates moves whereas the logic in the model simply updates state. It's a
	 * bit subjective where to put the cutoff for logic in an MVC design pattern,
	 * but generally the model is for storing state, not really business logic
	 * outside of that.
	 * 
	 * @param sourceX
	 * @param sourceY
	 * @param targetX
	 * @param targetY
	 */
	public void move(ChessButton chessButton, int sourceX, int sourceY, int targetX, int targetY) {
		if (MoveLogic.canMove(model, sourceX, sourceY, targetX, targetY)) {
			model.move(sourceX, sourceY, targetX, targetY);
		}
		// in either case we'll unselect the button and set the lastToggle to null
		chessButton.setSelected(false);
		updateToggle(null);
	}

	private void updateToggle(ChessButton chessButton) {
		this.lastToggle = chessButton;
		if (showMoves) {
			showMoves(chessButton);
		}
	}

	private void showMoves(ChessButton chessButton) {
		model.clearHighlight();
		if (chessButton != null) {
			model.setHighlight(MoveLogic.getMoves(model, chessButton.getI(), chessButton.getJ()));
		}
	}

}
