package chess.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import chess.event.ChessBoardModelEvent;
import chess.event.ChessBoardModelListener;
import chess.model.ChessBoardModel;
import chess.model.ChessPiece;

public class ChessBoardView extends JFrame implements ChessBoardModelListener{
	
	private JButton[][] buttons;
	
	public ChessBoardView(String appName, ChessBoardModel model){
		super(appName);
		model.addChessBoardModelListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(8,8));
		buttons = new JButton[8][8];
		for(int i = 0;i<buttons.length;i++) {
			for(int j = 0; j<buttons[0].length;j++) {
				//buttons[i][j] = new JButton("i="+i+", j="+j);
				buttons[i][j] = new JButton(model.getChessPiece(i, j).toString());
				buttonPanel.add(buttons[i][j]);
			}
		}
		super.getContentPane().add(buttonPanel);
		super.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void addBoardListener(ActionListener bl) {
		for(int i = 0;i<buttons.length;i++) {
			for(int j = 0; j<buttons[0].length;j++) {
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
