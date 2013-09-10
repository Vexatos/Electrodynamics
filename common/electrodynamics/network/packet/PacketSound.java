package electrodynamics.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import electrodynamics.Electrodynamics;
import electrodynamics.lib.client.Sound;
import electrodynamics.network.PacketTypeHandler;

public class PacketSound extends PacketED {

	public static final byte TYPE_SOUND = 0;
	public static final byte TYPE_SFX = 1;
	
	public String sound;
	
	public double x;
	public double y;
	public double z;
	
	public byte type;
	
	public PacketSound() {
		super(PacketTypeHandler.SOUND, false);
	}

	public PacketSound(String sound, double x, double y, double z, byte type) {
		super(PacketTypeHandler.SOUND, false);
		
		this.sound = sound;
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	public PacketSound(Sound sound, double x, double y, double z) {
		super(PacketTypeHandler.SOUND, false);
		
		this.sound = sound.getTag();
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = TYPE_SOUND;
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		this.sound = data.readUTF();
		this.x = data.readDouble();
		this.y = data.readDouble();
		this.z = data.readDouble();
		this.type = data.readByte();
	}

	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		dos.writeUTF(sound);
		dos.writeDouble(this.x);
		dos.writeDouble(this.y);
		dos.writeDouble(this.z);
		dos.writeByte(type);
	}

	@Override
	public void execute(INetworkManager network, Player player, Side side) {
		if (type == TYPE_SOUND) {
			Electrodynamics.proxy.handleSoundPacket(sound, x, y, z);
		} else if (type == TYPE_SFX) {
			Electrodynamics.proxy.handleSFXPacket(Integer.parseInt(sound), x, y, z);
		}
	}
	
}
