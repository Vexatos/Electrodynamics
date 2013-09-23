package electrodynamics.world.gen.feature;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import electrodynamics.lib.block.Ore;
import electrodynamics.world.gen.WorldGenOre;

public class FeatureOreGen extends FeatureBase {

	public Ore ore;
	
	public int d_clusterSize;
	public int d_count;
	public int d_minY;
	public int d_maxY;
	
	public int clusterSize;
	public int count;
	public int minY;
	public int maxY;
	
	public FeatureOreGen(String name, Ore ore) {
		super(name);
		
		this.ore = ore;
	}

	public FeatureOreGen setDefaults(int clusterSize, int count, int minY, int maxY) {
		this.d_clusterSize = clusterSize;
		this.d_count = count;
		this.d_minY = minY;
		this.d_maxY = maxY;
		return this;
	}
	
	@Override
	public void generateFeature(Random random, int chunkX, int chunkZ, World world, boolean retro) {
		super.generateFeature(random, chunkX, chunkZ, world, retro);
		
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		
		String biomeName = world.getBiomeGenForCoords(chunkX, chunkZ).biomeName.toLowerCase();
		boolean onList = validBiomes.contains(biomeName);
		
		if (onList) {
			for (int i=0; i<count; i++) {
				int x = blockX + random.nextInt(16);
				int y = minY + random.nextInt(maxY - minY);
				int z = blockZ + random.nextInt(16);
				(new WorldGenOre(ore.toItemStack(), clusterSize, ore.toItemStack().itemID)).generate(world, random, x, y, z);
			}
		}
		
		return;
	}

	@Override
	public void handleConfig(Configuration config) {
		super.handleConfig(config);
		
		final String CATEGORY = FeatureHandler.getFeatureCategory(this);

		this.clusterSize = config.get(CATEGORY, "clusterSize", d_clusterSize).getInt(d_clusterSize);
		this.count = config.get(CATEGORY, "count", d_count).getInt(d_count);
		this.minY = config.get(CATEGORY, "minY", d_minY).getInt(d_minY);
		this.maxY = config.get(CATEGORY, "maxY", d_maxY).getInt(d_maxY);
	}
	
}
