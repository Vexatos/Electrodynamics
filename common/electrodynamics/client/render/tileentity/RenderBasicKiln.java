package electrodynamics.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelBasicKiln;
import electrodynamics.client.model.ModelKilnTray;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.machine.TileEntityBasicKiln;
import electrodynamics.tileentity.machine.TileEntityMachine;
import electrodynamics.util.render.RenderUtil;

public class RenderBasicKiln extends TileEntitySpecialRenderer {

	private ModelBasicKiln modelKiln;
	private ModelKilnTray modelTray;

	public RenderBasicKiln() {
		modelKiln = new ModelBasicKiln();
		modelTray = new ModelKilnTray();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glDisable( GL11.GL_LIGHTING );

		GL11.glColor4f( 1, 1, 1, 1 );
		GL11.glTranslated( x + 0.5, y + 1.5, z + 0.5 );
		GL11.glRotatef( 180, 0, 0, 1 );

		if( ((TileEntityMachine) tile).rotation != null ) {
			switch( ((TileEntityMachine) tile).rotation ) {
				case NORTH:
//					GL11.glRotatef(0, 0, 1, 0);
					break;
				case SOUTH:
					GL11.glRotatef( 180, 0, 1, 0 );
					break;
				case WEST:
					GL11.glRotatef( 270, 0, 1, 0 );
					break;
				case EAST:
					GL11.glRotatef( 90, 0, 1, 0 );
					break;
				default:
					break;
			}
		}

		TileEntityBasicKiln kiln = (TileEntityBasicKiln) tile;

		Minecraft.getMinecraft().func_110434_K().func_110577_a(Textures.BASIC_KILN.resource);
		modelKiln.rotateDoor( kiln.doorAngle );
		modelKiln.render( 0.0625F );

		renderLED(kiln);

		// todo: render fire and particles

		if( kiln.trayInventory != null ) { // render tray
			renderTray( kiln.worldObj, kiln.trayInventory.inventory );
		}

		GL11.glEnable( GL11.GL_LIGHTING );
		GL11.glPopMatrix();
	}

	public void renderLED(TileEntityBasicKiln kiln) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.395, 0.6, -0.378);
		GL11.glColor4f(1, 1, 1, 1);
		
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		if (kiln.trayInventory != null) {
			if (kiln.totalCookTime > 0) {
				GL11.glColor3f(1, 0, 0);
			} else {
				GL11.glColor3f(0, 1, 0);
			}
		} else {
			GL11.glColor3f(0, 0, 0);
		}
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(0, 0, 0);
		GL11.glVertex3d(0, .05, 0);
		GL11.glVertex3d(.08, .05, 0);
		GL11.glVertex3d(.08, 0, 0);
		GL11.glEnd();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPopMatrix();
	}
	
	public void renderTray(World world, ItemStack[] inv) {
		GL11.glTranslated( 0, -0.15f, 0 );
//		GL11.glRotatef( 0.0f, 0, 1, 0 );
//		GL11.glScaled( 1.0f, 1.0f, 1.0f );

		Minecraft.getMinecraft().func_110434_K().func_110577_a(Textures.KILN_TRAY.resource);
		modelTray.renderAll( 0.0625F );

		GL11.glRotatef( 270, 0, 1, 0 );

		if( inv != null && inv.length > 0 ) {
			GL11.glTranslated(0, 1.2, .09 );
			GL11.glScaled( .65, .65, .65 );

			for( int i = 0; i < inv.length; i++ ) {
				ItemStack stack = inv[i];
				if( stack == null )
					continue;

				if( i != 0 ) {
					GL11.glTranslated( .28, 0, 0 );
				}

				if( i == 2) {
					GL11.glTranslated( -.56, 0, -.38 );
				}

				if( !(stack.getItem() instanceof ItemBlock) ) {
					GL11.glPushMatrix();
					GL11.glScaled( .8, .8, .8 );
					GL11.glRotatef( 90, 1, 0, 0 );
					GL11.glTranslated( 0, -.24, -.26 );

					RenderUtil.renderEntityItem(world, stack, true);

					GL11.glPopMatrix();
				} else {
					GL11.glPushMatrix();
					GL11.glRotatef( 180, 0, 0, 1 );
					GL11.glTranslated( 0, -.2, 0 );

					RenderUtil.renderEntityItem(world, stack, true);

					GL11.glPopMatrix();
				}
			}
		}
	}
}
