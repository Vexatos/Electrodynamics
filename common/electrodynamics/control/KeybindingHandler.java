package electrodynamics.control;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import electrodynamics.control.IKeybound.Type;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.packet.PacketKeyPress;

public class KeybindingHandler extends KeyBindingRegistry.KeyHandler {

	public KeybindingHandler() {
		super(KeyBindingHelper.getBindings(), KeyBindingHelper.getIsRepeating());
	}
	
	@Override
	public String getLabel() {
		return ModInfo.GENERIC_MOD_ID + ": " + this.getClass().getSimpleName();
	}
	
	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding key, boolean tickEnd, boolean isRepeat) {
		if (tickEnd) {
			if (FMLClientHandler.instance().getClient().inGameHasFocus) {
				EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
				
				if (player != null) {
					if (player.getCurrentEquippedItem() != null) {
						ItemStack currentItem = player.getCurrentEquippedItem();
						
						if (currentItem.getItem() instanceof IKeybound) {
							IKeybound keybound = (IKeybound) currentItem.getItem();
							
							if (keybound.getType() == Type.HELD || keybound.getType() == Type.BOTH) {
								if (keybound.getSide() == Side.SERVER) {
									PacketDispatcher.sendPacketToServer(PacketTypeHandler.fillPacket(new PacketKeyPress(key.keyCode, keybound.getType())));
								} else if (keybound.getSide() == Side.CLIENT) {
									keybound.onKeypress(player, currentItem, key.keyCode);
								}
							}
						}
					}

					for (ItemStack armor : player.inventory.armorInventory) {
						if (armor != null) {
							if (armor.getItem() instanceof IKeybound) {
								IKeybound keybound = (IKeybound) armor.getItem();
								
								if (keybound.getType() == Type.HELD || keybound.getType() == Type.BOTH) {
									if (keybound.getSide() == Side.SERVER) {
										PacketDispatcher.sendPacketToServer(PacketTypeHandler.fillPacket(new PacketKeyPress(key.keyCode, keybound.getType())));
									} else if (keybound.getSide() == Side.CLIENT) {
										keybound.onKeypress(player, armor, key.keyCode);
									}
								}
							}
						}
					}
					
				}
			}
		}
	}
	
	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		
	}
	
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
