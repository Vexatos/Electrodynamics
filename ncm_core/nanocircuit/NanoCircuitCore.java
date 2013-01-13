package nanocircuit;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import nanocircuit.core.CommonProxy;
import nanocircuit.core.Config;
import nanocircuit.core.Reference;

import nanocircuit.core.ItemPCB;
import nanocircuit.core.ItemComponent;

@Mod
(
		modid = "NCCore", 
		name = "NanoCircuit Core", 
		version = Reference.VERSION,
		dependencies = "after:IC2"
)
@NetworkMod
(
		clientSideRequired = false, 
		serverSideRequired = false
)
public class NanoCircuitCore 
{
	
	@Instance("NCCore")
	public static NanoCircuitCore instance;
	
	@SidedProxy
	(
			clientSide="nanocircuit.core.ClientProxy", 
			serverSide="nanocircuit.core.CommonProxy"
	)
	public static CommonProxy proxy;
	
	public static CreativeTabs tabsNCM = 
			new CreativeTabs(CreativeTabs.getNextID(), "NCM")
			{
				@Override
				public ItemStack getIconItemStack()
				{
					return new ItemStack(NanoCircuitCore.itemPcb, 1, 0);
				}
			};
			
	public static ItemPCB itemPcb;
	public static ItemComponent itemComponent;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.init(new File(event.getModConfigurationDirectory(), "NanoCircuit.cfg"));		
		proxy.registerRenderers();
	}
	
	@Init
	public void load(FMLInitializationEvent event) 
	{
		initItems();
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.NCM", "en_US", "NanoCircuit");
		
		NetworkRegistry.instance().registerGuiHandler(this.instance, proxy);
	}
	
	void initItems()
	{
		itemPcb = new ItemPCB(Config.ITEM_ID.pcbItem);
		itemComponent = new ItemComponent(Config.ITEM_ID.componentItem);
		
		LanguageRegistry.instance().addStringLocalization("item.chunkMagnetite.name", "en_US", "Magnetite Chunk");
		LanguageRegistry.instance().addStringLocalization("item.dustMagnetite.name", "en_US", "Magnetite Dust");	
		LanguageRegistry.instance().addStringLocalization("item.dustLodestone.name", "en_US", "Lodestone Dust");
		LanguageRegistry.instance().addStringLocalization("item.ingotLodestone.name", "en_US", "Lodestone Ingot");	
		LanguageRegistry.instance().addStringLocalization("item.rodIron.name", "en_US", "Iron Rod");
		LanguageRegistry.instance().addStringLocalization("item.fanbladeIron.name", "en_US", "Iron Fanblade");	
		LanguageRegistry.instance().addStringLocalization("item.magnetFerrite.name", "en_US", "Ferrite Magnet");
		
		LanguageRegistry.instance().addStringLocalization("item.doorClean.name", "en_US", "Clean Door");
		
		LanguageRegistry.instance().addStringLocalization("item.basicPCB.name", "en_US", "Basic PCB");
	}
}
