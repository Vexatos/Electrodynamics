package electrodynamics.world.gen.feature;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.BiomeDictionary.Type;
import electrodynamics.block.EDBlocks;
import electrodynamics.util.BlockUtil;

public class FeatureRubberTree extends FeatureBase {

	public int maxHeight;
	
	public FeatureRubberTree() {
		super("Rubber Tree");
	}

	@Override
	public void generate(World world, int chunkX, int chunkZ, Random random) {
		generate(random, chunkX, chunkZ, world);
	}
	
	public void generate(Random random, int chunkX, int chunkZ, World world) {
		if (world.getWorldInfo().getTerrainType() == WorldType.FLAT) {
			return;
		}
		
		int count = 0;
		
		if (Arrays.asList(BiomeDictionary.getBiomesForType(Type.PLAINS)).contains(world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16))) {
			count = 1;
		} else if (Arrays.asList(BiomeDictionary.getBiomesForType(Type.SWAMP)).contains(world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16))) {
			count = 2;
		} else if (Arrays.asList(BiomeDictionary.getBiomesForType(Type.JUNGLE)).contains(world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16))) {
			count = 3;
		}
		
		for (; count > 0; count--) {
			int x = (chunkX * 16) + random.nextInt(16);
			int z = (chunkZ * 16) + random.nextInt(16);
			int y = BlockUtil.getFirstUncoveredYPos(world, x, z);
			
			grow(world, x, y, z, random);
		}
	}

	public boolean grow(World world, int x, int y, int z, Random random) {
		if ((world == null) || (EDBlocks.blockRubberWood == null)) {
			return false;
		}
		
		int h = getGrowHeight(world, x, y, z, 6);
		if (h < 5) {
			return false;
		}

		h += random.nextInt((h / 2));		
		
		int canopyRadius = h/2;
		
		for(int r=canopyRadius; r>1; r--)
		{	
			world.setBlock(x, y+h-r, z+r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
			world.setBlock(x, y+h-r, z-r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
			world.setBlock(x+r, y+h-r, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
			world.setBlock(x-r, y+h-r, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
			
			int r2 = r*r;
			int cx = 1;
			int cy = (int) (Math.sqrt(r2 - 1) + 0.5);
			
			while(cx<cy)
			{	
				for(int i=0; i<cy; i++)
				{
					world.setBlock(x+cx, y+h-r, z+i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x+cx, y+h-r, z-i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x-cx, y+h-r, z+i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x-cx, y+h-r, z-i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x+i, y+h-r, z+cx, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x+i, y+h-r, z-cx, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x-i, y+h-r, z+cx, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x-i, y+h-r, z-cx, EDBlocks.blockRubberLeaves.blockID, 0, 7);				
				}
				
				switch(random.nextInt(4))
				{
					case 0:
						break;
					case 1:
						world.setBlock(x+r, y+h-r, z+1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
					case 2:
						world.setBlock(x+r, y+h-r, z-1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
					case 3:
						world.setBlock(x+r, y+h-r+1, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
				}
				switch(random.nextInt(4))
				{
					case 0:
						break;
					case 1:
						world.setBlock(x-r, y+h-r, z+1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
					case 2:
						world.setBlock(x-r, y+h-r, z-1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
					case 3:
						world.setBlock(x-r, y+h-r+1, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
				}
				switch(random.nextInt(4))
				{
					case 0:
						break;
					case 1:
						world.setBlock(x+1, y+h-r, z+r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
					case 2:
						world.setBlock(x-1, y+h-r, z+r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;					
					case 3:
						world.setBlock(x, y+h-r+1, z+r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
				}
				switch(random.nextInt(4))
				{
					case 0:
						break;
					case 1:
						world.setBlock(x+1, y+h-r, z-r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
					case 2:
						world.setBlock(x-1, y+h-r, z-r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
					case 3:
						world.setBlock(x, y+h-r+1, z-r, EDBlocks.blockRubberLeaves.blockID, 0, 7);
						break;
				}
				
				cx += 1;
				cy = (int) (Math.sqrt(r2 - cx*cx) + 0.5);
			}
			if(cx==cy)
			{
				for(int i=0; i<cy; i++)
				{
					world.setBlock(x+cx, y+h-r, z+i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x+cx, y+h-r, z-i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x-cx, y+h-r, z+i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
					world.setBlock(x-cx, y+h-r, z-i, EDBlocks.blockRubberLeaves.blockID, 0, 7);
				}
			}
		}
		
		
		//Build trunk
		for(int i = 0; i<h-1; i++)
		{
			world.setBlock(x, y+i, z, EDBlocks.blockRubberWood.blockID, 1, 7);
		}
		
		world.setBlock(x, y+h-1, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
		
		return true;
	}

	public int getGrowHeight(World world, int x, int y, int z, int max) {
		int id = world.getBlockId(x, y - 1, z);
		if ((Block.blocksList[id] == null) || (!Block.blocksList[id].canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable) Block.blocksList[EDBlocks.blockRubberSapling.blockID])) || ((world.getBlockId(x, y, z) != 0) && (world.getBlockId(x, y, z) != EDBlocks.blockRubberSapling.blockID))) {
			return 0;
		}
		int height = 1;
		for (; (world.getBlockId(x, y + 1, z) == 0) && (height < max); y++) {
			height++;
		}
		return height;
	}
	
	@Override
	public void handleConfig(Configuration config) {
		super.handleConfig(config);
		
		this.maxHeight = config.get(FeatureHandler.getFeatureCategory(this), "maxHeight", 10).getInt(10);
	}
	
}
