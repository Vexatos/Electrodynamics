package electrodynamics.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import electrodynamics.core.EDLogger;
import electrodynamics.inventory.InventoryItem;

public class ContainerTray extends ContainerInventory {

	public ContainerTray(EntityPlayer player, InventoryItem inventory) {
		super(player, inventory);
		
		// Tray Inventory
		this.addSlotToContainer(new Slot(inventory, 0, 62 + 1 * 18, 17 + 1 * 18));
		
		// Player Inventory
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		if (par2 == this.activeSlot) return null;

		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 0) {
				if (!this.mergeItemStack(itemstack1, 1, 36, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	protected boolean mergeItemStack(ItemStack itemStack, int slotMin, int slotMax, boolean reverse) {
		boolean returnValue = false;
		int i = slotMin;

		if( reverse ) {
			i = slotMax - 1;
		}

		Slot slot;
		if( itemStack.isStackable() ) {
			while( itemStack.stackSize > 0 && (!reverse && i < slotMax || reverse && i >= slotMin) ) {
				slot = (Slot) this.inventorySlots.get( i );
				ItemStack slotStack = slot.getStack();

				if( slotStack != null && slotStack.itemID == itemStack.itemID && (!itemStack.getHasSubtypes()
						|| itemStack.getItemDamage() == slotStack.getItemDamage()) && ItemStack.areItemStackTagsEqual( itemStack, slotStack ) ) {
					int total = slotStack.stackSize + itemStack.stackSize;
					int max = Math.min( itemStack.getMaxStackSize(), slot.getSlotStackLimit() );

					if( total <= max ) {
						itemStack.stackSize = 0;
						slotStack.stackSize = total;
						slot.onSlotChanged();
						returnValue = true;
					} else if( slotStack.stackSize < max ) {
						itemStack.stackSize -= max - slotStack.stackSize;
						slotStack.stackSize = max;
						slot.onSlotChanged();
						returnValue = true;
					}
				}

				if( reverse ) {
					--i;
				} else {
					++i;
				}
			}
		}

		if( itemStack.stackSize > 0 ) {
			if( reverse ) {
				i = slotMax - 1;
			} else {
				i = slotMin;
			}

			while( !reverse && i < slotMax || reverse && i >= slotMin ) {
				slot = (Slot) this.inventorySlots.get( i );
				ItemStack slotStack = slot.getStack();

				if( slotStack == null ) {
					int max = Math.min( itemStack.getMaxStackSize(), slot.getSlotStackLimit() );
					max = Math.min( itemStack.stackSize, max );
					slot.putStack( itemStack.copy() );
					slot.onSlotChanged();
					itemStack.stackSize -= max;
					return true;
				}

				if( reverse ) {
					--i;
				} else {
					++i;
				}
			}
		}

		return returnValue;
	}

	@Override
	protected void retrySlotClick(int slotID, int mouseButton, boolean shiftDown, EntityPlayer player) {
		if( slotID >= 9 && mouseButton == 1 ) return;
		super.retrySlotClick( slotID, mouseButton, shiftDown, player );
	}
	
}
