package electrodynamics.inventory;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.core.EDLogger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryItem implements IInventory, INBTTagable {

	public ItemStack[] inventory;
	
	public ItemStack parent;
	
	public NBTTagCompound parentNBT;
	
	public Container parentContainer;

	public int stackLimit = 1;

	public InventoryItem(int size, ItemStack parent) {
		this.inventory = new ItemStack[size];
		this.parent = parent;
		this.parentNBT = parent.getTagCompound();
		
		readFromNBT(parentNBT);
	}

	public InventoryItem(int size, ItemStack parent, int stackLimit) {
		this(size, parent);
		this.stackLimit = stackLimit < 1 ? 1 : stackLimit;
	}
	
	public void setParentNBT(NBTTagCompound nbt) {
		this.parentNBT = nbt;
		this.parent.setTagCompound(nbt);
	}
	
	public void save() {
		if (parentNBT == null) {
			setParentNBT(new NBTTagCompound());
		}

		writeToNBT(parentNBT);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		NBTTagList list = new NBTTagList();
		
		for (int i=0; i<this.inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound tempTag = new NBTTagCompound();
				tempTag.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(tempTag);
				list.appendTag(tempTag);
			}
		}
		
		tag.setTag("Items", list);
		setParentNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag == null) return;

		if (tag.hasKey("Items")) {
			NBTTagList items = tag.getTagList("Items");
			
			try {
				for (int i=0; i<items.tagCount(); i++) {
					NBTTagCompound tempTag = (NBTTagCompound) items.tagAt(i);
					
					if (tempTag != null) {
						inventory[i] = ItemStack.loadItemStackFromNBT(tempTag);
					}
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				EDLogger.warn("An inventory had more contents than available slots. The extra items have been lost! :(");
			}
		}
	}
	
	public List<ItemStack> getInventory() {
		if (this.inventory == null) return null;
		
		ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
		
		for (ItemStack stack : this.inventory) {
			inventory.add(stack);
		}
		
		return inventory;
	}
	
	public void setInventory(List<ItemStack> items) {
		if (items == null) return;
		
		this.inventory = new ItemStack[items.size()];
		
		for (int i=0; i<items.size(); i++) {
			this.inventory[i] = items.get(i);
		}
	}
	
	@Override
	public int getSizeInventory() {
		return this.inventory == null ? 0 : this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < this.inventory.length) {
			return inventory[slot];
		}
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.inventory[slot] != null) {
			ItemStack itemstack;

			if (this.inventory[slot].stackSize <= amount) {
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				this.onInventoryChanged();
				return itemstack;
			} else {
				itemstack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}

				this.onInventoryChanged();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot < this.inventory.length) {
			if (this.inventory[slot] != null) {
				ItemStack itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemstack;
			} else {
				return null;
			}
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		EDLogger.info("STACK LIMIT: "+this.getInventoryStackLimit());
		if (slot < this.inventory.length) {
			this.inventory[slot] = stack;

			if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
				stack.stackSize = this.getInventoryStackLimit();
			}

			this.onInventoryChanged();
		}
	}

	@Override
	public String getInvName() {
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return stackLimit;
	}

	@Override
	public void onInventoryChanged() {
		save();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

}
