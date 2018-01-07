package chess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess.controller.ChessBoardController;
import chess.controller.ChessBoardControllerINF;
import chess.event.ChessBoardHighlightEvent;
import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.event.NewGameAction;
import chess.event.ShowMovesSelectedAction;
import chess.event.ToggleAction;
import chess.model.ChessBoardModel;
import chess.model.ChessBoardSquare;
import chess.model.ChessPiece;
import chess.model.ChessPiece.ChessPieceType;

@SuppressWarnings("serial")
public class ChessBoardView extends JFrame implements ChessBoardModelListener {

	private ChessButton[][] buttons;

	private Color defaultBackgroundColor;

	private String defaultFrameTitle;

	private JCheckBoxMenuItem showMovesMenu;

	public ChessBoardView(ChessBoardController controller, ChessBoardModel model, boolean showMoves, String title) {
		// Doing super() like this invokes the constructor in the super class. The
		// reason for doing so is so that the super class constructor can do any setup
		// and variable setting it needs to, otherwise we'd have to do all of that for
		// ourselves in our constructor here.
		super();
		this.defaultFrameTitle = title;
		this.setTitle(defaultFrameTitle + model.getTurnString());
		setJMenuBar(createMenuBar(controller, showMoves));
		JPanel buttonPanel = createButtonPanel(model, controller);
		super.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		model.addChessBoardModelListener(this);

		super.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/////////////////////////////////////////////////////////////
	// GUI support code
	/////////////////////////////////////////////////////////////

	/**
	 * I break out creation of components of the view so the constructor doesn't get
	 * too enormous. This helps to isolate logic for certain components and makes it
	 * easier to reason about them as well as test them. That point is probably more
	 * relevant for breaking out business logic.
	 * 
	 * @param model
	 * @param mover
	 * @return
	 */
	private JPanel createButtonPanel(ChessBoardModel model, ChessBoardControllerINF mover) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(8, 8));
		buttons = new ChessButton[8][8];
		// Making a button group and adding all the buttons to it just means that only
		// one button can be toggled in that group.
		ButtonGroup bg = new ButtonGroup();

		// DragMouseAdapter dma = new DragMouseAdapter();
		//
		// MyTransferHandler th = new MyTransferHandler("icon");

