package electrodynamics.block.item;

import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.Ore;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOre extends ItemBlock {
	public ItemBlockOre(int i) {
		super(i);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return EDLanguage.getFormattedBlockName(Ore.get(itemstack.getItemDamage()).unlocalizedName);
	}
}
