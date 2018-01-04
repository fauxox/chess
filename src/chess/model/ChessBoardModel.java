package chess.model;

import java.util.ArrayList;

import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.model.ChessPiece.ChessPieceType;

public class ChessBoardModel {

	private ArrayList<ChessBoardModelListener> listeners;

	private ChessBoardSquare[][] board;

	// simple turn logic, true = black's turn
	boolean turn = true;

	public ChessBoardModel() {
		listeners = new ArrayList<ChessBoardModelListener>();
		initBoard();
	}

	/**
	 * There is no setter for turn; the way to change it is to call the move method.
	 * 
	 * @return
	 */
	public boolean getTurn() {
		return turn;
	}

	public String getTurnString() {
		if (turn) {
			return " | Turn : Black";
		} else {
			return " | Turn : White";
		}
	}

	/**
	 * There is no checking here for whether the move is valid. This simply remove
	 * the piece, if any, at sourceX, sourceY and places it at targetX, targetY. Any
	 * logic for checking moves should go in a controller. This class is simply a
	 * model for tracking state.
	 * 
	 * @param sourceX
	 * @param sourceY
	 * @param targetX
	 * @param targetY
	 */
	public void move(int sourceX, int sourceY, int targetX, int targetY) {
		setChessPiece(targetX, targetY, board[sourceX][sourceY].getChessPiece());
		setChessPiece(sourceX, sourceY, null);
		turn = !turn;
		fireTurnChanged();
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

	public void setHighlight(int i, int j) {
		board[i][j].setHighlighted(true);
		fireChessBoardChangedEvent(i, j, board[i][j]);
	}

	public void setHighlight(ArrayList<BoardCoordinate> selections) {
		for (int i = 0, n = selections.size(); i < n; i++) {
			BoardCoordinate bc = selections.get(i);
			setHighlight(bc.getX(), bc.getY());
		}
	}

	private void fireChessBoardChangedEvent(int i, int j, ChessBoardSquare cbs) {
		ChessBoardModelEvent e = new ChessBoardModelEvent(i, j, cbs);
		for (int k = 0, n = listeners.size(); k < n; k++) {
			listeners.get(k).chessBoardModelChanged(e);
		}
	}

	private void fireTurnChanged() {
		String turnString = getTurnString();
		for (int k = 0, n = listeners.size(); k < n; k++) {
			listeners.get(k).turnChanged(turnString);
		}
	}

	private void fireBoardReset() {
		for (int k = 0, n = listeners.size(); k < n; k++) {
			listeners.get(k).boardReset(this);
		}
	}

	public void addChessBoardModelListener(ChessBoardModelListener listener) {
		listeners.add(listener);
	}

	public boolean removeChessBoardModelListener(ChessBoardModelListener listener) {
		return listeners.remove(listener);
	}

	public void initBoard() {
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
		// set the turn to black
		turn = true;
	}

	public void newGame() {
		initBoard();
		fireBoardReset();
	}

}
