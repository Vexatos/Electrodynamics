package electrodynamics.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.api.crafting.util.WeightedRecipeOutput;

import net.minecraft.item.ItemStack;

public class RecipeSieve {

	public ItemStack itemInput;
	
	public ArrayList<WeightedRecipeOutput> itemOutputs;
	
	public int processingTime;

	public static final int DEFAULT_PROCESSING_TIME = 3 * 20; // 3 seconds.
	
	public RecipeSieve(ItemStack itemInput, ArrayList<WeightedRecipeOutput> itemOutputs, int processingTime) {
		this.itemInput = itemInput;
		this.itemOutputs = itemOutputs;
		this.processingTime = processingTime;
	}
	
	public RecipeSieve(ItemStack itemInput, int processingTime, WeightedRecipeOutput ... itemOutputs) {
		this(itemInput, new ArrayList<WeightedRecipeOutput>(Arrays.asList(itemOutputs)), processingTime);
	}
	
	public RecipeSieve(ItemStack itemInput, int processingTime) {
		this.itemInput = itemInput;
		this.processingTime = processingTime;
		this.itemOutputs = new ArrayList<WeightedRecipeOutput>();
		
		setOutput(this.itemOutputs);
	}

	public RecipeSieve(ItemStack itemInput) {
		this(itemInput, DEFAULT_PROCESSING_TIME);
	}
	
	public void setOutput(ArrayList<WeightedRecipeOutput> outputs) {

	}

	public ItemStack getHighestOutput() {
		float chance = 0F;
		ItemStack output = null;
		
		for (WeightedRecipeOutput out : this.itemOutputs) {
			if (out.chance > chance) {
				output = out.output;
			}
		}
		
		return output;
	}
	
	public boolean isInput(ItemStack input) {
		return input != null && itemInput.itemID == input.itemID && itemInput.getItemDamage() == input.getItemDamage();
//				&& ItemStack.areItemStackTagsEqual(itemInput, input);
	}
	
}
