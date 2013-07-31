package electrodynamics.client.render.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import electrodynamics.api.render.ICustomRender;

public class RenderUtil {

	public static final double OFFSET_CONSTANT = 0.01;
	
	public static void bindTexture(ResourceLocation file) {
		Minecraft.getMinecraft().func_110434_K().func_110577_a(file);
	}
	
	public static void renderEntityItem(World world, ItemStack stack, boolean forceFancy) {
		if (stack != null) {
			//Incredibly hackish, but better than essentially writing out a copy of the EntityItem renderer
			boolean fancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
			if (forceFancy) {
				Minecraft.getMinecraft().gameSettings.fancyGraphics = true;
			}
			
			ICustomRender renderer = null;
			if (stack.getItem() instanceof ICustomRender) {
				renderer = (ICustomRender) stack.getItem();
			}
			
			renderer.glManipulation();
			
			if (renderer.getCustomModel() != null) {
				renderer.getCustomModel().render(0.0625F);
			} else {
				EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
				entityitem.getEntityItem().stackSize = 1;
				entityitem.hoverStart = 0.0F;
				RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			}
			
			if (forceFancy) {
				Minecraft.getMinecraft().gameSettings.fancyGraphics = fancy;
			}
		}
	}
	
	public static void translateToWorldCoords(Entity entity, float frame) {       
        double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
        double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
        double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;
        
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
    }
	
	public static void drawCubeAt(int x, int y, int z) {
		for (ForgeDirection face : ForgeDirection.VALID_DIRECTIONS) {
			drawQuadOnFace(x, y, z, face);
		}
	}
	
	public static void drawQuadOnFace(int x, int y, int z, ForgeDirection face) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
		switch(face) {
			case UP: {
				tess.addVertex(x, y + 1 + OFFSET_CONSTANT, z);
				tess.addVertex(x, y + 1 + OFFSET_CONSTANT, z + 1);
				tess.addVertex(x + 1, y + 1 + OFFSET_CONSTANT, z + 1);
				tess.addVertex(x + 1, y + 1 + OFFSET_CONSTANT, z);
				
			}
			
			case DOWN: {
				tess.addVertex(x + 1, y - OFFSET_CONSTANT, z);
				tess.addVertex(x + 1, y - OFFSET_CONSTANT, z + 1);
				tess.addVertex(x,     y - OFFSET_CONSTANT, z + 1);
				tess.addVertex(x,     y - OFFSET_CONSTANT, z);
			}
			
			case EAST: {
				tess.addVertex(x + 1 + OFFSET_CONSTANT, y,     z);
				tess.addVertex(x + 1 + OFFSET_CONSTANT, y + 1, z);
				tess.addVertex(x + 1 + OFFSET_CONSTANT, y + 1, z + 1);
				tess.addVertex(x + 1 + OFFSET_CONSTANT, y,     z + 1);
			}
			
			case NORTH: {
				tess.addVertex(x,     y,     z - OFFSET_CONSTANT);
				tess.addVertex(x,     y + 1, z - OFFSET_CONSTANT);
				tess.addVertex(x + 1, y + 1, z - OFFSET_CONSTANT);
				tess.addVertex(x + 1, y,     z - OFFSET_CONSTANT);
			}
			
			case SOUTH: {
				tess.addVertex(x + 1, y,     z + 1 + OFFSET_CONSTANT);
				tess.addVertex(x + 1, y + 1, z + 1 + OFFSET_CONSTANT);
				tess.addVertex(x,     y + 1, z + 1 + OFFSET_CONSTANT);
				tess.addVertex(x,     y,     z + 1 + OFFSET_CONSTANT);
			}
			
			case WEST: {
				tess.addVertex(x - OFFSET_CONSTANT, y,     z + 1);
				tess.addVertex(x - OFFSET_CONSTANT, y + 1, z + 1);
				tess.addVertex(x - OFFSET_CONSTANT, y + 1, z);
				tess.addVertex(x - OFFSET_CONSTANT, y,     z);
			}
			
			default: break;
		}
		
		tess.draw();
	}
	
	public static void drawTooltip(String[] text, int x, int y, int guiWidth, int guiHeight, FontRenderer font) {
		if (text != null && text.length > 0) {
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int maxWidth = 0;

			for (String string : text) {
				if (font.getStringWidth(string) > maxWidth) {
					maxWidth = font.getStringWidth(string);
				}
			}
			
			int i1 = x + 12;
			int j1 = y - 12;
			int k1 = 8;

			if (text.length > 1) {
				k1 += 2 + (text.length - 1) * 10;
			}

			if (i1 + maxWidth > guiWidth) {
				i1 -= 28 + maxWidth;
			}

			if (j1 + k1 + 6 > guiHeight) {
				j1 = guiHeight - k1 - 6;
			}

			int l1 = -267386864;
			drawGradientRect(i1 - 3, j1 - 4, i1 + maxWidth + 3, j1 - 3, l1, l1);
			drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + maxWidth + 3, j1 + k1 + 4, l1, l1);
			drawGradientRect(i1 - 3, j1 - 3, i1 + maxWidth + 3, j1 + k1 + 3, l1, l1);
			drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
			drawGradientRect(i1 + maxWidth + 3, j1 - 3, i1 + maxWidth + 4, j1 + k1 + 3, l1, l1);
			int i2 = 1347420415;
			int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
			drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
			drawGradientRect(i1 + maxWidth + 2, j1 - 3 + 1, i1 + maxWidth + 3, j1 + k1 + 3 - 1, i2, j2);
			drawGradientRect(i1 - 3, j1 - 3, i1 + maxWidth + 3, j1 - 3 + 1, i2, i2);
			drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + maxWidth + 3, j1 + k1 + 3, j2, j2);

			for (int k2 = 0; k2 < text.length; ++k2) {
				String s1 = text[k2];
				font.drawStringWithShadow(s1, i1, j1, -1);

				if (k2 == 0) {
					j1 += 2;
				}

				j1 += 10;
			}

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	public static void drawGradientRect(int x, int y, int w, int h, int color1, int color2) {
		float f0 = (color1 >> 24 & 255) / 255.0F;
		float f1 = (color1 >> 16 & 255) / 255.0F;
		float f2 = (color1 >> 8 & 255) / 255.0F;
		float f3 = (color1 & 255) / 255.0F;
		float f4 = (color2 >> 24 & 255) / 255.0F;
		float f5 = (color2 >> 16 & 255) / 255.0F;
		float f6 = (color2 >> 8 & 255) / 255.0F;
		float f7 = (color2 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f1, f2, f3, f0);
		tessellator.addVertex(w, y, 1.0D);
		tessellator.addVertex(x, y, 1.0D);
		tessellator.setColorRGBA_F(f5, f6, f7, f4);
		tessellator.addVertex(x, h, 1.0D);
		tessellator.addVertex(w, h, 1.0D);
		tessellator.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void drawIcon(int par1, int par2, Icon par3Icon, int par4, int par5) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(par1 + 0, par2 + par5, 0, par3Icon.getMinU(), par3Icon.getMaxV());
        tessellator.addVertexWithUV(par1 + par4, par2 + par5, 0, par3Icon.getMaxU(), par3Icon.getMaxV());
        tessellator.addVertexWithUV(par1 + par4, par2 + 0, 0, par3Icon.getMaxU(), par3Icon.getMinV());
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, 0, par3Icon.getMinU(), par3Icon.getMinV());
        tessellator.draw();
    }
	
}
