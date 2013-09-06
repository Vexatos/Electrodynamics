package electrodynamics.client.render.entity;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import electrodynamics.entity.EntityBeam;

public class RenderBeam extends Render {

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f0) {
		float f1 = ActiveRenderInfo.rotationX;
        float f2 = ActiveRenderInfo.rotationZ;
        float f3 = ActiveRenderInfo.rotationYZ;
        float f4 = ActiveRenderInfo.rotationXY;
        float f5 = ActiveRenderInfo.rotationXZ;
		
		Tessellator tessellator = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
        tessellator.startDrawingQuads();

        ((EntityBeam)entity).renderParticle(tessellator, f0, f1, f5, f2, f3, f4);

        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
