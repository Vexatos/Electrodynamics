package electrodynamics.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.network.packet.PacketHotspotCallback;
import electrodynamics.network.packet.PacketPayload;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.util.math.Rectangle;

public class GuiHandSieve extends GuiElectrodynamics implements IHotspotCallback {

	public static final Rectangle HOTSPOT_DIMENSIONS = new Rectangle(64, 25, 47, 33);
	
	public static final int MAX_DUST_AMOUNT = 16;
	
	public EntityPlayer player;
	
	public Container container;
	
	public ItemStack sieve;
	
	public GuiHandSieve(EntityPlayer player, Container container, ItemStack sieve) {
		super(GuiType.HAND_SIEVE, container);
		
		this.player = player;
		this.container = container;
		this.sieve = sieve;
		
		this.manager.registerModule(new GuiModuleHotspot("sieveClick", HOTSPOT_DIMENSIONS.pointA.x, HOTSPOT_DIMENSIONS.pointA.y, HOTSPOT_DIMENSIONS.getWidth(), HOTSPOT_DIMENSIONS.getWidth()).setCallback(this));
	}

	@Override
	public void onClicked(EntityPlayer player, String uuid, MouseState state, ItemStack stack) {
		if (uuid.equalsIgnoreCase("sieveClick")) {
			if (stack != null) {
				if (ItemGlassJar.getStoredDusts(sieve).length < MAX_DUST_AMOUNT) {
					if (CraftingManager.getInstance().sieveManager.getRecipe(stack) != null) {
						PacketHotspotCallback packet = new PacketHotspotCallback(uuid, state, stack);
						PacketDispatcher.sendPacketToServer(packet.makePacket());
						switch(state) {
						case MOUSE_LEFT: {
							int max = MAX_DUST_AMOUNT - ItemGlassJar.getStoredDusts(sieve).length;
							if (stack.stackSize >= max) {
								stack.stackSize -= max;
							} else {
								stack.stackSize = 0;
							}
							break;
						}
						case MOUSE_RIGHT: {
							--stack.stackSize;
							break;
						}
						default: break;
						
						}
						if (stack.stackSize == 0) {
							stack = null;
						}
							
						this.player.inventory.setItemStack(stack);
					}
				}
			} else {
				long currentClickTime = System.currentTimeMillis();
				
				if (System.currentTimeMillis() - this.lastClickTime <= 200 && this.lastClickTime != 0L && stack == null) { // Was double click
					ItemStack[] dusts = ItemGlassJar.getStoredDusts(this.sieve);
					
					for (ItemStack stackDust : dusts) {
						stackDust.stackSize = 1;
						player.inventory.addItemStackToInventory(stackDust);
					}
					
					PacketPayload payload = new PacketPayload(1, 0, 0, 0, 0).setByte(0, (byte) 0);
					PacketDispatcher.sendPacketToServer(payload.makePacket());
					ItemGlassJar.dumpDusts(this.sieve);
				}
				
				this.lastClickTime = currentClickTime;
			}
		}
	}

}
