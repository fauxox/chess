package chess;

import java.util.Properties;

/**
 * Simple util class that adds defaults and a key reference to a properties
 * object.
 * 
 * @author John T. Langton
 *
 */
public class ChessProperties extends Properties {

	// Property keys (may not contain all keys for all properties)
	public static final String appNameKey = "appName";
	public static final String imagePathKey = "imagePath";
	public static final String showMovesKey = "showMoves";

	// Default values (may not contain defaults for all properties)
	public static final String appNameDefaultValue = "Chess";
	public static final String imagePathDefaultValue = "resources/img/";
	public static final String showMovesDefaultValue = "true";

	/**
	 * This just sets the default properties to default values
	 */
	public void initProperties() {
		setProperty(appNameKey, appNameDefaultValue);
		setProperty(imagePathKey, imagePathDefaultValue);
		setProperty(showMovesKey, showMovesDefaultValue);
	}

}
