package electrodynamics.recipe.manager;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import electrodynamics.Electrodynamics;
import electrodynamics.api.crafting.ICraftingManager;
import electrodynamics.api.crafting.util.TableRecipeType;
import electrodynamics.api.crafting.util.WeightedRecipeOutput;
import electrodynamics.purity.DynamicAlloyPurities;
import electrodynamics.recipe.RecipeGrinder;
import electrodynamics.recipe.RecipeSieve;

public class CraftingManager implements ICraftingManager {

	public RecipeManagerTable tableManager;
	public RecipeManagerSieve sieveManager;
	public RecipeManagerSinteringOven ovenManager;
	public RecipeManagerKiln kilnManager;
	public RecipeManagerGrinder grindManager;
	
	public DynamicAlloyPurities alloyManager;

	public static CraftingManager getInstance() {
		return Electrodynamics.instance.craftingManager;
	}

	@Override
	public void registerTableRecipe(TableRecipeType type, ItemStack input, ItemStack output, int damage) {
		tableManager.registerRecipe(type, input, output, damage);
	}

	@Override
	public void registerSieveRecipe(ItemStack input, ArrayList<WeightedRecipeOutput> output, int duration) {
		sieveManager.registerRecipe(new RecipeSieve(input, output, duration));
	}

	@Override
	public void registerOvenRecipe(ArrayList<ItemStack> input, ArrayList<ItemStack> output, int duration) {
		ovenManager.registerRecipe(input, output, duration);
	}

	@Override
	public void registerKilnRecipe(ArrayList<ItemStack> input, ArrayList<ItemStack> output, int duration) {
		kilnManager.registerRecipe(input, output, duration);
	}

	@Override
	public void registerGrindingRecipe(ItemStack input, ArrayList<ItemStack> itemOutput, FluidStack liquidOutput) {
		grindManager.registerRecipe(new RecipeGrinder(input, itemOutput, liquidOutput));
	}

}
