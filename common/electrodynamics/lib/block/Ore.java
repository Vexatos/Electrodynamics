package electrodynamics.lib.block;

import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;
import net.minecraft.item.ItemStack;

public enum Ore {

	CHALCOPYRITE( Strings.ORE_CHALCOPYRITE_NAME, "oreChalcopyrite" ),
	COBALTITE( Strings.ORE_COBALTITE_NAME, "oreCobaltite" ),
	GALENA( Strings.ORE_GALENA_ORE, "oreGalena" ),
	MAGNETITE( Strings.ORE_MAGNETITE_NAME, "oreMagnetite" ),
	NICKEL( Strings.ORE_NICKEL_NAME, "oreNickel" ),
	WOLFRAMITE( Strings.ORE_WOLFRAMITE_NAME, "oreWolframite" ),
	VOIDSTONE(Strings.ORE_VOIDSTONE, "oreVoidstone"),
	GRAPHITE(Strings.ORE_GRAPHITE, "oreGraphite");
	
	public String unlocalizedName;
	public String textureFile;

	public int altDropID;
	public int altDropMeta;
	public int altDropCount;
	
	public String oreDictionaryName;

	public int harvestLevel = 2;

	private Ore(String unlocalizedName, String textureFile) {
		this.unlocalizedName = unlocalizedName;
		this.textureFile = textureFile;
	}

	private Ore(String unlocalizedName, String localizedName, String alt) {
		this( unlocalizedName, localizedName );
		this.oreDictionaryName = alt;
	}

	private Ore(String unlocalizedName, String localizedName, int harvestLevel) {
		this( unlocalizedName, localizedName );
		this.harvestLevel = harvestLevel;
	}

	private Ore(String unlocalizedName, String localizedName, int harvestLevel, int dropID, int dropMeta, int dropCount) {
		this( unlocalizedName, localizedName );
		this.harvestLevel = harvestLevel;
		this.altDropID = dropID;
		this.altDropMeta = dropMeta;
		this.altDropCount = dropCount;
	}
	
	public String getTextureFile() {
		return ModInfo.ICON_PREFIX + "world/ore/" + textureFile;
	}

	public ItemStack toItemStack() {
		return new ItemStack( BlockIDs.BLOCK_ORE_ID, 1, this.ordinal() );
	}

	public static Ore get(int ordinal) {
		return Ore.values()[ordinal];
	}

}
