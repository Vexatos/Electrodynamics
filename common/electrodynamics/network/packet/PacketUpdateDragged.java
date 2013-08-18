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

public class PacketUpdateDragged extends PacketED {

	private ItemStack toSend;
	
	public PacketUpdateDragged() {
		super(PacketTypeHandler.UPDATE_DRAG, false);
	}

	public PacketUpdateDragged(ItemStack stack) {
		super(PacketTypeHandler.UPDATE_DRAG, false);
		this.toSend = stack;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		byte read = data.readByte();
		
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
			
			NBTTagCompound itemStack = new NBTTagCompound();
			this.toSend.writeToNBT(itemStack);
			PacketUtils.writeNBTTagCompound(itemStack, dos);
		} else {
			dos.writeByte(0);
		}
	}

	@Override
	public void execute(INetworkManager network, Player player, Side side) {
		EntityPlayer eplayer = (EntityPlayer)player;
		
		eplayer.inventory.setItemStack(this.toSend);
	}
	
}
