package chess.controller;

/**
 * Sometimes you'll see some folks use a convention of appending INF to
 * interfaces and IMPL to implementations, or using some other sort of naming
 * scheme to indicate interfaces versus implementation.
 * 
 * @author John T. Langton
 *
 */
public interface ChessBoardControllerINF {

	public void setShowMoves(boolean flag);

	public void selectionChanged(int lastRow, int lastCol, int targetRow, int targetCol);

	public void newGame();

}
