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
		chunkX = chunkX >> 4;
		chunkZ = chunkZ >> 4;		
		
		for (int i = 0; i < this.count; i++) {
			int x = chunkX + random.nextInt(16);
			int z = chunkX + random.nextInt(16);
			int y = random.nextInt(Math.max(this.maxY - this.minY, 0)) + this.minY;
			ChunkCoordinates coord = new ChunkCoordinates(x, y, z);
			this.generateReplace(world, random, x, y, z);
		}
	}
	
	public boolean generateReplace(World par1World, Random par2Random, int par3, int par4, int par5) {
		float var6 = par2Random.nextFloat() * (float) Math.PI;
		double var7 = par3 + 8 + MathHelper.sin(var6) * this.clusterSize / 8.0F;
		double var9 = par3 + 8 - MathHelper.sin(var6) * this.clusterSize / 8.0F;
		double var11 = par5 + 8 + MathHelper.cos(var6) * this.clusterSize / 8.0F;
		double var13 = par5 + 8 - MathHelper.cos(var6) * this.clusterSize / 8.0F;
		double var15 = par4 + par2Random.nextInt(3) - 2;
		double var17 = par4 + par2Random.nextInt(3) - 2;
 
		for (int var19 = 0; var19 <= this.clusterSize; ++var19) {
			double var20 = var7 + (var9 - var7) * var19 / this.clusterSize;
			double var22 = var15 + (var17 - var15) * var19 / this.clusterSize;
			double var24 = var11 + (var13 - var11) * var19 / this.clusterSize;
			double var26 = par2Random.nextDouble() * this.clusterSize / 16.0D;
			double var28 = (MathHelper.sin(var19 * (float) Math.PI / this.clusterSize) + 1.0F) * var26 + 1.0D;
			double var30 = (MathHelper.sin(var19 * (float) Math.PI / this.clusterSize) + 1.0F) * var26 + 1.0D;
			int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
			int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
			int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
			int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
			int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
			int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);
 
			for (int var38 = var32; var38 <= var35; ++var38) {
				double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);
 
				if (var39 * var39 < 1.0D) {
					for (int var41 = var33; var41 <= var36; ++var41) {
						double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);
 
						if (var39 * var39 + var42 * var42 < 1.0D) {
							for (int var44 = var34; var44 <= var37; ++var44) {
								double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);

								int block = par1World.getBlockId(var38, var41, var44);
								if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && (block == Block.stone.blockID)) { //TODO COMPAT
									par1World.setBlock(var38, var41, var44, BlockIDs.BLOCK_ORE_ID, this.ore.ordinal(), 2);
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
