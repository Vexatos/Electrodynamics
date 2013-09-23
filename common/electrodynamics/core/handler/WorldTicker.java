package electrodynamics.core.handler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import electrodynamics.core.EDLogger;
import electrodynamics.util.misc.ChunkLocation;
import electrodynamics.world.gen.feature.FeatureBase;
import electrodynamics.world.gen.feature.FeatureHandler;

public class WorldTicker implements ITickHandler {

	public static HashMap<Integer, ArrayList<ChunkLocation>> chunksToRegen = new HashMap<Integer, ArrayList<ChunkLocation>>();
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		WorldServer world = (WorldServer) tickData[0];
		final int REGEN_PER_TICK = 10;
		int dim = world.provider.dimensionId;
		int count = 0;
		ArrayList<ChunkLocation> chunks = (ArrayList)chunksToRegen.get(dim);
		
		if ((chunks != null) && (chunks.size() > 0)) {
			for (int i=0; i<REGEN_PER_TICK; i++) {
				try {
					count++;
					ChunkLocation chunkLoc = chunks.get(i);
					Random random = new Random();
					long worldSeed = world.getSeed();
					long xSeed = random.nextLong() >> 3;
					long zSeed = random.nextLong() >> 3;
					random.setSeed(xSeed * chunkLoc.x + zSeed * chunkLoc.z ^ worldSeed);
					for (FeatureBase feature : FeatureHandler.getInstance().loadedFeatures) {
						if (feature.enabled && feature.retro) {
							feature.generateFeature(random, chunkLoc.x, chunkLoc.z, world, true);
						}
					}
					chunks.remove(0);
				} catch (IndexOutOfBoundsException ex) {
					break;
				}
			}
			
			EDLogger.fine("Regenerated a batch of " + count + " chunks. We've got " + chunks.size() + " left to go.");
		}
		
		chunksToRegen.put(dim, chunks);
	}

	public EnumSet ticks() {
		return EnumSet.of(TickType.WORLD);
	}

	public String getLabel() {
		return "EDXWorld";
	}

}
