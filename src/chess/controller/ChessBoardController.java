package chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chess.model.ChessBoardModel;
import chess.model.ChessPiece;
import chess.view.ChessButton;

public class ChessBoardController implements ActionListener, ChessBoardControllerINF {

	private ChessBoardModel model;
	private boolean showMoves = false;

	public ChessBoardController(ChessBoardModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

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
	}

	private void showMoves(ChessButton chessButton) {
		model.clearHighlight();
		if (chessButton != null) {
			model.setHighlight(MoveLogic.getMoves(model, chessButton.getI(), chessButton.getJ()));
		}
	}

	@Override
	public void showMoves(boolean flag) {
		this.showMoves = flag;
	}

	/**
	 * You'll see this sometimes where a controller simply passes through a method
	 * call to the model. Because of that, some folks miss the point of the
	 * separation of MVC components, and then they'll start just making everything a
	 * pass through method invocation and have all the logic in the model. THat's
	 * not quite right. Try to avoid against that. You'll notice in here there's
	 * quite a bit of business logic that we don't necessarily want the model
	 * knowing about, such as whether the user was actually trying to make a move or
	 * if they were just selecting a different piece of theirs. It's arguable
	 * whether the model should be checking if an actual move is valid though. In
	 * this case I'm doing that in this controller. A model, however, is generally
	 * mainly for storing state.
	 */
	public void newGame() {
		model.newGame();
	}

	public void selectionChanged(ChessButton lastToggle, ChessButton targetButton) {
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

}
