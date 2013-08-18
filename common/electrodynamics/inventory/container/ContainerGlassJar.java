package electrodynamics.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import electrodynamics.util.InventoryUtil;

public class ContainerGlassJar extends Container {

	private EntityPlayer player;
	
	private int activeSlot;
	
	private ItemStack glassJar;
	
	public ContainerGlassJar(EntityPlayer player, ItemStack glassJar) {
		this.player = player;
		this.glassJar = glassJar;
		this.activeSlot = InventoryUtil.getActiveSlot(player.inventory.currentItem, null);
		
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
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public ItemStack slotClick(int slot, int x, int y, EntityPlayer player) {
		if (slot == this.activeSlot) return null;
		return super.slotClick(slot, x, y, player);
	}
	
}
