package electrodynamics.purity;

import java.util.HashMap;
import java.util.Map;

import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.lib.item.Ingot;
import electrodynamics.purity.Attribute.AttributeType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DynamicAlloyPurities {

	public static Map<String, AttributeWrapper> idToAttributeMapping = new HashMap<String, AttributeWrapper>();
	public static Map<ItemStack, String> stackToIDMapping = new HashMap<ItemStack, String>();
	
	static {
		registerAttributes(MetalType.ARSENIC.toString(), 
				new Attribute(AttributeType.DURABILITY, 0.025F), 
				new Attribute(AttributeType.MINING_SPEED, 0.025F)
		);
		
		registerAttributes(MetalType.COBALT.toString(), 
				new Attribute(AttributeType.DURABILITY, 0.15F), 
				new Attribute(AttributeType.CRITICAL, 0.05F),
				new Attribute(AttributeType.SHARPNESS, 0.05F),
				new Attribute(AttributeType.RADIOACTIVE, 0.05F)
		);
		
		registerAttributes(MetalType.COPPER.toString(), 
				new Attribute(AttributeType.CONDUCTIVITY, 0.05F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
		);
		
		registerAttributes(MetalType.GOLD.toString(), 
				new Attribute(AttributeType.CONDUCTIVITY, 0.1F),
				new Attribute(AttributeType.BLING, 0.05F),
				new Attribute(AttributeType.DURABILITY, -0.15F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
		);
		
		registerAttributes(MetalType.LEAD.toString(), 
				new Attribute(AttributeType.KNOCKBACK, 0.05F),
				new Attribute(AttributeType.KNOCKBACK_RESISTANCE, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, -0.1F)
		);
		
		registerAttributes(MetalType.LITHIUM.toString(), 
				new Attribute(AttributeType.REDUCED_WEIGHT, 0.1F),
				new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
				new Attribute(AttributeType.MINING_SPEED, 0.1F),
				new Attribute(AttributeType.DURABILITY, 0.1F)
		);
		
		registerAttributes(MetalType.MAGNETITE.toString(), 
				new Attribute(AttributeType.PICKUP_RADIUS, 0.05F),
				new Attribute(AttributeType.DURABILITY, -0.05F)
		);
		
		registerAttributes(MetalType.NICKEL.toString(), 
				new Attribute(AttributeType.DURABILITY, 0.1F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, -0.05F),
				new Attribute(AttributeType.SPEED, -0.05F)
		);
		
		registerAttributes(MetalType.SULFUR.toString(), 
				new Attribute(AttributeType.DURABILITY, -0.05F)
		);
		
		registerAttributes(MetalType.TELLURIUM.toString(), 
				new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
				new Attribute(AttributeType.SHARPNESS, 0.05F)
		);
		
		registerAttributes(MetalType.TUNGSTEN.toString(), 
				new Attribute(AttributeType.DURABILITY, 0.05F),
				new Attribute(AttributeType.MINING_SPEED, 0.05F)
		);
		
		registerAttributes(MetalType.VOIDSTONE.toString(), 
				new Attribute(AttributeType.EFFICIENCY, 0.2F),
				new Attribute(AttributeType.PICKUP_RADIUS, 0.2F),
				new Attribute(AttributeType.DURABILITY, 0.15F),
				new Attribute(AttributeType.CRITICAL, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, 0.1F),
				new Attribute(AttributeType.IMPLOSION_CHANCE, -0.01F)
		);
		
		assignIDToStack(MetalType.ARSENIC.toString(), Component.ARSENIC.toItemStack());
		assignIDToStack(MetalType.COBALT.toString(), Dust.COBALT.toItemStack(), Ingot.COBALT.toItemStack());
		assignIDToStack(MetalType.COPPER.toString(), Dust.COPPER.toItemStack(), Ingot.COPPER.toItemStack());
		assignIDToStack(MetalType.GOLD.toString(), Dust.GOLD.toItemStack(), new ItemStack(Item.ingotGold));
		assignIDToStack(MetalType.LEAD.toString(), Dust.LEAD.toItemStack(), Ingot.LEAD.toItemStack());
		assignIDToStack(MetalType.LITHIUM.toString(), Dust.LITHIUM.toItemStack());
		assignIDToStack(MetalType.MAGNETITE.toString(), Component.MAGNETITE_CHUNK.toItemStack(), Dust.MAGNETITE.toItemStack());
		assignIDToStack(MetalType.NICKEL.toString(), Dust.NICKEL.toItemStack(), Ingot.NICKEL.toItemStack());
		assignIDToStack(MetalType.SULFUR.toString(), Dust.SULFUR.toItemStack());
		assignIDToStack(MetalType.TELLURIUM.toString(), Dust.TELLURIUM.toItemStack(), Ingot.TELLURIUM.toItemStack());
		assignIDToStack(MetalType.TUNGSTEN.toString(), Dust.TUNGSTEN.toItemStack(), Ingot.TUNGSTEN.toItemStack());
		assignIDToStack(MetalType.VOIDSTONE.toString(), Dust.VOIDSTONE.toItemStack(), Component.VOIDSTONE_MAGNET.toItemStack());
	}
	
	public static void registerAttributes(String stack, AttributeWrapper attributes) {
		idToAttributeMapping.put(stack, attributes);
	}
	
	public static void registerAttributes(String stack, Attribute ... attributes) {
		idToAttributeMapping.put(stack, new AttributeWrapper(attributes));
	}
	
	public static void assignIDToStack(String id, ItemStack ... itemStacks) {
		for (ItemStack stack : itemStacks) {
			stackToIDMapping.put(stack, id);
		}
	}
	
	public static String getIDForStack(ItemStack stack) {
		if (stackToIDMapping.containsKey(stack)) {
			return stackToIDMapping.get(stack);
		} else {
			return null;
		}
	}
	
	public static AttributeWrapper getAttributes(String stack) {
		return idToAttributeMapping.get(stack);
	}
	
	public enum MetalType {
		ARSENIC, COBALT, COPPER, GOLD, LEAD, LITHIUM, MAGNETITE, NICKEL, SULFUR, TELLURIUM, TUNGSTEN, VOIDSTONE
	}
	
}
