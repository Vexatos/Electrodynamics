package electrodynamics.configuration;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import electrodynamics.configuration.annotation.EDXProperty;
import electrodynamics.core.EDLogger;
import electrodynamics.network.packet.PacketPayload;
import electrodynamics.network.packet.PacketPayload.IPayloadReceptor;
import electrodynamics.network.packet.PacketPayload.PayloadType;

public class ConfigurationSettings {

	/* ElMag armor ability settings */
	@EDXProperty(category = ConfigurationHandler.CATEGORY_ELMAG)
	public static double MAGNETIC_RANGE = 3D;
	
	@EDXProperty(category = ConfigurationHandler.CATEGORY_ELMAG)
	public static double MAGNETIC_ATTRACTION_SPEED = 0.8D;
	
	/* Audio settings */
	@EDXProperty(category = ConfigurationHandler.CATEGORY_SOUND)
	public static boolean VOIDSTONE_AMBIENT_SOUND = true;
	
	/* General settings */
	@EDXProperty(category = ConfigurationHandler.CATEGORY_SETTINGS)
	public static boolean SHOW_LOCALIZATION_ERRORS = false;

	@EDXProperty(category = ConfigurationHandler.CATEGORY_SETTINGS)
	public static boolean OLD_SHAKING_METHOD = false;
	
	public static void sendSettings(EntityPlayer player) {
		PacketPayload payload = new PacketPayload(PayloadType.CONFIG, 1, 0, 2, 0, 0);
		payload.setByte(0, (byte) (OLD_SHAKING_METHOD == false ? 0 : 1));
		payload.setDouble(0, MAGNETIC_RANGE);
		payload.setDouble(1, MAGNETIC_ATTRACTION_SPEED);
		PacketDispatcher.sendPacketToPlayer(payload.makePacket(), (Player) player);
	}
	
	public static void readSettings(PacketPayload packet) {
		EDLogger.info("Received configuration packet from server. Setting configuration settings.");
		
		OLD_SHAKING_METHOD = packet.bytePayload[0] == 0 ? false : true;
		MAGNETIC_RANGE = packet.doublePayload[0];
		MAGNETIC_ATTRACTION_SPEED = packet.doublePayload[1];
	}
	
}
