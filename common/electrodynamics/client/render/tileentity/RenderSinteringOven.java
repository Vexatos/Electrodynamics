package electrodynamics.client.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.model.ModelDust;
import electrodynamics.client.model.ModelIngot;
import electrodynamics.client.model.ModelMetalTray;
import electrodynamics.client.model.ModelSinteringOven;
import electrodynamics.item.ItemDust;
import electrodynamics.item.ItemIngot;
import electrodynamics.lib.client.Textures;
import electrodynamics.tileentity.machine.TileEntityMachine;
import electrodynamics.tileentity.machine.TileEntitySinteringOven;
import electrodynamics.util.InventoryUtil;
import electrodynamics.util.render.GLColor;
import electrodynamics.util.render.IconUtil;

public class RenderSinteringOven extends TileEntitySpecialRenderer {

	private ModelSinteringOven modelSinteringOven;
	private ModelMetalTray modelMetalTray;
	private ModelIngot modelIngot;
	private ModelDust modelDust;
	
	private boolean renderedTray = false;
	private boolean renderedIngot = false;
	private boolean renderedDust = false;
	
	public RenderSinteringOven() {
		this.modelSinteringOven = new ModelSinteringOven();
		this.modelMetalTray = new ModelMetalTray();
		this.modelIngot = new ModelIngot();
		this.modelDust = new ModelDust();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
		renderedTray = false;
		renderedIngot = false;
		renderedDust = false;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
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
			default:
				break;
			}
		}

		if (((TileEntitySinteringOven)tile).trayInventory != null) {
			renderedTray = true;
			renderTray(tile.worldObj, ((TileEntitySinteringOven)tile).trayInventory.inventory);
		}

		if (renderedTray) {
			GL11.glTranslated(0, 0.5, 0);
			GL11.glRotatef(-90, 0, 1, 0);
		}
		
		if (renderedIngot) {
			GL11.glRotated(90, 0, 1, 0);
			GL11.glTranslated(0.225, -1.3, 0.095);
		}
		
		if (renderedDust) {
			GL11.glTranslated(0, 0.046875, 0);
		}
		
		GLColor.WHITE.apply();
		Textures.SINTERING_OVEN.bind();
		modelSinteringOven.rotateDoor(Math.toRadians(((TileEntitySinteringOven)tile).doorAngle));
		modelSinteringOven.render(0.0625F);
		
		GL11.glDisable(GL11.GL_BLEND);
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
					renderedIngot = true;
					renderIngot(first);
				} else if (ItemDust.isDust(first) && InventoryUtil.containsOnly(inv, first)) {
					renderedDust = true;
					renderDust(InventoryUtil.getFirstItemInArray(inv));
				}
			}
		}
	}
	
	public void renderIngot(ItemStack stack) {
		IconUtil.getCachedColor(stack).apply();
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glTranslated(-0.225, 1.3, -0.095);
		
		Textures.INGOT.bind();
		this.modelIngot.render(0.0625F);
	}

	public void renderDust(ItemStack stack) {
		IconUtil.getCachedColor(stack).apply();
		GL11.glTranslated(0, -0.046875, 0);
		
		Textures.DUST.bind();
		this.modelDust.render(0.0625F);
	}
	
}
