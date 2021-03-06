package electrodynamics.client.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelAnvil;
import electrodynamics.client.model.ModelIngot;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.TileEntityAnvil;
import electrodynamics.tileentity.machine.TileEntityMachine;
import electrodynamics.util.render.IconUtil;

public class RenderAnvil extends TileEntitySpecialRenderer {

	public final ModelAnvil modelAnvil;
	public final ModelIngot modelIngot;
	
	public RenderAnvil() {
		this.modelAnvil = new ModelAnvil();
		this.modelIngot = new ModelIngot();
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
		this.modelAnvil.render(0.0625F);
		
		if (((TileEntityAnvil)tile).placedIngot != null) {
			IconUtil.getCachedColor(((TileEntityAnvil)tile).placedIngot).apply();

			GL11.glTranslated(-.25, .375, -.1);
			
			// Bigger ingot
//			GL11.glTranslated(-.3, .29, -.15);
//			GL11.glScaled(1.5, 1.5, 1.5);
			
			Textures.INGOT.bind();
			this.modelIngot.render(0.0625F);
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderAnvil((TileEntityAnvil) tileentity, d0, d1, d2, f);
	}

}
