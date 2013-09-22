package electrodynamics.lib.block;

import electrodynamics.block.BlockOre;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public enum Ore {

	CHALCOPYRITE(Strings.ORE_CHALCOPYRITE_NAME, "oreChalcopyrite"),
	COBALTITE(Strings.ORE_COBALTITE_NAME, "oreCobaltite"),
	GALENA(Strings.ORE_GALENA_ORE, "oreGalena"),
	MAGNETITE(Strings.ORE_MAGNETITE_NAME, "oreMagnetite"),
	NICKEL(Strings.ORE_NICKEL_NAME, "oreNickel"),
	WOLFRAMITE(Strings.ORE_WOLFRAMITE_NAME, "oreWolframite"),
	VOIDSTONE(Strings.ORE_VOIDSTONE, "oreVoidstone"),
	GRAPHITE(Strings.ORE_GRAPHITE, "oreGraphite");

    // Because enums won't let me do (constructer.method) :(
    static {
        // Harvest Levels
        CHALCOPYRITE.setHarvestLevel(EnumToolMaterial.STONE.getHarvestLevel());
        GALENA.setHarvestLevel(EnumToolMaterial.STONE.getHarvestLevel());
        WOLFRAMITE.setHarvestLevel(EnumToolMaterial.EMERALD.getHarvestLevel());

        // Hardness
        GRAPHITE.setHardness(3F);
        WOLFRAMITE.setHardness(4F);
        VOIDSTONE.setHardness(24);
    }

	public String unlocalizedName;
	public String textureFile;

    public String oreDictionaryName;

    public float blockHardness = BlockOre.DEFAULT_HARDNESS;
    public float blockResistance = BlockOre.DEFAULT_RESISTANCE;

	public int altDropID;
	public int altDropMeta;
	public int altDropCount;

	public int harvestLevel = 2;

	private Ore(String unlocalizedName, String textureFile) {
		this.unlocalizedName = unlocalizedName;
		this.textureFile = textureFile;
	}

	public Ore setAlternateDrops(int id, int meta, int count) {
        this.altDropID = id;
        this.altDropMeta = meta;
        this.altDropCount = count;
        return this;
    }

    private Ore setOreDictionaryName(String name) {
        this.oreDictionaryName = name;
        return this;
    }

    private Ore setHarvestLevel(int level) {
        this.harvestLevel = level;
        return this;
    }

    private Ore setHardness(float hardness) {
        this.blockHardness = hardness;
        return this;
    }

    private Ore setResistance(float resistance) {
        this.blockResistance = resistance;
        return this;
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
