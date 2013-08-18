package electrodynamics.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.PacketUtils;

public class PacketUpdateHeld extends PacketED {

	private ItemStack toSend;
	
	public PacketUpdateHeld() {
		super(PacketTypeHandler.UPDATE_HELD, false);
	}

	public PacketUpdateHeld(ItemStack stack) {
		super(PacketTypeHandler.UPDATE_HELD, false);
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
		EntityPlayerMP eplayer = (EntityPlayerMP) player;
		
		eplayer.setCurrentItemOrArmor(0, this.toSend);
		eplayer.updateHeldItem();
	}
	
}
