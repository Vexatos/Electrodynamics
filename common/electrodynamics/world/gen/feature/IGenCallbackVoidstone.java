package electrodynamics.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;
import electrodynamics.world.gen.feature.FeatureBlock.IGenCallback;

public class IGenCallbackVoidstone implements IGenCallback {

	@Override
	public void onGenned(World world, int x, int y, int z, Random random) {
		// Clears out a 5x5x5 cube with the voidstone in the center
		for (int ix = x - 1; ix <= x + 1; ix++) {
			for (int iy = y - 1; iy <= y + 1; iy++) {
				for (int iz = z - 1; iz <= z + 1; iz++) {
					if (world.getBlockId(ix, iy, iz) != BlockIDs.BLOCK_ORE_ID && world.getBlockMetadata(ix, iy, iz) != Ore.VOIDSTONE.ordinal()) {
						world.setBlockToAir(ix, iy, iz);
					}
				}
			}
		}
		
		// Top 3x3x1 clear
		for (int ix = x - 1; ix <= x + 1; ix++) {
			for (int iz = z - 1; iz <= z + 1; iz++) {
				world.setBlockToAir(ix, y + 2, iz);
			}
		}
		
		// Bottom 3x3x1 clear
		for (int ix = x - 1; ix <= x + 1; ix++) {
			for (int iz = z - 1; iz <= z + 1; iz++) {
				world.setBlockToAir(ix, y - 2, iz);
				world.setBlock(ix, y - 3, iz, Block.bedrock.blockID);
			}
		}
		
		// Left 3x3x1 clear
		for (int ix = x - 1; ix <= x + 1; ix++) {
			for (int iy = y - 1; iy <= y + 1; iy++) {
				world.setBlockToAir(ix, iy, z - 2);
			}
		}
		
		// Right 3x3x1 clear
		for (int ix = x - 1; ix <= x + 1; ix++) {
			for (int iy = y - 1; iy <= y + 1; iy++) {
				world.setBlockToAir(ix, iy, z + 2);
			}
		}
		
		// Forward 3x3x1 clear
		for (int iz = z - 1; iz <= z + 1; iz++) {
			for (int iy = y - 1; iy <= y + 1; iy++) {
				world.setBlockToAir(x - 2, iy, iz);
			}
		}
		
		// Forward 3x3x1 clear
		for (int iz = z - 1; iz <= z + 1; iz++) {
			for (int iy = y - 1; iy <= y + 1; iy++) {
				world.setBlockToAir(x + 2, iy, iz);
			}
		}
		
		// Bedrock underneath voidstone gen
		for (int iy = y - 1; iy >= 0; iy--) {
			world.setBlockToAir(x, iy, z);
		}
		world.setBlock(x, y - 1, z, Block.bedrock.blockID);
	}
	
}
