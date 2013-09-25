package electrodynamics.world.gen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import electrodynamics.world.gen.feature.FeatureBase;

public class WorldGenFeature implements IWorldGenerator {

	public final FeatureBase feature;
	
	public WorldGenFeature(FeatureBase feature) {
		this.feature = feature;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		feature.generateFeature(random, chunkX, chunkZ, world, false);
	}

}
