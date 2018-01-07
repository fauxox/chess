package chess.model;

import java.util.ArrayList;

import chess.event.ChessBoardHighlightEvent;
import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.model.ChessPiece.ChessPieceType;

public class ChessBoardModel {

	private ArrayList<ChessBoardModelListener> listeners;

	/**
	 * This is the board. I consider the first index as the row and the second as
	 * the col (or column). 0,0 is top left coordinate on the board. This is just
	 * for convenience. In reality, there is no intrinsic directionality to a double
	 * array (i.e. you could easily have the first index as column and the second
	 * index as row, but of course you'd have to update all of the logic in here for
	 * accessing its values).
	 */
	private ChessBoardSquare[][] board;

	// true = black's turn
	boolean turn = true;

	public ChessBoardModel() {
		listeners = new ArrayList<ChessBoardModelListener>(1);
		initBoard();
	}

	public void newGame() {
		initBoard();
		fireBoardReset();
	}

	public void initBoard() {
		// first index is row, second index is col
		board = new ChessBoardSquare[8][8];
		board[0][0] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, true), 0, 0);
		board[0][1] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, true), 0, 1);
		board[0][2] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, true), 0, 2);
		board[0][3] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Queen, true), 0, 3);
		board[0][4] = new ChessBoardSquare(new ChessPiece(ChessPieceType.King, true), 0, 4);
		board[0][5] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, true), 0, 5);
		board[0][6] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, true), 0, 6);
		board[0][7] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, true), 0, 7);

		for (int col = 0; col < 8; col++) {
			board[1][col] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Pawn, true), 1, col);
		}

		for (int row = 2; row < 6; row++) {
			for (int col = 0; col < 8; col++) {
				board[row][col] = new ChessBoardSquare(row, col);
			}
		}

		for (int col = 0; col < 8; col++) {
			board[6][col] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Pawn, false), 6, col);
		}

		board[7][0] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, false), 7, 0);
		board[7][1] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, false), 7, 1);
		board[7][2] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, false), 7, 2);
		board[7][3] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Queen, false), 7, 3);
		board[7][4] = new ChessBoardSquare(new ChessPiece(ChessPieceType.King, false), 7, 4);
		board[7][5] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Bishop, false), 7, 5);
		board[7][6] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Knight, false), 7, 6);
		board[7][7] = new ChessBoardSquare(new ChessPiece(ChessPieceType.Rook, false), 7, 7);
		// set the turn to black
		turn = true;
	}

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

	public ChessPiece getChessPiece(int row, int col) {
		return board[row][col].getChessPiece();
	}

	public void setChessPiece(int row, int col, ChessPiece piece) {
		board[row][col].setChessPiece(piece);
		fireChessBoardSquareChangedEvent(row, col, board[row][col]);
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

	public void setHighlight(ArrayList<BoardCoordinate> selections) {
		ArrayList<ChessBoardSquare> squares = new ArrayList<ChessBoardSquare>();
		for (int i = 0, n = selections.size(); i < n; i++) {
			BoardCoordinate bc = selections.get(i);
			int row = bc.getRow(), col = bc.getCol();
			ChessBoardSquare square = board[row][col];
			square.setHighlighted(true);
			squares.add(square);
		}
		fireChessBoardHighlightChangedEvent(squares);
	}

	public void clearHighlight() {
		ArrayList<ChessBoardSquare> squares = new ArrayList<ChessBoardSquare>();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col].isHighlighted()) {
					board[row][col].setHighlighted(false);
					squares.add(board[row][col]);
				}
			}
		}
		fireChessBoardHighlightChangedEvent(squares);
	}

	/**
	 * There is no checking here for whether the move is valid. This simply removes
	 * the piece, if any, at sourceX, sourceY and places it at targetX, targetY. Any
	 * logic for checking moves is elsewhere. This class is simply a model for
	 * tracking state, which is really the main responsibility of a model. One could
	 * argue a model should also not let it's values be set to illegal values, so in
	 * this case you could argue it should validate moves. But that's not what I've
	 * done here.
	 * 
	 * @param sourceRow
	 * @param sourceCol
	 * @param targetRow
	 * @param targetCol
	 */
	public void move(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		ChessPiece sourcePiece = getChessPiece(sourceRow, sourceCol);
		ChessPiece targetPiece = getChessPiece(targetRow, targetCol);
		setChessPiece(targetRow, targetCol, sourcePiece);
		setChessPiece(sourceRow, sourceCol, null);
		if (targetPiece != null && targetPiece.getChessPieceType() == ChessPieceType.King) {
			fireGameWon(sourcePiece.isBlack());
		}
		changeTurn();
	}

	public void addChessBoardModelListener(ChessBoardModelListener listener) {
		listeners.add(listener);
	}

	public boolean removeChessBoardModelListener(ChessBoardModelListener listener) {
		return listeners.remove(listener);
	}

	private void fireGameWon(boolean clr) {
		for (int k = 0, n = listeners.size(); k < n; k++) {
			listeners.get(k).gameWon(clr);
		}
	}

	private void changeTurn() {
		turn = !turn;
		fireTurnChanged();
	}

	private void fireChessBoardSquareChangedEvent(int row, int col, ChessBoardSquare cbs) {
		ChessBoardModelEvent e = new ChessBoardModelEvent(row, col, cbs);
		for (int k = 0, n = listeners.size(); k < n; k++) {
			listeners.get(k).chessBoardModelChanged(e);
		}
	}

	private void fireChessBoardHighlightChangedEvent(ArrayList<ChessBoardSquare> squares) {
		ChessBoardHighlightEvent e = new ChessBoardHighlightEvent(squares);
		for (int k = 0, n = listeners.size(); k < n; k++) {
			listeners.get(k).chessBoardHighlightChanged(e);
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
}
