package chess.controller;

import java.util.ArrayList;

import chess.model.BoardCoordinate;
import chess.model.ChessBoardModel;
import chess.model.ChessPiece;

/**
 * Note that the logic in here assumes black always starts at the top. We could
 * enable players to choose color and have it be on top or bottom, but didn't
 * think about it. In that case you'd have to update the logic a bit. I also
 * think in terms of pixel coordinates, so top left is double array index of 0,0
 * 
 * @author John T. Langton
 *
 */
public class MoveLogic {

	/**
	 * This method and the methods it calls are about finding all of the possible
	 * moves of a piece.
	 * 
	 * @param model
	 * @param row
	 * @param col
	 * @return
	 */
	public static ArrayList<BoardCoordinate> getMoves(ChessBoardModel model, int row, int col) {
		ChessPiece piece = model.getChessPiece(row, col);
		if (piece == null) {
			return new ArrayList<BoardCoordinate>(0);
		}
		switch (piece.getChessPieceType()) {
		case Rook:
			return getRookMoves(model, row, col);
		case Knight:
			return getKnightMoves(model, row, col);
		case Bishop:
			return getBishopMoves(model, row, col);
		case King:
			return getKingMoves(model, row, col);
		case Queen:
			return getQueenMoves(model, row, col);
		case Pawn:
			return getPawnMoves(model, row, col);
		default:
			return new ArrayList<BoardCoordinate>(0);
		}
	}

