package electrodynamics.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import electrodynamics.client.gui.module.Module;
import electrodynamics.client.gui.module.ModuleManager;
import electrodynamics.client.gui.module.Module.MouseState;
import electrodynamics.core.handler.GuiHandler.GuiType;

public class GuiElectrodynamics extends GuiContainer {

	protected ModuleManager manager;
	
	private GuiType guiType;
	
	public GuiElectrodynamics(GuiType guiType, Container container) {
		super(container);
		
		this.manager = new ModuleManager(this);
		this.initModules(manager);
		this.guiType = guiType;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float delta) {
		super.drawScreen(mouseX, mouseY, delta);
		
		for (Module module : manager.modules) {
			MouseState state = MouseState.MOUSE_OFF;
			if (this.isPointInRegion(module.x, module.y, module.w, module.h, mouseX, mouseY)) {
				state = MouseState.MOUSE_ON;
			}
			module.onRender(mouseX, mouseY, state);
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

	public void initModules(ModuleManager manager) {};
	
}
