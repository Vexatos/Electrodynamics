package electrodynamics.purity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import electrodynamics.item.EDItems;

public class AlloyFactory {

	public List<String> metals = new ArrayList<String>();
	
	public static AlloyFactory createNewAlloyFactory() {
		return new AlloyFactory();
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
				metals[i] = data;
			}
			
			return metals;
		}
		
		return null;
	}
	
	public ItemStack generateItemStack(int i) {
		AlloyStack alloyS = new AlloyStack(new ItemStack(EDItems.itemAlloy, 1, i));
		alloyS.setMetals(getMetals());
		return alloyS.getItem().copy();
	}
	
}
