package electrodynamics.world.gen.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import electrodynamics.addons.misc.EDAddonBOP;
import electrodynamics.util.math.BlockCoord;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.Configuration;

public abstract class FeatureBase {

	public static final int MAX_PREVIOUS_GEN_STORAGE = 10;
	
	public final String name;

	public List<ChunkCoordIntPair> prevGens = new ArrayList<ChunkCoordIntPair>();
	
	public boolean retro;
	public boolean enabled;
	public boolean inject;
	
	public int rarityDistance = 0;
	
	public FeatureBase(String name) {
		this.name = name;
	}
	
	public final String getFeatureName() {
		return this.name;
	}

	public FeatureBase forceRarity(int minimumDistance) {
		this.rarityDistance = minimumDistance;
		return this;
	}
	
	public void handleConfig(Configuration config) {
		this.retro = config.get(FeatureHandler.getFeatureCategory(this), "A_enableRetroGen", false).getBoolean(false);
		this.enabled = config.get(FeatureHandler.getFeatureCategory(this), "A_featureEnabled", true).getBoolean(true);

		if (this.rarityDistance > 0) {
			this.rarityDistance = config.get(FeatureHandler.getFeatureCategory(this), "A_rarityDistance", this.rarityDistance).getInt(rarityDistance);
		}
	}
	
	public boolean generateFeature(Random random, int chunkX, int chunkZ, World world, boolean retro) {
		ChunkCoordIntPair currentCoord = new ChunkCoordIntPair(chunkX, chunkZ);
		
		if (!canGenerate(currentCoord)) {
			return false;
		}
		
		storeGeneration(currentCoord);
		
		if (retro && !this.retro) { // Prevent retro gen if not enabled
			return false;
		}
		
		if (world.getWorldInfo().getTerrainType().getWorldTypeName().equalsIgnoreCase("flat")) { // Prevent gen on flat worlds
			return false;
		}
		
		if (EDAddonBOP.bopLoaded && world.provider.dimensionId == EDAddonBOP.promisedLandDIMID) { // Prevent gen in BOP promised land
			return false;
		}
		
		generate(world, chunkX, chunkZ, random);
		
		return true;
	}
	
	public abstract void generate(World world, int chunkX, int chunkZ, Random random);
	
	private int[] getDistanceBetweenChunks(ChunkCoordIntPair chunk1, ChunkCoordIntPair chunk2) {
        return new int[] {Math.abs(chunk2.chunkXPos - chunk1.chunkXPos), Math.abs(chunk2.chunkZPos - chunk1.chunkZPos)};
	}
	
	private boolean canGenerate(ChunkCoordIntPair coord) {
		if (prevGens != null && prevGens.size() > 0) {
			for (ChunkCoordIntPair coord2 : prevGens) {
				if (!(getDistanceBetweenChunks(coord, coord2)[0] > this.rarityDistance) || !(getDistanceBetweenChunks(coord, coord2)[1] > this.rarityDistance)) {
					return false;
				}
			}
			
			return true;
		}
		
		return true;
	}
	
	private void storeGeneration(ChunkCoordIntPair coord) {
		prevGens.add(coord);
		while (prevGens.size() >= MAX_PREVIOUS_GEN_STORAGE) {
			if (prevGens.size() >= MAX_PREVIOUS_GEN_STORAGE) {
				prevGens.remove(0);
			}
		}
	}
	
}
