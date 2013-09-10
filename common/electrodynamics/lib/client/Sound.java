package electrodynamics.lib.client;

import net.minecraft.util.ResourceLocation;
import electrodynamics.lib.core.ModInfo;

public enum Sound {

	VOIDSTONE_AMBIENT("block/voidstone.ogg"),
	BARK_CUT("block/barkCut1.ogg"),
	ORE_CRUSH("block/oreCrumble.ogg"),
	TABLE_SMASH("block/tableSmash.ogg"),
	TREETAP("block/treeTap.ogg");
	
	private String soundLocation;
	private String soundTag;
	
	private Sound(String soundLocation) {
		this.soundLocation = soundLocation;
		this.soundTag = this.soundLocation.substring(0, this.soundLocation.length() - ".ogg".length()).replace("/", ".");
	}

	public String getPath() {
		return ModInfo.ICON_PREFIX + this.soundLocation;
	}
	
	public String getTag() {
		return ModInfo.ICON_PREFIX + this.soundTag;
	}
	
	public String getSimple() {
		return this.soundTag;
	}
	
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(getPath());
	}
	
}
