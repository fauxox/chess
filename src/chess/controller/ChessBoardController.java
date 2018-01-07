package chess.controller;

import chess.model.ChessBoardModel;
import chess.model.ChessPiece;

public class ChessBoardController implements ChessBoardControllerINF {

	private ChessBoardModel model;
	private boolean showMoves = false;

	public ChessBoardController(ChessBoardModel model) {
		this.model = model;
	}

	/**
	 * This method differs from the move in the model. This is where the logic
	 * validates moves whereas the logic in the model simply updates state. Models
	 * can also police what their values are set to such as validate moves, but in
	 * this case we kept the move logic external.
	 * 
	 * @param sourceRow
	 * @param sourceCol
	 * @param targetRow
	 * @param targetCol
	 */
	public void move(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		// only move a piece if it's legal
		if (MoveLogic.canMove(model, sourceRow, sourceCol, targetRow, targetCol)) {
			model.move(sourceRow, sourceCol, targetRow, targetCol);
		}
	}

	private void showMoves(int row, int col) {
		model.clearHighlight();
		model.setHighlight(MoveLogic.getMoves(model, row, col));
	}

	@Override
	public void setShowMoves(boolean flag) {
		this.showMoves = flag;
		if (!showMoves) {
			model.clearHighlight();
		}
	}

	/**
	 * You'll see this sometimes where a controller simply passes through a method
	 * call to the model. Because of that, some folks miss the point of the
	 * separation of MVC components, and then they'll start just making everything a
	 * pass through method invocation and have all the logic in the model. That's
	 * not quite right. Try to avoid that. You'll notice in here there's quite a bit
	 * of business logic that we don't necessarily want the model knowing about,
	 * such as whether the user was actually trying to make a move or if they were
	 * just selecting a different piece of theirs. It's arguable whether the model
	 * should be checking if an actual move is valid though. In this case I'm doing
	 * that in this controller. A model is mainly for storing state.
	 */
	public void newGame() {
		model.newGame();
	}

	public void selectionChanged(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		ChessPiece sourcePiece;
		// if this is the first turn, sourceRow will be -1
		if (sourceRow < 0) {
			sourcePiece = null;
		} else {
			sourcePiece = model.getChessBoardSquare(sourceRow, sourceCol).getChessPiece();
		}

		ChessPiece targetPiece = model.getChessBoardSquare(targetRow, targetCol).getChessPiece();

		// if no piece was selected before, or the other players piece was selected
		// before... Note that with an OR statement, if the first clause is true, then
		// the rest of the clauses are not evaluated. If they were, the in this case
		// you'd throw a null pointer exception. We know if we get to the second clause,
		// that the first one was false, and therefore we won't throw a null pointer
		// exception because we're calling isBlack() on sourcePiece which isn't null.
		if (sourcePiece == null || sourcePiece.isBlack() != model.getTurn()) {
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
			// Otherwise the piece is not null and it's one of your pieces. So if showMoves
			// is selected go ahead and do so
			else if (showMoves) {
				showMoves(targetRow, targetCol);
				return;
			}
		}

		// Based on the total logic in here, if we get down here, lastPiece should be
		// the turn taker's piece. So we'll assume that from here.

		// If they're selecting a blank location or one of the other players pieces
		// we'll assume they're trying to move there.
		if (targetPiece == null || targetPiece.isBlack() != model.getTurn()) {
			move(sourceRow, sourceCol, targetRow, targetCol);
			if (showMoves) {
				model.clearHighlight();
			}
			return;
		}
		// if they're just selecting another one of their own pieces
		else if (targetPiece.isBlack() == model.getTurn()) {
			// then just update selection if we're showing moves
			if (showMoves) {
				showMoves(targetRow, targetCol);
			}
			return;
		}

		// At this point we should have addressed every possibility and returned. So if
		// we make it down here, I forgot some logic.
		try {
			throw new Exception("Uncle Johnny messed up.");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
