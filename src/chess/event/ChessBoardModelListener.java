package chess.event;

import chess.model.ChessBoardModel;

public interface ChessBoardModelListener {

	public void chessBoardModelChanged(ChessBoardModelEvent e);

	public void turnChanged(String turnString);

	public void boardReset(ChessBoardModel chessBoardModel);

	public void chessBoardHighlightChanged(ChessBoardHighlightEvent e);

}
