package electrodynamics.inventory.container;

import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_LEFT;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.api.crafting.util.WeightedRecipeOutput;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.network.packet.PacketHotspotCallback;
import electrodynamics.network.packet.PacketPayload.IPayloadReceptor;
import electrodynamics.purity.AlloyFactory;
import electrodynamics.recipe.RecipeSieve;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHandSieve extends Container implements IHotspotCallback, IPayloadReceptor {

	private EntityPlayer player;

	private ItemStack sieve;
	
	private int activeSlot;
	
	public ContainerHandSieve(EntityPlayer player, ItemStack sieve) {
		this.player = player;
		this.activeSlot = InventoryUtil.getActiveSlot(player.inventory.currentItem, null);
		this.sieve = sieve;
		
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
						ItemStack grind = stack.copy();
						grind.stackSize = 1;
						ItemGlassJar.addDusts(this.sieve, new ItemStack[] {grind});
						--stack.stackSize;
						if (stack.stackSize == 0) {
							stack = null;
						}
					}
					this.player.inventory.setItemStack(stack);
				}
			}
		}
	}
	
	@Override
	public void handlePayload(byte[] array) {
		if (array != null && array.length == 1 && array[0] == 0) {
			ItemStack[] dusts = ItemGlassJar.getStoredDusts(this.sieve);
			
			for (ItemStack stackDust : dusts) {
				stackDust.stackSize = 1;
				player.inventory.addItemStackToInventory(stackDust);
			}
			
			ItemGlassJar.dumpDusts(this.sieve);
		}
	}
	
}
