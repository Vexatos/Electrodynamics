package electrodynamics.core.handler;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import electrodynamics.core.EDLogger;
import electrodynamics.lib.core.ModInfo;

public class SoundHandler {

	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent e) {
		for (Sound sound : Sound.values()) {
			try {
				e.manager.soundPoolSounds.addSound(sound.get());
				EDLogger.info("Loaded sound: " + sound.path);
			} catch (Exception ex) {
				EDLogger.warn("Failed to load sound: " + sound.path + " - Reason: " + ex.getLocalizedMessage());
			}
		}
	}

	public static enum Sound {
		VOIDSTONE("block/voidstoneAmbient.ogg"),
		ORE_CRUMBLE("block/oreCrumble.ogg"), 
		TREETAP("block/treeTap.ogg"),
		BARKCUT("block/barkCut.ogg");
		
		public String path;
		private Sound(String path) {
			this.path = path;
		}
		
		public String get() {
			return ModInfo.GENERIC_MOD_ID.toLowerCase() + ":" + this.path;
		}
	}
	
}
