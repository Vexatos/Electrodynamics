package electrodynamics.client.render.handler;

import java.util.List;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import electrodynamics.lib.item.ArmorModule;
import electrodynamics.util.render.RenderUtil;

public class XRayOverlayHandler {

	public static boolean enabled = false;
	
	@ForgeSubscribe
	public void onWorldRenderLast(RenderWorldLastEvent event) {
		GL11.glPushMatrix();
		Entity entity = event.context.mc.renderViewEntity;
		EntityPlayer player = (EntityPlayer)entity;
		
		if (ArmorModule.XRAY.hasModule(player)) {
			RenderUtil.translateToWorldCoords(entity, event.partialTicks);
			renderEntityHighlight(entity, event.partialTicks);
		}
		
		GL11.glPopMatrix();
	}

	@SuppressWarnings("unchecked")
	private void renderEntityHighlight(Entity entity, float partial) {
		GL11.glDisable(GL11.GL_LIGHTING);
		
		World world = entity.worldObj;
		int range = 24 / 2;
		
		List<Entity> nearbyEntities = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(range, range, range));

		for (Entity ent : nearbyEntities) {
			if (ent instanceof EntityLiving) {
				EntityLiving entLiving = (EntityLiving) ent;
				
				GL11.glPushMatrix();
				GL11.glTranslated(entLiving.posX, entLiving.posY, entLiving.posZ);
				
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glLineWidth(2.0F);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_CULL_FACE);
				
	            GL11.glDepthMask(false);
	            GL11.glDisable(GL11.GL_DEPTH_TEST);
				
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		        GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
		        GL11.glPolygonOffset(-1.0F, -1.0F);

		        // To prevent color resetting when rendering entity model.
		        // Blocks out all colors except the ones we want
		        // Prevents mobs rendering as white, etc
		        GL11.glColorMask(false, true, true, false);
		        
		        // For the few mobs that don't reset the color
		        GL11.glColor4f(1, 1, 1, 1);
		        
		        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
				RenderManager.instance.getEntityRenderObject(entLiving).doRender(entLiving, 0, 0, 0, 0, partial);
				
				GL11.glColorMask(true, true, true, true);
				
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			    GL11.glEnable(GL11.GL_CULL_FACE);
			    GL11.glEnable(GL11.GL_TEXTURE_2D);
			    GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
	            GL11.glDepthMask(true);
				
				GL11.glPopMatrix();
			}
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
	}

}

