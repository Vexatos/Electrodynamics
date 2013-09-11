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

public class AlloyFactory {

	public static AlloyFactory fromInventory(ItemStack[] inv) {
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
			Map<ItemStack, Integer> metalAmounts = new HashMap<ItemStack, Integer>();
			
			int total = 0;
			
			for (ItemStack stack : this.metals) {
				if (!metalAmounts.containsKey(stack)) {
					metalAmounts.put(stack, 1);
				} else {
					int amount = metalAmounts.get(stack);
					metalAmounts.put(stack, amount + 1);
				}
				
				total++;
			}
			
			MetalData[] metals = new MetalData[metalAmounts.size()];
			
			for (int i=0; i<metalAmounts.size(); i++) {
				Entry<ItemStack, Integer> metalData = (Entry<ItemStack, Integer>) metalAmounts.entrySet().toArray()[i];
				double ratio = (double)metalData.getValue() / total;
				MetalData data = new MetalData(metalData.getKey(), ratio);
				data.setTotal(metalData.getValue());
				metals[i] = data;
			}
			
			return metals;
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
