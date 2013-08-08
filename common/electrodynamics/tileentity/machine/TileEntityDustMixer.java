package electrodynamics.tileentity.machine;

import java.util.Stack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import electrodynamics.item.ItemDust;

public class TileEntityDustMixer extends TileEntityMachine {

	public int rotation = 0;
	
	public Stack<ItemStack> inv = new Stack<ItemStack>();
	
	@Override
	public void updateEntity() {
		if (this.rotation > 0) {
			this.rotation -= 10;
		}
	}
	
	@Override
	public void onBlockActivated(EntityPlayer player) {
		if (player.getCurrentEquippedItem() == null) {
			stir();
		} else if (player.getCurrentEquippedItem().getItem() instanceof ItemDust){
			if (inv.size() <= 9) {
				ItemStack current = player.getCurrentEquippedItem();
				this.inv.push(current.copy());
				sendDustAddition(current.copy());
				if (current.stackSize == 1) {
					current = null;
				} else {
					--current.stackSize;
				}
			}
		}
	}		
	
	private void stir() {
		int rot = 360;
		if (this.rotation > 0) {
			rot += this.rotation;
		}
		sendRotationUpdate(rot);
	}
	
	@Override
	public void getDescriptionForClient(NBTTagCompound nbt) {
		super.getDescriptionForClient(nbt);
		writeToNBT(nbt);
	}
	
	@Override
	public void onDescriptionPacket(NBTTagCompound nbt) {
		super.onDescriptionPacket(nbt);
		readFromNBT(nbt);
	}

	@Override
	public void onUpdatePacket(NBTTagCompound nbt) {
		if (nbt.hasKey("dust")) {
			this.inv.push(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("dust")));
		}
		
		if (nbt.hasKey("rotation")) {
			this.rotation = nbt.getInteger("rotation");
		}
	}
	
	public void sendDustAddition(ItemStack stack) {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound nbt2 = new NBTTagCompound();
		stack.writeToNBT(nbt2);
		nbt.setTag("dust", nbt2);
		sendUpdatePacket(nbt);
	}
	
	public void sendRotationUpdate(int rotation) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("rotation", rotation);
		sendUpdatePacket(nbt);
	}
	
}
