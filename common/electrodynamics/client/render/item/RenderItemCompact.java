package electrodynamics.client.render.item;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import electrodynamics.item.ItemCompact;
import electrodynamics.util.render.RenderUtil;

public class RenderItemCompact implements IItemRenderer {

	public static void register(int id) {
		MinecraftForgeClient.registerItemRenderer(id, new RenderItemCompact());
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case ENTITY: return true;
		case FIRST_PERSON_MAP: return false;
		case INVENTORY: return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		if (type == ItemRenderType.ENTITY) {
			switch(helper) {
			case ENTITY_BOBBING: return true;
			case ENTITY_ROTATION: return true;
			default: return false;
			}
		}
		
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Icon icon = ((ItemCompact)item.getItem()).getIcon(ItemCompact.getItemType(item));
		
		switch(type) {
		case ENTITY: renderEntity(icon); break;
		case INVENTORY: renderInventory(icon); break;
		default: break;
		}
	}

	private void renderEntity(Icon icon) {
		GL11.glTranslatef(-.45F, -.35F, 0);
		ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), 256, 256, 0.0625F);
	}
	
	private void renderInventory(Icon icon) {
		RenderUtil.drawIcon(0, 0, icon, 16, 16);
	}
	
}
