package electrodynamics.recipe.manager;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import electrodynamics.api.crafting.util.WeightedRecipeOutput;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.lib.item.Grinding;
import electrodynamics.recipe.RecipeSieve;

public class RecipeManagerSieve {

	public static ArrayList<RecipeSieve> sieveRecipes = new ArrayList<RecipeSieve>();
	
	public void registerRecipe(RecipeSieve recipe) {
		sieveRecipes.add(recipe);
	}

	public RecipeSieve getRecipe(ItemStack input) {
		if (input == null) return null;
		
		for (RecipeSieve recipe : sieveRecipes) {
			if (input.isItemEqual(recipe.itemInput)) {
				return recipe;
			}
		}
		
		return null;
	}

	public RecipeSieve[] getRecipes() {
		return sieveRecipes.toArray(new RecipeSieve[sieveRecipes.size()]);
	}
	
	public void initRecipes() {
		registerRecipe(new RecipeSieve(Grinding.COBALTITE.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Component.ARSENIC.toItemStack(), 0.05F));
				outputs.add(new WeightedRecipeOutput(Dust.COBALT.toItemStack(), 1F));
				outputs.add(new WeightedRecipeOutput(Dust.SULFUR.toItemStack(), 0.05F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.CHALCOPYRITE.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.GOLD, 0.01F));
				outputs.add(new WeightedRecipeOutput(Dust.COPPER, 0.95F));
				outputs.add(new WeightedRecipeOutput(Dust.NICKEL, 0.01F));
				outputs.add(new WeightedRecipeOutput(Dust.SULFUR, 0.01F));
				outputs.add(new WeightedRecipeOutput(Dust.COBALT, 0.01F));
				outputs.add(new WeightedRecipeOutput(Dust.IRON, 0.01F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.GALENA.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Component.ARSENIC, 0.05F));
				outputs.add(new WeightedRecipeOutput(Dust.LEAD, 1F));
				outputs.add(new WeightedRecipeOutput(Dust.COPPER, 0.05F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.LITHIUM.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.CLAY, 1F));
				outputs.add(new WeightedRecipeOutput(Dust.LITHIUM, 1F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.MAGNETITE.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Component.MAGNETITE_CHUNK, 0.2F));
				outputs.add(new WeightedRecipeOutput(Dust.MAGNETITE, 1F));
				outputs.add(new WeightedRecipeOutput(Component.LODESTONE_PEBBLE, 0.1F));
				outputs.add(new WeightedRecipeOutput(Dust.IRON, 0.1F));
				outputs.add(new WeightedRecipeOutput(Dust.NICKEL, 0.05F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.NICKEL.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.IRON, 0.05F));
				outputs.add(new WeightedRecipeOutput(Dust.NICKEL, 1F));
				outputs.add(new WeightedRecipeOutput(Dust.SULFUR, 0.05F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.WOLFRAMITE.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.TELLURIUM, 0.001F));
				outputs.add(new WeightedRecipeOutput(Dust.TUNGSTEN, 1F));
			}
		});
		
		registerRecipe(new RecipeSieve(Dust.LIMESTONE.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.LIME_PURE, 1F));
				outputs.add(new WeightedRecipeOutput(Dust.IRON, 0.05F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.VOIDSTONE.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.VOIDSTONE, 1F));
				outputs.add(new WeightedRecipeOutput(Component.VOIDSTONE_MAGNET, 0.001F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.GOLD.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.GOLD, 1F));
				outputs.add(new WeightedRecipeOutput(Dust.LEAD, 0.05F));
				outputs.add(new WeightedRecipeOutput(Dust.TELLURIUM, 0.001F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.IRON.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.IRON, 1F));
				outputs.add(new WeightedRecipeOutput(Dust.LEAD, 0.05F));
				outputs.add(new WeightedRecipeOutput(Dust.TELLURIUM, 0.001F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.COPPER.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.COPPER, 1F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.TIN.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.TIN, 1F));
			}
		});
		
		registerRecipe(new RecipeSieve(Grinding.URANIUM.toItemStack()) {
			@Override
			public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {
				outputs.add(new WeightedRecipeOutput(Dust.URANIUM, 1F));
				outputs.add(new WeightedRecipeOutput(Dust.TELLURIUM, 0.001F));
			}
		});
	}
	
}
