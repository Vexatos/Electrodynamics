package electrodynamics.purity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import electrodynamics.item.ItemDust;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.lib.item.Ingot;
import electrodynamics.purity.Attribute.AttributeType;

public class DynamicAlloyPurities {

	public static Map<String, AttributeWrapper> idToAttributeMapping = new HashMap<String, AttributeWrapper>();
	
	static {
		for (MetalType metal : MetalType.values()) {
			registerAttributes(metal.toString(), metal.getAttributes());
		}
	}
	
	public static void registerAttributes(String stack, Attribute ... attributes) {
		idToAttributeMapping.put(stack, new AttributeWrapper(attributes));
	}
	
	public static AttributeWrapper getAttributes(String stack) {
		return idToAttributeMapping.get(stack);
	}
	
	public static String getIDForStack(ItemStack stack) {
		if (ItemDust.isDust(stack)) {
			for (MetalType type : MetalType.values()) {
				if (type.getDust() != null && type.getDust().isItemEqual(stack)) {
					return type.toString();
				}
			}
		} else {
			for (MetalType type : MetalType.values()) {
				if (type.getSolid() != null && type.getSolid().isItemEqual(stack)) {
					return type.toString();
				}
			}
		}
		
		return "UNKNOWN";
	}

	public enum MetalType {
		ARSENIC {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.DURABILITY, 0.025F), 
					new Attribute(AttributeType.EFFICIENCY, 0.025F)
				};
			}
			
			@Override
			public ItemStack getSolid() {
				return Component.ARSENIC.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		COBALT {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.DURABILITY, 0.15F), 
					new Attribute(AttributeType.CRITICAL, 0.05F),
					new Attribute(AttributeType.SHARPNESS, 0.05F),
					new Attribute(AttributeType.RADIOACTIVE, 0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.COBALT.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Ingot.COBALT.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		COPPER {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.CONDUCTIVITY, 0.05F),
					new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.COPPER.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Ingot.COPPER.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		GOLD {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.CONDUCTIVITY, 0.1F),
					new Attribute(AttributeType.BLING, 0.05F),
					new Attribute(AttributeType.DURABILITY, -0.15F),
					new Attribute(AttributeType.THERMAL_RESISTANCE, -0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.GOLD.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return new ItemStack(Item.ingotGold);
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		LEAD {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.KNOCKBACK, 0.05F),
					new Attribute(AttributeType.KNOCKBACK_RESISTANCE, 0.1F),
					new Attribute(AttributeType.REDUCED_WEIGHT, -0.1F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.LEAD.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Ingot.LEAD.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		LITHIUM {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.REDUCED_WEIGHT, 0.1F),
					new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
					new Attribute(AttributeType.EFFICIENCY, 0.1F),
					new Attribute(AttributeType.DURABILITY, 0.1F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.LITHIUM.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		MAGNETITE {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.PICKUP_RADIUS, 0.05F),
					new Attribute(AttributeType.DURABILITY, -0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.MAGNETITE.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Component.MAGNETITE_CHUNK.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		NICKEL {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.DURABILITY, 0.1F),
					new Attribute(AttributeType.THERMAL_RESISTANCE, 0.1F),
					new Attribute(AttributeType.REDUCED_WEIGHT, -0.05F),
					new Attribute(AttributeType.SPEED, -0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.NICKEL.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Ingot.NICKEL.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		SULFUR {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.DURABILITY, -0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.SULFUR.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		TELLURIUM {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.ATTACK_SPEED, 0.1F),
					new Attribute(AttributeType.SHARPNESS, 0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.TELLURIUM.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Ingot.TELLURIUM.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		TUNGSTEN {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.DURABILITY, 0.05F),
					new Attribute(AttributeType.EFFICIENCY, 0.05F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.TUNGSTEN.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Ingot.TUNGSTEN.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		},
		VOIDSTONE {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.EFFICIENCY, 0.2F),
					new Attribute(AttributeType.PICKUP_RADIUS, 0.2F),
					new Attribute(AttributeType.DURABILITY, 0.15F),
					new Attribute(AttributeType.CRITICAL, 0.1F),
					new Attribute(AttributeType.REDUCED_WEIGHT, 0.1F),
					new Attribute(AttributeType.IMPLOSION_CHANCE, -0.01F)
				};
			}
			
			@Override
			public ItemStack getDust() {
				return Dust.VOIDSTONE.toItemStack();
			}
			
			@Override
			public ItemStack getSolid() {
				return Component.VOIDSTONE_MAGNET.toItemStack();
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		};
		
		public Attribute[] getAttributes() { return new Attribute[] {};}
		
		public ItemStack getDust() { return null; };

		public ItemStack getSolid() { return null; };
		
		public int getSmeltingDuration() { return 0; }
		
		public static MetalType get(String id) {
			for (MetalType type : MetalType.values()) {
				if (type.toString().equalsIgnoreCase(id)) {
					return type;
				}
			}
			
			return null;
		}
		
		public static MetalType get(MetalData data) {
			return get(data.metalID);
		}
		
	}
	
}
