package electrodynamics.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import electrodynamics.network.packet.PacketED;

public class PacketUpdateConfig extends PacketED {

	//TODO
	
	public PacketUpdateConfig() {
		super(PacketTypeHandler.UPDATE_CONFIG, false);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(INetworkManager network, Player player, Side side) {
		// TODO Auto-generated method stub
		
	}

}
