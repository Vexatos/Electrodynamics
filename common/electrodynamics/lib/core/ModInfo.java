package electrodynamics.lib.core;

public class ModInfo {
	
	public static final String MOD_ID = "ED";
	public static final String GENERIC_MOD_ID = "Electrodynamics";
	
	public static final String DEPENDENCIES = "required-after:Forge@[7.8.0.684,)";
	
	public static final String MAJOR_VERSION = "@MAJOR@";
	public static final String MINOR_VERSION = "@MINOR@";
	public static final String REVISION_VERSION = "@REVIS@";
	public static final String BUILD = "@BUILD@";
	public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD;

	public static final String ICON_PREFIX = GENERIC_MOD_ID.toLowerCase()+":";
	
	public static final String CORE_CONFIG = GENERIC_MOD_ID + ".cfg";
	
}
