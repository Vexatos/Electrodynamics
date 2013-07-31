package electrodynamics.core.handler;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import electrodynamics.core.EDLogger;
import electrodynamics.lib.core.ModInfo;

public class SoundHandler {

	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent e) {
		String[] soundFiles = new String[] { 
			"block/voidstoneAmbient.ogg", 
			"block/oreCrumble.ogg", 
			"block/treeTap.ogg",
			"block/barkCut1.ogg",
			"block/barkCut2.ogg",
			"block/melonSquish.ogg"
		};

		for (int i = 0; i < soundFiles.length; i++) {
			try {
				e.manager.soundPoolSounds.addSound(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":sounds/" + soundFiles[i]);
				EDLogger.info("Loaded sound: " + soundFiles[i]);
			} catch (Exception ex) {
				EDLogger.warn("Failed to load sound: " + soundFiles[i] + " - Reason: " + ex.getLocalizedMessage());
			}
		}
	}

}
