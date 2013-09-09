package electrodynamics.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Machine;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.ItemIDs;

public class CreativeTabED extends CreativeTabs {

	public static CreativeTabs resource;
	public static CreativeTabs tool;
	public static CreativeTabs block;
	
	public int itemID = 1;
	public int itemMeta = 0;
	
	static {
		resource = new CreativeTabED("ED Resources").setIcon(BlockIDs.BLOCK_ORE_ID, Ore.VOIDSTONE.ordinal());
		tool = new CreativeTabED("ED Tools").setIcon(ItemIDs.ITEM_STEEL_HAMMER_ID + 256, 0);
		block = new CreativeTabED("ED Blocks").setIcon(BlockIDs.BLOCK_MACHINE_ID, Machine.SINTERING_FURNACE.ordinal());
	}
	
	public CreativeTabED(String label) {
		super(label.toLowerCase().replace(" ", "_"));
		LanguageRegistry.instance().addStringLocalization("itemGroup."+label.toLowerCase().replace(" ", "_"), label);
	}
	
	public CreativeTabED setIcon(int id, int meta) {
		this.itemID = id;
		this.itemMeta = meta;
		return this;
	}
	
	public CreativeTabED setIcon(ItemStack stack) {
		this.itemID = stack.itemID;
		this.itemMeta = stack.getItemDamage();
		return this;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(itemID, 1, itemMeta);
	}
	
}
