package electrodynamics.block.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.Storage;

public class ItemBlockStorage extends ItemBlock {

	public ItemBlockStorage(int i) {
		super(i);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return EDLanguage.getFormattedBlockName(Storage.get(itemstack.getItemDamage()).unlocalizedName);
	}
	
}
