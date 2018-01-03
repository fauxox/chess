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
