package electrodynamics.util.render;

import net.minecraft.entity.EntityLiving;

public class EntityRenderUtil {

	public static float getLimbYaw(EntityLiving living, float partial) {
		return living.prevLimbYaw + (living.limbYaw - living.prevLimbYaw) * partial;
	}
	
	public static float getLimbSwing(EntityLiving living, float partial) {
		return living.limbSwing - living.limbYaw * (1.0F - partial);
	}
	
	public static float getPitch(EntityLiving living, float partial) {
		return living.prevRotationPitch + (living.rotationPitch - living.prevRotationPitch) * partial;
	}
	
	public static float interpolateRotation(float prevRotation, float rotation, float partial) {
		float f3;

		for (f3 = prevRotation - rotation; f3 < -180.0F; f3 += 360.0F) {
			;
		}

		while (f3 >= 180.0F) {
			f3 -= 360.0F;
		}

		return prevRotation + partial * f3;
	}
	
	public static float handleRotationFloat(EntityLiving living, float partial) {
        return living.ticksExisted + partial;
    }
	
}
