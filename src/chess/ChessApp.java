package chess;

import chess.controller.ChessBoardController;
import chess.model.ChessBoardModel;
import chess.view.ChessBoardView;

public class ChessApp {

	public static void main(String[] args) {
		ChessBoardModel model = new ChessBoardModel();
		ChessBoardController jc = new ChessBoardController();
		ChessBoardView jv = new ChessBoardView("My App", model);
		jv.addBoardListener(jc);
		jv.setSize(600, 600);
		jv.setVisible(true);
		
		model.setChessPiece(0, 0, model.getChessPiece(0, 1));
	}
	
}
