package electrodynamics.purity;

import net.minecraft.nbt.NBTTagCompound;

public class MetalData {
		
	public String metalID;
	
	public double ratio;
	
	/** Total amount of metal ingots in whatever factory created this data */
	private int total = 1;
	
	public MetalData() {
		
	}
	
	public MetalData(String id, double ratio) {
		this.metalID = id;
		this.ratio = ratio;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.metalID = nbt.getString("metalID");
		this.ratio = nbt.getDouble("ratio");
		this.total = nbt.getInteger("total");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("metalID", this.metalID);
		nbt.setDouble("ratio", this.ratio);
		nbt.setInteger("total", this.total);
	}
	
	protected void setTotal(int total) {
		this.total = total;
	}
	
	public int getTotal() {
		return this.total;
	}
	
	@Override
	public String toString() {
		return this.metalID + ": " + this.ratio;
	}
	
}