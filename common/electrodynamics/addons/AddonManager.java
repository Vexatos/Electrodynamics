package electrodynamics.addons;

import java.util.ArrayList;

import cpw.mods.fml.common.Loader;

import electrodynamics.addons.computercraft.EDAddonComputerCraft;
import electrodynamics.addons.misc.EDAddonBOP;
import electrodynamics.addons.thermalexpansion.EDAddonThermalExpansion;

public class AddonManager {

	private static ArrayList<EDAddon> addons = new ArrayList<EDAddon>();
	
	public static void loadAddons() {
		if (Loader.isModLoaded("ComputerCraft")) {
			addons.add(new EDAddonComputerCraft());
		}
		
		if (Loader.isModLoaded("ThermalExpansion")) {
			addons.add(new EDAddonThermalExpansion());
		}
		
		if (Loader.isModLoaded("BiomesOPlenty")) {
			addons.add(new EDAddonBOP());
		}
	}
	
	/** Called during main mod's PostInit state on server*/
	public static void init() {
		for (EDAddon addon : addons) {
			addon.init();
		}
	}
	
	/** Called during main mod's PostInit state on client*/
	public static void initClient() {
		for (EDAddon addon : addons) {
			addon.initClient();
		}
	}
	
}
