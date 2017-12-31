package mvc;

import java.util.ArrayList;

import mvc.ChessPiece.ChessPieceType;
import mvc.ChessPiece.ColorType;
import mvc.event.ChessBoardModelEvent;
import mvc.event.ChessBoardModelListener;

public class ChessBoardModel {
	
	private ArrayList<ChessBoardModelListener> listeners;
	
	private ChessPiece[][] board;
	
	public ChessBoardModel() {
		listeners = new ArrayList<ChessBoardModelListener>();
		
	    board = new ChessPiece[8][8];
		board[0][0] = new ChessPiece(ChessPieceType.Rook, ColorType.Black);
		board[0][1] = new ChessPiece(ChessPieceType.Knight, ColorType.Black);
		board[0][2] = new ChessPiece(ChessPieceType.Bishop, ColorType.Black);
		board[0][3] = new ChessPiece(ChessPieceType.Queen, ColorType.Black);
		board[0][4] = new ChessPiece(ChessPieceType.King, ColorType.Black);
		board[0][5] = new ChessPiece(ChessPieceType.Bishop, ColorType.Black);
		board[0][6] = new ChessPiece(ChessPieceType.Knight, ColorType.Black);
		board[0][7] = new ChessPiece(ChessPieceType.Rook, ColorType.Black);
		
		for(int i = 0;i<8;i++) {
			board[1][i] = new ChessPiece(ChessPieceType.Pawn, ColorType.Black);
		}
		
		for(int i = 2;i<6;i++) {
			for(int j = 0;j<8;j++) {
				board[i][j] = new ChessPiece(ChessPieceType.None, ColorType.None);
			}
		}
		
		for(int i = 0;i<8;i++) {
			board[6][i] = new ChessPiece(ChessPieceType.Pawn, ColorType.White);
		}
		
		board[7][0] = new ChessPiece(ChessPieceType.Rook, ColorType.White);
		board[7][1] = new ChessPiece(ChessPieceType.Knight, ColorType.White);
		board[7][2] = new ChessPiece(ChessPieceType.Bishop, ColorType.White);
		board[7][3] = new ChessPiece(ChessPieceType.Queen, ColorType.White);
		board[7][4] = new ChessPiece(ChessPieceType.King, ColorType.White);
		board[7][5] = new ChessPiece(ChessPieceType.Bishop, ColorType.White);
		board[7][6] = new ChessPiece(ChessPieceType.Knight, ColorType.White);
		board[7][7] = new ChessPiece(ChessPieceType.Rook, ColorType.White);
	}
	
	public ChessPiece getChessPiece(int i, int j) {
		return board[i][j];
	}

	public void setChessPiece(int i, int j, ChessPiece piece) {
		board[i][j] = piece;
		fireChessBoardChangedEvent(i, j, piece);
	}

	private void fireChessBoardChangedEvent(int i, int j, ChessPiece piece) {
		ChessBoardModelEvent e = new ChessBoardModelEvent(i, j, piece);
		for(int k = 0, n=listeners.size();k<n;k++) {
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
