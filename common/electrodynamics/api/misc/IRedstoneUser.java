package electrodynamics.api.misc;

import net.minecraftforge.common.ForgeDirection;

public interface IRedstoneUser {

	public void updateSignalStrength(ForgeDirection side, int strength);
	
	public int getSignalStrength();
	
}
