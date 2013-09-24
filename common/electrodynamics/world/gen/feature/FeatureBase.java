package electrodynamics.world.gen.feature;

import java.util.HashSet;
import java.util.Random;

import electrodynamics.addons.misc.EDAddonBOP;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.Configuration;

public class FeatureBase {

	public final String name;

	public boolean retro;
	public boolean enabled;
	
	public FeatureBase(String name) {
		this.name = name;
	}
	
	public final String getFeatureName() {
		return this.name;
	}

	public void handleConfig(Configuration config) {
		this.retro = config.get(FeatureHandler.getFeatureCategory(this), "A_enableRetroGen", false).getBoolean(false);
		this.enabled = config.get(FeatureHandler.getFeatureCategory(this), "A_featureEnabled", true).getBoolean(true);
	}
	
	public void generateFeature(Random random, int chunkX, int chunkZ, World world, boolean retro) {
		if (retro && !this.retro) { // Prevent retro gen if not enabled
			return;
		}
		
		if (world.getWorldInfo().getTerrainType().getWorldTypeName().equalsIgnoreCase("flat")) { // Prevent gen on flat worlds
			return;
		}
		
		if (EDAddonBOP.bopLoaded && world.provider.dimensionId == EDAddonBOP.promisedLandDIMID) { // Prevent gen in BOP promised land
			return;
		}
	}
	
}
