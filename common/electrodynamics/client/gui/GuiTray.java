package electrodynamics.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.inventory.container.ContainerTray;
import electrodynamics.lib.core.Strings;

public class GuiTray extends GuiElectrodynamics {

	public EntityPlayer player;
	
	public ContainerTray container;
	
	public GuiTray(EntityPlayer player, ContainerTray container) {
		super(GuiType.METAL_TRAY, container);
		
		this.container = container;
		this.player = player;
	}

}
