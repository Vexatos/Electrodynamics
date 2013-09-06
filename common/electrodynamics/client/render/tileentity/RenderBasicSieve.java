package electrodynamics.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelBasicSieve;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.machine.TileEntityBasicSieve;
import electrodynamics.tileentity.machine.TileEntityMachine;

public class RenderBasicSieve extends TileEntitySpecialRenderer {

	private ModelBasicSieve modelBasicSieve;

	public RenderBasicSieve() {
		this.modelBasicSieve = new ModelBasicSieve();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotatef(180, 0, 0, 1);

		if (((TileEntityBasicSieve)tile).rotation != null) {
			switch (((TileEntityMachine) tile).rotation) {
			case NORTH:
				GL11.glRotatef(270, 0, 1, 0);
				break;
			case SOUTH:
				GL11.glRotatef(90, 0, 1, 0);
				break;
			case WEST:
				GL11.glRotatef(180, 0, 1, 0);
				break;
			case EAST:
				// GL11.glRotatef(0, 0, 1, 0);
				break;
			default:
				break;
			}

			Textures.BASIC_SIEVE.bind();
			
			modelBasicSieve.render(0.0625F);
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
