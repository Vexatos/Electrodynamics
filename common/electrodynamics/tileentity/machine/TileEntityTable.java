package electrodynamics.tileentity.machine;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.api.tool.ITool;
import electrodynamics.core.CoreUtils;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.client.FXType;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.packet.PacketFX;
import electrodynamics.network.packet.PacketSound;
import electrodynamics.recipe.RecipeTable;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.tileentity.TileEntityEDRoot;
import electrodynamics.util.BlockUtil;
import electrodynamics.util.InventoryUtil;

public class TileEntityTable extends TileEntityEDRoot {
	
	/** Item to be displayed on the display table */
	public ItemStack displayedItem;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if (displayedItem != null) {
			nbt.setTag("displayedItem", displayedItem.writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		if (nbt.hasKey("displayedItem")) {
			this.displayedItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("displayedItem"));
		}else{
			this.displayedItem = null;
		}
	}

	@Override
	public void onUpdatePacket(NBTTagCompound nbt) {
		super.onUpdatePacket(nbt);
		
		if (nbt.hasKey("displayedItem")) {
			this.displayedItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("displayedItem"));
		}

		if(nbt.hasKey("noDisplay"))
		{
			this.displayedItem = null;
		}
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
	public void onBlockBreak() {
		if(this.displayedItem != null) {
			InventoryUtil.ejectItem(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, this.displayedItem, new Random());
		}
	}
	
	@Override
	public void onBlockActivated(EntityPlayer player) {
		if(CoreUtils.isServer(worldObj))
		{
			if (player.isSneaking()) return;
			if (player.getCurrentEquippedItem() != null && (player.getCurrentEquippedItem().getItem() instanceof ITool) && hasRecipe(player.getCurrentEquippedItem())) {
				handleSmash(player, player.getCurrentEquippedItem());
				((ITool)player.getCurrentEquippedItem().getItem()).onToolUsed(player.getCurrentEquippedItem(), worldObj, xCoord, yCoord, zCoord, player);
			} else {
				if (this.displayedItem != null) {
					BlockUtil.dropItemFromBlock(worldObj, xCoord, yCoord, zCoord, this.displayedItem.copy(), new Random());
					setItem(null);
				} else {
					if (player.getCurrentEquippedItem() != null) {
						ItemStack toDisplay = player.getCurrentEquippedItem().copy();
						toDisplay.stackSize = 1;
						setItem(toDisplay);
						
						if (player.getCurrentEquippedItem().stackSize > 1) {
							player.getCurrentEquippedItem().stackSize--;
						} else {
							player.inventory.mainInventory[player.inventory.currentItem] = null;
						}
					}
				}
			}
		}
	}
	
	private void sendDisplayUpdate()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(this.displayedItem != null) {
			nbt.setTag("displayedItem", displayedItem.writeToNBT(new NBTTagCompound()));
		} else {
			nbt.setBoolean("noDisplay", true);
		}

		sendUpdatePacket(nbt);
	}

	public void setItem(ItemStack item) {
		displayedItem = item;
		sendDisplayUpdate();
	}
	
	public boolean hasRecipe(ItemStack tool) {
		byte type = (byte)this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if (type == 0 && displayedItem != null && displayedItem.getItem().itemID == Block.stoneSingleSlab.blockID) {
			return true;
		}
		
		return !(CraftingManager.getInstance().tableManager.getRecipe(displayedItem, tool) == null);
	}
	
	public void handleSmash(EntityPlayer player, ItemStack tool) {
		if (displayedItem != null) {
			byte type = (byte)this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if (type == 0 && displayedItem.getItem().itemID == Block.stoneSingleSlab.blockID) {
				this.displayedItem = null;
				this.worldObj.setBlock(xCoord, yCoord, zCoord, BlockIDs.BLOCK_TABLE_ID, 1, 2);
			} else if (type == 1) {
				RecipeTable recipe = CraftingManager.getInstance().tableManager.getRecipe(displayedItem, tool);
				
				if (recipe != null) {
					if (displayedItem.getItem() instanceof ItemBlock) {
						PacketFX packet = new PacketFX(FXType.BLOCK_BREAK, xCoord, yCoord, zCoord, new int[] {displayedItem.itemID, displayedItem.getItemDamage()});
						PacketDispatcher.sendPacketToAllAround(xCoord, yCoord + 2, zCoord, 64D, this.worldObj.provider.dimensionId, PacketTypeHandler.fillPacket(packet));
						PacketSound sound = new PacketSound("block.oreCrumble", xCoord, yCoord, zCoord, PacketSound.TYPE_SOUND);
						PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 32D, this.worldObj.provider.dimensionId, PacketTypeHandler.fillPacket(sound));
					}
					
					recipe.onSmashed(player, this, this.displayedItem);
					
					setItem(recipe.outputItem);
					tool.damageItem(recipe.hammerDamage, player);
				}
			}
		}
	}

}
