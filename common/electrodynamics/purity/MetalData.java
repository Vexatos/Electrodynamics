package electrodynamics.purity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MetalData {
		
	public ItemStack component;
	
	public double ratio;
	
	/** Total amount of metal ingots in whatever factory created this data */
	private int total = 1;
	
	public MetalData() {
		
	}
	
	public MetalData(ItemStack id, double ratio) {
		this.component = id;
		this.ratio = ratio;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.component = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("component"));
		this.ratio = nbt.getDouble("ratio");
		this.total = nbt.getInteger("total");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound componentTag = new NBTTagCompound();
		this.component.writeToNBT(componentTag);
		nbt.setCompoundTag("component", componentTag);
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
	public boolean equals(Object other) {
		if (other instanceof MetalData) {
			return equals(other);
		}
		return false;
	}
	
	public boolean equals(MetalData other) {
		return (this.component.isItemEqual(other.component) && this.ratio == other.ratio);
	}
	
	@Override
	public String toString() {
		return this.component.getDisplayName() + ": " + this.ratio;
	}
	
}