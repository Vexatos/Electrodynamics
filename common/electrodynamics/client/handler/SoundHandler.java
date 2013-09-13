package electrodynamics.client.handler;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import electrodynamics.core.EDLogger;
import electrodynamics.lib.client.Sound;

public class SoundHandler {

	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent e) {
		for (Sound sound : Sound.values()) {
			try {
				e.manager.soundPoolSounds.addSound(sound.getPath());
				EDLogger.info("Loaded sound: " + sound.getSimple());
			} catch (Exception ex) {
				EDLogger.warn("Failed to load sound: " + sound.getSimple() + " - Reason: " + ex.getLocalizedMessage());
			}
		}
	}

}
