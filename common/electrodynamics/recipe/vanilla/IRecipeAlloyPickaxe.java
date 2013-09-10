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
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemAlloy;
import electrodynamics.item.ItemAlloyTool;
import electrodynamics.item.ItemPeelingSpud;
import electrodynamics.purity.AlloyStack;
import electrodynamics.util.InventoryUtil;

public class IRecipeAlloyPickaxe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		ItemStack[] inv = InventoryUtil.trimInventory(InventoryUtil.getInvArrayFromInventory(inventorycrafting));
		
		return (InventoryUtil.containsInstanceOf(inv, ItemAlloy.class)) && 
				(InventoryUtil.containsInstanceOf(inv, ItemPickaxe.class) ||
				InventoryUtil.containsInstanceOf(inv, ItemAxe.class) ||
				InventoryUtil.containsInstanceOf(inv, ItemSpade.class) ||
				InventoryUtil.containsInstanceOf(inv, ItemSword.class) ||
				InventoryUtil.containsInstanceOf(inv, ItemHoe.class)) && inv.length == 2;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ItemStack alloy = inventorycrafting.getStackInRowAndColumn(1, 0);
		AlloyStack stack = new AlloyStack(alloy);
		ItemStack tool = new ItemStack(EDItems.itemAlloyPickaxe);
		AlloyStack pick = new AlloyStack(tool);
		pick.setMetals(stack.getMetals());
		//TODO Max damage
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
