package electrodynamics.purity;

import net.minecraft.nbt.NBTTagCompound;

public class MetalData {
		
	public String metalID;
	
	public double ratio;
	
	public MetalData() {
		
	}
	
	public MetalData(String id, double ratio) {
		this.metalID = id;
		this.ratio = ratio;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.metalID = nbt.getString("metalID");
		this.ratio = nbt.getDouble("ratio");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("metalID", this.metalID);
		nbt.setDouble("ratio", this.ratio);
	}
	
	@Override
	public String toString() {
		return this.metalID + ": " + this.ratio;
	}
	
}