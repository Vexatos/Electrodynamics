package electrodynamics.tileentity.machine.utilty;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.api.tool.ITool;
import electrodynamics.api.tool.ToolType;
import electrodynamics.core.CoreUtils;
import electrodynamics.inventory.InventoryLimited;
import electrodynamics.tileentity.TileEntityInventoryWrapper;
import electrodynamics.util.BlockUtil;
import electrodynamics.util.ItemUtil;

public class TileEntityActuator extends TileEntityInventoryWrapper implements ISidedInventory {

	@SideOnly(Side.CLIENT)
	public int blockRotation = 0;

	public byte mode = 0;
	
	public NBTTagCompound tileEntityData = null;

	private ItemStack previousItem = null;
	
	private boolean prevRedstoneState = false;
	
	public TileEntityActuator() {
		super(new InventoryLimited("Actuator", 1, 1));
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		ForgeDirection sideDir = ForgeDirection.getOrientation(side);
		return sideDir == this.rotation.getOpposite() ? new int[] { 0 } : new int[] {};
	}

	@Override
	public void updateEntityClient() {
		if (this.blockRotation < 360) {
			this.blockRotation += 2;
		} else {
			this.blockRotation = 0;
		}
	}

	@Override
	public void updateEntityServer() {
		if (!ItemUtil.areItemStacksEqual(getStackInSlot(0), previousItem, true)) {
			sendBlockUpdate();
		}
	}
	
	@Override
	public void onNeighborUpdate() {
		boolean redstoneState = this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		
		if (!prevRedstoneState && redstoneState) {
			update();
			prevRedstoneState = true;
		} else if (prevRedstoneState && !redstoneState) {
			this.prevRedstoneState = false;
		}
	}
	
	private void update() {
		ItemStack stack = this.getStackInSlot(0);
		int[] offsetCoords = getOffsetCoords();
		
		if (stack != null && getBlockInFront() == 0 && (this.mode == Mode.DEFAULT.ordinal() || this.mode == Mode.PLACE.ordinal())) { // Place block
			placeBlock(offsetCoords);
		} else if (stack == null && getBlockInFront() > 0 && (this.mode == Mode.DEFAULT.ordinal() || this.mode == Mode.BREAK.ordinal())) { // Pickup block
			breakBlock(offsetCoords);
		}
	}
	
	private void breakBlock(int[] coords) {
		ItemStack stack = this.getStackInSlot(0);
		int id = this.worldObj.getBlockId(coords[0], coords[1], coords[2]);
		int meta = this.worldObj.getBlockMetadata(coords[0], coords[1], coords[2]);
		ItemStack picked = new ItemStack(id, 1, meta);

		if (id == this.blockType.blockID) { // Removes the ability to make actuator chains
			return;
		}
		
		TileEntity tile = this.worldObj.getBlockTileEntity(coords[0], coords[1], coords[2]);

		if (tile != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			tile.writeToNBT(nbt);
			this.tileEntityData = nbt;
			tile.invalidate();
		}
		
		this.worldObj.setBlockToAir(coords[0], coords[1], coords[2]);
		this.setInventorySlotContents(0, picked);
		sendBlockUpdate();
	}
	
	private void placeBlock(int[] coords) {
		ItemStack stack = this.getStackInSlot(0);
		this.worldObj.setBlock(coords[0], coords[1], coords[2], stack.itemID, stack.getItemDamage(), 3);

		TileEntity tile = this.worldObj.getBlockTileEntity(coords[0], coords[1], coords[2]);

		if (tile != null) {
			tile.readFromNBT(this.tileEntityData);
			this.tileEntityData = null;
		}
		
		this.setInventorySlotContents(0, null);
		sendBlockUpdate();
	}
	
	@Override
	public void onBlockActivated(EntityPlayer player) {
		if (!CoreUtils.isServer(this.worldObj)) return;
		
		if (player.getCurrentEquippedItem() != null) {
			ItemStack current = player.getCurrentEquippedItem();
			
			if (current.getItem() instanceof ITool && ((ITool)current.getItem()).getToolType() == ToolType.HAMMER) {
				player.addChatMessage("Actuator mode changed: " + EnumChatFormatting.RED + switchMode().toString());
			}
		}
	}
	
	@Override
	public void onBlockBreak() {
		if (this.getStackInSlot(0) != null) {
			//Incoming, most hackish way for dropping stored block contents ever!
			int id = this.getStackInSlot(0).itemID;
			int meta = this.getStackInSlot(0).getItemDamage();
			BlockUtil.dropItemFromBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getStackInSlot(0), new Random());
		
			this.invalidate();
			this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
			
			placeBlock(new int[] {this.xCoord, this.yCoord, this.zCoord});
			
			Block.blocksList[id].breakBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 0, meta);
			this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	private Mode switchMode() {
		if (this.mode + 1 < Mode.values().length) {
			this.mode++;
		} else {
			this.mode = 0;
		}
		
		return Mode.values()[this.mode];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setByte("mode", this.mode);
		
		ItemStack stack = this.getStackInSlot(0);
		if (stack != null) {
			NBTTagCompound item = new NBTTagCompound();
			stack.writeToNBT(item);
			nbt.setTag("block", item);
		} else {
			nbt.setString("block", "null");
		}

		if (this.tileEntityData != null) {
			nbt.setCompoundTag("data", this.tileEntityData);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.mode = nbt.getByte("mode");
		
		if (nbt.hasKey("block")) {
			NBTBase block = nbt.getTag("block");
			
			if (block instanceof NBTTagCompound) {
				this.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("block")));
			} else if (block instanceof NBTTagString) {
				this.setInventorySlotContents(0, null);
			}
		}

		if (nbt.hasKey("data")) {
			this.tileEntityData = nbt.getCompoundTag("data");
		}
	}

	@Override
	public void getDescriptionForClient(NBTTagCompound nbt) {
		super.getDescriptionForClient(nbt);
		writeToNBT(nbt);
	}

	@Override
	public void onDescriptionPacket(NBTTagCompound nbt) { // load
		super.onDescriptionPacket(nbt);
		readFromNBT(nbt);
	}

	@Override
	public void onUpdatePacket(NBTTagCompound nbt) { // update
		super.onUpdatePacket(nbt);
		readFromNBT(nbt);
	}

	private void sendBlockUpdate() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		sendUpdatePacket(nbt);
	}

	public enum Mode {
		DEFAULT, BREAK, PLACE;
	}
	
}
