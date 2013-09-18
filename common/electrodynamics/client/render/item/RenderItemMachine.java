package electrodynamics.client.render.item;

import electrodynamics.client.model.ModelBasicKiln;
import electrodynamics.lib.block.Machine;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import electrodynamics.client.model.ModelBasicSieve;
import electrodynamics.client.model.ModelSinteringOven;
import electrodynamics.lib.client.Textures;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemMachine implements IItemRenderer {

	private ModelSinteringOven modelSinteringOven;
	private ModelBasicSieve modelBasicSieve;
	private ModelBasicKiln modelBasicKiln;
	
	public RenderItemMachine() {
		this.modelSinteringOven = new ModelSinteringOven();
		this.modelBasicSieve = new ModelBasicSieve();
		this.modelBasicKiln = new ModelBasicKiln();
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
		if (item.getItemDamage() == 0) {
			switch(type) {
				case ENTITY: {
					renderOven(0F, 1F, 0F);
					break;
				}

				case EQUIPPED: {
					renderOven(1.0F, 1.0F, 1.0F);
					break;
				}

				case EQUIPPED_FIRST_PERSON: {
					renderOven(1.0F, 2.0F, 1.0F);
					break;
				}
				
				case INVENTORY: {
					renderOven(0F, 1F, 0F);
					break;
				}
				
				default: break;
			}
		} else if (item.getItemDamage() == 1) {
			switch(type) {
				case ENTITY: {
					renderSieve(0F, 1F, 0F);
					break;
				}
				
				case EQUIPPED: {
					renderSieve(1.0F, 1.0F, 1.0F);
					break;
				}

				case EQUIPPED_FIRST_PERSON: {
					renderSieve(1.0F, 2.0F, 1.0F);
					break;
				}

				case INVENTORY: {
					renderSieve(0F, 1F, 0F);
					break;
				}
				
				default: break;
			}
		} else if( item.getItemDamage() == Machine.BASIC_KILN.ordinal() ) {
			switch(type) {
				case ENTITY: {
					renderKiln(0F, 1F, 0F);
					break;
				}

				case EQUIPPED: {
					renderKiln(1.0F, 1.0F, 1.0F);
					break;
				}

				case EQUIPPED_FIRST_PERSON: {
					renderKiln(1.0F, 2.0F, 1.0F);
					break;
				}

				case INVENTORY: {
					renderKiln(0F, 1F, 0F);
					break;
				}

				default: break;
			}
		}
	}

	private void renderOven(float x, float y, float z) {
		Textures.SINTERING_OVEN.bind();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		modelSinteringOven.render(0.0625F);
		GL11.glPopMatrix();
	}
	
	private void renderSieve(float x, float y, float z) {
		Textures.BASIC_SIEVE.bind();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		modelBasicSieve.render(0.0625F);
		GL11.glPopMatrix();
	}

	private void renderKiln(float x, float y, float z) {
		Textures.BASIC_KILN.bind();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		modelBasicKiln.render(0.0625F);
		GL11.glPopMatrix();
	}
	
}
