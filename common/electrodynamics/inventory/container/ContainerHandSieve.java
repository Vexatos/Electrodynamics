package electrodynamics.inventory.container;

import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_LEFT;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.api.crafting.util.WeightedRecipeOutput;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.network.packet.PacketHotspotCallback;
import electrodynamics.recipe.RecipeSieve;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHandSieve extends Container implements IHotspotCallback {

	private EntityPlayer player;
	
	private int activeSlot;
	
	public ContainerHandSieve(EntityPlayer player) {
		this.player = player;
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

	@Override
	public void onClicked(EntityPlayer player, String uuid, MouseState state, ItemStack stack) {
		if (uuid.equalsIgnoreCase("sieveClick")) {
			if (stack != null) {
				if (CraftingManager.getInstance().sieveManager.getRecipe(stack) != null) {
					if (state == MOUSE_LEFT) {
						RecipeSieve recipe = CraftingManager.getInstance().sieveManager.getRecipe(stack);
						
						float percentage = 0.0F;
						int index = -1;
						
						for (int i=0; i<recipe.itemOutputs.size(); i++) {
							WeightedRecipeOutput output = recipe.itemOutputs.get(i);
							
							if (output != null && output.chance > percentage) {
								percentage = output.chance;
								index = i;
							}
						}
						
						player.dropPlayerItem(recipe.itemOutputs.get(index).output.copy());
						--stack.stackSize;
					}
					this.player.inventory.setItemStack(stack);
				}
			}
		}
	}
	
}
