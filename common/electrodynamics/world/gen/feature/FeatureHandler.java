package electrodynamics.world.gen.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import electrodynamics.Electrodynamics;
import electrodynamics.lib.block.BlockIDs;
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
		
		// Wormwood
		registerFeature(new FeatureWormwood());
		
		// Rubber Tree
		registerFeature(new FeatureWormwood());
		
		// Chalcopyrite
		registerFeature(new FeatureOreGen("Chalcopyrite", Ore.CHALCOPYRITE).setDefaults(8, 6, 16, 64));
		
		// Cobaltite
		registerFeature(new FeatureOreGen("Cobaltite", Ore.COBALTITE).setDefaults(8, 4, 32, 78));
		
		// Galena
		registerFeature(new FeatureOreGen("Galena", Ore.GALENA).setDefaults(4, 2, 16, 32));
		
		// Magnetite
		registerFeature(new FeatureOreGen("Magnetite", Ore.MAGNETITE).setDefaults(12, 3, 16, 32));
		
		// Nickel
		registerFeature(new FeatureOreGen("Nickel", Ore.NICKEL).setDefaults(8, 6, 16, 64));
		
		// Wolframite
		registerFeature(new FeatureBlockNear("Wolframite", BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal()).setDefaults(4, 6, 16));
		
		// Voidstone
		registerFeature(new FeatureBlock("Voidstone", BlockIDs.BLOCK_ORE_ID, Ore.VOIDSTONE.ordinal()) {
			@Override
			public void onGenned(World world, int x, int y, int z, Random random) {
				for (int ix = x - 2; ix < x + 2; ix++) {
					for (int iy = y - 2; iy < y + 2; iy++) {
						for (int iz = z - 2; iz < z + 2; iz++) {
							if (world.getBlockId(ix, iy, iz) != this.genID) {
								world.setBlockToAir(ix, iy, iz);
							}
						}
					}
				}
			}
		}.setDefaults(1, 0, 10));
		
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
	
	public FeatureBase getFeatureForName(String name) {
		for (FeatureBase feature : this.loadedFeatures) {
			if (feature.name.equalsIgnoreCase(name)) {
				return feature;
			}
		}
		
		return null;
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
