package electrodynamics.world.gen.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import electrodynamics.Electrodynamics;
import electrodynamics.core.EDLogger;
import electrodynamics.lib.block.Ore;
import electrodynamics.world.gen.WorldGenFeature;

public class FeatureHandler {

	public static final String FEATURE_CONFIG_FILE = "EDFeatures.cfg";
	public static final String CATEGORY_FEATURES = "FEATURE";

	private static FeatureHandler instance;
	
	public ArrayList<FeatureBase> loadedFeatures = new ArrayList<FeatureBase>();
	
	public Configuration config;
	
	public static FeatureHandler getInstance() {
		if (instance == null) {
			instance = new FeatureHandler();
		}
		
		return instance;
	}
	
	public FeatureHandler() {
		this.config = getConfig();
	}
	
	public void registerFeature(FeatureBase feature) {
		feature.handleConfig(this.config);
		this.loadedFeatures.add(feature);
	}
	
	public void prepareFeatures() {
		// Gas
		registerFeature(new FeatureGasPocket());
		
		// Limestone
		registerFeature(new FeatureLimestone());
		
		if (this.config.hasChanged()) {
			this.config.save();
		}
	}

	public void insertFeatures() {
		for (FeatureBase feature : loadedFeatures) {
			if (feature.enabled) {
				GameRegistry.registerWorldGenerator(new WorldGenFeature(feature));
			}
		}
	}
	
	public boolean retroGenEnabled() {
		for (FeatureBase feature : loadedFeatures) {
			if (feature.retro) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Configuration getConfig() {
		Configuration config = new Configuration(new File(Electrodynamics.instance.configFolder, FEATURE_CONFIG_FILE));
		config.load();
		
		config.addCustomCategoryComment(CATEGORY_FEATURES, "Enable/Disable features/generation specifics as you see fit.");
	
		//Removes default categories, as I have no use for them here
		config.removeCategory(config.getCategory(Configuration.CATEGORY_BLOCK));
		config.removeCategory(config.getCategory(Configuration.CATEGORY_ITEM));
		config.removeCategory(config.getCategory(Configuration.CATEGORY_GENERAL));
		
		return config;
	}
	
	public static String getFeatureCategory(FeatureBase feature) {
		return CATEGORY_FEATURES + "." + feature.name.toUpperCase().replace(" ", "_");
	}
	
}
