package chess.model;

import java.util.ArrayList;

import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.model.ChessPiece.ChessPieceType;

public class ChessBoardModel {

	private ArrayList<ChessBoardModelListener> listeners;

	/**
	 * This is the board. I consider the first index as the row and the second as
	 * the col (or column). The 0,0 is top left coordinate on the board. This is
	 * just for convenience, in reality, there is no intrinsic directionality to a
	 * double array (i.e. you could easily have the first index as column and the
	 * second index as row, but of course you'd have to update all of the logic for
	 * accessing its values).
	 */
	private ChessBoardSquare[][] board;

	// true = black's turn
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
	 * @param sourceRow
	 * @param sourceCol
	 * @param targetRow
	 * @param targetCol
	 */
	public void move(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		setChessPiece(targetRow, targetCol, board[sourceRow][sourceCol].getChessPiece());
		setChessPiece(sourceRow, sourceCol, null);
		turn = !turn;
		fireTurnChanged();
	}

	public ChessPiece getChessPiece(int row, int col) {
		return board[row][col].getChessPiece();
	}

	public void setChessPiece(int row, int col, ChessPiece piece) {
		board[row][col].setChessPiece(piece);
		fireChessBoardChangedEvent(row, col, board[row][col]);
	}

	/**
	 * There's no matching set ChessBoardSquare because the board is really final on
	 * construction whereas pieces may be moved around.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public ChessBoardSquare getChessBoardSquare(int row, int col) {
		return board[row][col];
	}

	public void clearHighlight() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j].setHighlighted(false);
				fireChessBoardChangedEvent(i, j, board[i][j]);
			}
		}
	}

	public void setHighlight(int row, int col) {
		board[row][col].setHighlighted(true);
		fireChessBoardChangedEvent(row, col, board[row][col]);
	}

	public void setHighlight(ArrayList<BoardCoordinate> selections) {
		for (int i = 0, n = selections.size(); i < n; i++) {
			BoardCoordinate bc = selections.get(i);
			setHighlight(bc.getRow(), bc.getCol());
		}
	}

	private void fireChessBoardChangedEvent(int row, int col, ChessBoardSquare cbs) {
		ChessBoardModelEvent e = new ChessBoardModelEvent(row, col, cbs);
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
		// first index is row, second index is col
		board = new ChessBoardSquare[8][8];
		board[0][0] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, true));
		board[0][1] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, true));
		board[0][2] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, true));
		board[0][3] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Queen, true));
		board[0][4] = new ChessBoardSquare(new ChessPiece(ChessPieceType.King, true));
		board[0][5] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, true));
		board[0][6] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, true));
		board[0][7] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, true));

		for (int col = 0; col < 8; col++) {
			board[1][col] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Pawn, true));
		}

		for (int row = 2; row < 6; row++) {
			for (int col = 0; col < 8; col++) {
				board[row][col] = new ChessBoardSquare();
			}
		}

		for (int col = 0; col < 8; col++) {
			board[6][col] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Pawn, false));
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
