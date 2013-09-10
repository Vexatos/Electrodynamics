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
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.PacketUtils;

public class PacketHotspotCallback extends PacketED {

	private String uuid;
	
	private MouseState state;
	
	private ItemStack stack;
	
	public PacketHotspotCallback() {
		super(PacketTypeHandler.HOTSPOT_CALLBACK, false);
	}

	public PacketHotspotCallback(String uuid, MouseState state, ItemStack stack) {
		super(PacketTypeHandler.HOTSPOT_CALLBACK, false);
		
		this.uuid = uuid;
		this.state = state;
		this.stack = stack;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		this.uuid = data.readUTF();
		this.state = MouseState.values()[data.readByte()];
		if (data.readBoolean()) {
			NBTTagCompound itemStack = PacketUtils.readNBTTagCompound(data);
			this.stack = ItemStack.loadItemStackFromNBT(itemStack);
		}
	}

	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		dos.writeUTF(this.uuid);
		dos.writeByte(this.state.ordinal());
		if (this.stack != null) {
			dos.writeBoolean(true);
			NBTTagCompound item = new NBTTagCompound();
			this.stack.writeToNBT(item);
			PacketUtils.writeNBTTagCompound(item, dos);
		} else {
			dos.writeBoolean(false);
		}
	}

	@Override
	public void execute(INetworkManager network, Player player, Side side) {
		EntityPlayer eplayer = (EntityPlayer)player;
		
		if (eplayer.openContainer instanceof IHotspotCallback) {
			((IHotspotCallback)eplayer.openContainer).onClicked(eplayer, uuid, state, stack);
		}
	}
	
}
