package chess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
	 * Note you can just use a Properties object. I added a ChessProperties subclass
	 * that only adds some constants for convenience.
	 * 
	 * In practice you can have multiple properties objects such as default
	 * properties, and then the properties that were set by the user. This way you
	 * can restore defaults, etc. You can also look at Java Preferences API, reading
	 * environment variables, etc.
	 */
	private static final String defaultPropertiesPath = "resources/properties";

	private ChessProperties properties;

	public ChessApp(ChessProperties props) {
		// we want to keep a reference to props so we can save them on application close
		this.properties = props;
	}

	public static void main(String[] args) {
		ChessProperties props = loadProperties();
		ChessApp chessApp = new ChessApp(props);
		String imgPath = props.getProperty(ChessProperties.imagePathKey, ChessProperties.imagePathDefaultValue);
		IconManager.getIconManager(imgPath);
		ChessBoardModel model = new ChessBoardModel();
		ChessBoardController controller = new ChessBoardController(model);
		String title = props.getProperty(ChessProperties.appNameKey, ChessProperties.appNameDefaultValue);
		// the property is text so we have to parse it to an actual boolean
		boolean showMoves = Boolean
				.parseBoolean(props.getProperty(ChessProperties.showMovesKey, ChessProperties.showMovesDefaultValue));
		ChessBoardView jv = new ChessBoardView(controller, model, showMoves, title);
		jv.addWindowListener(chessApp);
		jv.setSize(600, 600);
		jv.setVisible(true);

	}

	private static ChessProperties loadProperties() {
		// create and load default properties
		ChessProperties props = new ChessProperties();
		FileInputStream in;
		try {
			in = new FileInputStream(defaultPropertiesPath);
			props.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO add logging, for now we'll ignore
			e.printStackTrace();
		} catch (IOException e) {
			// TODO add logging, for now we'll ignore
			e.printStackTrace();
		}
		if (props.isEmpty()) {
			props.initProperties();
		}
		return props;
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
