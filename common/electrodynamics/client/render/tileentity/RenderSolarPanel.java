package electrodynamics.client.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelSolarPanel;
import electrodynamics.client.render.util.RenderUtil;
import electrodynamics.interfaces.energy.IEnergyConnection;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.machine.energy.TileEntitySolarPanel;

public class RenderSolarPanel extends TileEntitySpecialRenderer {

	private ModelSolarPanel solarPanel;
	
	public RenderSolarPanel() {
		this.solarPanel = new ModelSolarPanel();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		GLRotation[] rotations = null;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(d0 + 0.5, d1 + 1.5, d2 + 0.5);
		
		GL11.glRotatef(180, 1, 0, 0);
		
		RenderUtil.bindTexture(Textures.SOLAR_PANEL.resource);
		
		float rotation = ((TileEntitySolarPanel)tileentity).currAngle * 100;
		float vertOffset = ((TileEntitySolarPanel)tileentity).vertOffset;
		
		ForgeDirection attachedSide = ((TileEntitySolarPanel)tileentity).attached;
		
		GL11.glTranslatef(0, 0.75F, 0);
		GL11.glRotatef(rotation, 0, 0, 1);
		GL11.glTranslatef(0, -0.75F, 0);
		GL11.glTranslated(0, vertOffset, 0);
		this.solarPanel.renderPanel(0.0625F);
		GL11.glTranslated(0, -vertOffset, 0);
		GL11.glTranslatef(0, 0.75F, 0);
		GL11.glRotatef(-rotation, 0, 0, 1);
		GL11.glTranslatef(0, -0.75F, 0);
		this.solarPanel.renderPipe(0.0625F);

		if (((TileEntitySolarPanel)tileentity).showStand) {
			GL11.glTranslatef(0, 1F, 0);
			rotations = getGLRotations(attachedSide);
			if (rotations != null && rotations.length > 0) {
				for (GLRotation glRotation : getGLRotations(attachedSide)) {
					glRotation.apply(false);
				}
			}
			GL11.glTranslatef(0, -1F, 0);
			this.solarPanel.renderPipeBase(0.0625F);
			this.solarPanel.renderPipeBaseConnector(0.0625F);
			GL11.glTranslatef(0, 1F, 0);
			if (rotations != null && rotations.length > 0) {
				for (GLRotation glRotation : getGLRotations(attachedSide)) {
					glRotation.apply(true);
				}
			}
			GL11.glTranslatef(0, -1F, 0);
		}
		
		for (ForgeDirection forgeDir : ForgeDirection.VALID_DIRECTIONS) {
			if (forgeDir != ForgeDirection.UP && forgeDir != ForgeDirection.DOWN && forgeDir != attachedSide.getOpposite()) {
				TileEntity tile = tileentity.worldObj.getBlockTileEntity(tileentity.xCoord + forgeDir.offsetX, tileentity.yCoord + forgeDir.offsetY, tileentity.zCoord + forgeDir.offsetZ);
				
				if (tile != null && tile instanceof IEnergyConnection) {
					GL11.glTranslatef(0, 1F, 0);
					rotations = getGLRotations(forgeDir);
					
					for (GLRotation glRotation : rotations) {
						glRotation.apply(false);
					}
					GL11.glTranslatef(0, -1F, 0);
					
					this.solarPanel.renderPipeBase(0.0625F);
					this.solarPanel.renderPipeBaseConnector(0.0625F);
					
					GL11.glTranslatef(0, 1F, 0);
					rotations = getGLRotations(forgeDir);

					for (GLRotation glRotation : rotations) {
						glRotation.apply(true);
					}
					GL11.glTranslatef(0, -1F, 0);
				}
			}
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	private GLRotation[] getGLRotations(ForgeDirection dir) {
		GLRotation[] rotations = null;
		
		if (dir != null) {
			switch(dir) {
			case EAST: {
				rotations = new GLRotation[] {new GLRotation(-90, 0, 0, 1)};
				break;
			}
			case WEST: {
				rotations = new GLRotation[] {new GLRotation(90, 0, 0, 1)};
				break;
			}
			case NORTH: {
				rotations = new GLRotation[] {new GLRotation(90, 1, 0, 0)};
				break;
			}
			case SOUTH: {
				rotations = new GLRotation[] {new GLRotation(-90, 1, 0, 0)};
				break;
			}
			default: break;
			}
		}
		
		return rotations;
	}
	
	private class GLRotation {
		public int x;
		public int y;
		public int z;
		
		public float angle;
		
		public GLRotation(float angle, int x, int y, int z) {
			this.angle = angle;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public void apply(boolean reverse) {
			GL11.glRotatef(reverse == true ? -angle : angle, x, y, z);
		}
	}
	
}
