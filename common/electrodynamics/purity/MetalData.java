package electrodynamics.purity;

import net.minecraft.nbt.NBTTagCompound;

public class MetalData {
		
	public String metalID;
	
	public float ratio;
	
	public MetalData() {
		
	}
	
	public MetalData(String id, float ratio) {
		this.metalID = id;
		this.ratio = ratio;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.metalID = nbt.getString("metalID");
		this.ratio = nbt.getFloat("ratio");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("metalID", this.metalID);
		nbt.setFloat("ratio", this.ratio);
	}
	
}