	public static boolean onBoard(int row, int col) {
		if (row < 0 || row > 7 || col < 0 || col > 7) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This is a bit different than seeing if a piece can move to a space. This is
	 * only the logic of whether a piece can occupy a space, assuming it could move
	 * there (move logic is controlled elsewhere). This is just a convenience method
	 * since this logic is repetitive in other methods. Any time you have logic that
	 * seems to repeat in a lot of different places, consider making a method for
	 * that specific logic. That way you can reduce the overall amount of code, it's
	 * easier to read and maintain (e.g. when you make changes to that logic it's
	 * reflected everywhere it's used).
	 * 
	 * @param model
	 * @param sourceRow
	 * @param sourceCol
	 * @param targetRow
	 * @param targetCol
	 * @return
	 */
	public static boolean canOccupySpace(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow,
			int targetCol) {
		// if it's not on the board then no
		if (!(onBoard(sourceRow, sourceCol) && onBoard(targetRow, targetCol))) {
			return false;
		}

		ChessPiece source = model.getChessPiece(sourceRow, sourceCol);
		// should never really get this, can't move a blank space
		if (source == null) {
			return false;
		}

		ChessPiece target = model.getChessPiece(targetRow, targetCol);
		// if there's no piece in the location then sure
		if (target == null) {
			return true;
		}
		// if the piece in the target location belongs to the other player (indicated by
		// the color being different) then yes, you can take it
		else if (source.isBlack() != target.isBlack()) {
			return true;
		}
		// the only other situation is your own
		// piece is in the location so no you can't
		else {
			return false;
		}
	}

	public static ArrayList<BoardCoordinate> getRookMoves(ChessBoardModel model, int row, int col) {
		// I put a 16 into the constructor because we know it will never get larger
		ArrayList<BoardCoordinate> locations = new ArrayList<BoardCoordinate>(16);

		// go left
		for (int targetCol = col - 1; targetCol > 0; targetCol--) {
			if (canOccupySpace(model, row, col, row, targetCol)) {
				locations.add(new BoardCoordinate(row, targetCol));
			} else {
				// stop as soon as we reach a space we can't occupy
				// this breaks out of the for loop
				break;
			}
		}

		// go down
		for (int targetRow = row + 1; targetRow < 8; targetRow++) {
			if (canOccupySpace(model, row, col, targetRow, col)) {
				locations.add(new BoardCoordinate(targetRow, col));
			} else {
				break;
			}
		}

		// go right
		for (int targetCol = col + 1; targetCol < 8; targetCol++) {
			if (canOccupySpace(model, row, col, row, targetCol)) {
				locations.add(new BoardCoordinate(row, targetCol));
			} else {
				break;
			}
		}

		// go up
		for (int targetRow = row - 1; targetRow > 0; targetRow--) {
			if (canOccupySpace(model, row, col, targetRow, col)) {
				locations.add(new BoardCoordinate(targetRow, col));
			} else {
				break;
			}
		}

		return locations;
	}

	public static ArrayList<BoardCoordinate> getKnightMoves(ChessBoardModel model, int row, int col) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getBishopMoves(ChessBoardModel model, int row, int col) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getKingMoves(ChessBoardModel model, int row, int col) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getQueenMoves(ChessBoardModel model, int row, int col) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getPawnMoves(ChessBoardModel model, int sourceRow, int sourceCol) {
		ChessPiece piece = model.getChessPiece(sourceRow, sourceCol);
		if (piece == null) {
			return new ArrayList<BoardCoordinate>(0);
		}

		ArrayList<BoardCoordinate> moves = new ArrayList<BoardCoordinate>();

		// if you're black
		if (piece.isBlack()) {
			// See if you can move 1 down
			// You can move down one if there's no piece there and it's still on the board.
			// The logic in cabOccupySpace assumes you can take a piece, but pawns can only
			// take a piece moving sideways, so we'll have to do one more check there's
			// nothing there
			int targetRow = sourceRow + 1;
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, sourceCol)
					&& model.getChessPiece(targetRow, sourceCol) == null) {
				moves.add(new BoardCoordinate(targetRow, sourceCol));
			}
			targetRow = sourceRow + 2;
			// see if you can move 2 down, not only sourceCol value changes in this logic
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, sourceCol)
					&& model.getChessPiece(targetRow, sourceCol) == null) {
				moves.add(new BoardCoordinate(targetRow, sourceCol));
			}
			// see if we can move down and to the right
			int targetCol = sourceCol + 1;
			targetRow = sourceRow + 1;
			// in this case we're taking a piece so we want it to be non-null
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, targetCol)
					&& model.getChessPiece(targetRow, targetCol) != null) {
				moves.add(new BoardCoordinate(targetRow, targetCol));
			}
			// see if we can move down and to the left
			targetCol = sourceCol - 1;
			targetRow = sourceRow + 1;
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, targetCol)
					&& model.getChessPiece(targetRow, targetCol) != null) {
				moves.add(new BoardCoordinate(targetRow, targetCol));
			}
		}

		// if you're white, just the inverse of the prior logic
		else {
			int targetRow = sourceRow - 1;
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, sourceCol)
					&& model.getChessPiece(targetRow, sourceCol) == null) {
				moves.add(new BoardCoordinate(targetRow, sourceCol));
			}
			targetRow = sourceRow - 2;
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, sourceCol)
					&& model.getChessPiece(targetRow, sourceCol) == null) {
				moves.add(new BoardCoordinate(targetRow, sourceCol));
			}
			int targetCol = sourceCol + 1;
			targetRow = sourceRow - 1;
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, targetCol)
					&& model.getChessPiece(targetRow, targetCol) != null) {
				moves.add(new BoardCoordinate(targetRow, targetCol));
			}
			targetCol = sourceCol - 1;
			targetRow = sourceRow - 1;
			if (canOccupySpace(model, sourceRow, sourceCol, targetRow, targetCol)
					&& model.getChessPiece(targetRow, targetCol) != null) {
				moves.add(new BoardCoordinate(targetRow, targetCol));
			}
		}

		return moves;
	}

	public static boolean canMove(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow, int targetCol) {
		ChessPiece piece = model.getChessPiece(sourceRow, sourceCol);
		if (piece == null) {
			return false;
		}
		switch (piece.getChessPieceType()) {
		case Rook:
			return canRookMove(model, sourceRow, sourceCol, targetCol, targetCol);
		case Knight:
			return canKnightMove(model, sourceRow, sourceCol, targetCol, targetCol);
		case Bishop:
			return canBishopMove(model, sourceRow, sourceCol, targetCol, targetCol);
		case King:
			return canKingMove(model, sourceRow, sourceCol, targetCol, targetCol);
		case Queen:
			return canQueenMove(model, sourceRow, sourceCol, targetCol, targetCol);
		case Pawn:
			return canPawnMove(model, sourceRow, sourceCol, targetCol, targetCol);
		default:
			return false;
		}
	}

	public static boolean canRookMove(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow,
			int targetCol) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean canKnightMove(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow,
			int targetCol) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean canBishopMove(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow,
			int targetCol) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean canKingMove(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow,
			int targetCol) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean canQueenMove(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow,
			int targetCol) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean canPawnMove(ChessBoardModel model, int sourceRow, int sourceCol, int targetRow,
			int targetCol) {
		// quick check to make sure both positions are on the board
		if (!(onBoard(sourceRow, sourceCol) && onBoard(targetRow, targetCol))) {
			return false;
		}

		ChessPiece sourcePiece = model.getChessPiece(sourceRow, sourceCol);
		ChessPiece targetPiece = model.getChessPiece(targetRow, targetCol);
		boolean sourceIsBlack = sourcePiece.isBlack();

		// how much you're moving up/down
		int yDelta = targetCol - sourceCol;
		// how much you're moving left/right, in this case we only care how many square
		// you move, but you can move in either direction, so we want to eliminate sign
		int xDelta = targetRow = sourceRow;

		// we'll default to returning false, so look for each condition where we should
		// return true
		if (sourceIsBlack) {
			// if your on the starting row, you can move down 2, if you don't move sidways,
			// and there's no piece in that location
			if (sourceCol == 1 && yDelta == 2 && xDelta == 0 && targetPiece == null) {
				return true;
			}
			// otherwise you can move down 1 if there's no piece in that location
			if (yDelta == 1 && xDelta == 0 && targetPiece == null) {
				return true;
			}

			// otherwise you can move down and sideways 1 if you're taking a piece that
			// belongs to the other player
			if (yDelta == 1 && (xDelta == 1 || xDelta == -1) && targetPiece != null
					&& sourceIsBlack != targetPiece.isBlack()) {
				return true;
			}
		}
		// otherwise if you're white, you kind of invert the logic
		else {
			if (sourceCol == 6 && yDelta == -2 && xDelta == 0 && targetPiece == null) {
				return true;
			}
			if (yDelta == -1 && xDelta == 0 && targetPiece == null) {
				return true;
			}
			if (yDelta == -1 && (xDelta == 1 || xDelta == -1) && targetPiece != null
					&& sourceIsBlack != targetPiece.isBlack()) {
				return true;
			}
		}
		// all other moves are illegal
		return false;
	}
}