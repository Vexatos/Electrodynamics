package electrodynamics.purity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
			
			for (String string : this.metals) {
				if (!metalAmounts.containsKey(string)) {
					metalAmounts.put(string, 1);
				} else {
					int amount = metalAmounts.get(string);
					metalAmounts.put(string, amount + 1);
				}
			}
			
			int total = metalAmounts.size();
			
			MetalData[] metals = new MetalData[total];
			
			for (int i=0; i<total; i++) {
				Entry<String, Integer> metalData = (Entry<String, Integer>) metalAmounts.entrySet().toArray()[i];
				float ratio = metalData.getValue() / total;
				MetalData data = new MetalData(metalData.getKey(), ratio);
				metals[i] = data;
			}
			
			return metals;
		}
		
		return null;
	}
	
}
