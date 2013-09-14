package electrodynamics.inventory.container;

import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_LEFT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import electrodynamics.client.gui.GuiGlassJar;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.item.ItemDust;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.network.packet.PacketPayload.IPayloadReceptor;
import electrodynamics.network.packet.PacketPayload;
import electrodynamics.network.packet.PacketUpdateSlot;
import electrodynamics.purity.AlloyFactory;
import electrodynamics.purity.DynamicAlloyPurities;
import electrodynamics.util.InventoryUtil;

public class ContainerGlassJar extends Container implements IHotspotCallback, IPayloadReceptor {

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

	@Override
	public void onClicked(EntityPlayer player, String uuid, MouseState state, ItemStack stack) {
		// Replicated here to ensure everything is kept in sync
		// Main code is run in GuiGlassJar
		if (!ItemGlassJar.isMixed(this.glassJar)) {
			if (player.capabilities.isCreativeMode) {
				if (state == MouseState.MOUSE_RIGHT) {
					ItemGlassJar.setMixed(this.glassJar, true);
				}
			}
			
			if (ItemDust.isDust(stack)) {
				if (ItemGlassJar.getStoredDusts(this.glassJar).length < GuiGlassJar.DUST_MAX) {
					if (state == MOUSE_LEFT) {
						ItemStack newDust = stack.copy();
						newDust.stackSize = 1;
						
						if (stack.stackSize > 1) {
							--stack.stackSize;
						} else {
							stack = null;
						}
					}
					this.player.inventory.setItemStack(stack);
				}
			}
		}
	}

	@Override
	public void handlePayload(byte[] byteArray, int[] intArray, double[] doubleArray, float[] floatArray, String[] stringArray) {
		if (byteArray != null && byteArray.length == 1 && byteArray[0] == 0) {
			ItemStack[] dusts = ItemGlassJar.getStoredDusts(this.glassJar);
			AlloyFactory factory = null;
			
			for (ItemStack stackDust : dusts) {
				if (!ItemGlassJar.isMixed(this.glassJar)) {
					player.inventory.addItemStackToInventory(stackDust);
				} else {
					factory = AlloyFactory.fromArray(dusts);
					factory.addMetal(stackDust.copy());
				}
			}
			
			if (factory != null) {
				ItemStack alloyStack = factory.generateItemStack(0);
				alloyStack.stackSize = dusts.length;
				player.inventory.addItemStackToInventory(alloyStack.copy());
			}
		}
	}
	
}
