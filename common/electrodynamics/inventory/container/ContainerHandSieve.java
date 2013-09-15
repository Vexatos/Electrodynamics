package electrodynamics.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import electrodynamics.client.gui.GuiHandSieve;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.network.packet.PacketPayload.IPayloadReceptor;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.util.InventoryUtil;

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
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null; // Simply disables shift-clicking
	}
	
	@Override
	public void onClicked(EntityPlayer player, String uuid, MouseState state, ItemStack stack) {
		if (uuid.equalsIgnoreCase("sieveClick")) {
			if (stack != null) {
				if (CraftingManager.getInstance().sieveManager.getRecipe(stack) != null) {
					switch(state) {
					case MOUSE_LEFT: {
						int max = GuiHandSieve.MAX_DUST_AMOUNT - ItemGlassJar.getStoredDusts(sieve).length;
						if (stack.stackSize >= max) {
							stack.stackSize -= max;
							ItemStack grind = stack.copy();
							grind.stackSize = max;
							ItemGlassJar.addDusts(this.sieve, new ItemStack[] {grind});
							break;
						} else {
							ItemGlassJar.addDusts(this.sieve, new ItemStack[] {stack.copy()});
							stack.stackSize = 0;
							break;
						}
					}
					case MOUSE_RIGHT: {
						--stack.stackSize;
						ItemStack grind = stack.copy();
						grind.stackSize = 1;
						ItemGlassJar.addDusts(this.sieve, new ItemStack[] {grind});
					}
					default: break;
					
					}
					if (stack.stackSize == 0) {
						stack = null;
					}
						
					this.player.inventory.setItemStack(stack);
				}
			}
		}
	}
	
	@Override
	public void handlePayload(byte[] byteArray, int[] intArray, double[] doubleArray, float[] floatArray, String[] stringArray) {
		if (byteArray != null && byteArray.length == 1 && byteArray[0] == 0) {
			ItemStack[] dusts = ItemGlassJar.getStoredDusts(this.sieve);
			
			for (ItemStack stackDust : dusts) {
				stackDust.stackSize = 1;
				player.inventory.addItemStackToInventory(stackDust);
			}
			
			ItemGlassJar.dumpDusts(this.sieve);
		}
	}
	
}
