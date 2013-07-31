package electrodynamics.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelTable;
import electrodynamics.client.model.ModelTableStone;
import electrodynamics.client.model.ModelTableWood;
import electrodynamics.client.render.util.RenderUtil;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.machine.TileEntityTable;

public class RenderTable extends TileEntitySpecialRenderer {

	private ModelTable[] tables = new ModelTable[2];
	
	private ResourceLocation[] textures = new ResourceLocation[] {Textures.TABLE_WOOD.resource, Textures.TABLE_STONE.resource};

	public RenderTable() {
		tables[0] = new ModelTableWood();
		tables[1] = new ModelTableStone();
	}
	
	public void renderTableContentsAt(TileEntityTable table) {
		ItemStack itemstack = table.displayedItem;

		if (itemstack != null) {
			byte type = (byte)table.worldObj.getBlockMetadata(table.xCoord, table.yCoord, table.zCoord);
			renderItem(table.worldObj, itemstack, type);
		}
	}

	private void renderItem(World world, ItemStack stack, byte type) {
		GL11.glRotatef(180, 1, 0, 0);
		if (stack.getItem() instanceof ItemBlock) {
			GL11.glScaled(2.25, 2.25, 2.25);
			
			if (type == 0) {
				GL11.glTranslated(0, -.33, 0);
			} else if (type == 1) {
				GL11.glTranslated(0, -.25, 0);
			}
		} else {
			GL11.glTranslated(0, .25, 0);
			GL11.glRotated(90, 1, 0, 0);
			GL11.glTranslated(0, -.25, 0);
			GL11.glTranslated(0, 0, .27);
			
			if (type == 0) {
				GL11.glTranslated(0, 0, .76);
			} else if (type == 1) {
				GL11.glTranslated(0, 0, .584);
			}
		}
		
		RenderUtil.renderEntityItem(world, stack, true);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotatef(180, 0, 0, 1);
		
		byte type = (byte)tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);

		Minecraft.getMinecraft().func_110434_K().func_110577_a(textures[type]);
		tables[type].render(0.0625F);
		
		renderTableContentsAt((TileEntityTable)tile);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
