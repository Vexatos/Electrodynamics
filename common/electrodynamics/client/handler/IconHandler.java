package electrodynamics.client.handler;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class IconHandler {

	public HashMap<String, Icon> registeredIcons = new HashMap<String, Icon>();

	private static IconHandler INSTANCE;
	
	public static IconHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new IconHandler();
		}
		
		return INSTANCE;
	}
	
	@ForgeSubscribe
	public void onTextureStichPre(TextureStitchEvent.Pre event) {
		if (event.map.textureType == 0) { //BLOCKS
			registerIcon(event.map, "misc.liquidLatex");
		} else if (event.map.textureType == 1) { //ITEMS
			registerIcon(event.map, "dust.dust");
		}
	}
	
	private void registerIcon(TextureMap map, String id) {
		registeredIcons.put(id, map.registerIcon("electrodynamics:" + id.replace(".", "/")));
	}
	
	public Icon getIcon(String id) {
		return registeredIcons.get(id);
	}
	
}
