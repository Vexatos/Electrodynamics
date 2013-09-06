package electrodynamics.core;

import java.util.EnumSet;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class PlayerTicker implements ITickHandler {

	public static HashMap<String, PlayerData> playerData = new HashMap<String, PlayerData>();
	
	public int timer = 60;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			if (timer == 0) {
				EntityPlayer player = (EntityPlayer) tickData[0];
				PlayerData data = PlayerData.readFromPlayer(player);
				playerData.put(player.username, data);
				timer = 60;
			} else {
				timer--;
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "Player Data Tracker";
	}

	public static class PlayerData {
		public double x;
		public double y;
		public double z;
		public double mx;
		public double my;
		public double mz;
		
		public float foodSat;
		public float rotationPitch;
		public float rotationYaw;
		public float rotationHeadYaw;
		public float fallDistance;
		public float health;
		
		public int foodLevel;
		
		public boolean onFire;
		
		public static PlayerData readFromPlayer(EntityPlayer player) {
			PlayerData data = new PlayerData();
			data.x = player.posX;
			data.y = player.posY;
			data.z = player.posZ;
			data.mx = player.motionX;
			data.my = player.motionY;
			data.mz = player.motionZ;
			data.foodSat = player.getFoodStats().getSaturationLevel();
			data.rotationPitch = player.rotationPitch;
			data.rotationYaw = player.rotationYaw;
			data.rotationHeadYaw = player.rotationYawHead;
			data.fallDistance = player.fallDistance;
			data.health = player.getHealth();;
			data.foodLevel = player.getFoodStats().getFoodLevel();
			data.onFire = player.isBurning();
			return data;
		}
		
		public static void writeToPlayer(EntityPlayer player, PlayerData data) {
			player.setPositionAndRotation(data.x, data.y, data.z, data.rotationPitch, data.rotationYaw);
			player.motionX = data.mx;
			player.motionY = data.my;
			player.motionZ = data.mz;
			player.getFoodStats().setFoodSaturationLevel(data.foodSat);
			player.rotationYawHead = data.rotationHeadYaw;
			player.fallDistance = data.fallDistance;
			player.setHealth(data.health);
			player.getFoodStats().setFoodLevel(data.foodLevel);
			player.setFire(data.onFire ? 10 : 0); // Ugly, do reflection here to get player's fire var
		}
	}
	
}
