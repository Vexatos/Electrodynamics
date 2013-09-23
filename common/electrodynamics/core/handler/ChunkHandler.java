package electrodynamics.core.handler;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.util.misc.ChunkLocation;
import electrodynamics.world.gen.feature.FeatureHandler;

public class ChunkHandler {

	@ForgeSubscribe
	public void onChunkLoad(ChunkDataEvent.Load e) {
		System.out.println("Loading chunk: " + e.getChunk().toString());
		
		// If chunk HAS already been populated (existing chunk) AND doesn't have an EDX tag OR chunk doesn't have a regen key OR chunk has a false regen key AND regen is required on at least one registered feature
		if ((e.getChunk().isTerrainPopulated && (!e.getData().hasKey(ModInfo.GENERIC_MOD_ID) || !e.getData().getCompoundTag(ModInfo.GENERIC_MOD_ID).hasKey(ConfigurationSettings.REGEN_KEY) || !e.getData().getCompoundTag(ModInfo.GENERIC_MOD_ID).getBoolean(ConfigurationSettings.REGEN_KEY))) && FeatureHandler.getInstance().retroGenEnabled()) {
			int dim = e.world.provider.dimensionId;
			
			ArrayList chunks = (ArrayList) WorldTicker.chunksToRegen.get(Integer.valueOf(dim));
			if (chunks == null) {
				WorldTicker.chunksToRegen.put(Integer.valueOf(dim), new ArrayList());
				chunks = (ArrayList) WorldTicker.chunksToRegen.get(Integer.valueOf(dim));
			}
			
			if (chunks != null) {
				chunks.add(new ChunkLocation(e.getChunk()));
				WorldTicker.chunksToRegen.put(Integer.valueOf(dim), chunks);
			}
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setBoolean(ConfigurationSettings.REGEN_KEY, true);
			e.getData().setCompoundTag(ModInfo.GENERIC_MOD_ID, tag);
		}
	}
	
}
