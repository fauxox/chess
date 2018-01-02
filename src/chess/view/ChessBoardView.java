package chess.view;

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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.TransferHandler;

import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.model.ChessBoardModel;
import chess.model.ChessBoardSquare;
import chess.model.ChessPiece;

public class ChessBoardView extends JFrame implements ChessBoardModelListener {

	private ChessButton[][] buttons;
	private ImageIcon blackRook, blackKnight, blackBishop, blackKing, blackQueen, blackPawn, whiteRook, whiteKnight,
			whiteBishop, whiteKing, whiteQueen, whitePawn;
	private Color defaultBackgroundColor;

	public ChessBoardView(String appName, ChessBoardModel model) {
		super(appName);
		model.addChessBoardModelListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(8, 8));
		buttons = new ChessButton[8][8];
		// Making a button group and adding all the buttons to it just means that only
		// one button can be toggled in that group.
		ButtonGroup bg = new ButtonGroup();
		createIcons();

		// DragMouseAdapter dma = new DragMouseAdapter();
		//
		// MyTransferHandler th = new MyTransferHandler("icon");

		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new ChessButton((byte) i, (byte) j);
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
		super.getContentPane().add(buttonPanel);
		super.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Create all of the image icons that will be used on the buttons. You usually
	 * don't want to hard code stuff like this. Other ways to do this would be to
	 * create a properties file, so that someone would swap in other images if they
	 * wanted to. Since this is such a simple, small app I'll just hard code this
	 * icon creation for now, but before we're done, if you want, we can go ahead
	 * and change this so it's reading them in from a properties file. It's good to
	 * know how to use properties files.
	 */
	private void createIcons() {
		BufferedImage img = null;
		try {
			// The path is relative to the root of the classpath (in this case the build
			// path in the IDE)
			img = ImageIO.read(new File("img/black_rook.png"));
			blackRook = new ImageIcon(img);
			img = ImageIO.read(new File("img/white_rook.png"));
			whiteRook = new ImageIcon(img);
			img = ImageIO.read(new File("img/black_knight.png"));
			blackKnight = new ImageIcon(img);
			img = ImageIO.read(new File("img/white_knight.png"));
			whiteKnight = new ImageIcon(img);
			img = ImageIO.read(new File("img/black_bishop.png"));
			blackBishop = new ImageIcon(img);
			img = ImageIO.read(new File("img/white_bishop.png"));
			whiteBishop = new ImageIcon(img);
			img = ImageIO.read(new File("img/black_queen.png"));
			blackQueen = new ImageIcon(img);
			img = ImageIO.read(new File("img/white_queen.png"));
			whiteQueen = new ImageIcon(img);
			img = ImageIO.read(new File("img/black_king.png"));
			blackKing = new ImageIcon(img);
			img = ImageIO.read(new File("img/white_king.png"));
			whiteKing = new ImageIcon(img);
			img = ImageIO.read(new File("img/black_pawn.png"));
			blackPawn = new ImageIcon(img);
			img = ImageIO.read(new File("img/white_pawn.png"));
			whitePawn = new ImageIcon(img);
		} catch (IOException e) {
			// TODO we can talk about loggers and logging and how you might want to respod
			// to different types of errors, e.g. ones that are bad but your app can keep
			// going, vs ones that are catastrophic.
			System.out.println(e);
		}
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
			// return so you don't go through the switch statement - otherwise you'll call
			// piece.getCpt() when piece is null so would throw a null pointer exception
			return;
		}

		// giant switch for setting the icon of the button
		if (piece.isBlack()) {
			switch (piece.getChessPieceType()) {
			case Rook:
				buttons[i][j].setIcon(blackRook);
				break;
			case Knight:
				buttons[i][j].setIcon(blackKnight);
				break;
			case Bishop:
				buttons[i][j].setIcon(blackBishop);
				break;
			case King:
				buttons[i][j].setIcon(blackKing);
				break;
			case Queen:
				buttons[i][j].setIcon(blackQueen);
				break;
			case Pawn:
				buttons[i][j].setIcon(blackPawn);
				break;
			}
		} else {
			switch (piece.getChessPieceType()) {
			case Rook:
				buttons[i][j].setIcon(whiteRook);
				break;
			case Knight:
				buttons[i][j].setIcon(whiteKnight);
				break;
			case Bishop:
				buttons[i][j].setIcon(whiteBishop);
				break;
			case King:
				buttons[i][j].setIcon(whiteKing);
				break;
			case Queen:
				buttons[i][j].setIcon(whiteQueen);
				break;
			case Pawn:
				buttons[i][j].setIcon(whitePawn);
				break;
			}
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
