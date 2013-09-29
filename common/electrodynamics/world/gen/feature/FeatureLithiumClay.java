package electrodynamics.world.gen.feature;

import java.util.Random;

import electrodynamics.lib.block.BlockIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class FeatureLithiumClay extends FeatureBase {

	// d_ MEANS DEFAULT! THESE VALUES ARE NOT SENT TO GENERATION METHOD
	public int d_count;
	public int d_minY;
	public int d_maxY;
		
	// THESE ONES ARE SET VIA THE CONFIG! THESE ONES ARE SENT TO THE GENERATION METHOD
	public int count;
	public int minY;
	public int maxY;
	
	public FeatureLithiumClay() {
		super("Lithium Clay");
	}

	public FeatureLithiumClay setDefaults(int count, int minY, int maxY) {
		this.d_count = count;
		this.d_minY = minY;
		this.d_maxY = maxY;
		return this;
	}
	
	@Override
	public void generate(World world, int chunkX, int chunkZ, Random random) {
		for (int i = 0; i < this.count; i++) {
			int x = (chunkX * 16) + random.nextInt(16);
			int z = (chunkZ * 16) + random.nextInt(16);
			int y = random.nextInt(Math.max(this.maxY - this.minY, 0)) + this.minY;
			this.generateClay(world, random, x, y, z);
		}
	}

	private void generateClay(World world, Random random, int x, int y, int z) {
		if (world.getBlockMaterial(x, y, z) == Material.water) {
			int l = random.nextInt(4 - 2) + 2;
			byte b0 = 1;

			for (int i1 = x - l; i1 <= x + l; ++i1) {
				for (int j1 = z - l; j1 <= z + l; ++j1) {
					int k1 = i1 - x;
					int l1 = j1 - z;

					if (k1 * k1 + l1 * l1 <= l * l) {
						for (int i2 = y - b0; i2 <= y + b0; ++i2) {
							int j2 = world.getBlockId(i1, i2, j1);

							if (j2 == Block.dirt.blockID || j2 == Block.blockClay.blockID) {
								if (random.nextInt(10) == 0) { // 10% chance to be lithium clay
									world.setBlock(i1, i2, j1, BlockIDs.BLOCK_LITHIUM_CLAY_ID, 0, 2);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void handleConfig(Configuration config) {
		super.handleConfig(config);
		
		final String CATEGORY = FeatureHandler.getFeatureCategory(this);

		this.count = config.get(CATEGORY, "count", d_count).getInt(d_count);
		this.minY = config.get(CATEGORY, "minY", d_minY).getInt(d_minY);
		this.maxY = config.get(CATEGORY, "maxY", d_maxY).getInt(d_maxY);
	}
	
}
