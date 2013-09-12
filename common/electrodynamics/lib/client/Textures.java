package electrodynamics.lib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.core.CoreUtils;

/** Handles all textures not related to blocks or items */

public enum Textures {

	/* Model Textures */
	TABLE_WOOD("models/textures/tableWood.png"),
	TABLE_STONE("models/textures/tableStone.png"),
	SINTERING_OVEN("models/textures/basicSinteringOven.png"),
	BASIC_SIEVE("models/textures/basicSieve.png"),
	BASIC_KILN("models/textures/basicKiln.png"),
	TREETAP("models/textures/treeTap.png"),
	BUCKET("models/textures/latexBucket.png"),
	BUCKET_LATEX("models/textures/latexBucketFull.png"),
	KILN_TRAY("models/textures/kilnTray.png"),
	METAL_TRAY("models/textures/metalTray.png"),
	CHICKEN_RAW("models/textures/chicken.png"),
	CHICKEN_COOKED("models/textures/chickenCooked.png"),
	SINTERING_FURNACE("models/textures/sinteringFurnace.png"),
	INGOT("models/textures/ingotTrayRender.png"),
	MOB_GRINDER_CLEAN("models/textures/mobGrinder.png"),
	MOB_GRINDER_BLOODY("models/textures/mobGrinder_blood.png"),
	SIGNAL_DIMMER("textures/misc/signalDimmer.png"), // Move this texture to the models texture folder
	RED_WIRE("textures/misc/redAlloyWire.png"), // This one too
	SOLAR_PANEL("models/textures/solarPanel.png"),
	GLASS_JAR("models/textures/glassJar.png"),
	
	/* GUI Textures */
	GUI_METAL_TRAY(new ResourceLocation("textures/gui/container/dispenser.png")),
	GUI_TESLA_MODULE("textures/gui/teslaModule.png"),
	GUI_KILN_TRAY("textures/gui/kilnTray.png"),
	GUI_GLASS_JAR("textures/gui/glassJar.png"),
	GUI_HAND_SIEVE("textures/gui/handSieve.png"),
	
	/* Misc Textures */
	LIGHTNING_BOLT("textures/misc/bolt_small.png"),
	BEAM("textures/misc/beam1.png");
	
	public ResourceLocation resource;
	
	private Textures(String path) {
		this.resource = CoreUtils.getResource(path);
	}
	
	private Textures(ResourceLocation resource) {
		this.resource = resource;
	}
	
	@SideOnly(Side.CLIENT)
	public void bind() {
		Minecraft.getMinecraft().renderEngine.bindTexture(this.resource);
	}
	
}
