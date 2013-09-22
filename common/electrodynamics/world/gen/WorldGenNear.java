package electrodynamics.world.gen;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import electrodynamics.addons.AddonManager;
import electrodynamics.addons.misc.EDAddonBOP;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;

public class WorldGenNear implements IWorldGenerator {

	public int blockID;
	public int blockMeta;
	public int blockCount;
	
	public int yMin = 0;
	public int yMax = 64;
	
	public int targetBlockID;
	public int targetBlockMeta;
	
	public boolean targetSet;
	
	public int rarity;

	public WorldGenNear(int id, int meta, int count, int rarity) {
		this.blockID = id;
		this.blockMeta = meta;
		this.blockCount = count;
		this.rarity = rarity;
	}
	
	public WorldGenNear setTarget(int id, int meta) {
		this.targetBlockID = id;
		this.targetBlockMeta = meta;
		this.targetSet = true;
		
		return this;
	}
	
	public WorldGenNear setYValues(int min, int max) {
		this.yMin = min;
		this.yMax = max;
		
		return this;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.getWorldInfo().getTerrainType() == WorldType.FLAT) {
			return;
		}
		
		if (EDAddonBOP.bopLoaded && world.provider.dimensionId == EDAddonBOP.promisedLandDIMID) {
			return;
		}
		
		for (int i=0; i<rarity; i++) {
			int x = (chunkX * 16) + random.nextInt(16);
			int y = random.nextInt(MathHelper.getRandomIntegerInRange(random, yMin, yMax));
			int z = (chunkZ * 16) + random.nextInt(16);
			
			if (targetSet && world.getBlockId(x, y, z) != targetBlockID && world.getBlockMetadata(x, y, z) != targetBlockMeta) {
				return;
			}
			
			world.setBlock(x, y, z, BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), 7);
		}
	}
	
}
