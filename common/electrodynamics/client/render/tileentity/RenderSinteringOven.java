package electrodynamics.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelChicken;
import electrodynamics.client.model.ModelIngot;
import electrodynamics.client.model.ModelMetalTray;
import electrodynamics.client.model.ModelSinteringOven;
import electrodynamics.item.ItemIngot;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.machine.TileEntityMachine;
import electrodynamics.tileentity.machine.TileEntitySinteringOven;
import electrodynamics.util.InventoryUtil;
import electrodynamics.util.render.RenderUtil;

public class RenderSinteringOven extends TileEntitySpecialRenderer {

	private ModelSinteringOven modelSinteringOven;
	private ModelMetalTray modelMetalTray;
	private ModelChicken modelChicken;
	private ModelIngot modelIngot;
	
	private final boolean chickenEasterEgg = true;
	
	public RenderSinteringOven() {
		this.modelSinteringOven = new ModelSinteringOven();
		this.modelMetalTray = new ModelMetalTray();
		this.modelChicken = new ModelChicken();
		this.modelIngot = new ModelIngot();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotatef(180, 0, 0, 1);

		if (((TileEntityMachine) tile).rotation != null  ) {
			switch (((TileEntityMachine) tile).rotation) {
			case NORTH:
				GL11.glRotatef(270, 0, 1, 0);
				break;
			case SOUTH:
				GL11.glRotatef(90, 0, 1, 0);
				break;
			case WEST:
				GL11.glRotatef(180, 0, 1, 0);
				break;
			case EAST:
				// GL11.glRotatef(0, 0, 1, 0);
				break;
			default:
				break;
			}
		}

		Textures.SINTERING_OVEN.bind();
		
		modelSinteringOven.rotateDoor(((TileEntitySinteringOven)tile).doorAngle);
		modelSinteringOven.renderAll(0.0625F);

		if (((TileEntitySinteringOven)tile).trayInventory != null) {
			renderTray(tile.worldObj, ((TileEntitySinteringOven)tile).trayInventory.inventory);
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	public void renderTray(World world, ItemStack[] inv) {
		GL11.glTranslated(0, -0.5, 0);
		GL11.glRotatef(90, 0, 1, 0);
		
		Textures.METAL_TRAY.bind();
		modelMetalTray.render(0.0625F);

		if (inv != null && inv.length > 0) {
			ItemStack first = InventoryUtil.getFirstItemInArray(inv);
			
			if (first != null) {
				if (ItemIngot.isIngot(first) && InventoryUtil.containsOnly(inv, first)) {
					renderIngot(world, InventoryUtil.getFirstItemInArray(inv));
				} else if ((first.getItem() == Item.chickenRaw && InventoryUtil.containsOnly(inv, first)) || (first.getItem() == Item.chickenCooked && InventoryUtil.containsOnly(inv, first))) {
					renderChicken(world, InventoryUtil.getFirstItemInArray(inv));
				} else {
					GL11.glTranslated(-.11, 1.35, .165);
					GL11.glScaled(.4, .4, .4);
					
					for (int i=0; i<inv.length; i++) {
						ItemStack stack = inv[i];

						if (i != 0) {
							GL11.glTranslated(.28, 0, 0);
						}
						
						if (i == 3 || i == 6) {
							GL11.glTranslated(-.84, 0, -.38);
						}
						
						if (stack != null) {
							if (!(stack.getItem() instanceof ItemBlock)) {
								GL11.glPushMatrix();
								GL11.glScaled(.8, .8, .8);
								GL11.glRotatef(90, 1, 0, 0);
								GL11.glTranslated(0, -.24, -.26);
								
								RenderUtil.renderEntityItem(world, stack, true);
								
								GL11.glPopMatrix();
							} else {
								GL11.glPushMatrix();
								GL11.glRotatef(180, 0, 0, 1);
								GL11.glTranslated(0, -.2, 0);
								
								RenderUtil.renderEntityItem(world, stack, true);
								
								GL11.glPopMatrix();
							}
						}
					}
				}
			}
		}
	}
	
	public void renderIngot(World world, ItemStack stack) {
		ItemIngot.getColorForIngot(stack).apply();
		GL11.glTranslated(0, 1.3, 0);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glTranslated(-.218, 0, -.09);
		
		Textures.INGOT.bind();
		this.modelIngot.render(0.0625F);
	}
	
	public void renderChicken(World world, ItemStack stack) {
		if (chickenEasterEgg) {
			if (stack.getItem() == Item.chickenRaw) {
				Textures.CHICKEN_RAW.bind();
			} else if (stack.getItem() == Item.chickenCooked) {
				Textures.CHICKEN_COOKED.bind();
			}
			
			this.modelChicken.render(0.0625F);
			return;
		}
	}
	
	public void renderItem(World world, ItemStack stack) {
		if (stack != null) {
			//Incredibly hackish, but better than essentially writing out a copy of the EntityItem renderer
			boolean fancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
			Minecraft.getMinecraft().gameSettings.fancyGraphics = true;
			
			EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
			entityitem.getEntityItem().stackSize = 1;
			entityitem.hoverStart = 0.0F;
			RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			
			Minecraft.getMinecraft().gameSettings.fancyGraphics = fancy;
		}
	}
	
}
