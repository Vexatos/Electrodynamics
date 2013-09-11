package electrodynamics.purity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import electrodynamics.lib.core.Strings;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.nbt.NBTTagCompound;

public class Attribute {

	public AttributeType attribute;
	
	public float modifier;
	
	public Attribute() {
		
	}
	
	public Attribute(AttributeType attribute, float modifier) {
		this.attribute = attribute;
		this.modifier = modifier;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setByte("attribute", (byte) this.attribute.ordinal());
		nbt.setFloat("modifier", this.modifier);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.attribute = AttributeType.values()[nbt.getByte("attribute")];
		this.modifier = nbt.getFloat("modifier");
	}
	
	public void writeToDataStream(DataOutputStream dos) throws IOException {
		dos.writeByte(this.attribute.ordinal());
		dos.writeFloat(this.modifier);
	}
	
	public void readFromDataStream(DataInputStream dis) throws IOException {
		this.attribute = AttributeType.values()[dis.readByte()];
		this.modifier = dis.readFloat();
	}
	
	public enum AttributeType {
		DURABILITY(Strings.ATTRIBUTE_DURABILITY),
		CONDUCTIVITY(Strings.ATTRIBUTE_CONDUCT),
		CRITICAL(Strings.ATTRIBUTE_CRIT),
		SHARPNESS(Strings.ATTRIBUTE_SHARP),
		RADIOACTIVE(Strings.ATTRIBUTE_RADIO),
		BLING(Strings.ATTRIBUTE_BLING),
		THERMAL_RESISTANCE(Strings.ATTRIBUTE_THERM_RESIST),
		KNOCKBACK(Strings.ATTRIBUTE_KNOCK),
		KNOCKBACK_RESISTANCE(Strings.ATTRIBUTE_KNOCK_RESIST),
		WEIGHT_REDUCTION(Strings.ATTRIBUTE_REDUC_WEIGHT),
		ATTACK_SPEED(Strings.ATTRIBUTE_ATTACK_SPEED),
		PICKUP_RADIUS(Strings.ATTRIBUTE_PICKUP_RADIUS),
		SPEED(Strings.ATTRIBUTE_SPEED),
		EFFICIENCY(Strings.ATTRIBUTE_EFFIC, EnumToolMaterial.IRON.getEfficiencyOnProperMaterial()),
		IMPLOSION_CHANCE(Strings.ATTRIBUTE_IMPLO_CHANCE),
		REDUCED_WEIGHT(Strings.ATTRIBUTE_REDUC_WEIGHT);
		
		public String unlocalizedName;
		
		public float baseValue;
		
		private AttributeType(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}
		
		private AttributeType(String unlocalizedName, float baseValue) {
			this.unlocalizedName = unlocalizedName;
			this.baseValue = baseValue;
		}
		
		public Attribute get(float modifier) {
			return new Attribute(this, modifier);
		}
	}
	
}
