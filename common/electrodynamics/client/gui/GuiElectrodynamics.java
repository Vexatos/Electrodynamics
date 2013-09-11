package electrodynamics.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import electrodynamics.client.gui.module.GuiModule;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleManager;
import electrodynamics.core.handler.GuiHandler.GuiType;

public class GuiElectrodynamics extends GuiContainer {

	protected GuiModuleManager manager;
	
	private GuiType guiType;
	
	protected long lastClickTime = 0L;
	
	public GuiElectrodynamics(GuiType guiType, Container container) {
		super(container);
		
		this.manager = new GuiModuleManager(this);
		this.initModules(manager);
		this.guiType = guiType;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float delta) {
		super.drawScreen(mouseX, mouseY, delta);
		
		for (int i=0; i<this.manager.modules.size(); i++) {
			GuiModule module = this.manager.modules.get(i);
			
			MouseState state = MouseState.MOUSE_OFF;
			if (this.isPointInRegion(module.x, module.y, module.w, module.h, mouseX, mouseY)) {
				state = MouseState.MOUSE_ON;
			}
			module.onRender(mouseX, mouseY, state);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		for (int i=0; i<this.manager.modules.size(); i++) {
			GuiModule module = this.manager.modules.get(i);

			if (this.isPointInRegion(module.x, module.y, module.w, module.h, mouseX, mouseY)) {
				module.onClicked(mouseButton == 0 ? MouseState.MOUSE_LEFT : MouseState.MOUSE_RIGHT, FMLClientHandler.instance().getClient().thePlayer.inventory.getItemStack());
			}
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.guiType.texture.bind();
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	public void initModules(GuiModuleManager manager) {};
	
}
