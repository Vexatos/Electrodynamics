package electrodynamics.recipe.manager;


import electrodynamics.lib.block.Decorative;
import electrodynamics.lib.item.Component;
import electrodynamics.recipe.RecipeKiln;
import electrodynamics.util.ItemUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeManagerKiln {

	private List<RecipeKiln> kilnRecipes = new ArrayList<RecipeKiln>();

	public void initRecipes() {
		// Limestone -> Scorched Limestone
		registerRecipe(
				new RecipeKiln( Arrays.asList( Decorative.LIMESTONE.toItemStack() ), Arrays.asList( Decorative.LIMESTONE_SCORCHED.toItemStack() ) )
		);
		
		// Rotten Flesh -> Leather
		registerRecipe(
				new RecipeKiln( Arrays.asList( new ItemStack(Item.rottenFlesh) ), Arrays.asList( new ItemStack(Item.leather) ) )
		);
		
		// Wormwood Leaf -> Sap
		registerRecipe(
				new RecipeKiln( Arrays.asList(Component.WORMWOOD_LEAF.toItemStack()), Arrays.asList(Component.SAP.toItemStack()) )
		);
	}

	public void registerRecipe(RecipeKiln recipe) {
		if( recipe != null )
			kilnRecipes.add( recipe );
	}

	public void registerRecipe(List<ItemStack> input, List<ItemStack> output, int processingTime) {
		registerRecipe( new RecipeKiln( input, output, processingTime ) );
	}

	// gets the recipe for all the items in the tray
	public RecipeKiln getKilnRecipe(List<ItemStack> itemsList) {
		RecipeKiln recipe = getRecipeFrom( itemsList );
		if( recipe != null ) {
			itemsList = ItemUtil.removeItemsFromList( recipe.getInputs(), itemsList );
		}

		if( !itemsList.isEmpty() ) {
			RecipeKiln recipe2 = getJoinedRecipe( itemsList );
			recipe = RecipeKiln.mergeRecipes( recipe, recipe2 );
		}
		return recipe;
	}

	private RecipeKiln getRecipeFrom(List<ItemStack> itemsList) {
		for( RecipeKiln current : kilnRecipes ) {
			if( current.matchesInput( itemsList ) ) {
				return getMultipliedRecipe( current, itemsList );
			}
		}
		return null;
	}

	// Used for getting the recipes of individual items in the tray
	private RecipeKiln getJoinedRecipe(List<ItemStack> itemsList) {
		RecipeKiln recipe = null;
		for( int i = 0; i < itemsList.size(); i++ ) {
			List<ItemStack> subList = itemsList.subList( i, i + 1 );
			RecipeKiln current = getRecipeFrom( subList );
			if( current != null ) {
				recipe = RecipeKiln.mergeRecipes( recipe, current );
			}
		}
		return recipe;
	}

	private RecipeKiln getMultipliedRecipe(RecipeKiln recipe, List<ItemStack> itemStackList) {
		itemStackList = ItemUtil.trimItemsList( itemStackList, false );
		int multiplier = (int) Math.floor( recipe.getMultiplier( itemStackList ) );
		List<ItemStack> realInputs = new ArrayList<ItemStack>();
		List<ItemStack> realOutputs = new ArrayList<ItemStack>();

		for( ItemStack input : recipe.getInputs() ) {
			if( input != null ) {
				ItemStack copy = input.copy();
				copy.stackSize = multiplier * input.stackSize;
				realInputs.add( copy );
			}
		}
		for( ItemStack output : recipe.getOutputs() ) {
			if( output != null ) {
				ItemStack copy = output.copy();
				copy.stackSize = multiplier * output.stackSize;
				realOutputs.add( copy );
			}
		}

		if( realOutputs.isEmpty() )
			return null;
		return new RecipeKiln( realInputs, realOutputs, recipe.processingTime * multiplier );
	}


}
