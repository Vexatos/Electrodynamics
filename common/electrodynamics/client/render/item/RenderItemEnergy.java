package electrodynamics.client.render.item;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelSolarPanel;
import electrodynamics.lib.client.Textures;
import electrodynamics.util.render.RenderUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class RenderItemEnergy implements IItemRenderer {

	private ModelSolarPanel solarPanel;
	
	public RenderItemEnergy() {
		this.solarPanel = new ModelSolarPanel();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper != ItemRendererHelper.HOLD_BACKGROUND && helper != ItemRendererHelper.HOLD_HANDS;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
		case ENTITY: {
			renderSolarPanel(0F, 1F, 0F);
			break;
		}

		case EQUIPPED: {
			renderSolarPanel(1.0F, 1.0F, 1.0F);
			break;
		}

		case EQUIPPED_FIRST_PERSON: {
			renderSolarPanel(1.0F, 2.0F, 1.0F);
			break;
		}

		case INVENTORY: {
			renderSolarPanel(0F, 1F, 0F);
			break;
		}

		default: break;
	}
	}

	private void renderSolarPanel(float x, float y, float z) {
		RenderUtil.bindTexture(Textures.SOLAR_PANEL.resource);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		solarPanel.render(0.0625F);
		GL11.glPopMatrix();
	}
	
}
