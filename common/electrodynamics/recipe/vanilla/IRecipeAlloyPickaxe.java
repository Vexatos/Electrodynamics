package electrodynamics.recipe.vanilla;

import electrodynamics.item.EDItems;
import electrodynamics.purity.AlloyStack;
import electrodynamics.util.ItemUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class IRecipeAlloyPickaxe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		ItemStack stick = new ItemStack(Item.stick);
		ItemStack alloy = new ItemStack(EDItems.itemAlloy);
		
		for (int i=0; i<3; i++) {
			ItemStack inSlot = inventorycrafting.getStackInSlot(i);
			
			if (inSlot != null && ItemUtil.areItemStacksEqual(inSlot, alloy, false)) {
				AlloyStack alloyS = new AlloyStack(inSlot);
				if (alloyS.getMetals() == null || alloyS.getMetals().length == 0) {
					return false;
				}
				
				ItemStack[] headSlots = new ItemStack[] {inventorycrafting.getStackInSlot(0), inventorycrafting.getStackInSlot(1), inventorycrafting.getStackInSlot(2)};
				
				if (ItemUtil.areAllEqual(headSlots, true)) {
					ItemStack spot1 = inventorycrafting.getStackInRowAndColumn(1, 1);
					ItemStack spot2 = inventorycrafting.getStackInRowAndColumn(1, 2);
					
					if (spot1 != null && spot2 != null && spot1.isItemEqual(stick) && spot2.isItemEqual(stick)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ItemStack alloy = inventorycrafting.getStackInRowAndColumn(1, 0);
		AlloyStack stack = new AlloyStack(alloy);
		ItemStack tool = new ItemStack(EDItems.itemAlloyPickaxe);
		AlloyStack pick = new AlloyStack(tool);
		pick.setMetals(stack.getMetals());
		return tool;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
