package chess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;

import chess.controller.ChessBoardController;
import chess.model.ChessBoardModel;
import chess.view.ChessBoardView;
import chess.view.IconManager;

/**
 * 
 * @author John T. Langton
 * 
 *         This is the main file that runs the app. I created this separate from
 *         the JFrame main view to emphasize decoupling of the model, view, and
 *         controller parts.
 *
 */
public class ChessApp implements WindowListener {

	/*
	 * Note you can just use a Properties object, I just added a ChessProperties
	 * subclass that only adds some constants for convenience.
	 * 
	 * In practice you can have multiple properties objects such as default
	 * properties, and then the properties that were set by the user. This way you
	 * can restore defaults, etc. This is a tiny app so just maintaining one.
	 */
	private final String defaultPropertiesPath = "resources/properties";
	private ChessProperties properties;

	public ChessApp() {
		loadProperties();
		String imgPath = properties.getProperty(ChessProperties.imagePathKey, ChessProperties.imagePathDefaultValue);
		IconManager.getIconManager(imgPath);
		ChessBoardModel model = new ChessBoardModel();
		ChessBoardController controller = new ChessBoardController(model);
		String title = properties.getProperty(ChessProperties.appNameKey, ChessProperties.appNameDefaultValue)
				+ model.getTurnString();
		ChessBoardView jv = new ChessBoardView(controller, model, properties, title);
		jv.addBoardListener(controller);
		jv.addWindowListener(this);
		jv.setSize(600, 600);
		jv.setVisible(true);
	}

	public static void main(String[] args) {
		ChessApp chessApp = new ChessApp();
	}

	private void loadProperties() {
		// create and load default properties
		properties = new ChessProperties();
		FileInputStream in;
		try {
			in = new FileInputStream(defaultPropertiesPath);
			properties.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO add logging, for now we'll ignore
			e.printStackTrace();
		} catch (IOException e) {
			// TODO add logging, for now we'll ignore
			e.printStackTrace();
		}
		if (properties.isEmpty()) {
			properties.initProperties();
		}
	}

	private void saveProperties() {
		FileOutputStream out;
		try {
			out = new FileOutputStream(defaultPropertiesPath);
			properties.store(out, "---No Comment---");
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		boolean showMoves = ((ChessBoardView) e.getSource()).isShowMovesSelected();
		properties.setProperty(ChessProperties.showMovesKey, Boolean.toString(showMoves));
		saveProperties();
	}

	// we ignore the rest of these window events

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
