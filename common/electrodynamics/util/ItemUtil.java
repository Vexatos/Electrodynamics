package electrodynamics.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

	public static boolean areAllEqual(ItemStack[] inv, boolean compareNBT) {
		if (inv == null || inv.length == 0) {
			return true;
		}
		
		ItemStack compare = inv[0];
		
		for (ItemStack stack : inv) {
			if (stack == null) {
				return false;
			}
			
			if (!areItemStacksEqual(stack, compare, compareNBT)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean areItemStacksEqual(ItemStack is1, ItemStack is2, boolean compareNBT) {
		if (is1 == null || is2 == null) return false;
		
		if (is1.getItemDamage() != OreDictionary.WILDCARD_VALUE && is2.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
			if (compareNBT) {
				return (ItemStack.areItemStacksEqual(is1, is2) && ItemStack.areItemStackTagsEqual(is1, is2));
			} else {
				return is1.isItemEqual(is2);
			}
		} else {
			return is1.itemID == is2.itemID;
		}
	}
	
	public static ItemStack getAndResize(ItemStack stack, int amount) {
		ItemStack stack2 = stack.copy();
		stack2.stackSize = amount;
		return stack2;
	}
	
	public static int getFuelValue(ItemStack stack) {
		if (stack == null) return 0;
		return Math.max(TileEntityFurnace.getItemBurnTime(stack), GameRegistry.getFuelValue(stack));
	}

	public static List<ItemStack> trimItemsList(List<ItemStack> itemsList, boolean ignoreNBT) {
		List<ItemStack> result = new ArrayList<ItemStack>();
		for( ItemStack current : itemsList ) {
			if( current == null )
				continue;

			int index = getIndexOf( current, result, ignoreNBT );
			if( index != -1 ) {
				result.get( index ).stackSize += current.stackSize;
			} else {
				result.add( current.copy() );
			}
		}
		return result;
	}

	public static boolean isItemInList(ItemStack item, List<ItemStack> list, boolean ignoreNBT) {
		if( item == null )
			return list.contains( null ); // I won't deal with this..
		for( ItemStack current : list ) {
			if( current == null )
				continue;
			if( areItemStacksSameItem( current, item, ignoreNBT ) )
				return true;
		}
		return false;
	}

	public static boolean areItemStacksSameItem(ItemStack item1, ItemStack item2, boolean ignoreNBT) {
		if( item1 == null || item2 == null )
			return item1 == item2;

		return item1.itemID == item2.itemID && item1.getItemDamage() == item2.getItemDamage()
				&& (ignoreNBT || ItemStack.areItemStackTagsEqual( item1, item2 ));
	}

	public static int getIndexOf(ItemStack item, List<ItemStack> list, boolean ignoreNBT) {
		for( int index = 0; index < list.size(); index++ ) {
			ItemStack s = list.get( index );
			if( areItemStacksSameItem( item, s, ignoreNBT ) ) {
				return index;
			}
		}
		return -1;
	}

	// removes the exact amount of the items on the first list, from the second list.
	public static List<ItemStack> removeItemsFromList(List<ItemStack> items, List<ItemStack> from) {
		List<ItemStack> result = new ArrayList<ItemStack>();
		items = copyList( trimItemsList( items, false ) );
		from = copyList( from );

		for( ItemStack current : from ) {
			if( current == null )
				continue;
			int index = getIndexOf( current, items, false );
			if( index == -1 ) {
				result.add( current );
			} else {
				ItemStack stack = items.get( index );

				if( current.stackSize >= stack.stackSize ) {
					current.stackSize -= stack.stackSize;
					stack.stackSize = 0;
					if( current.stackSize > 0 )
						result.add( current );
				} else {
					stack.stackSize -= current.stackSize;
				}

				if( stack.stackSize < 0 )
					stack.stackSize = 0;
			}
		}
		return result;
	}

	public static void removeItemsFromArray(List<ItemStack> items, ItemStack[] from) {
		items = copyList( trimItemsList( items, false ) );
		for( int i = 0; i<from.length; i++) {
			ItemStack current = from[i];
			if( current == null )
				continue;
			int index = getIndexOf( current, items, false );
			if( index != -1 ) {
				ItemStack stack = items.get( index );

				if( current.stackSize >= stack.stackSize ) {
					current.stackSize -= stack.stackSize;
					stack.stackSize = 0;
				} else {
					stack.stackSize -= current.stackSize;
					current.stackSize = 0;
				}

				if( stack.stackSize < 0 )
					stack.stackSize = 0;
				if( current.stackSize <= 0 )
					from[i] = null;
			}

		}
	}

	public static List<ItemStack> removeListFromList(List<ItemStack> remove, List<ItemStack> from) {
		List<ItemStack> result = new ArrayList<ItemStack>();
		for( ItemStack current : from ) {
			if( !isItemInList( current, remove, false ) )
				result.add( current );
		}
		return result;
	}

	public static List<ItemStack> copyList(List<ItemStack> list) {
		List<ItemStack> result = new ArrayList<ItemStack>();
		for( ItemStack item : list ) {
			if( item == null )
				result.add( null );
			else
				result.add( item.copy() );
		}
		return result;
	}
}
