package electrodynamics.block.item;

import electrodynamics.block.BlockTable;
import electrodynamics.core.lang.EDLanguage;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTable extends ItemBlock {

	public ItemBlockTable(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return EDLanguage.getFormattedBlockName(BlockTable.subNames[stack.getItemDamage()]);
	}
	
}
