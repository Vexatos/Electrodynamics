package electrodynamics.api.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import electrodynamics.api.crafting.util.TableRecipeType;
import electrodynamics.api.crafting.util.WeightedRecipeOutput;

public interface ICraftingManager {

	/** Registers a table recipe. Stack-size is ignored */
	public void registerTableRecipe(TableRecipeType type, ItemStack input, ItemStack output, int damage);
	
	/** Registers a sieve recipe. Stack-size of input is ignored */
	public void registerSieveRecipe(ItemStack input, ArrayList<WeightedRecipeOutput> output, int duration);
	
	/** Registers a kiln recipe. Stack-size of input/output is assumed to be one <br />
	 *  Input and output size cannot exceed 4 */
	public void registerKilnRecipe(ArrayList<ItemStack> input, ArrayList<ItemStack> output, int duration);
	
	/** Registers a grinding recipe. Stack-size of input/output is assumed to be one 
	 *  itemOutput OR liquidOutput can be null, but not both */
	public void registerGrindingRecipe(ItemStack input, ArrayList<ItemStack> itemOutput, FluidStack liquidOutput);
	
	//TODO DAP Access
	
}
