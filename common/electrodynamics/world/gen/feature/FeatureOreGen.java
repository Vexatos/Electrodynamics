package electrodynamics.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.Configuration;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;

public class FeatureOreGen extends FeatureBase {

	public Ore ore;
	
	// d_ MEANS DEFAULT! THESE VALUES ARE NOT SENT TO GENERATION METHOD
	public int d_clusterSize;
	public int d_count;
	public int d_minY;
	public int d_maxY;
	
	// THESE ONES ARE SET VIA THE CONFIG! THESE ONES ARE SENT TO THE GENERATION METHOD
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
		
		for (int i=0; i<count; i++) {
			int x = blockX + random.nextInt(16);
			int y = minY + random.nextInt(maxY - minY);
			int z = blockZ + random.nextInt(16);
			
			(new WorldGenMinable(BlockIDs.BLOCK_ORE_ID, this.ore.ordinal(), this.clusterSize, Block.stone.blockID)).generate(world, random, x, y, z);
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
