package chess.controller;

import chess.view.ChessButton;

/**
 * Sometimes you'll see some folks use a convention of appending INF to
 * interfaces and IMPL to implementations, or using some other sort of naming
 * scheme to indicate interaces versus implementation.
 * 
 * @author John T. Langton
 *
 */
public interface ChessBoardControllerINF {

	public void showMoves(boolean flag);

	public void selectionChanged(ChessButton lastToggle, ChessButton chessButton);

	public void newGame();

}
