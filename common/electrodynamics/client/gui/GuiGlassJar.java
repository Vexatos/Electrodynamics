package electrodynamics.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.inventory.container.ContainerGlassJar;
import electrodynamics.item.ItemDust;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.network.packet.PacketUpdateDragged;
import electrodynamics.network.packet.PacketUpdateHeld;

import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_LEFT;
import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_RIGHT;

public class GuiGlassJar extends GuiElectrodynamics implements IHotspotCallback {

	public EntityPlayer player;

	public ItemStack jar;
	
	public ContainerGlassJar container;
	
	public GuiGlassJar(EntityPlayer player, ContainerGlassJar container) {
		super(GuiType.GLASS_JAR, container);
		
		this.container = container;
		this.player = player;
		this.jar = player.getCurrentEquippedItem();
		this.manager.registerModule(new GuiModuleHotspot("dustHotspot", 62, 16, 53, 63).setCallback(this));
	}

	@Override
	public void onClicked(String uuid, MouseState state, ItemStack stack) {
		ItemStack toSend = null;
		
		if (ItemDust.isDust(stack)) {
			if (state == MOUSE_LEFT) {
				addDust(stack);
			} else if (state == MOUSE_RIGHT) {
				ItemStack newDust = stack.copy();
				newDust.stackSize = 1;
				addDust(newDust);
				
				if (stack.stackSize > 1) {
					toSend = stack.copy();
					--toSend.stackSize;
				}
			}

			//TODO Still has some sync issues :(
			this.player.inventory.setItemStack(toSend);
			PacketUpdateDragged packet = new PacketUpdateDragged(toSend);
			PacketDispatcher.sendPacketToServer(packet.makePacket());
			
			updateJar();
		}
	}
	
	private void addDust(ItemStack dust) {
		ItemGlassJar.addDust(this.jar, dust);
		this.player.setCurrentItemOrArmor(0, this.jar);
		PacketUpdateHeld packet = new PacketUpdateHeld(this.jar);
		PacketDispatcher.sendPacketToServer(packet.makePacket());
	}
	
	private void updateJar() {
		this.jar = this.player.getCurrentEquippedItem();
	}
	
}
