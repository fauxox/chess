package chess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.TransferHandler;

import chess.ChessProperties;
import chess.controller.ChessBoardController;
import chess.controller.ChessBoardControllerINF;
import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.event.NewGameAction;
import chess.event.ShowMovesAction;
import chess.event.ToggleAction;
import chess.model.ChessBoardModel;
import chess.model.ChessBoardSquare;
import chess.model.ChessPiece;

public class ChessBoardView extends JFrame implements ChessBoardModelListener {

	private ChessButton[][] buttons;

	private ChessButton lastToggle;

	private Color defaultBackgroundColor;

	private String defaultFrameTitle;

	private JCheckBoxMenuItem showMoves;

	/**
	 * 
	 * @param controller
	 * @param model
	 * @param properties
	 */
	public ChessBoardView(ChessBoardController controller, ChessBoardModel model, ChessProperties properties,
			String title) {
		// Doing super() like this invokes the constructor in the super class. The
		// reason for doing so is so that the super class constructor can do any setup
		// and variable setting it needs to, otherwise we'd have to do all of that for
		// ourselves in our constructor here.
		super(title);
		this.defaultFrameTitle = title;
		model.addChessBoardModelListener(this);
		setJMenuBar(createMenuBar(controller, properties));
		JPanel buttonPanel = createButtonPanel(model, controller);
		super.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		addBoardListener(controller);

		super.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * I break out creation of components of the view so the constructor doesn't get
	 * too enormous. This helps to isolate logic for certain components and makes it
	 * easier to reason about them as well as test them. That point is probably more
	 * relevant for breaking out business logic.
	 * 
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

		ToggleAction toggleAction = new ToggleAction(mover);

		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new ChessButton(toggleAction, (byte) i, (byte) j);
				updatePosition(i, j, model.getChessBoardSquare(i, j));
				buttonPanel.add(buttons[i][j]);
				bg.add(buttons[i][j]);
				// buttons[i][j].addMouseListener(dma);
				// buttons[i][j].setTransferHandler(th);
			}
		}
		// Just grabbing the default background color off the first button. You can set
		// this explicitly somewhere else if you want.
		defaultBackgroundColor = buttons[0][0].getBackground();
		return buttonPanel;
	}

	private JMenuBar createMenuBar(ChessBoardControllerINF mover, ChessProperties properties) {
		JMenuBar bar = new JMenuBar();
		bar.add(createFileMenu(mover));
		bar.add(createOptionsMenu(mover, properties));
		return bar;
	}

	private JMenu createFileMenu(ChessBoardControllerINF mover) {
		JMenu fileMenu = new JMenu("File");
		JMenuItem newGame = new JMenuItem(new NewGameAction(mover));
		fileMenu.add(newGame);
		return fileMenu;
	}

	private JMenu createOptionsMenu(ChessBoardControllerINF mover, ChessProperties properties) {
		JMenu optionsMenu = new JMenu("Options");
		showMoves = new JCheckBoxMenuItem(new ShowMovesAction(mover));
		showMoves.setSelected(Boolean.parseBoolean(
				properties.getProperty(ChessProperties.showMovesKey, ChessProperties.showMovesDefaultValue)));
		optionsMenu.add(showMoves);
		return optionsMenu;
	}

	/**
	 * This updates a single position on the board with the piece that will go there
	 * (or blank).
	 * 
	 * @param i
	 * @param j
	 * @param chessBoardSquare
	 *            should never be null, once board is initialized, though the piece
	 *            it contains may be null
	 */
	private void updatePosition(int i, int j, ChessBoardSquare chessBoardSquare) {
		// set the color of the square itself
		if (chessBoardSquare.isHighlighted()) {
			buttons[i][j].setBackground(Color.yellow);
		} else {
			buttons[i][j].setBackground(defaultBackgroundColor);
		}

		ChessPiece piece = chessBoardSquare.getChessPiece();
		// if there's no piece at this location on the board
		if (piece == null) {
			// set the icon to null so nothing appears
			buttons[i][j].setIcon(null);
		}
		// otherwise update the icon
		else {
			IconManager.getIconManager().updateIcon(buttons[i][j], piece);
		}

	}

	public void addBoardListener(ActionListener bl) {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j].addActionListener(bl);
			}
		}
	}

	@Override
	public void chessBoardModelChanged(ChessBoardModelEvent e) {
		updatePosition(e.getX(), e.getY(), e.getChessBoardSquare());
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
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				updatePosition(i, j, model.getChessBoardSquare(i, j));
			}
		}
		// reset the title for the turn
		turnChanged(model.getTurnString());
	}

	/**
	 * Convenience method for app saving properties.
	 * 
	 * @return whether the Show Moves JCheckBoxMenuItem in the Options menu is
	 *         selected.
	 */
	public boolean isShowMovesSelected() {
		return showMoves.isSelected();
	}

	// Drag and drop support

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
