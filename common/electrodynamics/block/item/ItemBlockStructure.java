package electrodynamics.block.item;

import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.StructureComponent;
import net.minecraft.item.ItemStack;

public class ItemBlockStructure extends ItemBlockGeneric {

	public ItemBlockStructure(int itemID) {
		super( itemID );
	}

	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return EDLanguage.getFormattedBlockName(StructureComponent.get(itemstack.getItemDamage()).getUnlocalizedName());
	}

}
