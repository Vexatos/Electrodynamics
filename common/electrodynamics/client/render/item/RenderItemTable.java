package electrodynamics.client.render.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import electrodynamics.client.model.ModelTableStone;
import electrodynamics.client.model.ModelTable;
import electrodynamics.client.model.ModelTableWood;
import electrodynamics.lib.client.Textures;

public class RenderItemTable implements IItemRenderer {

	private ModelTable[] tables = new ModelTable[2];
	
	private ResourceLocation[] textures = new ResourceLocation[] {Textures.TABLE_WOOD.resource, Textures.TABLE_STONE.resource};

	public RenderItemTable() {
		tables[0] = new ModelTableWood();
		tables[1] = new ModelTableStone();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
			case ENTITY: {
				renderTable((byte) item.getItemDamage(), 0F, 1F, 0F);
				break;
			}
			
			case EQUIPPED: {
				renderTable((byte) item.getItemDamage(), 1.0F, 1.0F, 1.0F);
				break;
			}

			case EQUIPPED_FIRST_PERSON: {
				renderTable((byte) item.getItemDamage(), 1.0F, 2.0F, 1.0F);
				break;
			}
			
			case INVENTORY: {
				renderTable((byte) item.getItemDamage(), 0F, 1F, 0F);
				break;
			}
			
			default: break;
		}
	}

	private void renderTable(byte type, float x, float y, float z) {
		FMLClientHandler.instance().getClient().func_110434_K().func_110577_a(textures[type]);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		tables[type].render(0.0625F);
		GL11.glPopMatrix();
	}
	
}
