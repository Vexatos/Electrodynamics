package electrodynamics.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
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
	public void generate(World world, int chunkX, int chunkZ, Random random) {
		int cX = chunkX * 16;
		int cZ = chunkZ * 16;		
		
		for (int i = 0; i < this.count; i++) {
			int x = cX + random.nextInt(16);
			int z = cZ + random.nextInt(16);
			int y = random.nextInt(Math.max(this.maxY - this.minY, 0)) + this.minY;
			this.generateReplace(world, random, x, y, z);
		}
	}
	
	public boolean generateReplace(World world, Random rand, int x, int y, int z) {
		float f = rand.nextFloat() * (float) Math.PI;
		double d0 = x + 8 + MathHelper.sin(f) * clusterSize / 8.0F;
		double d1 = x + 8 - MathHelper.sin(f) * clusterSize / 8.0F;
		double d2 = z + 8 + MathHelper.cos(f) * clusterSize / 8.0F;
		double d3 = z + 8 - MathHelper.cos(f) * clusterSize / 8.0F;
		double d4 = y + rand.nextInt(3) - 2;
		double d5 = y + rand.nextInt(3) - 2;

		for (int l = 0; l <= clusterSize; ++l) {
			double d6 = d0 + (d1 - d0) * l / clusterSize;
			double d7 = d4 + (d5 - d4) * l / clusterSize;
			double d8 = d2 + (d3 - d2) * l / clusterSize;
			double d9 = rand.nextDouble() * clusterSize / 16.0D;
			double d10 = (MathHelper.sin(l * (float) Math.PI / clusterSize) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * (float) Math.PI / clusterSize) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

			for (int k2 = i1; k2 <= l1; ++k2) {
				double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D) {
					for (int l2 = j1; l2 <= i2; ++l2) {
						double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D) {
							for (int i3 = k1; i3 <= j2; ++i3) {
								double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);

								Block block = Block.blocksList[world.getBlockId(k2, l2, i3)];
								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && block != null && block.isGenMineableReplaceable(world, k2, l2, i3, Block.stone.blockID)) {
									System.out.println(ore.toString() + ": " + k2 + ", " + l2 + ", " + i3);
									world.setBlock(k2, l2, i3, BlockIDs.BLOCK_ORE_ID, ore.ordinal(), 1);
								}
							}
						}
					}
				}
			}
		}
		
		return true;
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
