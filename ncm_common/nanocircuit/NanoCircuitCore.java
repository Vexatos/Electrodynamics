package nanocircuit;

import java.io.File;

import nanocircuit.core.client.render.RenderThermalOverlay;
import nanocircuit.core.configuration.ConfigurationHandler;
import nanocircuit.core.core.CommonProxy;
import nanocircuit.core.core.helper.HeatHelper;
import nanocircuit.core.item.ItemHandler;
import nanocircuit.core.lib.ModInfo;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "NC|Core", name = "NanoCircuit - Core", version = ModInfo.VERSION, dependencies = "after:IC2")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class NanoCircuitCore {
	@Instance("NC|Core")
	public static NanoCircuitCore instance;
	@SidedProxy(clientSide = "nanocircuit.core.core.ClientProxy", serverSide = "nanocircuit.core.core.CommonProxy")
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.handleConfig(new File(event.getModConfigurationDirectory(), ModInfo.CORE_CONFIG));
		
		ItemHandler.initializeItems();
	}

	@Init
	public void init(FMLInitializationEvent event) {
		HeatHelper.initializeMapping();
		MinecraftForge.EVENT_BUS.register(new RenderThermalOverlay());
	}

}
