package electrodynamics;

import java.io.File;
import java.lang.reflect.Field;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.CoreModManager;
import electrodynamics.api.IEDApi;
import electrodynamics.api.crafting.ICraftingManager;
import electrodynamics.configuration.ConfigurationHandler;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.core.CommonProxy;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.item.ItemIDs;
import electrodynamics.network.PacketHandler;
import electrodynamics.recipe.manager.CraftingManager;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.GENERIC_MOD_ID, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(channels = { ModInfo.GENERIC_MOD_ID }, clientSideRequired = false, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Electrodynamics implements IEDApi {
	@Instance(ModInfo.MOD_ID)
	public static Electrodynamics instance;

	@SidedProxy(clientSide = "electrodynamics.core.ClientProxy", serverSide = "electrodynamics.core.CommonProxy")
	public static CommonProxy proxy;

	public boolean showOptifineError = false;
	public boolean obfuscated;
	public File configFolder;
	public CraftingManager craftingManager;
	public EDLanguage languageManager;

	public static Material gas = new Material(MapColor.airColor);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Electrodynamics.instance.configFolder = new File(event.getModConfigurationDirectory(), ModInfo.GENERIC_MOD_ID);
		ConfigurationHandler.handleClass(BlockIDs.class);
		ConfigurationHandler.handleClass(ItemIDs.class);
		ConfigurationHandler.handleClass(ConfigurationSettings.class);
		
		try {
			Field deobfBool;
			deobfBool = CoreModManager.class.getDeclaredField("deobfuscatedEnvironment");
			deobfBool.setAccessible(true);
			obfuscated = !deobfBool.getBoolean(Boolean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@Override
	public ICraftingManager getCraftingManager() {
		return craftingManager;
	}

}
