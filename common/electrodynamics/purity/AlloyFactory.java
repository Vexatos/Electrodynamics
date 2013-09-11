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
	
	public List<String> metals = new ArrayList<String>();
	
	public void addMetal(ItemStack stack) {
		metals.add(DynamicAlloyPurities.getIDForStack(stack));
	}
	
	public void removeMetal(ItemStack stack) {
		metals.remove(DynamicAlloyPurities.getIDForStack(stack));
	}
	
	public boolean containsMetal(ItemStack stack) {
		return metals.contains(DynamicAlloyPurities.getIDForStack(stack));
	}
	
	public void addMetal(String id) {
		this.metals.add(id);
	}
	
	public void removeMetal(String id) {
		this.metals.remove(id);
	}
	
	public boolean containsMetal(String id) {
		return this.metals.contains(id);
	}
	
	public MetalData[] getMetals() {
		if (this.metals != null && this.metals.size() > 0) {
			Map<String, Integer> metalAmounts = new HashMap<String, Integer>();
			
			int total = 0;
			
			for (String string : this.metals) {
				if (!metalAmounts.containsKey(string)) {
					metalAmounts.put(string, 1);
				} else {
					int amount = metalAmounts.get(string);
					metalAmounts.put(string, amount + 1);
				}
				
				total++;
			}
			
			MetalData[] metals = new MetalData[metalAmounts.size()];
			
			for (int i=0; i<metalAmounts.size(); i++) {
				Entry<String, Integer> metalData = (Entry<String, Integer>) metalAmounts.entrySet().toArray()[i];
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
