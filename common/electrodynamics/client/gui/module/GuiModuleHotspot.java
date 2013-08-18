package electrodynamics.client.gui.module;

import net.minecraft.item.ItemStack;

public class GuiModuleHotspot extends GuiModule {

	private IHotspotCallback callback;
	
	public GuiModuleHotspot(String uuid, int x, int y, int w, int h) {
		super(uuid, x, y, w, h);
	}

	public GuiModuleHotspot setCallback(IHotspotCallback callback) {
		this.callback = callback;
		return this;
	}
	
	@Override
	public void onClicked(MouseState state, ItemStack stack) {
		this.callback.onClicked(this.uuid, state, stack);
	}
	
	@Override
	public void onRender(int mouseX, int mouseY, MouseState state) {}
	
	public interface IHotspotCallback {
		public void onClicked(String uuid, MouseState state, ItemStack stack);
	}
	
}
