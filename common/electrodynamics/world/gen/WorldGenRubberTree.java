package electrodynamics.world.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.IWorldGenerator;
import electrodynamics.block.EDBlocks;
import electrodynamics.util.BlockUtil;
import electrodynamics.util.gen.Rule90;

public class WorldGenRubberTree implements IWorldGenerator {

	public int maxTreeHeight;

	public ArrayList<BiomeGenBase> validBiomes;

	public WorldGenRubberTree(int height, ArrayList<BiomeGenBase> biomes) {
		this.maxTreeHeight = height;
		this.validBiomes = biomes;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
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
			
			if (validBiomes.contains(world.getBiomeGenForCoords(x, z))) {
				grow(world, x, y, z, random);
			}
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
		int branchStart = random.nextInt(h / 2) + h / 2;
		int canopyWidth = h - random.nextInt(h/2);	
		if(canopyWidth > 5) canopyWidth = 5;
		boolean[] cellInit = new boolean[canopyWidth];		
		Rule90[] cells = new Rule90[4];
		for(int i =0; i<4; i++)
		//Initialize 4 independent CAs for main branch lines of tree.
		{
			cellInit[0] = random.nextBoolean();
			cellInit[1] = random.nextBoolean();
			cells[i] = new Rule90(cellInit.clone());
		}
		
		for(int i=0; i<h; i++)
		//Begin "growing" tree using 4 cellular automata as base for branches
		//Basic algorithm for the four unique cases:
		//Place wood block for every "1" cell
		//Place 1-2 random leaves blocks orthogonally to wood block in opposite X/Z plane
		//Repeat for 3 other "branches"
		//Repeat for rest of tree
		{
			for(int c=0; c<6; c++)
			{
				for(int j=0; j<canopyWidth; j++)
				{
					if(cells[0].testCell(i, j))
					{		
						world.setBlock(x+j, y+branchStart+i, z, EDBlocks.blockRubberWood.blockID, 0, 7);
						switch(random.nextInt(4))
						{
							case 0:
								world.setBlock(x+j, y+branchStart+i+1, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 1:
								world.setBlock(x+j, y+branchStart+i, z+1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 2:
								world.setBlock(x+j, y+branchStart+i, z-1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;	
							case 3:
								world.setBlock(x+j, y+branchStart+i-1, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
						}
					}
				}
				for(int j=0; j<canopyWidth; j++)
				{
					if(cells[1].testCell(i, j))
					{	
						world.setBlock(x-j, y+branchStart+i, z, EDBlocks.blockRubberWood.blockID, 0, 7);
						switch(random.nextInt(4))
						{
							case 0:
								world.setBlock(x-j, y+branchStart+i+1, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 1:
								world.setBlock(x-j, y+branchStart+i, z+1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 2:
								world.setBlock(x-j, y+branchStart+i, z-1, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;	
							case 3:
								world.setBlock(x-j, y+branchStart+i-1, z, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
						}
					}
				}
				for(int j=0; j<canopyWidth; j++)
				{
					if(cells[2].testCell(i, j))
					{	
						world.setBlock(x, y+branchStart+i, z+j, EDBlocks.blockRubberWood.blockID, 0, 7);
						switch(random.nextInt(4))
						{
							case 0:
								world.setBlock(x, y+branchStart+i+1, z+j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 1:
								world.setBlock(x+1, y+branchStart+i, z+j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 2:
								world.setBlock(x-1, y+branchStart+i, z+j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;	
							case 3:
								world.setBlock(x, y+branchStart+i-1, z+j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
						}
					}
				}
				for(int j=0; j<canopyWidth; j++)
				{
					if(cells[3].testCell(i, j))
					{			
						world.setBlock(x, y+branchStart+i, z-j, EDBlocks.blockRubberWood.blockID, 0, 7);
						switch(random.nextInt(4))
						{
							case 0:
								world.setBlock(x, y+branchStart+i+1, z-j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 1:
								world.setBlock(x+1, y+branchStart+i, z-j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
							case 2:
								world.setBlock(x-1, y+branchStart+i, z-j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;	
							case 3:
								world.setBlock(x, y+branchStart+i-1, z-j, EDBlocks.blockRubberLeaves.blockID, 0, 7);
								break;
						}
					}
				}
			}
		}
		
		//Build trunk
		for(int i = 0; i<h; i++)
		{
			world.setBlock(x, y+i, z, EDBlocks.blockRubberWood.blockID, 0, 7);
		}
		
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

}
