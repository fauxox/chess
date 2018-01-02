package chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import chess.model.BoardCoordinate;
import chess.model.ChessBoardModel;
import chess.model.ChessBoardSquare;
import chess.model.ChessPiece;
import chess.view.ChessButton;

public class ChessBoardController implements ActionListener {

	private ChessBoardModel model;
	private BoardCoordinate lastToggle;
	private boolean showMoves = true;
	private boolean turn = true;

	public ChessBoardController(ChessBoardModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// The only thing we'll get ActionEvents from
		// at this point are buttons on the board
		ChessButton chessButton = (ChessButton) e.getSource();
		int newX = chessButton.getI();
		int newY = chessButton.getJ();
		ChessBoardSquare target = model.getChessBoardSquare(newX, newY);
		ChessPiece targetPiece = target.getChessPiece();

		// if nothing was selected, you're selecting a piece, and it's your piece (noted
		// by it's color matching the turn color)
		if (lastToggle == null && targetPiece != null && targetPiece.isBlack() == turn) {
			lastToggle = new BoardCoordinate((byte) newX, (byte) newY);
			if (showMoves) {
				showMoves(model, newX, newY);
			}
			return;
		}

		// We don't do anything if nothing was selected and you're
		// selecting a blank target or the other person's piece.

		// I set up the logic in here such that the last thing selected should always be
		// your piece if we get to this if statement. If you did a move, then I'll set
		// the lastToggle to null (even though in the view the last piece moved remains
		// selected). That means it would have been picked up by the prior if statement.
		// So the only time you'd get here is if the last thing selected is your piece.
		// If you're just selecting another one of your own pieces, update selection
		if (targetPiece.isBlack() == turn) {
			lastToggle = new BoardCoordinate((byte) newX, (byte) newY);
			if (showMoves) {
				showMoves(model, newX, newY);
			}
		}

		// we don't really need to do this check, but for completeness
		if (targetPiece.isBlack() != turn) {
			int sourceX = lastToggle.getX();
			int sourceY = lastToggle.getY();
			ChessPiece sourcePiece = model.getChessPiece(sourceX, sourceY);
		}

	}

	private void move(ChessBoardModel model, int sourceX, int sourceY, int targetX, int targetY) {

	}

	/**
	 * It's great if you can avoid referencing global variables and instead require
	 * what you need in your method parameters.
	 * 
	 * @param model
	 * @param i
	 * @param j
	 */
	private void showMoves(ChessBoardModel model, int i, int j) {
		// clear any old highlight
		model.clearHighlight();
		// set the new highlights
		model.setHighlight(MoveLogic.getMoves(model, i, j));
	}

}
