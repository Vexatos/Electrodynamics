package electrodynamics.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import electrodynamics.api.tool.IArmorModule;
import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.inventory.container.ContainerTeslaModule;

public class GuiTeslaModule extends GuiElectrodynamics {

	public EntityPlayer player;
	
	public ContainerTeslaModule container;
	
	public GuiTeslaModule(EntityPlayer player, ContainerTeslaModule container) {
		super(GuiType.TESLA_MODULE, container);
		
		this.ySize = 133;
		this.container = container;
		this.player = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (this.inventorySlots.getSlot(0).getHasStack()) {
			ItemStack stack = this.inventorySlots.getSlot(0).getStack();
			IArmorModule module = (IArmorModule) stack.getItem();
			
			this.fontRenderer.drawString(module.getModuleName(stack), 8 + 16 + 4, 8, 4210752);
			this.fontRenderer.drawSplitString(module.getModuleDescription(stack), 8, 8 + 16 + 4, 160, 4210752);
		}
	}

}
