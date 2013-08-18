package electrodynamics.util.misc;

import java.lang.reflect.Method;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import electrodynamics.Electrodynamics;
import electrodynamics.core.EDLogger;

public class EntityReflectionWrapper {

	/* CONSTANTS */
	public static final String OBF_DROP_ID = "func_70633_aT";
	public static final String DROP_ID = "getDropItemId";
	public static final String OBF_DEATH_SOUND = "func_70673_aS";
	public static final String DEATH_SOUND = "getDeathSound";
	
	private EntityLivingBase entity;
	
	public EntityReflectionWrapper(EntityLivingBase entity) {
		this.entity = entity;
	}
	
	public int getMainDropID() {
		try {
			Method dropID = EntityLiving.class.getDeclaredMethod(Electrodynamics.instance.obfuscated == true ? OBF_DROP_ID : DROP_ID, new Class[0]);
			dropID.setAccessible(true);
			return ((Integer)dropID.invoke(this.entity, new Object[0])).intValue();
		} catch(Exception ex) {
			EDLogger.warn("Failed to get method " + DROP_ID + " from Entity " + this.entity.getEntityName());
			ex.printStackTrace();
		}
		
		return 0;
	}
	
	public String getDeathSound() {
		try {
			Method deathSound = EntityLiving.class.getDeclaredMethod(Electrodynamics.instance.obfuscated == true ? OBF_DEATH_SOUND : DEATH_SOUND, new Class[0]);
			deathSound.setAccessible(true);
			return ((String)deathSound.invoke(this.entity, new Object[0]));
		} catch(Exception ex) {
			EDLogger.warn("Failed to get method " + DEATH_SOUND + " from Entity " + this.entity.getEntityName());
			ex.printStackTrace();
		}
		
		return "";
	}
	
}
