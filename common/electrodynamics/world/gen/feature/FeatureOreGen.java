package electrodynamics.world.gen.feature;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import electrodynamics.lib.block.Ore;
import electrodynamics.world.gen.WorldGenOre;
import electrodynamics.world.gen.feature.FeatureHandler.FeatureType;

public class FeatureOreGen extends FeatureBase {

	public final WorldGenerator worldGenerator;
	public final int count;
	public final int minY;
	public final int maxY;
	
	public static void registerFeatureOreGen(Configuration config, FeatureType type, Ore ore, int clusterSize, int count, int minY, int maxY) {
		int[] generationData = getOreGenerationSettings(config, type, ore, clusterSize, count, minY, maxY);
		FeatureHandler.getInstance().registerFeature(type, new FeatureOreGen("ORE_" + ore.toString(), new WorldGenOre(ore.toItemStack(), generationData[0]), generationData[1], generationData[2], generationData[3]));
	}
	
	public static int[] getOreGenerationSettings(Configuration config, FeatureType type, Ore ore, int clusterSize, int count, int minY, int maxY) {
		Property featureClusterSize = config.get(FeatureHandler.getFeatureCategory(type), ore.toString() + ".clusterSize", clusterSize);
		Property featureGenCount = config.get(FeatureHandler.getFeatureCategory(type), ore.toString() + ".generationCount", count);
		Property featureMinimumY = config.get(FeatureHandler.getFeatureCategory(type), ore.toString() + ".minimumY", minY);
		Property featureMaximumY = config.get(FeatureHandler.getFeatureCategory(type), ore.toString() + ".maximumY", maxY);
		
		return new int[] {featureClusterSize.getInt(clusterSize), featureGenCount.getInt(count), featureMinimumY.getInt(minY), featureMaximumY.getInt(maxY)};
	}
	
	public FeatureOreGen(String name, WorldGenerator worldGenerator, int count, int minY, int maxY) {
		super(name);
		
		this.worldGenerator = worldGenerator;
		this.count = count;
		this.minY = minY;
		this.maxY = maxY;
	}

	@Override
	public void generateFeature(Random random, int chunkX, int chunkZ, World world, boolean newGeneration) {
		if (!newGeneration) {
			return;
		}
		
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		
		if (type > 0) {
			String biomeName = world.getBiomeGenForCoords(chunkX, chunkZ).biomeName.toLowerCase();
			boolean onList = validBiomes.contains(biomeName);
			
			if (type == 1 && !onList || type == 2 && onList) {
				return;
			}
		}
		
		for (int i=0; i<count; i++) {
			int x = blockX + random.nextInt(16);
			int y = minY + random.nextInt(maxY - minY);
			int z = blockZ + random.nextInt(16);
			worldGenerator.generate(world, random, x, y, z);
		}
		
		return;
	}

	@Override
	public void handleConfig(Configuration config) {
		// TODO Auto-generated method stub
		
	}
	
}
