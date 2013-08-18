package electrodynamics.item;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import electrodynamics.entity.EntityBeam;
import electrodynamics.util.misc.LaserWrapper;

public abstract class ItemLaserTool extends ItemPowerTool {

	public static HashMap<String, EntityBeam> emissionBeams = new HashMap<String, EntityBeam>();
	public static HashMap<String, Integer> startCharges = new HashMap<String, Integer>();
	public static HashMap<String, Integer> useCount = new HashMap<String, Integer>();
	
	public ItemLaserTool(int id) {
		super(id);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		// Don't know what this actually does...
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		String id = "BEAM: " + player.username;
		
		if (ItemPowerTool.getCharge(stack) > 0) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
			
			startCharges.put(id, ItemPowerTool.getCharge(stack));
		}
		
		return stack;
	}
	
	@Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
		String id = "BEAM: " + player.username;
		
		int charge = startCharges.get(id);
		
		int use = 1;
		if (useCount.get(id) != null) {
			useCount.put(id, useCount.get(id) + 1);
		} else {
			useCount.put(id, 1);
		}
		
		if (charge > 0 && charge - use > 0) {
			generateOrUpdateLaser(id, player.worldObj, onTick(stack, player, charge, use));
		}
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count) {
		String id = "BEAM: " + player.username;
		
		// Discharge is done here to prevent constant item update animation while in use
		ItemPowerTool.setCharge(stack, ItemPowerTool.getCharge(stack) - useCount.get(id));
		
		emissionBeams.put(id, null);
		useCount.put(id, 0);
	}
	
	public void generateOrUpdateLaser(String id, World world, LaserWrapper laser) {
		if (laser != null) {
			EntityBeam laserEntity = null;
			
			if (emissionBeams.get(id) == null) {
				laserEntity = new EntityBeam(world, laser.startX, laser.startY, laser.startZ, laser.endX, laser.endY, laser.endZ, 1);
				laserEntity.setRGB(laser.color.r, laser.color.g, laser.color.b);
				laserEntity.setEndMod(1.0F);
				laserEntity.setPulse(false);
				
				emissionBeams.put(id, laserEntity);
				
				world.spawnEntityInWorld(laserEntity);
			} else {
				laserEntity = emissionBeams.get(id);
				laserEntity.updateBeam(laser.startX, laser.startY, laser.startZ, laser.endX, laser.endY, laser.endZ);
				laserEntity.setRGB(laser.color.r, laser.color.g, laser.color.b);
			}
		}
	}
	
	/**
	 * Inherited by all tools sub-classing this. Used to determine what the tool does
	 * @param stack ItemStack of tool
	 * @param player Player using tool
	 * @param charge Current charge of tool (as of usage start, doesn't drain as it's used)
	 * @param usageTick Current tick of usage (can be used to stop usage if tool would be drained)
	 * @return Laser info to create/update. Can be null.
	 */
	public abstract LaserWrapper onTick(ItemStack stack, EntityPlayer player, int charge, int usageTick);
	
}
