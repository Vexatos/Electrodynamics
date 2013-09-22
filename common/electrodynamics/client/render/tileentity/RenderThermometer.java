package electrodynamics.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelThermometer;
import electrodynamics.interfaces.IHeatable;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.machine.TileEntityMachine;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

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
		
		int rotation = tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		
		switch (ForgeDirection.getOrientation(rotation)) {
		case NORTH:
			GL11.glRotatef(270, 0, 1, 0);
			break;
		case EAST:
			GL11.glRotatef(180, 0, 1, 0);
			break;
		case WEST:
			GL11.glRotatef(90, 0, 1, 0);
			break;
		default:
			break;
		}

		TileEntity tile = tileentity.worldObj.getBlockTileEntity(tileentity.xCoord, tileentity.yCoord - 1, tileentity.zCoord);
		
		if (tile != null && tile instanceof IHeatable) {
			IHeatable heat = (IHeatable) tile;
			final float CONSTANT = 230F / heat.getMaxHeat();
			
			this.model.rotateDial((float) Math.toRadians(CONSTANT * heat.getHeat()));
		} else {
			this.model.rotateDial((float) Math.toRadians(0D));
		}
		
		Textures.THERMOMETER.bind();
		this.model.render(0.0625F);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
