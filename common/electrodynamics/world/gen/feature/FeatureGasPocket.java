package electrodynamics.world.gen.feature;

import java.util.Random;

import electrodynamics.block.EDBlocks;
import electrodynamics.util.BlockUtil;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeDirection;

public class FeatureGasPocket extends FeatureBase {

	public FeatureGasPocket() {
		super("Gas Pocket");
	}

	@Override
	public void generateFeature(Random random, int chunkX, int chunkZ, World world, boolean retro) {
		int x = (chunkX * 16) + random.nextInt(16);
		int y = random.nextInt(64);
		int z = (chunkZ * 16) + random.nextInt(16);
		int pocketSize = random.nextInt(10);
		
		for (; pocketSize > 0; pocketSize--) {
			if (generateGasPod(world, x, y, z)) {
				adjustCoordinates(x, y, z, getRandomDirection());
			} else {
				break;
			}
		}
		
		return;
	}

	private boolean generateGasPod(World world, int x, int y, int z) {
		if (canBeReplaced(world, x, y, z) && isIncased(world, x, y, z)) {
			world.setBlock(x, y, z, EDBlocks.blockGas.blockID);
			
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
				int[] coords = BlockUtil.getCoordsOnSide(world, x, y, z, side);
				if (canBeReplaced(world, coords[0], coords[1], coords[2]) && isIncased(world, coords[0], coords[1], coords[2])) {
					world.setBlock(x, y, z, EDBlocks.blockGas.blockID);
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	private boolean canBeReplaced(World world, int x, int y, int z) {
		int blockID = world.getBlockId(x, y, z);
		if (blockID != 0 && Block.blocksList[blockID].isGenMineableReplaceable(world, x, y, z, Block.stone.blockID)) {
			return true;
		}
		return false;
	}
	
	private boolean isIncased(World world, int x, int y, int z) {
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
			int[] coords = BlockUtil.getCoordsOnSide(world, x, y, z, side);
			int blockID = BlockUtil.getBlockOnSide(world, x, y, z, side);
			
			if (blockID != 0 || !world.isAirBlock(coords[0], coords[1], coords[2])) {
				return false;
			}
		}
		
		return true;
	}
	
	private ForgeDirection getRandomDirection() {
		return ForgeDirection.VALID_DIRECTIONS[(new Random()).nextInt(ForgeDirection.VALID_DIRECTIONS.length)];
	}
	
	private void adjustCoordinates(int x, int y, int z, ForgeDirection side) {
		x += side.offsetX;
		y += side.offsetY;
		z += side.offsetZ;
	}

}
