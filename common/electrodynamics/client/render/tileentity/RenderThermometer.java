package electrodynamics.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelThermometer;
import electrodynamics.lib.client.Textures;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderThermometer extends TileEntitySpecialRenderer {

	public ModelThermometer model;
	
	public RenderThermometer() {
		this.model = new ModelThermometer();
	}
	
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(d0, d1, d2);
		
		GL11.glRotated(180, 1, 0, 0);
		GL11.glTranslated(0, -1.55, 0);
		GL11.glTranslated(.5, 0, -.5);
		
		Textures.THERMOMETER.bind();
		this.model.render(0.0625F);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
