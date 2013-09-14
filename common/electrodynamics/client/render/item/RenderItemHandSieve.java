package electrodynamics.client.render.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelHandSieve;
import electrodynamics.item.EDItems;
import electrodynamics.lib.client.Textures;

public class RenderItemHandSieve implements IItemRenderer {

	private static final int SHAKE_MAX = 10;
	private static final int SHAKE_MIN = -SHAKE_MAX;
	
	private ModelHandSieve model; //TEMP
	
	private int shakeProgress = 0;
	
	private boolean increase = true;
	
	public RenderItemHandSieve() {
		this.model = new ModelHandSieve();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
		case ENTITY: {
			renderHandSieve(0F, 1.35F, 0F, null, false);
			break;
		}

		case EQUIPPED: {
			GL11.glScaled(2, 2, 2);
			renderHandSieve(0.25F, 1.50F, 0.25F, (EntityPlayer) data[1], false);
			break;
		}

		case EQUIPPED_FIRST_PERSON: {
			GL11.glScaled(2, 2, 2);
			GL11.glRotated(15, 0, 0, 1);
			renderHandSieve(.3F, 1.8F, .3F, (EntityPlayer) data[1], true);
			break;
		}

		case INVENTORY: {
			// MESS OF DOOM
			GL11.glScaled(2, 2, 2);
			GL11.glRotated(90, 1, 0, 0);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(15, 0, 1, 0);
			GL11.glRotated(90, 1, 0, 0);
			GL11.glRotated(-70, 0, 0, 1);
			GL11.glTranslated(0, 0.10, 0);
			renderHandSieve(0F, 1.25F, 0F, null, false);
			break;
		}

		default: break;
	}
	}

	private void renderHandSieve(float x, float y, float z, EntityPlayer player, boolean animate) {
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 1, 0, 0);
		
		if (animate) {
			if (player != null) {
				if (player.getItemInUse() != null && player.getItemInUse().getItem() == EDItems.itemHandheldSieve) {
					if (increase) {
						shakeProgress += 4;
						
						if (shakeProgress >= SHAKE_MAX) {
							increase = false;
						}
					} else if (!increase) {
						shakeProgress -= 4;
						
						if (shakeProgress <= SHAKE_MIN) {
							increase = true;
						}
					}
					
					GL11.glTranslatef(0.01F * this.shakeProgress, 0, 0.01F * this.shakeProgress);
				} else {
					this.shakeProgress = 0;
					this.increase = true;
				}
			}
		}
		
		Textures.HANDSIEVE.bind();
		this.model.render(0.0625F);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();
	}

}
