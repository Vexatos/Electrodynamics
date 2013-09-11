package electrodynamics.recipe.vanilla;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import electrodynamics.core.EDLogger;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemAlloy;
import electrodynamics.item.ItemAlloyTool;
import electrodynamics.item.ItemAlloyTool.ToolType;
import electrodynamics.item.ItemPeelingSpud;
import electrodynamics.purity.AlloyStack;
import electrodynamics.util.InventoryUtil;

public class IRecipeAlloyPickaxe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		ItemStack stack1 = inventorycrafting.getStackInRowAndColumn(0, 0);
		ItemStack stack2 = inventorycrafting.getStackInRowAndColumn(1, 0);
		
		if (stack1 != null && stack2 != null) {
			boolean alloyIn;
			boolean toolIn;
			
			alloyIn = (stack1.getItem() instanceof ItemAlloy);
			toolIn = (ToolType.getTypeFromClass(stack2.getItem().getClass()) != null);
			
			return (alloyIn && toolIn);
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ItemStack alloy = inventorycrafting.getStackInRowAndColumn(0, 0);
		ItemStack tool = inventorycrafting.getStackInRowAndColumn(1, 0);
		
		AlloyStack alloyStack = new AlloyStack(alloy);
		AlloyStack alloyTool = new AlloyStack(new ItemStack(ToolType.getTypeFromClass(tool.getItem().getClass()).getItem()));
		
		alloyTool.setMetals(alloyStack.getMetals());
		
		return alloyTool.getItem().copy();
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
