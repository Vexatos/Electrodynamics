package electrodynamics.core.handler;

import java.io.File;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import electrodynamics.Electrodynamics;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.core.EDLogger;

public class ConnectionHandler implements IConnectionHandler {

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		EntityPlayer eplayer = (EntityPlayer) player;
		EDLogger.info(eplayer.username + " logged in. Sending configuration settings.");
		ConfigurationSettings.sendSettings(eplayer);
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
		
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
		
	}

	@Override
	public void connectionClosed(INetworkManager manager) {

	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
		if (Electrodynamics.instance.showOptifineError) {
			FMLClientHandler.instance().getClient().thePlayer.addChatMessage(EnumChatFormatting.RED.toString() + "[Electrodynamics] OPTIFINE DETECTED: " + EnumChatFormatting.RESET.toString() + "Optifine does several things that break some of the graphical parts of this mod. If you notice any graphical glitches, disable Optifine before reporting them to me!");
			FMLClientHandler.instance().getClient().thePlayer.addChatMessage("This message will only show once.");
			
			try {
				(new File(Electrodynamics.instance.configFolder, "optifineErrorShown.flag")).createNewFile();
			} catch (IOException e) {
			}
		}
	}

}
