package electrodynamics.world.gen.feature;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;
import electrodynamics.util.BlockUtil;

public class FeatureWolframite extends FeatureBase {

	public int d_count;
	
	public int count;
	
	public FeatureWolframite() {
		super("Wolframite");
	}

	public FeatureWolframite setDefaults(int count) {
		this.d_count = count;
		return this;
	}
	
	@Override
	public void generate(World world, int chunkX, int chunkZ, Random random) {
		for (int i=0; i<this.count; i++) {
			int x = (chunkX * 16) + random.nextInt(16);
			int z = (chunkZ * 16) + random.nextInt(16);
			int y = BlockUtil.getFirstInstanceOfBlock(world, x, z, Block.lavaStill.blockID, OreDictionary.WILDCARD_VALUE);
			ForgeDirection[] sideDirs = new ForgeDirection[] {NORTH, WEST, SOUTH, EAST};
			
			if (y != -1 && y < 255) {
				boolean potentialLake = false;
				for (ForgeDirection dir : sideDirs) {
					int sX = x + dir.offsetX;
					int sZ = z + dir.offsetZ;
					
					if (BlockUtil.matches(world, sX, y, sZ, Block.lavaStill.blockID, OreDictionary.WILDCARD_VALUE)) {
						potentialLake = true;
						break;
					}
				}
				
				world.setBlock(x,     y - 1, z,     BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), 2);
				world.setBlock(x + 1, y - 1, z,     BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), 2);
				world.setBlock(x - 1, y - 1, z,     BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), 2);
				world.setBlock(x,     y - 1, z + 1, BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), 2);
				world.setBlock(x,     y - 1, z - 1, BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), 2);
			}
		}
	}

	@Override
	public void handleConfig(Configuration config) {
		super.handleConfig(config);
		
		final String CATEGORY = FeatureHandler.getFeatureCategory(this);

		this.count = config.get(CATEGORY, "count", d_count).getInt(d_count);
	}
}
