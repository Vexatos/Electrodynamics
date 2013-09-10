package electrodynamics.recipe.vanilla;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemAlloy;
import electrodynamics.purity.AlloyStack;

public class IRecipeAlloyPickaxe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		ItemStack alloy = inventorycrafting.getStackInRowAndColumn(1, 0);
		return (alloy != null && alloy.getItem() instanceof ItemAlloy && alloy.getItemDamage() == 1);
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
