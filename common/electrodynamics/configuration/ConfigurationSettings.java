package electrodynamics.configuration;

import electrodynamics.configuration.annotation.EDXProperty;

public class ConfigurationSettings {

	/* ElMag armor ability settings */
	@EDXProperty(category = ConfigurationHandler.CATEGORY_ELMAG)
	public static double MAGNETIC_RANGE = 3D;
	
	@EDXProperty(category = ConfigurationHandler.CATEGORY_ELMAG)
	public static double MAGNETIC_ATTRACTION_SPEED = 0.8D;
	
	/* Audio settings */
	@EDXProperty(category = ConfigurationHandler.CATEGORY_SOUND)
	public static boolean VOIDSTONE_AMBIENT_SOUND = true;
	
	/* General settings */
	@EDXProperty(category = ConfigurationHandler.CATEGORY_SETTINGS)
	public static boolean SHOW_LOCALIZATION_ERRORS = false;
	
}
