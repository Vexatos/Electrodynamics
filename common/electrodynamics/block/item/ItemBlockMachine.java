package electrodynamics.block.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.Machine;

public class ItemBlockMachine extends ItemBlock {

	public ItemBlockMachine(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return EDLanguage.getFormattedBlockName(Machine.get(itemstack.getItemDamage()).unlocalizedName);
	}
	
}
