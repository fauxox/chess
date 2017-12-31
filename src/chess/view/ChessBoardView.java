package chess.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.model.ChessBoardModel;
import chess.model.ChessPiece;
import chess.model.ChessPiece.ColorType;

public class ChessBoardView extends JFrame implements ChessBoardModelListener {

	private JButton[][] buttons;
	private ImageIcon blackRook, blackKnight, blackBishop, blackKing, blackQueen, blackPawn, whiteRook, whiteKnight,
			whiteBishop, whiteKing, whiteQueen, whitePawn;

	public ChessBoardView(String appName, ChessBoardModel model) {
		super(appName);
		model.addChessBoardModelListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(8, 8));
		buttons = new JButton[8][8];
		createIcons();
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new JButton();
				updatePosition(i, j, model.getChessPiece(i, j));
				buttonPanel.add(buttons[i][j]);
			}
		}
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
	 * @param piece
	 */
	private void updatePosition(int i, int j, ChessPiece piece) {

		switch (piece.getCpt()) {
		case Rook:
			if (piece.getCt() == ColorType.Black) {
				buttons[i][j].setIcon(blackRook);
			} else {
				buttons[i][j].setIcon(whiteRook);
			}
			break;
		case Knight:
			if (piece.getCt() == ColorType.Black) {
				buttons[i][j].setIcon(blackKnight);
			} else {
				buttons[i][j].setIcon(whiteKnight);
			}
			break;
		case Bishop:
			if (piece.getCt() == ColorType.Black) {
				buttons[i][j].setIcon(blackBishop);
			} else {
				buttons[i][j].setIcon(whiteBishop);
			}
			break;
		case King:
			if (piece.getCt() == ColorType.Black) {
				buttons[i][j].setIcon(blackKing);
			} else {
				buttons[i][j].setIcon(whiteKing);
			}
			break;
		case Queen:
			if (piece.getCt() == ColorType.Black) {
				buttons[i][j].setIcon(blackQueen);
			} else {
				buttons[i][j].setIcon(whiteQueen);
			}
			break;
		case Pawn:
			if (piece.getCt() == ColorType.Black) {
				buttons[i][j].setIcon(blackPawn);
			} else {
				buttons[i][j].setIcon(whitePawn);
			}
			break;
		// we'll default to clearing the button
		default:
			buttons[i][j].setIcon(null);
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
		ChessPiece cp = e.getCp();
		String text = cp.toString();
		JButton button = buttons[e.getX()][e.getY()];
		button.setText(text);
	}

}
