package electrodynamics.world.gen.vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.BiomeEvent.CreateDecorator;
import electrodynamics.lib.block.BlockIDs;

public class WorldGenClay extends WorldGenerator implements IVanillaGenPatch {

	private final int numberOfBlocks = 4;
	
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
		if (par1World.getBlockMaterial(par3, par4, par5) != Material.water) {
			return false;
		} else {
			int l = par2Random.nextInt(this.numberOfBlocks - 2) + 2;
			byte b0 = 1;

			for (int i1 = par3 - l; i1 <= par3 + l; ++i1) {
				for (int j1 = par5 - l; j1 <= par5 + l; ++j1) {
					int k1 = i1 - par3;
					int l1 = j1 - par5;

					if (k1 * k1 + l1 * l1 <= l * l) {
						for (int i2 = par4 - b0; i2 <= par4 + b0; ++i2) {
							int j2 = par1World.getBlockId(i1, i2, j1);

							if (j2 == Block.dirt.blockID || j2 == Block.blockClay.blockID) {
								if (par2Random.nextInt(4) == 0) { // 25% chance to be lithium clay
									par1World.setBlock(i1, i2, j1, BlockIDs.BLOCK_LITHIUM_CLAY_ID, 0, 2);
									System.out.println(i1 + ", " + i2 + ", " + j1);
								} else {
									par1World.setBlock(i1, i2, j1, Block.blockClay.blockID, 0, 2);
								}
							}
						}
					}
				}
			}

			return true;
		}
	}

	@ForgeSubscribe
	@Override
	public void onDecorationEvent(CreateDecorator event) {
		event.newBiomeDecorator.clayGen = this;
	}
}
