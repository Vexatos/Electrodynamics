package electrodynamics.tileentity;


import electrodynamics.block.BlockGeneric;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileEntityGeneric extends TileEntity {

	private int subBlock = -1;

	public void setSubBlock(int subBlock) {
		this.subBlock = subBlock;
	}

	public int getSubBlock() {
		return subBlock;
	}

	@Override
	public boolean shouldRefresh(int oldID, int newID, int oldMeta, int newMeta, World world, int x, int y, int z) {
		return oldID != newID; // only refresh if changing IDs.
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT( nbt );
		subBlock = nbt.getInteger( "subBlock" );
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT( nbt );
		nbt.setInteger( "subBlock", subBlock );
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT( nbttagcompound );
		return new Packet132TileEntityData( this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound );
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		readFromNBT( pkt.data );
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord ); // re-render.
	}

	public static TileEntityGeneric createReplaceableTE() {
		return new TileGenericReplaceable();
	}

	// Needed to have the client synchronized.
	static class TileGenericReplaceable extends TileEntityGeneric {

		static {
			TileEntity.addMapping( TileEntityGeneric.TileGenericReplaceable.class, "tile.replaceable.generic" );
		}

		@Override
		public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
			// Get the right tile entity that will replace this one.
			BlockGeneric block = (BlockGeneric) this.getBlockType();
			int subBlock = pkt.data.getInteger( "subBlock" );
			TileEntity tile = block.createSpecificTileEntity( this.worldObj, xCoord, yCoord, zCoord, pkt.data, subBlock );
			if( tile == null )
				return;

			worldObj.setBlockTileEntity( xCoord, yCoord, zCoord, tile ); // Replace this tile entity
			tile.onDataPacket( net, pkt ); // Load the data into the tile entity
			worldObj.markBlockForRenderUpdate( xCoord, yCoord, zCoord ); // re-render.
		}
	}


}
