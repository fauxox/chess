package chess.model;

import java.util.ArrayList;

import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.model.ChessPiece.ChessPieceType;

public class ChessBoardModel {

	private ArrayList<ChessBoardModelListener> listeners;

	private ChessBoardSquare[][] board;

	public ChessBoardModel() {
		listeners = new ArrayList<ChessBoardModelListener>();

		board = new ChessBoardSquare[8][8];
		board[0][0] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, true));
		board[0][1] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, true));
		board[0][2] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, true));
		board[0][3] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Queen, true));
		board[0][4] = new ChessBoardSquare(new ChessPiece(ChessPieceType.King, true));
		board[0][5] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, true));
		board[0][6] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, true));
		board[0][7] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, true));

		for (int i = 0; i < 8; i++) {
			board[1][i] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Pawn, true));
		}

		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new ChessBoardSquare();
			}
		}

		for (int i = 0; i < 8; i++) {
			board[6][i] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Pawn, false));
		}

		board[7][0] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, false));
		board[7][1] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, false));
		board[7][2] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, false));
		board[7][3] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Queen, false));
		board[7][4] = new ChessBoardSquare(new ChessPiece(ChessPieceType.King, false));
		board[7][5] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, false));
		board[7][6] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, false));
		board[7][7] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, false));
	}

	public ChessPiece getChessPiece(int i, int j) {
		return board[i][j].getChessPiece();
	}

	public void setChessPiece(int i, int j, ChessPiece piece) {
		board[i][j].setChessPiece(piece);
		fireChessBoardChangedEvent(i, j, board[i][j]);
	}

	/**
	 * There's no matching set ChessBoardSquare because the board is really final on
	 * construction whereas pieces may be moved around.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public ChessBoardSquare getChessBoardSquare(int i, int j) {
		return board[i][j];
	}

	public void clearHighlight() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j].setHighlighted(false);
				fireChessBoardChangedEvent(i, j, board[i][j]);
			}
		}
	}

	public void highlight(int i, int j) {
		board[i][j].setHighlighted(true);
		fireChessBoardChangedEvent(i, j, board[i][j]);
	}

	public void setHighlight(ArrayList<BoardCoordinate> selections) {
		for (int i = 0, n = selections.size(); i < n; i++) {
			BoardCoordinate bc = selections.get(i);
			highlight(bc.getX(), bc.getY());
		}
	}

	private void fireChessBoardChangedEvent(int i, int j, ChessBoardSquare cbs) {
		ChessBoardModelEvent e = new ChessBoardModelEvent(i, j, cbs);
		for (int k = 0, n = listeners.size(); k < n; k++) {
			listeners.get(k).chessBoardModelChanged(e);
		}
	}

	public void addChessBoardModelListener(ChessBoardModelListener listener) {
		listeners.add(listener);
	}

	public boolean removeChessBoardModelListener(ChessBoardModelListener listener) {
		return listeners.remove(listener);
	}
}
