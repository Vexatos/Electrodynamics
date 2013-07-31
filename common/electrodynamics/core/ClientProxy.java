package electrodynamics.core;

import java.io.File;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import electrodynamics.Electrodynamics;
import electrodynamics.addons.AddonManager;
import electrodynamics.client.fx.FXLightningBolt;
import electrodynamics.control.KeyBindingHelper;
import electrodynamics.control.KeybindingHandler;
import electrodynamics.core.handler.IconHandler;
import electrodynamics.core.handler.SoundHandler;
import electrodynamics.lib.client.FXType;
import electrodynamics.module.ModuleManager;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		// Optifine check
		Electrodynamics.instance.showOptifineError = (FMLClientHandler.instance().hasOptifine()) && !(new File(Electrodynamics.instance.configFolder, "optifineErrorShown.flag").exists());

		// Sound handler registration
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
		
		// Icon handler
		MinecraftForge.EVENT_BUS.register(IconHandler.getInstance());
		
		ModuleManager.preInitClient();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		// Key handler registration
		KeyBindingRegistry.registerKeyBinding(new KeybindingHandler());
		
		ModuleManager.initClient();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		
		ModuleManager.postInitClient();
		AddonManager.initClient();
	}
	
	@Override
	public void setKeyBinding(String name, int value, boolean repeats) {
		KeyBindingHelper.addKeyBinding(name, value, repeats);
	}

	@Override
	public void registerKeyBindings() {
		KeyBindingRegistry.registerKeyBinding(new KeybindingHandler());
	}

	@Override
	public void handleFXPacket(FXType type, double x, double y, double z, int[] extraInfo) {
		switch (type) {
			case BLOCK_BREAK: {
				addBlockDestroyParticles((int)x, (int)y, (int)z, extraInfo[0], extraInfo[1]);
				break;
			}
			case ENDER_PARTICLES: {
				addEnderParticles(x, y, z);
				break;
			}
		}
	}
	
	@Override
	public void handleSoundPacket(String sound, double x, double y, double z) {
		FMLClientHandler.instance().getClient().theWorld.playSound(x, y, z, sound, 1F, 1F, false);
	}
	
	@Override
	public void handleSFXPacket(int sfx, double x, double y, double z) {
		FMLClientHandler.instance().getClient().theWorld.playAuxSFX(sfx, (int)x, (int)y, (int)z, 0);
	}
	
	@Override
	public void addBlockDestroyParticles(int x, int y, int z, int blockID, int meta) {
		if (blockID > 0) {
			Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(x, y, z, blockID, meta);
		}
	}

	@Override
	public void addEnderParticles(double x, double y, double z) {
		short short1 = 128;

		Random rand = new Random();
		
		for (int l = 0; l < short1; ++l) {
			double d6 = l / (short1 - 1.0D);
			float f = (rand.nextFloat() - 0.5F) * 0.2F;
			float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
			float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
			double d7 = x + (x - x) * d6 + (rand.nextDouble() - 0.5D) * .2 * 2.0D;
			double d8 = y + (y - y) * d6 + rand.nextDouble() * .2;
			double d9 = z + (z - z) * d6 + (rand.nextDouble() - 0.5D) * .2 * 2.0D;
			FMLClientHandler.instance().getClient().theWorld.spawnParticle("portal", d7, d8, d9, f, f1, f2);
		}
	}
	
	@Override
	public void addLightningFX(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration) {
		FXLightningBolt bolt = new FXLightningBolt(world, x1, y1, z1, x, y, z, seed, duration);
		bolt.defaultFractal();
		//TODO add this to method params
		bolt.setRandomType(4, 7);
		bolt.finalizeBolt();
	}
	
}
