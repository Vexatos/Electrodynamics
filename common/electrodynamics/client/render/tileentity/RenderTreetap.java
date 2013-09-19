package electrodynamics.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.handler.IconHandler;
import electrodynamics.client.model.ModelLatexBucket;
import electrodynamics.client.model.ModelTreeTap;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.TileEntityTreetap;
import electrodynamics.util.render.RenderUtil;

public class RenderTreetap extends TileEntitySpecialRenderer {

	private ModelTreeTap modelTreetap;
	private ModelLatexBucket modelBucket;
	
	public RenderTreetap() {
		modelTreetap = new ModelTreeTap();
		modelBucket = new ModelLatexBucket();
	}

	public void renderTreetapAt(TileEntityTreetap tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x + 0.5, y + 0.7, z + 0.69);
		GL11.glRotatef(180, 1, 0, 0);
		
		switch(tile.rotation) {
			case EAST: {
				GL11.glRotatef(-90, 0, 1, 0);
				GL11.glTranslated(0.19, 0, 0.17);
				break;
			}
			case NORTH: {
				GL11.glRotatef(-180, 0, 1, 0);
				break;
			}
			case SOUTH: {
				GL11.glTranslated(0, 0, 0.37);
				break;
			}
			case WEST: {
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glTranslated(-0.19, 0, 0.17);
				break;
			}
			default: break;
		}

		Textures.TREETAP.bind();
		modelTreetap.render(0.0625F);
		
		if (tile.hasBucket) {
			renderBucket(tile);
			if (tile.liquidAmount > 0) {
				RenderUtil.bindBlockAtlas();
				
				GL11.glTranslated(-.75, 0.3, -.7);
				
				Icon latex = IconHandler.getInstance().getIcon("misc.liquidLatex");
				Tessellator tess = Tessellator.instance;
				tess.startDrawingQuads();
				tess.addVertexWithUV(.5, 0, .5, latex.getMinU(), latex.getMaxV());
				tess.addVertexWithUV(1, 0, .5, latex.getMaxU(), latex.getMaxV());
				tess.addVertexWithUV(1, 0, .9, latex.getMaxU(), latex.getMinV());
				tess.addVertexWithUV(.5, 0, .9, latex.getMinU(), latex.getMinV());
				
				tess.draw();
			}
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	public void renderBucket(TileEntityTreetap tile) {
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.84, 0);
		GL11.glRotatef(90, 0, 1, 0);
		
		ResourceLocation texture = (tile.liquidAmount == 1000 ? Textures.BUCKET_LATEX.resource : Textures.BUCKET.resource);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		modelBucket.render(0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
		if (((TileEntityTreetap)tile).rotation != null) {
			renderTreetapAt((TileEntityTreetap) tile, x, y, z, partial);
		}
	}
	
	
	
}
