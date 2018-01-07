package chess;

import java.util.Properties;

/**
 * Simple util class that extends the Properties class to add constants for keys
 * and default values.
 * 
 * @author John T. Langton
 *
 */
@SuppressWarnings("serial")
public class ChessProperties extends Properties {

	public static final String appNameKey = "appName";
	public static final String imagePathKey = "imagePath";
	public static final String showMovesKey = "showMoves";

	public static final String appNameDefaultValue = "Chess";
	public static final String imagePathDefaultValue = "resources/img/";
	public static final String showMovesDefaultValue = "true";

	public void initProperties() {
		setProperty(appNameKey, appNameDefaultValue);
		setProperty(imagePathKey, imagePathDefaultValue);
		setProperty(showMovesKey, showMovesDefaultValue);
	}

}