		// It's kind of funky but we're using the same action for all buttons.
		ToggleAction toggleAction = new ToggleAction(mover);

		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				// we could use ChessBoardSquare as a model for each button
				buttons[row][col] = new ChessButton(toggleAction, (byte) row, (byte) col);
				updatePosition(row, col, model.getChessBoardSquare(row, col));
				buttonPanel.add(buttons[row][col]);
				bg.add(buttons[row][col]);
				// buttons[i][j].addMouseListener(dma);
				// buttons[i][j].setTransferHandler(th);
			}
		}
		// Just grabbing the default background color off the first button. You can set
		// this explicitly somewhere else if you want.
		defaultBackgroundColor = buttons[0][0].getBackground();
		return buttonPanel;
	}

	private JMenuBar createMenuBar(ChessBoardControllerINF mover, boolean showMoves) {
		JMenuBar bar = new JMenuBar();
		bar.add(createFileMenu(mover));
		bar.add(createOptionsMenu(mover, showMoves));
		return bar;
	}

	private JMenu createFileMenu(ChessBoardControllerINF mover) {
		JMenu fileMenu = new JMenu("File");
		JMenuItem newGame = new JMenuItem(new NewGameAction(mover));
		fileMenu.add(newGame);
		return fileMenu;
	}

	private JMenu createOptionsMenu(ChessBoardControllerINF mover, boolean showMoves) {
		JMenu optionsMenu = new JMenu("Options");
		showMovesMenu = new JCheckBoxMenuItem(new ShowMovesSelectedAction(mover));
		showMovesMenu.setSelected(showMoves);
		mover.setShowMoves(showMoves);
		optionsMenu.add(showMovesMenu);
		return optionsMenu;
	}

	/////////////////////////////////////////////////////////////
	// Internal business logic
	/////////////////////////////////////////////////////////////

	/**
	 * This updates a single position on the board with the piece that will go there
	 * (or blank).
	 * 
	 * @param row
	 * @param col
	 * @param chessBoardSquare
	 *            should never be null, once board is initialized, though the piece
	 *            it contains may be null
	 */
	private void updatePosition(int row, int col, ChessBoardSquare chessBoardSquare) {
		// set the color of the square itself
		if (chessBoardSquare.isHighlighted()) {
			buttons[row][col].setBackground(Color.yellow);
		} else {
			buttons[row][col].setBackground(defaultBackgroundColor);
		}

		ChessPiece piece = chessBoardSquare.getChessPiece();
		// if there's no piece at this location on the board
		if (piece == null) {
			// set the icon to null so nothing appears
			buttons[row][col].setIcon(null);
		}
		// otherwise update the icon
		else {
			IconManager.getIconManager().updateIcon(buttons[row][col], piece);
		}

	}

	/**
	 * Convenience method for app saving properties.
	 * 
	 * @return whether the Show Moves JCheckBoxMenuItem in the Options menu is
	 *         selected.
	 */
	public boolean isShowMovesSelected() {
		return showMovesMenu.isSelected();
	}

	private void disableButtons() {
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].setEnabled(false);
			}
		}
	}

	private void enableButtons() {
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].setEnabled(true);
			}
		}
	}

	/////////////////////////////////////////////////////////////
	// Event related code
	/////////////////////////////////////////////////////////////

	public void addBoardListener(ActionListener bl) {
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].addActionListener(bl);
			}
		}
	}

	@Override
	public void chessBoardModelChanged(ChessBoardModelEvent e) {
		updatePosition(e.getRow(), e.getCol(), e.getChessBoardSquare());
	}

	@Override
	public void turnChanged(String turnString) {
		// Note that this class extends the JFrame class. This method, setTitle, is not
		// defined in this file, however, it is defined in the super class. You can
		// simply call the method as we have here, which is synonymous with calling
		// super.setTitle() or this.setTitle(). The reasons you sometimes see
		// super.method() invocations specifically is because a method in here could
		// override the method in the super class, and if you wanted to call the method
		// in the super class, you'd have to specify super.method() otherwise you'd
		// default to calling the local method.
		setTitle(defaultFrameTitle + turnString);
	}

	@Override
	public void boardReset(ChessBoardModel model) {
		// reset the buttons
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				updatePosition(row, col, model.getChessBoardSquare(row, col));
			}
		}
		// reset the title for the turn
		turnChanged(model.getTurnString());
		enableButtons();
	}

	@Override
	public void chessBoardHighlightChanged(ChessBoardHighlightEvent e) {
		ArrayList<ChessBoardSquare> squares = e.getSquares();
		for (int i = 0; i < squares.size(); i++) {
			ChessBoardSquare square = squares.get(i);
			int row = square.getRow();
			int col = square.getCol();
			if (square.isHighlighted()) {
				buttons[row][col].setBackground(Color.yellow);
			} else {
				buttons[row][col].setBackground(defaultBackgroundColor);
			}
		}
	}

	@Override
	public void gameWon(boolean clr) {
		String msg;
		if (clr) {
			msg = "Black won!";
		} else {
			msg = "White won!";
		}

		JOptionPane.showMessageDialog(this, msg, "Chess Message", JOptionPane.INFORMATION_MESSAGE,
				IconManager.getIconManager().getIcon(clr, ChessPieceType.King));
		// I guess disable all of the buttons until they select new game
		disableButtons();
	}

	// Drag and drop support, started to do this but left off...

	// class DragMouseAdapter extends MouseAdapter {
	// public void mousePressed(MouseEvent e) {
	// JToggleButton c = (JToggleButton) e.getSource();
	// TransferHandler handler = c.getTransferHandler();
	// handler.exportAsDrag(c, e, TransferHandler.COPY);
	// }
	// }
	//
	// class MyTransferHandler extends TransferHandler {
	//
	// private BufferedImage img = null;
	//
	// MyTransferHandler(String str) {
	// super(str);
	//
	// try {
	// img = ImageIO.read(new File("img/black_rook.png"));
	// img = new BufferedImage(img.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// }
	//
	// @Override
	// public int getSourceActions(JComponent c) {
	// if (img == null) {
	// System.out.println("image is null");
	// } else {
	// System.out.println("image is NOT null");
	// }
	// setDragImage(img);
	// return COPY;
	// }
	// }

}
