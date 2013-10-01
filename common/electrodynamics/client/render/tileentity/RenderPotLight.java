package electrodynamics.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelPotLight;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.TileEntityPotLight;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class RenderPotLight extends TileEntitySpecialRenderer {

	public ModelPotLight model;
	
	public RenderPotLight() {
		this.model = new ModelPotLight();
	}
	
	public void renderPotLight(TileEntityPotLight tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x + 0.5, y - 1, z + 0.5);
		
		for (int i=0; i<tile.hasLight.length; i++) {
			if (tile.hasLight[i]) {
				ForgeDirection side = ForgeDirection.getOrientation(i);
				
				GL11.glPushMatrix();
				switch (side) {
					case NORTH: {
						GL11.glRotated(90, 1, 0, 0);
						GL11.glTranslated(0, -2, -1.5);
						break;
					}
					case DOWN: {
						GL11.glTranslated(0, -0.5, 0);
						break;
					}
					case EAST: {
						GL11.glRotated(90, 0, 0, 1);
						GL11.glTranslated(1.5, -2, 0);
						break;
					}
					case SOUTH: {
						GL11.glRotated(-90, 1, 0, 0);
						GL11.glTranslated(0, -2, 1.5);
						break;
					}
					case UP: {
						GL11.glRotated(180, 1, 0, 0);
						GL11.glTranslated(0, -3.5, 0);
						break;
					}
					case WEST: {
						GL11.glRotated(-90, 0, 0, 1);
						GL11.glTranslated(-1.5, -2, 0);
						break;
					}
				default: break;
				}
				
				Textures.POT_LIGHT.bind();
				this.model.render(0.0625F);
				
				GL11.glPopMatrix();
			}
		}
		 
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderPotLight((TileEntityPotLight) tileentity, d0, d1, d2, f);
	}
	
	
}
