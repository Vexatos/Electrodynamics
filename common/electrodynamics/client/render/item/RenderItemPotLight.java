package electrodynamics.client.render.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelGlassJar;
import electrodynamics.client.model.ModelPotLight;
import electrodynamics.lib.client.Textures;

public class RenderItemPotLight implements IItemRenderer {

	private ModelPotLight potLight;
	
	public RenderItemPotLight() {
		this.potLight = new ModelPotLight();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
		case ENTITY: {
			GL11.glScaled(2, 2, 2);
			renderPotLight(0F, 1.35F, 0F);
			break;
		}

		case EQUIPPED: {
			GL11.glScaled(2, 2, 2);
			renderPotLight(0.35F, 1.60F, 0.35F);
			break;
		}

		case EQUIPPED_FIRST_PERSON: {
			renderPotLight(1.0F, 2.125F, 1.0F);
			break;
		}

		case INVENTORY: {
			GL11.glScaled(2, 2, 2);
			renderPotLight(0F, 1.5F, 0F);
			break;
		}

		default: break;
	}
	}

	private void renderPotLight(float x, float y, float z) {
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		
		Textures.POT_LIGHT.bind();
		this.potLight.render(0.0625F);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();
	}
	
}
