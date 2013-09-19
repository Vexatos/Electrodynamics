package electrodynamics.lib.block;

import net.minecraft.item.ItemStack;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.Ingot;

public enum Storage {

	COPPER(Strings.STORAGE_COPPER, "blockCopper", Ingot.COPPER),
	LEAD(Strings.STORAGE_LEAD, "blockLead", Ingot.LEAD),
	LODESTONE(Strings.STORAGE_LODESTONE, "blockLodestone", Ingot.LODESTONE),
	NICKEL(Strings.STORAGE_NICKEL, "blockNickel", Ingot.NICKEL),
	TELLURIUM(Strings.STORAGE_TELLURIUM, "blockTellurium", Ingot.TELLURIUM),
	TUNGSTEN(Strings.STORAGE_TUNGSTEN, "blockTungsten", Ingot.TUNGSTEN),
	COBALT(Strings.STORAGE_COBALT, "blockCobalt", Ingot.COBALT);
	
	public String unlocalizedName;
	public String textureFile;
	
	public Ingot ingot;
	
	private Storage(String unlocalizedName, String textureFile, Ingot ingot) {
		this.unlocalizedName = unlocalizedName;
		this.textureFile = textureFile;
		this.ingot = ingot;
	}
	
	public String getTextureFile() {
		return ModInfo.ICON_PREFIX + "storage/" + textureFile;
	}
	
	public ItemStack toItemStack() {
		return new ItemStack(BlockIDs.BLOCK_STORAGE_ID, 1, this.ordinal());
	}
	
	public static Storage get(int ordinal) {
		return Storage.values()[ordinal];
	}
	
}
