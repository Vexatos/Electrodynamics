package electrodynamics.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class PlayerUtil {

	public static ItemStack getPlayerHead(EntityPlayer player) {
		return getPlayerHead(player.getEntityName());
	}
	
	public static ItemStack getPlayerHead(String name) {
		ItemStack head = new ItemStack(Item.skull, 1, 3);
		NBTTagCompound headNBT = new NBTTagCompound();
		headNBT.setString("SkullOwner", name);
		head.setTagCompound(headNBT);
		return head;
	}
	
	public static ForgeDirection determine2DOrientation_F(EntityLivingBase entity) {
		switch(determine2DOrientation_I(entity)) {
		case 0: return ForgeDirection.EAST;
		case 1: return ForgeDirection.NORTH;
		case 2: return ForgeDirection.SOUTH;
		case 3: return ForgeDirection.WEST;
		default: return ForgeDirection.UNKNOWN;
		}
	}
	
	public static int determine2DOrientation_I(EntityLivingBase entity) {
		return MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	}
	
	public static ForgeDirection determine3DOrientation_F(World world, int x, int y, int z, EntityLivingBase entity) {
		return (ForgeDirection.getOrientation(determine3DOrientation_I(world, x, y, z, entity)));
	}
	
	public static int determine3DOrientation_I(World world, int x, int y, int z, EntityLivingBase entity) {
		if (MathHelper.abs((float) entity.posX - x) < 2.0F && MathHelper.abs((float) entity.posZ - z) < 2.0F) {
			double d0 = entity.posY + 1.82D - entity.yOffset;

			if (d0 - y > 2.0D) {
				return 1;
			}

			if (y - d0 > 0.0D) {
				return 0;
			}
		}

		int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
	}
	
	public static double[] getLookCoordinates(EntityPlayer player, double range) {
		Vec3 vec3d = Vec3.fakePool.getVecFromPool(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3 vec3d1 = player.getLookVec();
		Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * range, vec3d1.yCoord * range, vec3d1.zCoord * range);
		return new double[] {vec3d2.xCoord, vec3d2.yCoord, vec3d2.zCoord};
	}
	
	public static MovingObjectPosition getLookedBlock(World world, EntityPlayer player, double range) {
		double[] lookCoords = getLookCoordinates(player, range);
		return getLookedBlock(world, player, world.getWorldVec3Pool().getVecFromPool(lookCoords[0], lookCoords[1], lookCoords[2]));
	}

	public static MovingObjectPosition getLookedBlock(World world, EntityPlayer player, Vec3 end) {
		return world.rayTraceBlocks_do_do(world.getWorldVec3Pool().getVecFromPool(player.posX, player.posY + player.getEyeHeight(), player.posZ), end, false, true);
	}
	
	public static Entity getPointedEntity(World world, EntityPlayer entityplayer, double range, float padding) {
		return getPointedEntity(world, entityplayer, range, padding, false);
	}

	@SuppressWarnings("rawtypes")
	public static Entity getPointedEntity(World world, EntityPlayer entityplayer, double range, float padding, boolean nonCollide) {
		Entity pointedEntity = null;
		double d = range;
		Vec3 vec3d = Vec3.fakePool.getVecFromPool(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ);
		Vec3 vec3d1 = entityplayer.getLookVec();
		Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
		float f1 = padding;
		List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expand(f1, f1, f1));

		double d2 = 0.0D;
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity) list.get(i);
			if (((entity.canBeCollidedWith()) || (nonCollide)) && (world.rayTraceBlocks_do_do(world.getWorldVec3Pool().getVecFromPool(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ), world.getWorldVec3Pool().getVecFromPool(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false, true) == null)) {
				float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
				if (axisalignedbb.isVecInside(vec3d)) {
					if ((0.0D < d2) || (d2 == 0.0D)) {
						pointedEntity = entity;
						d2 = 0.0D;
					}

				} else if (movingobjectposition != null) {
					double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
					if ((d3 < d2) || (d2 == 0.0D)) {
						pointedEntity = entity;
						d2 = d3;
					}
				}
			}
		}
		return pointedEntity;
	}

	@SuppressWarnings("rawtypes")
	public static Entity getPointedEntity(World world, EntityPlayer entityplayer, double range, Class clazz) {
		Entity pointedEntity = null;
		double d = range;
		Vec3 vec3d = Vec3.fakePool.getVecFromPool(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ);
		Vec3 vec3d1 = entityplayer.getLookVec();
		Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
		float f1 = 1.1F;
		List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expand(f1, f1, f1));

		double d2 = 0.0D;
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity) list.get(i);
			if ((entity.canBeCollidedWith()) && (world.rayTraceBlocks_do_do(world.getWorldVec3Pool().getVecFromPool(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ), world.getWorldVec3Pool().getVecFromPool(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false, true) == null) && (!clazz.isInstance(entity))) {
				float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
				if (axisalignedbb.isVecInside(vec3d)) {
					if ((0.0D < d2) || (d2 == 0.0D)) {
						pointedEntity = entity;
						d2 = 0.0D;
					}

				} else if (movingobjectposition != null) {
					double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
					if ((d3 < d2) || (d2 == 0.0D)) {
						pointedEntity = entity;
						d2 = d3;
					}
				}
			}
		}
		return pointedEntity;
	}
	
}
