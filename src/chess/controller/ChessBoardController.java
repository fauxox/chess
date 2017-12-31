package chess.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ChessBoardController implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			System.out.println(((JButton) e.getSource()).getText());
		}
	}

}
