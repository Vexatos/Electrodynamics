package electrodynamics.purity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import electrodynamics.item.ItemDust;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.purity.Attribute.AttributeType;

public class DynamicAlloyPurities {

	public static Map<ItemStack, AttributeWrapper> itemToAttributeMapping = new HashMap<ItemStack, AttributeWrapper>();
	
	/** Simple mapping to store item heat/smelt duration requirements. 
	 *  Value is simple two length array. Index 0 stores required heat, 
	 *  Index 1 stores required duration                            */
	public static Map<ItemStack, int[]> itemToSmeltInfoMapping  = new HashMap<ItemStack, int[]>();
	
	static {
		// Arsenic Chunk
		itemToAttributeMapping.put(Component.ARSENIC.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.DURABILITY, 0.025F),
				new Attribute(AttributeType.EFFICIENCY, 0.025F)
		));
		itemToSmeltInfoMapping.put(Component.ARSENIC.toItemStack(), new int[] {0, 0});
		
		// Arsenic - Lead
		
		// Arsenic - Copper
		
		// Coal
		
		// Cobalt
		itemToAttributeMapping.put(Dust.COBALT.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.DURABILITY, 0.15F),
				new Attribute(AttributeType.CRITICAL, 0.05F),
				new Attribute(AttributeType.SHARPNESS, 0.05F),
				new Attribute(AttributeType.SHARPNESS, 0.05F)
		));
		
		// Copper
		itemToAttributeMapping.put(Dust.COPPER.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.CONDUCTIVITY, 0.05F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
		));
		
		// Gold
		itemToAttributeMapping.put(Dust.GOLD.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.CONDUCTIVITY, 0.1F),
				new Attribute(AttributeType.BLING, 0.05F),
				new Attribute(AttributeType.DURABILITY, -0.15F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
		));
		
		// Lead
		itemToAttributeMapping.put(Dust.LEAD.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.KNOCKBACK, 0.01F),
				new Attribute(AttributeType.KNOCKBACK_RESISTANCE, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, -0.1F),
				new Attribute(AttributeType.EFFICIENCY, -0.1F)
		));
		
		// Lithium
		itemToAttributeMapping.put(Dust.LITHIUM.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.REDUCED_WEIGHT, 0.01F),
				new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
				new Attribute(AttributeType.EFFICIENCY, 0.01F),
				new Attribute(AttributeType.DURABILITY, 0.1F)
		));
		
		// Magnetite
		itemToAttributeMapping.put(Dust.MAGNETITE.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.PICKUP_RADIUS, 0.05F),
				new Attribute(AttributeType.DURABILITY, -0.05F)
		));
		
		// Nickel
		itemToAttributeMapping.put(Dust.NICKEL.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.DURABILITY, 0.1F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, -0.05F),
				new Attribute(AttributeType.SPEED, -0.05F)
		));
		
		// Silicon
		
		// Sulfur
		itemToAttributeMapping.put(Dust.SULFUR.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.DURABILITY, -0.05F)
		));
		
		// Tellurium
		itemToAttributeMapping.put(Dust.TELLURIUM.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
				new Attribute(AttributeType.SHARPNESS, 0.05F)
		));
		
		// Tungsten
		itemToAttributeMapping.put(Dust.TUNGSTEN.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.DURABILITY, 0.05F),
				new Attribute(AttributeType.EFFICIENCY, 0.05F),
				new Attribute(AttributeType.REDUCED_WEIGHT, -0.05F)
		));
		
		// Voidstone
		itemToAttributeMapping.put(Dust.VOIDSTONE.toItemStack(), new AttributeWrapper(
				new Attribute(AttributeType.EFFICIENCY, 0.2F),
				new Attribute(AttributeType.PICKUP_RADIUS, 0.2F),
				new Attribute(AttributeType.DURABILITY, 0.15F),
				new Attribute(AttributeType.CRITICAL, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, 0.1F),
				new Attribute(AttributeType.IMPLOSION_CHANCE, 0.01F)
		));
	}
	
	public static int[] getSmeltInfoForStack(ItemStack stack) {
		if (stack == null) {
			return new int[] {};
		}
		
		for (Entry<ItemStack, int[]> entry : itemToSmeltInfoMapping.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				return entry.getValue();
			}
		}
		
		return new int[] {};
	}
	
	public static Attribute[] getAttributesForStack(ItemStack stack) {
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		for (Entry<ItemStack, AttributeWrapper> entry : itemToAttributeMapping.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				for (Attribute attribute : entry.getValue().attributes) {
					attributes.add(attribute);
				}
			}
		}
		
		return attributes.toArray(new Attribute[attributes.size()]);
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
