package electrodynamics.tileentity;

import electrodynamics.block.EDBlocks;
import electrodynamics.util.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityPotLight extends TileEntityEDRoot {

	public int mimicID;
	public int mimicMeta;
	
	public boolean requiresUpdate = true;
	
	public boolean[] hasLight;
	public int[] lightColors;
	
	public TileEntityPotLight() {
		this.hasLight = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
		this.lightColors = new int[ForgeDirection.VALID_DIRECTIONS.length];
	}
	
	@Override
	public void updateEntity() {
		if (requiresUpdate) {
			for (int i=0; i<hasLight.length; i++) {
				ForgeDirection side = ForgeDirection.getOrientation(i);
				int[] coords = BlockUtil.getCoordsOnSide(worldObj, xCoord, yCoord, zCoord, side);
				
				if (hasLight[i]) {
					if (worldObj.getBlockId(coords[0], coords[1], coords[2]) == 0) {
						worldObj.setBlock(coords[0], coords[1], coords[2], EDBlocks.blockLightSource.blockID, 0, 2);
					}
				} else {
					if (worldObj.getBlockId(coords[0], coords[1], coords[2]) == EDBlocks.blockLightSource.blockID) {
						worldObj.setBlockToAir(coords[0], coords[1], coords[2]);
					}
				}
			}
			
			updateClient();
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("mimicID", this.mimicID);
		nbt.setInteger("mimicMeta", this.mimicMeta);
		
		for (int i=0; i<hasLight.length; i++) {
			nbt.setBoolean("Light_" + i, hasLight[i]);
		}
		
		for (int i=0; i<lightColors.length; i++) {
			nbt.setInteger("Color_" + i, lightColors[i]);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.mimicID = nbt.getInteger("mimicID");
		this.mimicMeta = nbt.getInteger("mimicMeta");
		
		this.hasLight = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
		for (int i=0; i<hasLight.length; i++) {
			hasLight[i] = nbt.getBoolean("Light_" + i);
		}
		
		this.lightColors = new int[ForgeDirection.VALID_DIRECTIONS.length];
		for (int i=0; i<lightColors.length; i++) {
			lightColors[i] = nbt.getInteger("Color_" + i);
		}
	}
	
	@Override
	public void getDescriptionForClient(NBTTagCompound nbt) {
		writeToNBT(nbt);
	}
	
	public void onDescriptionPacket(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	public Block getMimickedBlock() {
		return Block.blocksList[this.mimicID];
	}
	
	public void setMimickBlock(int id, int meta) {
		this.mimicID = id;
		this.mimicMeta = meta;
	}
	
	public boolean hasLight() {
		for (boolean bool : this.hasLight) {
			if (bool) {
				return true;
			}
		}
		
		return false;
	}
	
}
