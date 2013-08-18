package electrodynamics.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.network.packet.PacketClientData;
import electrodynamics.network.packet.PacketED;
import electrodynamics.network.packet.PacketFX;
import electrodynamics.network.packet.PacketInitializeMBS;
import electrodynamics.network.packet.PacketInvalidateMBS;
import electrodynamics.network.packet.PacketJump;
import electrodynamics.network.packet.PacketKeyPress;
import electrodynamics.network.packet.PacketSound;
import electrodynamics.network.packet.PacketUpdateDragged;
import electrodynamics.network.packet.PacketUpdateHeld;

public enum PacketTypeHandler {

	KEY(PacketKeyPress.class),
	JUMP(PacketJump.class),
	FX(PacketFX.class),
	SOUND(PacketSound.class),
	MBS_INIT(PacketInitializeMBS.class),
	MBS_INVALIDATE(PacketInvalidateMBS.class),
	CLIENT_DATA(PacketClientData.class),
	UPDATE_HELD(PacketUpdateHeld.class),
	UPDATE_DRAG(PacketUpdateDragged.class);
	
	private Class<? extends PacketED> clazz;
	
	private PacketTypeHandler(Class<? extends PacketED> clazz) {
		this.clazz = clazz;
	}
	
	public static PacketED buildPacket(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int id = bis.read();
		DataInputStream dis = new DataInputStream(bis);
		
		PacketED packet = null;
		
		try {
			packet = values()[id].clazz.newInstance();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		packet.readByteStream(dis);
		
		return packet;
	}
	
	public static PacketED buildPacket(PacketTypeHandler type) {
		PacketED packet = null;
		
		try {
			packet = type.clazz.newInstance();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return packet;
	}
	
	public static Packet fillPacket(PacketED packet) {
		byte[] data = packet.writeToByteStream();
		
		Packet250CustomPayload packet250 = new Packet250CustomPayload();
		packet250.channel = ModInfo.GENERIC_MOD_ID;
		packet250.data = data;
		packet250.length = data.length;
		packet250.isChunkDataPacket = packet.isChunkPacket;
		
		return packet250;
	}
	
	public static NBTTagCompound readNBTTagCompound(DataInputStream par0DataInputStream) throws IOException {
		short short1 = par0DataInputStream.readShort();

		if (short1 < 0) {
			return null;
		} else {
			byte[] abyte = new byte[short1];
			par0DataInputStream.readFully(abyte);
			return CompressedStreamTools.decompress(abyte);
		}
	}

	public static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutputStream par1DataOutputStream) throws IOException {
		if (par0NBTTagCompound == null) {
			par1DataOutputStream.writeShort(-1);
		} else {
			byte[] abyte = CompressedStreamTools.compress(par0NBTTagCompound);
			par1DataOutputStream.writeShort((short) abyte.length);
			par1DataOutputStream.write(abyte);
		}
	}
	
	/* Extra packet dispatch methods */
	@SuppressWarnings("unchecked")
	public static void sendPacketToAllInDimensionExceptSender(EntityPlayer player, Packet packet, int dimId) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (server != null) {
			ArrayList<EntityPlayer> onlinePlayers = (ArrayList<EntityPlayer>) MinecraftServer.getServerConfigurationManager(server).playerEntityList;
			for (int j = 0; j < onlinePlayers.size(); ++j) {
				EntityPlayerMP entityplayermp = (EntityPlayerMP) onlinePlayers.get(j);

				if (entityplayermp.dimension == dimId && !(entityplayermp.getEntityName().equals(player.getEntityName()))) {
					entityplayermp.playerNetServerHandler.sendPacketToPlayer(packet);
				}
			}
		} else {
			FMLLog.fine("Attempt to send packet to all in dimension without a server instance available");
		}
	}
	
}
