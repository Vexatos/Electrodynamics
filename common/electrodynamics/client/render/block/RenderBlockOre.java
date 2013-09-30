package electrodynamics.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import electrodynamics.block.BlockOre;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;
import electrodynamics.util.BlockUtil;
import electrodynamics.util.render.GLColor;

public class RenderBlockOre extends BlockRenderer implements ISimpleBlockRenderingHandler {

	public static int renderID;
	
	static {
		renderID = RenderingRegistry.getNextAvailableRenderId();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
		
		if ((metadata == Ore.VOIDSTONE.ordinal())) {
			Tessellator t = Tessellator.instance;
			t.setBrightness(320);

			block.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
			drawFaces(renderer, block, ((BlockOre) block).voidstoneTexture, true);
			
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBoundsFromBlock(block);
			drawFaces(renderer, block, ((BlockOre) block).oreTransparency, true);
		} else {
			drawFaces(renderer, block, ((BlockOre) block).textures[metadata], true);
		}
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		setBrightness(world, x, y, z, block);
		int metadata = world.getBlockMetadata(x, y, z);
	    
		if ((metadata == Ore.VOIDSTONE.ordinal())) {
			for (int i=1; i<=20; i++) {
				final float adjustConstant = 0.001F;
				block.setBlockBounds(0F + (i * adjustConstant), 0F + (i * adjustConstant), 0F + (i * adjustConstant), 1F - (i * adjustConstant), 1F - (i * adjustConstant), 1F - (i * adjustConstant));
				renderer.setRenderBoundsFromBlock(block);
				renderAllSides(world, x, y, z, block, renderer, ((BlockOre)block).darkOre);
			}

			Tessellator t = Tessellator.instance;
			t.setBrightness(320);
			
			block.setBlockBounds(0.02F, 0.02F, 0.02F, 0.98F, 0.98F, 0.98F);
			renderer.setRenderBoundsFromBlock(block);
			renderAllSides(world, x, y, z, block, renderer, ((BlockOre) block).voidstoneTexture);
		}

	    block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	    renderer.setRenderBoundsFromBlock(block);

	    renderer.renderStandardBlock(block, x, y, z);
	    
	    if (metadata != Ore.VOIDSTONE.ordinal()) {
	    	renderAllSides(world, x, y, z, block, renderer, ((BlockOre)block).oreOnly[metadata]);
	    }
	    
	    renderer.clearOverrideBlockTexture();
	    block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	    renderer.setRenderBoundsFromBlock(block);
		
	    return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

}
