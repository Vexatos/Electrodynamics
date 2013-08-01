package electrodynamics.purity;

import java.util.HashMap;
import java.util.Map;

import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.purity.Attribute.AttributeType;

import net.minecraft.item.ItemStack;

public class DynamicAlloyPurities {

	public static Map<ItemStack, AttributeWrapper> itemAttributes = new HashMap<ItemStack, AttributeWrapper>();
	
	static {
		registerAttributes(Component.ARSENIC.toItemStack(), 
				new Attribute(AttributeType.DURABILITY, 0.025F), 
				new Attribute(AttributeType.MINING_SPEED, 0.025F)
		);
		
		registerAttributes(Dust.COBALT.toItemStack(), 
				new Attribute(AttributeType.DURABILITY, 0.15F), 
				new Attribute(AttributeType.CRITICAL, 0.05F),
				new Attribute(AttributeType.SHARPNESS, 0.05F),
				new Attribute(AttributeType.RADIOACTIVE, 0.05F)
		);
		
		registerAttributes(Dust.COPPER.toItemStack(), 
				new Attribute(AttributeType.CONDUCTIVITY, 0.05F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
		);
		
		registerAttributes(Dust.GOLD.toItemStack(), 
				new Attribute(AttributeType.CONDUCTIVITY, 0.1F),
				new Attribute(AttributeType.BLING, 0.05F),
				new Attribute(AttributeType.DURABILITY, -0.15F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
		);
		
		registerAttributes(Dust.LEAD.toItemStack(), 
				new Attribute(AttributeType.KNOCKBACK, 0.05F),
				new Attribute(AttributeType.KNOCKBACK_RESISTANCE, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, -0.1F)
		);
		
		registerAttributes(Dust.LITHIUM.toItemStack(), 
				new Attribute(AttributeType.REDUCED_WEIGHT, 0.1F),
				new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
				new Attribute(AttributeType.MINING_SPEED, 0.1F),
				new Attribute(AttributeType.DURABILITY, 0.1F)
		);
		
		registerAttributes(Dust.MAGNETITE.toItemStack(), 
				new Attribute(AttributeType.PICKUP_RADIUS, 0.05F),
				new Attribute(AttributeType.DURABILITY, -0.05F)
		);
		
		registerAttributes(Dust.NICKEL.toItemStack(), 
				new Attribute(AttributeType.DURABILITY, 0.1F),
				new Attribute(AttributeType.THERMAL_RESISTANCE, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, -0.05F),
				new Attribute(AttributeType.SPEED, -0.05F)
		);
		
		registerAttributes(Dust.SULFUR.toItemStack(), 
				new Attribute(AttributeType.DURABILITY, -0.05F)
		);
		
		registerAttributes(Dust.TELLURIUM.toItemStack(), 
				new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
				new Attribute(AttributeType.SHARPNESS, 0.05F)
		);
		
		registerAttributes(Dust.TUNGSTEN.toItemStack(), 
				new Attribute(AttributeType.DURABILITY, 0.05F),
				new Attribute(AttributeType.MINING_SPEED, 0.05F)
		);
		
		registerAttributes(Dust.VOIDSTONE.toItemStack(), 
				new Attribute(AttributeType.EFFICIENCY, 0.2F),
				new Attribute(AttributeType.PICKUP_RADIUS, 0.2F),
				new Attribute(AttributeType.DURABILITY, 0.15F),
				new Attribute(AttributeType.CRITICAL, 0.1F),
				new Attribute(AttributeType.REDUCED_WEIGHT, 0.1F),
				new Attribute(AttributeType.IMPLOSION_CHANCE, -0.01F)
		);
	}
	
	public static void registerAttributes(ItemStack stack, AttributeWrapper attributes) {
		itemAttributes.put(stack, attributes);
	}
	
	public static void registerAttributes(ItemStack stack, Attribute ... attributes) {
		itemAttributes.put(stack, new AttributeWrapper(attributes));
	}
	
	public static AttributeWrapper getAttributes(ItemStack stack) {
		return itemAttributes.get(stack);
	}
	
}
