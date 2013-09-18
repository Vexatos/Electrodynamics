package electrodynamics.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.core.CoreUtils;
import electrodynamics.network.PacketUtils;
import electrodynamics.network.packet.Packet132TileEntityDataType;

public abstract class TileEntityEDRoot extends TileEntity {

	public void onBlockActivated(EntityPlayer player) {

	}

	public void onBlockAdded(EntityPlayer player, ForgeDirection side) {

	}

	public void onNeighborUpdate() {

	}

	public AxisAlignedBB getAABB() {
		return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
	
	public void onBlockBreak() {

	}

	public void onUpdatePacket(NBTTagCompound nbt) {

	}
	
	public void onDescriptionPacket(NBTTagCompound nbt) {

	}

	public void sendUpdatePacket(NBTTagCompound nbt) {
		sendUpdatePacket(new Packet132TileEntityData(xCoord, yCoord, zCoord, Packet132TileEntityDataType.UPDATE, nbt));
	}

	public void sendUpdatePacket(Packet packet) {
		PacketUtils.sendToPlayers(packet, this);
	}
	
	public void getDescriptionForClient(NBTTagCompound nbt) {

	}

	public void updateEntityClient() {

	}

	public void updateEntityServer() {

	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		getDescriptionForClient(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, Packet132TileEntityDataType.LOAD, nbt);
	}

	@Override
	public void updateEntity() {
		if (CoreUtils.isServer(this.worldObj)) {
			updateEntityServer();
		}
		else 
		if (CoreUtils.isClient(this.worldObj)) {
			updateEntityClient();
		}
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		if(pkt.actionType == Packet132TileEntityDataType.LOAD) 
			onDescriptionPacket(pkt.data);
		else
		if(pkt.actionType == Packet132TileEntityDataType.UPDATE) 
			onUpdatePacket(pkt.data);
		
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

	public void updateClient() {
		this.sendUpdatePacket(getDescriptionPacket());
	}
	
}
