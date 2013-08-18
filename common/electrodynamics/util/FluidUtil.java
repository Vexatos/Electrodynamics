package electrodynamics.util;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class FluidUtil {

	public static boolean areSameFluid(FluidStack ls1, FluidStack ls2) {
		return ls1.isFluidEqual(ls2);
	}
	
	public static boolean canStoreFluid(IFluidTank tank, FluidStack fluid) {
		if (tank.getFluid() == null || tank.getFluidAmount() == 0) {
			return true;
		} else if (areSameFluid(tank.getFluid(), fluid) && tank.getFluidAmount() + fluid.amount <= tank.getCapacity()) {
			return true;
		} else {
			return false;
		}
	}
	
}
