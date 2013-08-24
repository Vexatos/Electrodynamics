package electrodynamics.purity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.lib.item.Ingot;
import electrodynamics.purity.Attribute.AttributeType;

public class DynamicAlloyPurities {

	public static Map<String, AttributeWrapper> idToAttributeMapping = new HashMap<String, AttributeWrapper>();
	public static Map<String, Integer> idToDurationMapping = new HashMap<String, Integer>();
	public static Map<ItemStack, String> stackToIDMapping = new HashMap<ItemStack, String>();
	
	static {
		for (MetalType metal : MetalType.values()) {
			registerAttributes(metal.toString(), metal.getAttributes());
			assignIDToStack(metal.toString(), metal.getItemStacks());
		}
	}
	
	public static void registerAttributes(String stack, Attribute ... attributes) {
		idToAttributeMapping.put(stack, new AttributeWrapper(attributes));
	}
	
	public static AttributeWrapper getAttributes(String stack) {
		return idToAttributeMapping.get(stack);
	}
	
	public static void assignIDToStack(String id, ItemStack ... itemStacks) {
		for (ItemStack stack : itemStacks) {
			stackToIDMapping.put(stack, id);
		}
	}
	
	public static String getIDForStack(ItemStack stack) {
		for (Entry<ItemStack, String> entry : stackToIDMapping.entrySet()) {
			if (stack.isItemEqual(entry.getKey())) {
				return entry.getValue();
			}
		}
		
		return "unknown";
	}
	
	public static void assignCooktimeToID(String id, int cooktime) {
		idToDurationMapping.put(id, cooktime);
	}
	
	public static int getDurationForID(String id) {
		if (idToDurationMapping.containsKey(id)) {
			return idToDurationMapping.get(id);
		} else {
			return 1;
		}
	}
	
	public enum MetalType {
		ARSENIC {
			@Override
			public Attribute[] getAttributes() {
				return new Attribute[] {
					new Attribute(AttributeType.DURABILITY, 0.025F), 
					new Attribute(AttributeType.MINING_SPEED, 0.025F)
				};
			}
			
			@Override
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Component.ARSENIC.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.COBALT.toItemStack(),
					Ingot.COBALT.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.COPPER.toItemStack(),
					Ingot.COPPER.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.GOLD.toItemStack(),
					new ItemStack(Item.ingotGold)
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.LEAD.toItemStack(),
					Ingot.LEAD.toItemStack()
				};
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
					new Attribute(AttributeType.MINING_SPEED, 0.1F),
					new Attribute(AttributeType.DURABILITY, 0.1F)
				};
			}
			
			@Override
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.LITHIUM.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.MAGNETITE.toItemStack(),
					Component.MAGNETITE_CHUNK.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.NICKEL.toItemStack(),
					Ingot.NICKEL.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.SULFUR.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.TELLURIUM.toItemStack(),
					Ingot.TELLURIUM.toItemStack()
				};
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
					new Attribute(AttributeType.MINING_SPEED, 0.05F)
				};
			}
			
			@Override
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.TUNGSTEN.toItemStack(),
					Ingot.TUNGSTEN.toItemStack()
				};
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
			public ItemStack[] getItemStacks() {
				return new ItemStack[] {
					Dust.VOIDSTONE.toItemStack(),
					Component.VOIDSTONE_MAGNET.toItemStack()
				};
			}
			
			@Override
			public int getSmeltingDuration() {
				return 0;
			}
		};
		
		public Attribute[] getAttributes() { return new Attribute[] {};}
		
		public ItemStack[] getItemStacks() { return new ItemStack[] {}; }
		
		public int getSmeltingDuration() { return 0; }
	}
	
}
