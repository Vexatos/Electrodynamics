package electrodynamics.purity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import electrodynamics.item.ItemDust;
import electrodynamics.lib.item.Dust;

public class DynamicAlloyPurities {

	public Map<ItemStack, ArrayList<AttributeWrapper>> itemToAttributeMapping = new HashMap<ItemStack, ArrayList<AttributeWrapper>>();
	public Map<ItemStack, Integer> itemToSmeltingDurationMapping  = new HashMap<ItemStack, Integer>();
	
	public Map<MetalData[], MetalData[]> definedRecipes = new HashMap<MetalData[], MetalData[]>();
	
	{
		this.definedRecipes.put(new MetalData[] {new MetalData(Dust.NICKEL.toItemStack(), 0.5F), new MetalData(Dust.LITHIUM.toItemStack(), 0.5F)}, new MetalData[] {new MetalData(Dust.LIME_PURE.toItemStack(), 0.5F)});
	}
	
	public boolean hasSpecificRecipe(MetalData...data) {
//		return this.definedRecipes.containsKey(data);
		return false;
	}
	
	public static ComponentType getTypeForStack(ItemStack stack) {
		if (ItemDust.isDust(stack)) {
			return ComponentType.DUST;
		} else {
			return ComponentType.COMPONENT;
		}
	}
	
	public static enum ComponentType {
		COMPONENT, DUST;
	}
	
}
