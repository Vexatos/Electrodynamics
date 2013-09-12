package electrodynamics.purity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import electrodynamics.core.EDLogger;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.item.EDItems;
import electrodynamics.recipe.manager.CraftingManager;

public class AlloyFactory {

	public static AlloyFactory fromArray(ItemStack...inv) {
		AlloyFactory factory = new AlloyFactory();
		for (ItemStack stack : inv) {
			factory.addMetal(stack);
		}
		return factory;
	}
	
	public List<ItemStack> metals = new ArrayList<ItemStack>();
	
	
	public void addMetal(ItemStack stack) {
		metals.add(stack);
	}
	
	public void removeMetal(ItemStack stack) {
		metals.remove(stack);
	}
	
	public boolean containsMetal(ItemStack stack) {
		return metals.contains(stack);
	}
	
	public MetalData[] getMetals() {
		if (this.metals != null && this.metals.size() > 0) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			List<Integer> itemCounts = new ArrayList<Integer>();
			
			int total = 0;
			
			for (ItemStack stack : this.metals) {
				int index = -1;
				int count = 0;
				
				for (int i=0; i<items.size(); i++) {
					if (items.get(i).isItemEqual(stack)) {
						count = itemCounts.get(i) + 1;
						index = i;
					}
				}
				
				if (index != -1) {
					itemCounts.set(index, count);
				} else {
					items.add(stack);
					itemCounts.add(1);
				}
				
				total++;
			}
			
			if (items.size() != itemCounts.size()) {
				EDLogger.warn("Something went wrong when creating an alloy! Array sizes differ!");
				return new MetalData[] {};
			}
			
			MetalData[] metals = new MetalData[items.size()];
			
			for (int i=0; i<items.size(); i++) {
				ItemStack stack = items.get(i);
				int count = itemCounts.get(i);
				
				double ratio = (double)count / total;
				MetalData data = new MetalData(stack, ratio);
				data.setTotal(count);
				metals[i] = data;
			}
			
			if (CraftingManager.getInstance().alloyManager.hasSpecificRecipe(metals)) {
//				EDLogger.info("Has recipe");
//				return CraftingManager.getInstance().alloyManager.definedRecipes.get(metals);
			} else {
				return metals;
			}
		}
		
		return null;
	}
	
	public AlloyStack generateAlloyStack(int i) {
		AlloyStack alloyS = new AlloyStack(new ItemStack(EDItems.itemAlloy, 1, i));
		alloyS.setMetals(getMetals());
		return alloyS;
	}
	
	public ItemStack generateItemStack(int i) {
		return generateAlloyStack(i).getItem().copy();
	}
	
}
