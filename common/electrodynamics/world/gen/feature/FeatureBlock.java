package electrodynamics.world.gen.feature;

import java.util.Random;

import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class FeatureBlock extends FeatureBase {

	public int genID;
	public int genMeta;
	
	public int d_count;
	public int d_minY;
	public int d_maxY;
	
	public int count;
	public int minY;
	public int maxY;	

	public FeatureBlock(String name, int blockID, int blockMeta) {
		super(name);
	}

	public FeatureBlock setDefaults(int count, int minY, int maxY) {
		this.d_count = count;
		this.d_minY = minY;
		this.d_maxY = maxY;
		return this;
	}
	
	@Override
	public void generateFeature(Random random, int chunkX, int chunkZ, World world, boolean retro) {
		super.generateFeature(random, chunkX, chunkZ, world, retro);
		
		for (int i=0; i<count; i++) {
			int x = (chunkX * 16) + random.nextInt(16);
			int y = random.nextInt(MathHelper.getRandomIntegerInRange(random, minY, maxY));
			int z = (chunkZ * 16) + random.nextInt(16);
			
			world.setBlock(x, y, z, genID, genMeta, 7);
		}
	}
	
	@Override
	public void handleConfig(Configuration config) {
		super.handleConfig(config);
		
		String CATEGORY = FeatureHandler.getFeatureCategory(this);
		
		this.count = config.get(CATEGORY, "count", d_count).getInt(d_count);
		this.minY = config.get(CATEGORY, "minY", d_minY).getInt(d_minY);
		this.maxY = config.get(CATEGORY, "maxY", d_maxY).getInt(d_maxY);
	}
	
	public void onGenned(World world, int x, int y, int z, Random random) {}
	
}
