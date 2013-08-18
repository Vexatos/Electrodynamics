package electrodynamics.client.gui.module;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import electrodynamics.client.render.util.RenderUtil;

public class GuiModule {

	public int x;
	public int y;
	public int w;
	public int h;
	
	public String uuid;

	public GuiModuleManager manager;
	
	public GuiModule(String uuid, int x, int y, int w, int h) {
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public String[] getTooltip() {
		return null;
	}
	
	/**
	 * Fired whenever the region this module occupies is clicked
	 * @param state Returns mouse-button used
	 * @param stack Returns dragged stack. Can be null.
	 */
	public void onClicked(MouseState state, ItemStack stack) {
		
	}
	
	public void onRender(int mouseX, int mouseY, MouseState state) {
		if (state == MouseState.MOUSE_ON) {
			String[] tooltipText = getTooltip();
			
			if (tooltipText != null && tooltipText.length > 0) {
				RenderUtil.drawTooltip(tooltipText, mouseX, mouseY, manager.parent.width, manager.parent.height, Minecraft.getMinecraft().fontRenderer);
			}
		}
	}
	
	public enum MouseState {
		MOUSE_ON,
		MOUSE_OFF,
		MOUSE_LEFT,
		MOUSE_RIGHT;
	}
	
}
