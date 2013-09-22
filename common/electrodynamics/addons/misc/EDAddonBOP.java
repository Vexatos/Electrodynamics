package electrodynamics.addons.misc;

import electrodynamics.addons.EDAddon;

public class EDAddonBOP extends EDAddon {
	
	public static boolean bopLoaded = false;
	
	public static int promisedLandDIMID = 20;
	
	@Override
	public void init() {
		try {
			Class bopConfig = Class.forName("biomesoplenty.configuration.configfile.BOPConfigurationIDs");
		
			if (bopConfig != null) {
				promisedLandDIMID = bopConfig.getDeclaredField("promisedLandDimID").getInt(Integer.class);
			}
		} catch (Exception e) {
		}
		
	}
	
}
