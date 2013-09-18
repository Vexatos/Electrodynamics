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

	public static final String FEATURE_CONFIG_FOLDER = "EDFeatures.cfg";
	public static final String CATEGORY_FEATURES = "modules";
	
	public Map<FeatureType, FeatureBase> features = new EnumMap<FeatureType, FeatureBase>(FeatureType.class);
	
	public ArrayList<FeatureBase> loadedFeatures = new ArrayList<FeatureBase>();
	
	private static FeatureHandler instance;
	
	public static FeatureHandler getInstance() {
		if (instance == null) {
			instance = new FeatureHandler();
		}
		
		return instance;
	}
	
	public void registerFeature(FeatureType type, FeatureBase feature) {
		features.put(type, feature);
	}
	
	public void prepareFeatures() {
		Configuration config = getConfig();
		
		// Ore //TODO Re-write
		FeatureOreGen.registerFeatureOreGen(config, FeatureType.ORE_CHALCOPYRITE, Ore.CHALCOPYRITE, 8, 6, 16, 64);
		FeatureOreGen.registerFeatureOreGen(config, FeatureType.ORE_COBALTITE, Ore.COBALTITE, 8, 4, 32, 78);
		FeatureOreGen.registerFeatureOreGen(config, FeatureType.ORE_GALENA, Ore.GALENA, 4, 2, 16, 32);
		FeatureOreGen.registerFeatureOreGen(config, FeatureType.ORE_MAGNETITE, Ore.MAGNETITE, 12, 3, 16, 32);
		FeatureOreGen.registerFeatureOreGen(config, FeatureType.ORE_NICKEL, Ore.NICKEL, 8, 6, 16, 64);
		
		// Gas
		registerFeature(FeatureType.GAS_POCKET, new FeatureGasPocket());
		
		// Limestone
		registerFeature(FeatureType.LIMESTONE, new FeatureLimestone("Limestone"));
		
		for (FeatureType feature : FeatureType.values()) {
			if (isEnabled(config, feature)) {
				FeatureBase instance = features.get(feature);
				
				if (instance != null) {
					loadedFeatures.add(instance);
					instance.handleConfig(config);
				} else {
					EDLogger.warn("Feature " + feature.toString() + " is missing a mapping!");
				}
			}
		}
		
		config.save();
	}

	public void insertFeatures() {
		for (FeatureBase feature : loadedFeatures) {
			GameRegistry.registerWorldGenerator(new WorldGenFeature(feature));
		}
	}
	
	private boolean isEnabled(Configuration config, FeatureType feature) {
		Property featureEnabled = config.get(getFeatureCategory(feature), feature.toString() + ".enabled", true);
		return featureEnabled.getBoolean(true);
	}
	
	private Configuration getConfig() {
		Configuration config = new Configuration(new File(Electrodynamics.instance.configFolder, FEATURE_CONFIG_FOLDER));
		config.load();
		
		config.addCustomCategoryComment(CATEGORY_FEATURES, "Enable/Disable features/generation specifics as you see fit.");
	
		//Removes default categories, as I have no use for them here
		config.removeCategory(config.getCategory(Configuration.CATEGORY_BLOCK));
		config.removeCategory(config.getCategory(Configuration.CATEGORY_ITEM));
		config.removeCategory(config.getCategory(Configuration.CATEGORY_GENERAL));
		
		return config;
	}
	
	public static String getFeatureCategory(FeatureType type) {
		return CATEGORY_FEATURES + "." + type.toString().toLowerCase();
	}
	
	public enum FeatureType {
		ORE_CHALCOPYRITE,
		ORE_COBALTITE,
		ORE_GALENA,
		ORE_MAGNETITE,
		ORE_NICKEL,
		GAS_POCKET,
		LIMESTONE
	}
	
}
