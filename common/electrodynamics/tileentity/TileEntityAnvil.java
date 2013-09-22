package electrodynamics.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemIngot;
import electrodynamics.tileentity.machine.TileEntityMachine;

public class TileEntityAnvil extends TileEntityMachine {

	public ItemStack placedIngot;
	
	@Override
	public void onBlockActivated(EntityPlayer player) {
		ItemStack item = player.getCurrentEquippedItem();
		
		if (item != null && placedIngot == null && ItemIngot.isIngot(item) && item.getItem() == EDItems.itemAlloy) {
			this.placedIngot = item.copy();
			player.setCurrentItemOrArmor(0, null);
			((EntityPlayerMP)player).updateHeldItem();
			updateIngot();
		} else if (item == null && placedIngot != null) { // Temporary. Will have more sophisticated logic soon
			player.setCurrentItemOrArmor(0, placedIngot.copy());
			this.placedIngot = null;
			((EntityPlayerMP)player).updateHeldItem();
			updateIngot();
		}
	}
	
	@Override
	public void getDescriptionForClient(NBTTagCompound nbt) {
		super.getDescriptionForClient(nbt);
		
		if (placedIngot != null) {
			NBTTagCompound tag = new NBTTagCompound();
			placedIngot.writeToNBT(tag);
			nbt.setTag("ingot", tag);
		}
	}
	
	@Override
	public void onUpdatePacket(NBTTagCompound nbt) {
		super.onUpdatePacket(nbt);
		
		if (nbt.hasKey("ingot")) {
			this.placedIngot = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("ingot"));
		}
	}
	
	private void updateIngot() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (placedIngot != null) {
			NBTTagCompound tag = new NBTTagCompound();
			placedIngot.writeToNBT(tag);
			nbt.setTag("ingot", tag);
		}
		sendUpdatePacket(nbt);
	}
	
}
