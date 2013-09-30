package electrodynamics.tileentity.machine.energy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.interfaces.energy.IEnergyConnection;
import electrodynamics.tileentity.TileEntityEDRoot;
import electrodynamics.util.BlockUtil;

public class TileEntitySolarPanel extends TileEntityEDRoot implements IEnergyConnection {

//	@SideOnly(Side.CLIENT)
	public float vertOffset = 0.0F;
//	@SideOnly(Side.CLIENT)
	public float currAngle = 0.0F;
	
	public float setAngle;

	private boolean prevPanelDisabled;
	public boolean panelDisabled = false;

	public ForgeDirection attached;
	
	@Override
	public void onBlockAdded(EntityPlayer player, ForgeDirection side) {
		if (player.isSneaking()) {
			this.attached = ForgeDirection.UNKNOWN;
		} else {
			this.attached = side;
		}
	}

	@Override
	public void onNeighborUpdate() {
		if (BlockUtil.getBlockOnSide(worldObj, xCoord, yCoord, zCoord, attached)[0] == 0 || this.attached == null) {
			this.attached = ForgeDirection.UNKNOWN;
			sendStateUpdate();
		}
	}
	
	@Override
	public AxisAlignedBB getAABB() {
		double minX = xCoord + 0.2;
		double minY = yCoord + 0;
		double minZ = zCoord + 0.2;
		double maxX = xCoord + 0.8;
		double maxY = yCoord + 1;
		double maxZ = zCoord + 0.8;
		
//		switch (this.attached) {
//		case DOWN: {
//			minY = yCoord;
//			break;
//		}
//		case NORTH: {
//			minZ = zCoord;
//			break;
//		}
//		case SOUTH: {
//			maxZ = zCoord + 1;
//			break;
//		}
//		case EAST: {
//			maxX = xCoord + 1;
//			break;
//		}
//		case WEST: {
//			minX = xCoord;
//			break;
//		}
//		default: break;
//		}
		
		AxisAlignedBB aabb = AxisAlignedBB.getAABBPool().getAABB(minX, minY, minZ, maxX, maxY, maxZ);
		
		return aabb;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateEntityClient() {
		if (!this.panelDisabled) {
			this.setAngle = -(float) (Math.round(calculateAngle() * 100.0) / 100.0);

			if (this.setAngle > 0.45F)  {
				this.setAngle = 0.45F;
			}
			
			if (this.setAngle < -0.45F) {
				this.setAngle = -0.45F;
			}
			
			ForgeDirection facing = null;
			if (this.setAngle == 0) {
				facing = ForgeDirection.UP;
			} else if (this.setAngle > 0 && this.setAngle <= 0.45F) {
				facing = ForgeDirection.EAST;
			} else if (this.setAngle < 0 && this.setAngle >= -0.45F) {
				facing = ForgeDirection.WEST;
			}
			
			if (this.attached == facing) {
				this.setAngle = 0;
			} else {
				Block block = Block.blocksList[this.worldObj.getBlockId(xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ)];
				
				if (block != null) {
					if (block.isOpaqueCube() || block.renderAsNormalBlock()) {
						this.setAngle = 0;
					}
				}
			}
		} else {
			this.setAngle = 0;
		}
		
		if (this.vertOffset == 0) {
			if (currAngle < setAngle) {
				currAngle += 0.01;
				currAngle = (float) (Math.round(currAngle * 100.0) / 100.0);
			} else if (currAngle > setAngle) {
				currAngle -= 0.01;
				currAngle = (float) (Math.round(currAngle * 100.0) / 100.0);
			}
		}
		
		if (this.currAngle == 0) {
			if (panelDisabled) {
				if (this.vertOffset <= 0.20F) {
					this.vertOffset += 0.01F;
					this.vertOffset = (float) (Math.round(vertOffset * 100.0) / 100.0);
				}
			} else {
				if (this.vertOffset > 0) {
					this.vertOffset -= 0.01F;
					this.vertOffset = (float) (Math.round(vertOffset * 100.0) / 100.0);
				}
			}
		}
	}	

	@Override
	public void updateEntityServer() {
		if (!this.worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || (getWorldTime() >= 12500) || this.worldObj.isRaining() || this.worldObj.isThundering()) {
			this.prevPanelDisabled = this.panelDisabled;
			this.panelDisabled = true;
		} else {
			this.prevPanelDisabled = this.panelDisabled;
			this.panelDisabled = false;
		}
		
		if (this.panelDisabled != this.prevPanelDisabled) {
			sendStateUpdate();
			this.prevPanelDisabled = this.panelDisabled;
		}
	}
	
	private float calculateAngle() {
		final float TIME_TO_ANGLE = 66.666F;
		long worldTime = getWorldTime();
		
		float celestialAngle = ((worldTime / TIME_TO_ANGLE) / 100);
		return celestialAngle - 0.9F;
	}
	
	private long getWorldTime() {
		final long TOTAL_WORLD_TIME = 24000;
		
		long worldTime = this.worldObj.getWorldTime();
		if (worldTime > TOTAL_WORLD_TIME) {
			long remainder = worldTime % TOTAL_WORLD_TIME;
			
			if (remainder != 0) {
				return remainder;
			} else {
				return worldTime - (TOTAL_WORLD_TIME * (worldTime / TOTAL_WORLD_TIME));
			}
		}
		return worldTime;
	}
	
	private Vec3 getBlockVector() {
		return Vec3.fakePool.getVecFromPool(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5);
	}
	
	private Vec3 getPanelFaceVector() {
		float yaw = -this.setAngle != 0 ? (this.setAngle > 0 && this.setAngle <= 0.45 ? -90 : 0) : (this.setAngle < 0 && this.setAngle >= -0.45 ? 90 : 0);

		double pitchRad = Math.toRadians(this.setAngle);
		double yawRad = Math.toRadians(yaw);
		
		double sinPitch = Math.sin(pitchRad);
		double cosPitch = Math.cos(pitchRad);
		double sinYaw = Math.sin(yawRad);
		double cosYaw = Math.sin(yawRad);
		
		return Vec3.fakePool.getVecFromPool(-cosPitch * sinYaw, sinPitch, -cosPitch * cosYaw);
	}
	
	public Vec3 getPanelFaceCoords() {
		final float RANGE = 10F;
		
		Vec3 base = getBlockVector();
		Vec3 face = getPanelFaceVector();
		Vec3 look = base.addVector(face.xCoord * RANGE, face.yCoord * RANGE, face.zCoord * RANGE);
	
		return look;
	}
	
	public MovingObjectPosition getPanelFaceBlock() {
		return this.worldObj.rayTraceBlocks_do_do(getBlockVector(), getPanelFaceCoords(), false, true);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setByte("attach", (byte) this.attached.ordinal());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.attached = ForgeDirection.values()[nbt.getByte("attach")];
	}
	
	@Override
	public void onDescriptionPacket(NBTTagCompound nbt) {
		super.onDescriptionPacket(nbt);

		this.setAngle = nbt.getFloat("setAngle");
		readFromNBT(nbt);
	}
	
	@Override
	public void getDescriptionForClient(NBTTagCompound nbt) {
		super.getDescriptionForClient(nbt);
		
		nbt.setFloat("setAngle", this.setAngle);
		writeToNBT(nbt);
	}
	
	@Override
	public void onUpdatePacket(NBTTagCompound nbt) {
		super.onUpdatePacket(nbt);
		
		if (nbt.hasKey("setAngle")) {
			this.setAngle = nbt.getFloat("setAngle");
		}
		
		if (nbt.hasKey("state")) {
			this.panelDisabled = nbt.getBoolean("state");
		}
		
		if (nbt.hasKey("attach")) {
			this.attached = ForgeDirection.values()[nbt.getByte("attach")];
		}
	}
	
	private void sendAngleUpdate() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("setAngle", this.setAngle);
		sendUpdatePacket(nbt);
	}

	private void sendStateUpdate() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("state", this.panelDisabled);
		nbt.setByte("attach", (byte) this.attached.ordinal());
		sendUpdatePacket(nbt);
	}
	
}
