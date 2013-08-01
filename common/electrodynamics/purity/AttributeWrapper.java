package electrodynamics.purity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AttributeWrapper {

	private List<Attribute> attributes = new ArrayList<Attribute>();
	
	public AttributeWrapper() {
		
	}
	
	public AttributeWrapper(Attribute ... attributes) {
		for (Attribute attribute : attributes) {
			this.attributes.add(attribute);
		}
	}

	public AttributeWrapper add(Attribute attribute) {
		this.attributes.add(attribute);
		return this;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		for (Attribute attribute : this.attributes) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			attribute.writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		nbt.setTag("Attributes", list);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Attributes")) {
			NBTTagList list = nbt.getTagList("Attributes");
			for (int i=0; i<list.tagCount(); i++) {
				NBTTagCompound nbtTag = (NBTTagCompound) list.tagAt(i);
				Attribute attribute = new Attribute();
				attribute.readFromNBT(nbtTag);
				this.attributes.add(attribute);
			}
		}
	}
	
	public void writeToDataStream(DataOutputStream dos) throws IOException {
		dos.writeInt(this.attributes.size());
		for (Attribute attribute : this.attributes) {
			attribute.writeToDataStream(dos);
		}
	}
	
	public void readFromDataStream(DataInputStream dis) throws IOException {
		this.attributes = new ArrayList<Attribute>();
		for (int i=0; i<dis.readInt(); i++) {
			Attribute attribute = new Attribute();
			attribute.readFromDataStream(dis);
			this.attributes.add(attribute);
		}
	}
	
}
