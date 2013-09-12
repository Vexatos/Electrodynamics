package electrodynamics.client.gui;

import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_LEFT;

import java.awt.geom.Rectangle2D;

import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import electrodynamics.client.gui.GuiGlassJar.Rectangle;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.network.packet.PacketHotspotCallback;
import electrodynamics.recipe.manager.CraftingManager;

public class GuiHandSieve extends GuiElectrodynamics implements IHotspotCallback {

	public static final Rectangle HOTSPOT_DIMENSIONS = new Rectangle(64, 25, 47, 33);
	
	public EntityPlayer player;
	
	public Container container;
	
	public GuiHandSieve(EntityPlayer player, Container container) {
		super(GuiType.HAND_SIEVE, container);
		
		this.player = player;
		this.container = container;
		
		this.manager.registerModule(new GuiModuleHotspot("sieveClick", HOTSPOT_DIMENSIONS.x, HOTSPOT_DIMENSIONS.y, HOTSPOT_DIMENSIONS.w, HOTSPOT_DIMENSIONS.h).setCallback(this));
	}

	@Override
	public void onClicked(EntityPlayer player, String uuid, MouseState state, ItemStack stack) {
		if (uuid.equalsIgnoreCase("sieveClick")) {
			if (stack != null) {
				if (CraftingManager.getInstance().sieveManager.getRecipe(stack) != null) {
					if (state == MOUSE_LEFT) {
						PacketHotspotCallback packet = new PacketHotspotCallback(uuid, state, stack);
						PacketDispatcher.sendPacketToServer(packet.makePacket());
						--stack.stackSize;
					}
					this.player.inventory.setItemStack(stack);
				}
			}
		}
	}

}
