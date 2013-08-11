package electrodynamics.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.inventory.container.ContainerGlassJar;
import electrodynamics.lib.core.Strings;

public class GuiGlassJar extends GuiElectrodynamics {

	public EntityPlayer player;
	
	public ContainerGlassJar container;
	
	public GuiGlassJar(EntityPlayer player, ContainerGlassJar container) {
		super(GuiType.GLASS_JAR, container);
		
		this.container = container;
		this.player = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = EDLanguage.getInstance().translate(Strings.ITEM_GLASS_JAR);
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

}
