package electrodynamics.module;

import org.lwjgl.input.Keyboard;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.Electrodynamics;
import electrodynamics.client.render.handler.XRayOverlayHandler;
import electrodynamics.core.PlayerTicker;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemArmorModule;
import electrodynamics.item.elmag.ItemElMagArmor;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.ArmorModule;
import electrodynamics.lib.item.ItemIDs;

public class EDModuleElMag extends EDModule {

	@Override
	public void preInit() {
		TickRegistry.registerTickHandler(new PlayerTicker(), Side.SERVER);
		
		// Keybindings
		Electrodynamics.proxy.setKeyBinding("Backtrack", Keyboard.KEY_B, false);
		
		EDItems.itemTeslaHelm = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_HELM_ID, 0).setUnlocalizedName(Strings.ITEM_ELMAG_HAT);
		GameRegistry.registerItem(EDItems.itemTeslaHelm, Strings.ITEM_ELMAG_HAT);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaHelm);
		
		EDItems.itemTeslaChest = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_CHEST_ID, 1).setUnlocalizedName(Strings.ITEM_ELMAG_CHEST);
		GameRegistry.registerItem(EDItems.itemTeslaChest, Strings.ITEM_ELMAG_CHEST);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaChest);
		
		EDItems.itemTeslaLegs = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_LEGS_ID, 2).setUnlocalizedName(Strings.ITEM_ELMAG_LEGS);
		GameRegistry.registerItem(EDItems.itemTeslaLegs, Strings.ITEM_ELMAG_LEGS);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaLegs);
		
		EDItems.itemTeslaBoots = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_BOOTS_ID, 3).setUnlocalizedName(Strings.ITEM_ELMAG_BOOTS);
		GameRegistry.registerItem(EDItems.itemTeslaBoots, Strings.ITEM_ELMAG_BOOTS);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaBoots);
	
		EDItems.itemTeslaModule = new ItemArmorModule(ItemIDs.ITEM_ELMAG_MODULE_ID).setUnlocalizedName(Strings.ITEM_ELMAG_MODULE);
		GameRegistry.registerItem(EDItems.itemTeslaModule, Strings.ITEM_ELMAG_MODULE);
		for (ArmorModule module : ArmorModule.values()) {
			EDLanguage.getInstance().registerItemStack(module.toItemStack(), module.unlocalizedName);
		}
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void postInit() {
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void initClient() {
		MinecraftForge.EVENT_BUS.register(new XRayOverlayHandler());
	}
	
}
