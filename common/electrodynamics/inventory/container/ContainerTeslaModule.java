package electrodynamics.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import electrodynamics.api.tool.IArmorModule;
import electrodynamics.inventory.InventoryItem;

public class ContainerTeslaModule extends ContainerInventory {

	public int armorType;
	
	public ContainerTeslaModule(EntityPlayer player, InventoryItem inventory) {
		super(player, inventory);
		
		this.armorType = ((ItemArmor)this.inventory.parent.getItem()).armorType;
		
		// Tray Inventory
		this.addSlotToContainer(new SlotModule(inventory, 0, 8, 8, armorType));

		// Player Inventory
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 51));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 58 + 51) {
				@Override
				public boolean isItemValid(ItemStack stack) {
			        return stack.getItem() instanceof IArmorModule;
			    }
			});
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

			if (par2 < this.inventory.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
	
	public class SlotModule extends Slot {

		private int armorType;
		
		public SlotModule(IInventory par1iInventory, int par2, int par3, int par4, int armorType) {
			super(par1iInventory, par2, par3, par4);
			this.armorType = armorType;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			if (stack.getItem() instanceof IArmorModule) {
				for (int type : ((IArmorModule)stack.getItem()).validArmorTypes(stack)) {
					if (type == this.armorType) {
						return true;
					}
				}
			}
			
			return false;
		}
		
	}
	
}
