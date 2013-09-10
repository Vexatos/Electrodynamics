package electrodynamics.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import electrodynamics.network.PacketTypeHandler;

public class PacketPayload extends PacketED {

	public PayloadType type;
	
	public byte[] bytePayload;
	
	public int x;
	public int y;
	public int z;
	
	public PacketPayload() {
		super(PacketTypeHandler.PAYLOAD, false);
	}

	public PacketPayload(int length) {
		super(PacketTypeHandler.PAYLOAD, false);

		this.type = PayloadType.CONTAINER;
		this.bytePayload = new byte[length];
	}
	
	public PacketPayload(int length, int x, int y, int z) {
		super(PacketTypeHandler.PAYLOAD, false);
		
		this.type = PayloadType.TILE_ENTITY;
		this.bytePayload = new byte[length];
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PacketPayload set(int index, byte value) {
		this.bytePayload[index] = value;
		return this;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		this.type = PayloadType.values()[data.readByte()];
		this.bytePayload = new byte[data.readInt()];
		for (int i=0; i<bytePayload.length; i++) {
			this.bytePayload[i] = data.readByte();
		}
	}

	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		dos.writeByte(this.type.ordinal());
		dos.write(this.bytePayload.length);
		for (int i=0; i<bytePayload.length; i++) {
			dos.write(bytePayload[i]);
		}
	}

	@Override
	public void execute(INetworkManager network, Player player, Side side) {
		EntityPlayer eplayer = (EntityPlayer) player;
		
		switch(this.type) {
		case CONTAINER: {
			switch (side) {
			case CLIENT: {
				GuiScreen screen = FMLClientHandler.instance().getClient().currentScreen;
				
				if (screen != null && screen instanceof IPayloadReceptor) {
					((IPayloadReceptor)screen).handlePayload(this.bytePayload);
				}
			}
			case SERVER: {
				Container container = eplayer.openContainer;
				
				if (container != null && container instanceof IPayloadReceptor) {
					((IPayloadReceptor)container).handlePayload(this.bytePayload);
				}
			}
			default: break;
			}
		}
		case TILE_ENTITY: {
			TileEntity tile = eplayer.worldObj.getBlockTileEntity(x, y, z);
			
			if (tile != null && tile instanceof IPayloadReceptor) {
				((IPayloadReceptor)tile).handlePayload(this.bytePayload);
			}
		}
		default: break;
		}
	}
	
	public interface IPayloadReceptor {
		public void handlePayload(byte[] array);
	}
	
	public enum PayloadType {
		CONTAINER,
		TILE_ENTITY;
	}
	
}
