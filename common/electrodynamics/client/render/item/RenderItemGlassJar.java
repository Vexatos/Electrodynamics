package electrodynamics.client.render.item;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelGlassJar;
import electrodynamics.lib.client.Textures;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemGlassJar implements IItemRenderer {

	private ModelGlassJar glassJar;
	
	public RenderItemGlassJar() {
		this.glassJar = new ModelGlassJar();
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
			renderGlassJar(0F, 1.35F, 0F);
			break;
		}

		case EQUIPPED: {
			renderGlassJar(0.50F, 1.50F, .50F);
			break;
		}

		case EQUIPPED_FIRST_PERSON: {
			renderGlassJar(1.0F, 2.0F, 1.0F);
			break;
		}

		case INVENTORY: {
			GL11.glScaled(2, 2, 2);
			renderGlassJar(0F, 1.25F, 0F);
			break;
		}

		default: break;
	}
	}

	private void renderGlassJar(float x, float y, float z) {
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		
		Textures.GLASS_JAR.bind();
		this.glassJar.render(0.0625F);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();
	}
	
}
