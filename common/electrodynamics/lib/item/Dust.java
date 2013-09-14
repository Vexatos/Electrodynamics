package electrodynamics.lib.item;

import net.minecraft.item.ItemStack;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;

public enum Dust {

	CLAY(Strings.DUST_CLAY, "dustClay"),
	COBALT(Strings.DUST_COBALT, "dustCobalt"),
	COBALT_HEXAHYDRATE(Strings.DUST_COB_HEX, "dustCobaltHexahydrate"),
	COPPER(Strings.DUST_COPPER, "dustCopper"),
	LEAD(Strings.DUST_LEAD, "dustLead"),
	LIME_PURE(Strings.DUST_LIME_PURE, "dustLimePure"),
	LIMESTONE(Strings.DUST_LIMESTONE, "dustLimestone"),
	LITHIUM(Strings.DUST_LITHIUM, "dustLithium"),
	MAGNETITE(Strings.DUST_MAGNETITE, "dustMagnetite"),
	NICKEL(Strings.DUST_NICKEL, "dustNickel"),
	SILICON(Strings.DUST_SILICON, "dustSilicon"), 
	SILICON_SMALL(Strings.DUST_SILICON_SMALL, "dustSiliconSmall"),
	SULFUR(Strings.DUST_SULFUR, "dustSulfur"),
	TELLURIUM(Strings.DUST_TELLURIUM, "dustTellurium"),
	TUNGSTEN(Strings.DUST_TUNGSTEN, "dustTungsten"),
	LODESTONE(Strings.DUST_LODESTONE, "dustLodestone"),
	VOIDSTONE(Strings.DUST_VOIDSTONE, "dustVoidstone"),
	GRAPHITE(Strings.DUST_GRAPHITE, "dustGraphite"),
	IRON(Strings.DUST_IRON, "dustIron"),
	GOLD(Strings.DUST_GOLD, "dustGold"),
	TIN(Strings.DUST_TIN, "dustTin"),
	URANIUM(Strings.DUST_URANIUM, "dustUranium"),
	SILVER(Strings.DUST_SILVER, "dustSilver");
	
	public String unlocalizedName;
	public String textureFile;
	
	private Dust(String unlocalizedName, String textureFile) {
		this.unlocalizedName = unlocalizedName;
		this.textureFile = textureFile;
	}
	
	public String getTextureFile() {
		return ModInfo.ICON_PREFIX + "dust/" + textureFile;
	}
	
	public ItemStack toItemStack() {
		return new ItemStack(ItemIDs.ITEM_DUST_ID + 256, 1, this.ordinal());
	}
	
	public static Dust get(int ordinal) {
		return Dust.values()[ordinal];
	}
	
}
