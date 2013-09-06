package electrodynamics.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import electrodynamics.api.render.ModelTechne;
import electrodynamics.client.model.ModelMobGrinder;
import electrodynamics.client.model.ModelSinteringFurnace;
import electrodynamics.lib.block.StructureComponent;
import electrodynamics.lib.client.Textures;
import electrodynamics.mbs.MultiBlockStructure;
import electrodynamics.tileentity.structure.TileEntityStructure;
import electrodynamics.tileentity.structure.TileEntityStructure.TileStructurePlaceHolder;

public class RenderTileStructure extends TileEntitySpecialRenderer {

	private ModelTechne sintFurnace;
	private ModelTechne mobGrinder;
	
	private int mobGrinderBladeRotation = 0;
	
	public RenderTileStructure() {
		this.sintFurnace = new ModelSinteringFurnace();
		this.mobGrinder = new ModelMobGrinder();
	}
	
	public void renderStructureBlockAt(TileEntityStructure structure, double x, double y, double z, float partial) {
		if ((structure).isCentralTileEntity()) {
			renderMBSAt(structure, x, y, z, partial);
		} else {
			if (structure.isValidStructure()) return;
			
			StructureComponent component = StructureComponent.values()[structure.getSubBlock()];
			
			if (component.getModel() != null) {
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				
				GL11.glTranslated(x + .5, y + .5, z + .5);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(component.getModelTexture());
				component.applyGLTransformations((byte) 0, structure);
				if (component.alternativeRender()) {
					component.getModel().render(0.0625F);
				}
				
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
			}
		}
	}

	public void renderMBSAt(TileEntityStructure structure, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x, y, z);
		
		MultiBlockStructure mbs = structure.getMBS();
		if (mbs != null) {
			if (mbs.getUID().equals("MobGrinder")) {
				if (structure instanceof TileStructurePlaceHolder) {
					TileStructurePlaceHolder ph = (TileStructurePlaceHolder) structure;

					if (ph.clientNBT != null && ph.clientNBT.hasKey("active")) {
						if (ph.clientNBT.getBoolean("active")) {
							this.mobGrinderBladeRotation++;
							
							if (this.mobGrinderBladeRotation > 360) {
								this.mobGrinderBladeRotation = 0;
							}
						}
					}
				}
				
				Textures.MOB_GRINDER_CLEAN.bind();
				
				GL11.glTranslated(.5, 1.5, .5);
				GL11.glRotatef(180, 1, 0, 0);
				int rotation = structure.getRotation();
				GL11.glRotatef(90 * (rotation / 2), 0, 1, 0);
				
				((ModelMobGrinder)this.mobGrinder).rotateBlades(mobGrinderBladeRotation);
				
				GL11.glColor4f(1, 1, 1, 1);
				this.mobGrinder.render(0.0625F);
			} else if (mbs.getUID().equals("SintFurnace")) {
				Textures.SINTERING_FURNACE.bind();
				
				GL11.glTranslated(.5, 1.5, .5);
				GL11.glRotatef(180, 1, 0, 0);
				int rotation = structure.getRotation();
				GL11.glRotatef(90 * (rotation / 2), 0, 1, 0);
				
				GL11.glColor4f(1, 1, 1, 1);
				this.sintFurnace.render(0.0625F);
			}
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderStructureBlockAt((TileEntityStructure) tileentity, d0, d1, d2, f);
	}

}
