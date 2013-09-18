package electrodynamics.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelAnvil;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.TileEntityAnvil;
import electrodynamics.tileentity.machine.TileEntityMachine;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAnvil extends TileEntitySpecialRenderer {

	public final ModelAnvil model;
	
	public RenderAnvil() {
		this.model = new ModelAnvil();
	}
	
	public void renderAnvil(TileEntityAnvil tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslated(x, y, z);
		GL11.glRotated(180, 1, 0, 0);
		GL11.glTranslated(.5, -1.5, -.5);
		
		if (((TileEntityMachine) tile).rotation != null  ) {
			switch (((TileEntityMachine) tile).rotation) {
			case NORTH: GL11.glRotatef(180, 0, 1, 0); break;
			case WEST: GL11.glRotatef(90, 0, 1, 0); break;
			case EAST: GL11.glRotatef(270, 0, 1, 0); break;
			default: break;
			}
		}
		
		Textures.ANVIL.bind();
		this.model.render(0.0625F);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderAnvil((TileEntityAnvil) tileentity, d0, d1, d2, f);
	}

}
