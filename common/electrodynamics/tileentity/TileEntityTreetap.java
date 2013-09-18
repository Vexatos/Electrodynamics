package electrodynamics.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.block.BlockRubberWood;
import electrodynamics.core.CoreUtils;
import electrodynamics.item.EDItems;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.tileentity.machine.TileEntityMachine;

public class TileEntityTreetap extends TileEntityMachine {
	
	/** Does treetap has bucket? */
	public boolean hasBucket;
	
	/** Liquid amount */
	public int liquidAmount = 0;
	
	/** Should tile entity send out update packet */
	public boolean dirty = true;
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		tag.setBoolean("hasBucket", hasBucket);
		tag.setInteger("liquidAmount", liquidAmount);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		hasBucket = tag.getBoolean("hasBucket");
		liquidAmount = tag.getInteger("liquidAmount");
	}
	
	@Override
	public void onDescriptionPacket(NBTTagCompound nbt) {
		super.onDescriptionPacket(nbt);

		readFromNBT(nbt);
	}
	
	@Override
	public void getDescriptionForClient(NBTTagCompound nbt) {
		super.getDescriptionForClient(nbt);
		
		writeToNBT(nbt);
	}
	
	@Override
	public void onUpdatePacket(NBTTagCompound nbt) {
		super.onUpdatePacket(nbt);
		
		if (nbt.hasKey("liquidAmount")) {
			liquidAmount = nbt.getInteger("liquidAmount");
		}
		
		if (nbt.hasKey("hasBucket")) {
			hasBucket = nbt.getBoolean("hasBucket");
		}
	}

	@Override
	public void onBlockActivated(EntityPlayer player) {
		if(CoreUtils.isServer(worldObj))
		{
			if (player.getCurrentEquippedItem() != null) {
				if (!hasBucket && player.getCurrentEquippedItem().getItem() == Item.bucketEmpty) {
					hasBucket = true;
					sendBucketUpdate();
				
					--player.getCurrentEquippedItem().stackSize;
					((EntityPlayerMP)player).updateHeldItem();
				} else if (hasBucket && player.getCurrentEquippedItem().getItem() == EDItems.itemLatexBucket && liquidAmount == 0) {
					liquidAmount = 1000;
					sendLiquidUpdate();
					
					player.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty));
					((EntityPlayerMP)player).updateHeldItem();
				} else if (hasBucket && player.getCurrentEquippedItem().getItem() == Item.bucketEmpty) {
					liquidAmount = 0;
					sendLiquidUpdate();
					
					player.setCurrentItemOrArmor(0, new ItemStack(EDItems.itemLatexBucket));
					((EntityPlayerMP)player).updateHeldItem();
				}
			} else {
				if (hasBucket && (liquidAmount == 0 || liquidAmount == 1000)) {
					dropBucket(player);
				}
			}
		}
	}

	private void sendLiquidUpdate()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("liquidAmount", liquidAmount);
		sendUpdatePacket(nbt);
	}

	private void sendBucketUpdate()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("hasBucket", hasBucket);
		sendUpdatePacket(nbt);
	}

	public void tick() {
		if (liquidAmount + 1000 <= 1000 && hasBucket) {
			drawLatex();
		}
	}
	
	public boolean isTapSupported() {
		int x = xCoord + rotation.getOpposite().offsetX;
		int y = yCoord;
		int z = zCoord + rotation.getOpposite().offsetZ;
		
		return (this.worldObj.getBlockId(x, y, z) == BlockIDs.BLOCK_RUBBER_WOOD_ID);
	}
	
	public boolean isBucketSupported() {
		return (this.worldObj.isBlockSolidOnSide(xCoord, yCoord - 1, zCoord, ForgeDirection.UP));
	}
	
	public ItemStack getBucket() {
		return liquidAmount == 0 ? new ItemStack(Item.bucketEmpty) : new ItemStack(EDItems.itemLatexBucket);
	}
	
	public void dropBucket(EntityPlayer player) {
		player.setCurrentItemOrArmor(0, getBucket());
		hasBucket = false;
		liquidAmount = 0;
		sendLiquidUpdate();
		sendBucketUpdate();
		((EntityPlayerMP)player).updateHeldItem();
	}
	
	public void drawLatex() {
		if (getHighestValidLog() > 0) {
			if (BlockRubberWood.suckLatex(this.worldObj, xCoord + this.rotation.getOpposite().offsetX, yCoord + (getHighestValidLog() - 1), zCoord + this.rotation.getOpposite().offsetZ)) {
				this.liquidAmount += 1000;
				sendLiquidUpdate();
			}
		}
	}
	
	public int getHighestValidLog() {
		if (rotation != null) {
			boolean foundEnd = false;
			int height = 0;
			
			int x = xCoord + rotation.getOpposite().offsetX;
			int y = yCoord;
			int z = zCoord + rotation.getOpposite().offsetZ;
			
			while (!foundEnd) {
				if (this.worldObj.getBlockId(x, y, z) == BlockIDs.BLOCK_RUBBER_WOOD_ID) {
					if (!(this.worldObj.getBlockMetadata(x, y, z) >= 1 && this.worldObj.getBlockMetadata(x, y, z) <= 9)) {
						foundEnd = true;
						break;
					}
				} else {
					foundEnd = true;
					break;
				}
				
				height += 1;
				y += 1;
			}
			
			return height;
		}
		
		return 0;
	}
	
}
