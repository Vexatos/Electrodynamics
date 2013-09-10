package electrodynamics.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import electrodynamics.block.EDBlocks;
import electrodynamics.block.BlockWormwood;
import electrodynamics.util.BlockUtil;

public class WorldGenWormwood implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		handleWormwood( world, chunkX, chunkZ, random );
	}

	public void handleWormwood(World world, int chunkX, int chunkZ, Random random) {
		BiomeGenBase biome = world.getBiomeGenForCoords( chunkX, chunkZ );
		int type = BlockWormwood.getTypeForBiome( biome );
		if( type == -1 ) // won't generate on this biome.
			return;
		int stage = type == 0 ? BlockWormwood.GROWN_NORMAL : BlockWormwood.GROWN_DRIED;
		int meta = random.nextInt( stage + 1 ); // random growth stage.
		if( meta > stage )
			meta = BlockWormwood.getNextGrowthStage( type, stage );
		generateWormwood( world, chunkX, chunkZ, meta );
	}
	
	public void generateWormwood(World world, int chunkX, int chunkZ, int meta) {
		final int COUNT = 8;
		
		Random random = new Random();
		
		//Generation starts in middle of selected chunk
		int x = (chunkX * 16) + 8;
		int z = (chunkZ * 16) + 8;
		int y = BlockUtil.getFirstUncoveredYPos(world, chunkX, chunkZ);
		
		for (int i=0; i<COUNT; i++) {
			//Generation spans from slightly off the middle to same point across
			x = x + random.nextInt(8) - random.nextInt(4);
			z = z + random.nextInt(8) - random.nextInt(4);

			if (world.isAirBlock(x, y, z) && (!world.provider.hasNoSky || y < 127) && Block.blocksList[EDBlocks.blockWormwood.blockID].canBlockStay(world, x, y, z)) {
				world.setBlock(x, y, z, EDBlocks.blockWormwood.blockID, meta, 2);
			}
		}
	}
	
}
