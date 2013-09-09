package electrodynamics.recipe.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import electrodynamics.item.ItemAlloy;
import electrodynamics.recipe.RecipeSinteringOven;

public class RecipeManagerSinteringOven {

	public static final int DEFAULT_PROCESSING_TIME = 4 * 20; // 4 seconds
	public static final float DEFAULT_PROCESSING_RATE = 1.3f; // full tray will take 33.2 seconds

	public ArrayList<RecipeSinteringOven> sinteringOvenRecipes = new ArrayList<RecipeSinteringOven>();
	
	public void registerRecipe(RecipeSinteringOven recipe) {
		sinteringOvenRecipes.add(recipe);
	}
	
	public void registerRecipe(List<ItemStack> input, List<ItemStack> output, int duration) {
		this.registerRecipe(new RecipeSinteringOven(input, output, duration));
	}
	
	public RecipeSinteringOven getRecipe(List<ItemStack> input) {
		if ((input == null) || (input.size() == 0)) return null;
		
		RecipeSinteringOven recipe = getOvenRecipe(input);
		
		if (recipe == null) { // Handle vanilla furnace recipes.
			input = trimItemStackList(input);
			int processingTime = 0;
			float experience = 0.0f;
			List<ItemStack> realInput = new ArrayList<ItemStack>(), output = new ArrayList<ItemStack>();
			for( ItemStack item : input ) {
				recipe = getFurnaceRecipe( item );
				if( recipe != null ) { // VANILLA
					realInput.add( item );
					output.addAll( recipe.itemOutputs );
					experience += recipe.getExperience();
					processingTime = processingTime == 0 ? DEFAULT_PROCESSING_TIME : (int) Math.ceil( processingTime * DEFAULT_PROCESSING_RATE );
				} else { // ALLOY?
					if (item.getItem() instanceof ItemAlloy && item.getItemDamage() == 0) { // ALLOY!
						realInput.add(item);
						ItemStack alloy = item.copy();
						alloy.setItemDamage(1);
						output.add(alloy);
						processingTime = processingTime == 0 ? DEFAULT_PROCESSING_TIME : (int) Math.ceil( processingTime * DEFAULT_PROCESSING_RATE ); // TEMP
					}
				}
			}
			recipe = new RecipeSinteringOven( realInput, output, processingTime );
			recipe.setExperience( experience < 0.0f ? 0.0f : experience );
		}
		
		return recipe;
	}
	
	public RecipeSinteringOven getOvenRecipe(List<ItemStack> input) {
		if (input == null) return null;
		
		for (RecipeSinteringOven recipe : sinteringOvenRecipes) {
			if (recipe.isInput(input)) {
				return recipe;
			}
		}
		
		return null;
	}
	
	public RecipeSinteringOven getFurnaceRecipe(ItemStack stack) {
		if (stack == null) return null;

		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult( stack );
		if( result != null ) {
			float experience = FurnaceRecipes.smelting().getExperience( result ) * stack.stackSize;
			RecipeSinteringOven recipe = new RecipeSinteringOven( Arrays.asList( stack ), Arrays.asList( result ), 0 );
			recipe.setExperience( experience );
			return recipe;
		}
		
		return null;
	}
	
	public List<ItemStack> trimItemStackList(List<ItemStack> input) {
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		
		for (ItemStack stack : input) {
			if (stack != null) {
				inputs.add(stack);
			}
		}
		
		return inputs;
	}
	
	public void initRecipes() {
		
	}
	
}
