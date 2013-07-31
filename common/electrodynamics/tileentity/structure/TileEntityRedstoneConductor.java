package electrodynamics.tileentity.structure;

import electrodynamics.api.misc.IRedstoneUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityRedstoneConductor extends TileEntityStructure {

	@Override
	public void onBlockUpdate() {
		if (this.isValidStructure() && !this.isCentralTileEntity()) {
			TileEntityStructure central = this.getCentralTileEntity();
			
			if (central != null && central instanceof IRedstoneUser) {
				((IRedstoneUser)central).updateSignalStrength(ForgeDirection.UNKNOWN, this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) ? 15 : 0);
			}
		}
	}
	
	@Override
	public boolean onBlockActivatedBy(EntityPlayer player, int side, float xOff, float yOff, float zOff) {
		return false;
	}

}
