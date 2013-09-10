package electrodynamics.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.PacketUtils;

public class PacketUpdateSlot extends PacketED {

	private int slot;
	
	private ItemStack toSend;
	
	public PacketUpdateSlot() {
		super(PacketTypeHandler.UPDATE_SLOT, false);
	}

	public PacketUpdateSlot(ItemStack stack, int slot) {
		super(PacketTypeHandler.UPDATE_SLOT, false);
		this.slot = slot;
		this.toSend = stack;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		byte read = data.readByte();
		
		this.slot = data.readInt();
		
		if (read == 1) {
			NBTTagCompound itemStack = PacketUtils.readNBTTagCompound(data);
			this.toSend = ItemStack.loadItemStackFromNBT(itemStack);
		} else {
			this.toSend = null;
		}
	}

	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		if (this.toSend != null) {
			dos.writeByte(1);
			dos.writeInt(this.slot);
			
			NBTTagCompound itemStack = new NBTTagCompound();
			this.toSend.writeToNBT(itemStack);
			PacketUtils.writeNBTTagCompound(itemStack, dos);
		} else {
			dos.writeByte(0);
			dos.writeInt(this.slot);
		}
	}

	@Override
	public void execute(INetworkManager network, Player player, Side side) {
		EntityPlayer eplayer = (EntityPlayer)player;
		
		eplayer.inventory.setInventorySlotContents(this.slot, this.toSend);
	}
	
}
