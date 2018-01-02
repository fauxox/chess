package chess.controller;

import java.util.ArrayList;

import chess.model.BoardCoordinate;
import chess.model.ChessBoardModel;
import chess.model.ChessPiece;

public class MoveLogic {

	public static ArrayList<BoardCoordinate> getMoves(ChessBoardModel model, int i, int j) {
		ChessPiece piece = model.getChessPiece(i, j);
		if (piece == null) {
			return new ArrayList<BoardCoordinate>(0);
		}
		switch (piece.getChessPieceType()) {
		case Rook:
			return getRookMoves(model, i, j);
		case Knight:
			return getKnightMoves(model, i, j);
		case Bishop:
			return getBishopMoves(model, i, j);
		case King:
			return getKingMoves(model, i, j);
		case Queen:
			return getQueenMoves(model, i, j);
		case Pawn:
			return getPawnMoves(model, i, j);
		default:
			return new ArrayList<BoardCoordinate>(0);
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
	 * @param targeterColor
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public static boolean canOccupySpace(ChessBoardModel model, int sourceX, int sourceY, int targetX, int targetY) {
		// if it's not on the board then no
		if (targetX < 0 || targetX > 7 || targetY < 0 || targetY > 7) {
			return false;
		}

		ChessPiece source = model.getChessPiece(sourceX, sourceY);
		// should never really get this, can't move a blank space
		if (source == null) {
			return false;
		}

		ChessPiece target = model.getChessPiece(targetX, targetY);
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

	public static ArrayList<BoardCoordinate> getRookMoves(ChessBoardModel model, int i, int j) {
		// I put a 16 into the constructor because we know it will never get larger
		ArrayList<BoardCoordinate> locations = new ArrayList<BoardCoordinate>(16);

		// go up
		for (int y = j - 1; y > 0; y--) {
			if (canOccupySpace(model, i, j, i, y)) {
				locations.add(new BoardCoordinate((byte) i, (byte) y));
			}
			// stop as soon as we reach a space we can't occupy
			else {
				// this breaks out of the for loop
				break;
			}
		}

		// go right (the only thing changing from last loop are the indexes
		for (int x = i + 1; x < 8; x++) {
			if (canOccupySpace(model, i, j, x, j)) {
				locations.add(new BoardCoordinate((byte) x, (byte) j));
			}
			// stop as soon as we reach a space we can't occupy
			else {
				// this breaks out of the for loop
				break;
			}
		}

		// go down
		for (int y = j + 1; y < 8; y++) {
			if (canOccupySpace(model, i, j, i, y)) {
				locations.add(new BoardCoordinate((byte) i, (byte) y));
			}
			// stop as soon as we reach a space we can't occupy
			else {
				// this breaks out of the for loop
				break;
			}
		}

		// go left
		for (int x = i - 1; x > 0; x--) {
			if (canOccupySpace(model, i, j, x, j)) {
				locations.add(new BoardCoordinate((byte) x, (byte) j));
			}
			// stop as soon as we reach a space we can't occupy
			else {
				// this breaks out of the for loop
				break;
			}
		}

		return locations;
	}

	public static ArrayList<BoardCoordinate> getKnightMoves(ChessBoardModel model, int i, int j) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getBishopMoves(ChessBoardModel model, int i, int j) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getKingMoves(ChessBoardModel model, int i, int j) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getQueenMoves(ChessBoardModel model, int i, int j) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}

	public static ArrayList<BoardCoordinate> getPawnMoves(ChessBoardModel model, int i, int j) {
		// TODO: placeholder, replace with actual moves
		return new ArrayList<BoardCoordinate>(0);
	}
}