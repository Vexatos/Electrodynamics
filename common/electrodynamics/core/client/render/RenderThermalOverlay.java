package electrodynamics.core.client.render;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import electrodynamics.core.configuration.ConfigurationSettings;
import electrodynamics.core.core.EDLogger;
import electrodynamics.core.item.ItemHandler;
import electrodynamics.core.item.tesla.ItemArmorTeslaHelm;
import electrodynamics.core.util.EntityRenderUtil;
import electrodynamics.core.util.RenderUtil;

public class RenderThermalOverlay {

	@ForgeSubscribe
	public void onWorldRenderLast(RenderWorldLastEvent event) {
		GL11.glPushMatrix();
		Entity entity = event.context.mc.renderViewEntity;
		EntityPlayer player = (EntityPlayer) entity;
		RenderUtil.translateToWorldCoords(entity, event.partialTicks);

		if (player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].itemID == ItemHandler.itemTeslaHelm.itemID) {
			if (((ItemArmorTeslaHelm)player.inventory.armorInventory[3].getItem()).thermalEnabled) {
				renderEntityHighlight(entity, event.partialTicks);
			}
		}
		
		GL11.glPopMatrix();
	}

	@SuppressWarnings("unchecked")
	private void renderEntityHighlight(Entity entity, float partial) {
		World world = entity.worldObj;
		int range = (int) (ConfigurationSettings.THERMAL_VIEW_RANGE / 2);
		
		List<Entity> nearbyEntities = world.getEntitiesWithinAABB(EntityLiving.class, entity.boundingBox.expand(range, range, range));

		for (Entity ent : nearbyEntities) {
			if (ent == entity) return;
			
			if (ent instanceof EntityLiving) {
				EntityLiving entLiving = (EntityLiving) ent;
				
				GL11.glPushMatrix();
				GL11.glColor4f(1, 1, 1, 1);
				GL11.glTranslated(entLiving.posX, entLiving.posY + 1.6, entLiving.posZ);
				GL11.glRotatef(180, 1, 0, 0);
				GL11.glScaled(1.1, 1.1, 1.1);
				
				GL11.glDisable(GL11.GL_LIGHTING);
	            GL11.glDepthMask(false);
	            GL11.glDisable(GL11.GL_DEPTH_TEST);
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
				
	            GL11.glColor4f(0.5F, 0, 0, 1);
				
	            ModelBase entLivingModel = getModelForEntity(entLiving);
	            
	            //TODO need to get better control over the model rendering
	            float limbYaw = EntityRenderUtil.getLimbYaw(entLiving, partial);
	            float limbSwing = EntityRenderUtil.getLimbSwing(entLiving, partial);
	            float rotBody = EntityRenderUtil.interpolateRotation(entLiving.prevRenderYawOffset, entLiving.renderYawOffset, partial);
	            float rotHead = EntityRenderUtil.interpolateRotation(entLiving.prevRotationYawHead, entLiving.rotationYawHead, partial);
	            float pitch = EntityRenderUtil.getPitch(entLiving, partial);
	            float rotation2 = EntityRenderUtil.handleRotationFloat(entLiving, partial);
	            
	            if (entLiving.isChild()) {
	            	limbSwing *= 3.0F;
	            }

	            if (limbYaw > 1.0F) {
	            	limbYaw = 1.0F;
	            }
	            
	            GL11.glRotatef(rotBody, 0, 1, 0);
	            entLivingModel.setLivingAnimations(entLiving, limbYaw, limbSwing, partial);
	            entLivingModel.render(entLiving, limbYaw, limbSwing, rotation2, rotBody - rotHead, pitch, 0.0625F);
	            
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
	            GL11.glDepthMask(true);
	            GL11.glEnable(GL11.GL_LIGHTING);
	            
				GL11.glPopMatrix();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private ModelBase getModelForEntity(EntityLiving entity) {
		try {
			RenderLiving livingRender = (RenderLiving) RenderManager.instance.getEntityRenderObject(entity);
			Class livingRenderClass = RenderLiving.class;
			Field modelBaseField = livingRenderClass.getDeclaredField("mainModel");
			modelBaseField.setAccessible(true);
			ModelBase livingModel = (ModelBase) modelBaseField.get(livingRender);
			
			return livingModel;
		} catch(Exception ex) {
			EDLogger.warn("Failed to get the model for " + entity.getEntityName() + " when rendering thermal view. Reason: " + ex.toString());
		}
		
		return null;
	}
	
}