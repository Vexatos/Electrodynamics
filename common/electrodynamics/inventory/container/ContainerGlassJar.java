package electrodynamics.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import electrodynamics.inventory.InventoryItem;

public class ContainerGlassJar extends ContainerInventory {

	public ContainerGlassJar(EntityPlayer player, InventoryItem inventory) {
		super(player, inventory);
		
		// Player Inventory
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 51));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 58 + 51));
		}
	}
	
	
	
}
