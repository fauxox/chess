package chess.controller;

import chess.model.ChessBoardModel;
import chess.model.ChessPiece;

public class MoveLogic {

	public static int[][] getMoves(ChessBoardModel model, ChessPiece piece, int i, int j) {
		switch (piece.getCpt()) {
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
		}
		return null;
	}

	public static int[][] getKnightMoves(ChessBoardModel model, int i, int j) {
		return null;
	}

	public static int[][] getRookMoves(ChessBoardModel model, int i, int j) {
		return null;
	}

	public static int[][] getBishopMoves(ChessBoardModel model, int i, int j) {
		return null;
	}

	public static int[][] getKingMoves(ChessBoardModel model, int i, int j) {
		return null;
	}

	public static int[][] getQueenMoves(ChessBoardModel model, int i, int j) {
		return null;
	}

	public static int[][] getPawnMoves(ChessBoardModel model, int i, int j) {
		return null;
	}
}