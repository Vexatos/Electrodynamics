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
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.network.PacketTypeHandler;

public class PacketPayload extends PacketED {

	public PayloadType type;
	
	public byte[] bytePayload;
	
	public int[] intPayload;
	
	public double[] doublePayload;
	
	public float[] floatPayload;
	
	public String[] stringPayload;
	
	public int x;
	public int y;
	public int z;
	
	public PacketPayload() {
		super(PacketTypeHandler.PAYLOAD, false);
	}

	public PacketPayload(PayloadType type, int byteLength, int intLength, int doubleLength, int floatLength, int stringLength) {
		super(PacketTypeHandler.PAYLOAD, false);
		
		this.type = type;
		this.bytePayload = new byte[byteLength];
		this.doublePayload = new double[doubleLength];
		this.intPayload = new int[intLength];
		this.floatPayload = new float[floatLength];
		this.stringPayload = new String[stringLength];
	}
	
	public PacketPayload(int byteLength, int intLength, int doubleLength, int floatLength, int stringLength) {
		this(PayloadType.CONTAINER, byteLength, intLength, doubleLength, floatLength, stringLength);
	}
	
	public PacketPayload(int byteLength, int intLength, int doubleLength, int floatLength, int stringLength, int x, int y, int z) {
		this(PayloadType.TILE_ENTITY, byteLength, intLength, doubleLength, floatLength, stringLength);
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PacketPayload setByte(int index, byte value) {
		this.bytePayload[index] = value;
		return this;
	}

	public PacketPayload setInt(int index, int value) {
		this.intPayload[index] = value;
		return this;
	}
	
	public PacketPayload setDouble(int index, double value) {
		this.doublePayload[index] = value;
		return this;
	}
	
	public PacketPayload setFloat(int index, float value) {
		this.floatPayload[index] = value;
		return this;
	}
	
	public PacketPayload setString(int index, String value) {
		this.stringPayload[index] = value;
		return this;
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		this.type = PayloadType.values()[data.readByte()];
		
		this.bytePayload = new byte[data.readInt()];
		for (int i=0; i<bytePayload.length; i++) {
			this.bytePayload[i] = data.readByte();
		}
		
		this.intPayload = new int[data.readInt()];
		for (int i=0; i<intPayload.length; i++) {
			this.intPayload[i] = data.readInt();
		}
		
		this.doublePayload = new double[data.readInt()];
		for (int i=0; i<doublePayload.length; i++) {
			this.doublePayload[i] = data.readDouble();
		}
		
		this.floatPayload = new float[data.readInt()];
		for (int i=0; i<floatPayload.length; i++) {
			this.floatPayload[i] = data.readFloat();
		}
		
		this.stringPayload = new String[data.readInt()];
		for (int i=0; i<stringPayload.length; i++) {
			this.stringPayload[i] = data.readUTF();
		}
	}

	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		dos.writeByte(this.type.ordinal());
		
		dos.writeInt(this.bytePayload.length);
		for (int i=0; i<bytePayload.length; i++) {
			dos.write(bytePayload[i]);
		}
		
		dos.writeInt(this.intPayload.length);
		for (int i=0; i<intPayload.length; i++) {
			dos.writeInt(intPayload[i]);
		}
		
		dos.writeInt(this.doublePayload.length);
		for (int i=0; i<doublePayload.length; i++) {
			dos.writeDouble(doublePayload[i]);
		}
		
		dos.writeInt(this.floatPayload.length);
		for (int i=0; i<floatPayload.length; i++) {
			dos.writeFloat(floatPayload[i]);
		}
		
		dos.writeInt(this.stringPayload.length);
		for (int i=0; i<stringPayload.length; i++) {
			dos.writeUTF(stringPayload[i]);
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
					((IPayloadReceptor)screen).handlePayload(this.bytePayload, intPayload, doublePayload, floatPayload, stringPayload);
				}
			}
			case SERVER: {
				Container container = eplayer.openContainer;
				
				if (container != null && container instanceof IPayloadReceptor) {
					((IPayloadReceptor)container).handlePayload(this.bytePayload, intPayload, doublePayload, floatPayload, stringPayload);
				}
			}
			default: break;
			}
		}
		case TILE_ENTITY: {
			TileEntity tile = eplayer.worldObj.getBlockTileEntity(x, y, z);
			
			if (tile != null && tile instanceof IPayloadReceptor) {
				((IPayloadReceptor)tile).handlePayload(this.bytePayload, intPayload, doublePayload, floatPayload, stringPayload);
			}
		}
		case CONFIG: {
			ConfigurationSettings.readSettings(this);
		}
		default: break;
		}
	}
	
	public interface IPayloadReceptor {
		public void handlePayload(byte[] byteArray, int[] intArray, double[] doubleArray, float[] floatArray, String[] stringArray);
	}
	
	public enum PayloadType {
		CONTAINER,
		TILE_ENTITY,
		CONFIG; // Only used when Player connects to server
	}
	
}
