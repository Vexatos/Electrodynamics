package electrodynamics.purity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AlloyStack {

	private ItemStack itemStack;
	
	public AlloyStack(ItemStack stack) {
		this.itemStack = stack;
		
		setBrittle(false); // Default
	}
	
	public ItemStack getItem() {
		return this.itemStack;
	}
	
	public Type getType() {
		return Type.values()[this.itemStack.getItemDamage()];
	}
	
	public void setMetals(MetalData[] metals) {
		if (itemStack.stackTagCompound == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		
		if (metals != null) {
			NBTTagList list = new NBTTagList();
			
			for (MetalData data : metals) {
				NBTTagCompound nbt = new NBTTagCompound();
				data.writeToNBT(nbt);
				list.appendTag(nbt);
			}
			
			itemStack.stackTagCompound.setTag("Metals", list);
		}
	}
	
	public MetalData[] getMetals() {
		if (itemStack.stackTagCompound == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		
		if (itemStack.stackTagCompound.hasKey("Metals")) {
			NBTTagList list = itemStack.stackTagCompound.getTagList("Metals");
			MetalData[] metals = new MetalData[list.tagCount()];
			
			for (int i=0; i<list.tagCount(); i++) {
				NBTTagCompound nbt = (NBTTagCompound) list.tagAt(i);
				MetalData metal = new MetalData();
				metal.readFromNBT(nbt);
				metals[i] = metal;
			}
			
			return metals;
		}
		
		return null;
	}
	
	public void setBrittle(boolean value) {
		if (itemStack.stackTagCompound == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		
		itemStack.stackTagCompound.setBoolean("brittle", value);
	}
	
	public boolean getBrittle() {
		if (itemStack.stackTagCompound == null) {
			return false;
		}
		
		return itemStack.stackTagCompound.getBoolean("brittle");
	}
	
	public enum Type {
		DUST, INGOT;
	}
	
}
