package electrodynamics.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import electrodynamics.core.CreativeTabED;
import electrodynamics.item.EDItems;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.ItemIDs;
import electrodynamics.util.ItemUtil;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockWormwood extends BlockFlower implements IPlantable {

	public static final BiomeDictionary.Type[] WORMWOOD_VALID_BIOMES = new BiomeDictionary.Type[] { BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.MOUNTAIN };
	public static final BiomeDictionary.Type[] DRY_WORMWOOD_VALID_BIOMES = new BiomeDictionary.Type[] { BiomeDictionary.Type.DESERT, BiomeDictionary.Type.WASTELAND };
	
	public static final int GROWN_NORMAL = 7;
	public static final int GROWN_DRIED = 9;
	public static final int optAdd = 3;

	private Icon[] textures;

	public BlockWormwood(int id) {
		super( id );
		this.setTickRandomly( true );
		setCreativeTab( CreativeTabED.resource );
	}
	
	public void fertilize(World world, int x, int y, int z){
		int metadata = world.getBlockMetadata( x, y, z );
		if( !isFullyGrown( metadata ) ) { // not fully grown
			int type = getTypeForBiome( world.getBiomeGenForCoords( x, z ) );
			if( type != -1 ) {
				int nextStage = getNextGrowthStage( type, metadata );
				world.setBlockMetadataWithNotify( x, y, z, nextStage, 2 );
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.getBlockLightValue(x, y, z) >= 9) {
            if (random.nextInt(4) == 0){
                fertilize(world, x, y, z);
            }
        }
		super.updateTick( world, x, y, z, random );
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if( isFullyGrown( meta ) )
			return textures[meta - optAdd];
		return textures[meta];
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int id) {
		return id == Block.sand.blockID || super.canThisPlantGrowOnThisBlockID(id);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		dropBlockAsItem(world, x, y, z, id, meta);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int id, int metadata) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int type = getTypeForBiome( world.getBiomeGenForCoords( x, z ) );
		Random random = new Random();

		if (type == 1 & isFullyGrown(metadata)) { // sap, only when fully grown.
			ret.add(ItemUtil.getAndResize(Component.SAP.toItemStack(), random.nextInt(1)));
		} 
		
		if(metadata > 3) { // leaves
			ret.add(ItemUtil.getAndResize(Component.WORMWOOD_LEAF.toItemStack(), random.nextInt(4) == 0 ? 1 : 0));
		} 
		
		if(metadata > 6) { // twine
			ret.add(ItemUtil.getAndResize(Component.TWINE.toItemStack(), random.nextInt(1)));
		}
		
		ret.add(ItemUtil.getAndResize(new ItemStack(EDItems.itemWormSeed), 1));
		
		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		textures = new Icon[10];

		textures[0] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_0" );
		textures[1] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_1" );
		textures[2] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_2" );
		textures[3] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_3" );
		textures[4] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_4" );
		textures[5] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_5" );
		textures[6] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_6" );
		textures[7] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_7" );
		textures[8] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_7_dry_1" );
		textures[9] = icon.registerIcon( ModInfo.ICON_PREFIX + "world/plant/plantWormwood_7_dry_2" );
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, GROWN_NORMAL));
		list.add(new ItemStack(id, 1, GROWN_DRIED));
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Plains;
	}

	@Override
	public int getPlantID(World world, int x, int y, int z) {
		return blockID;
	}

	@Override
	public int getPlantMetadata(World world, int x, int y, int z) {
		if( world.getBlockId( x, y, z ) != this.blockID )
			return 0;
		return world.getBlockMetadata( x, y, z );
	}
	
	public static int getTypeForBiome(BiomeGenBase biome) {
		List<BiomeDictionary.Type> biomeTypes = Arrays.asList(BiomeDictionary.getTypesForBiome(biome));
		for (BiomeDictionary.Type type : WORMWOOD_VALID_BIOMES) {
			if (biomeTypes.contains(type)) {
				return 0;
			}
		}
		for (BiomeDictionary.Type type : DRY_WORMWOOD_VALID_BIOMES) {
			if (biomeTypes.contains(type)) {
				return 1;
			}
		}
		return -1;
	}

	public static boolean isFullyGrown(int metadata) {
		return metadata == GROWN_NORMAL + optAdd || metadata == GROWN_DRIED + optAdd;
	}

	public static int getNextGrowthStage(int type, int currentStage) {
		if (isFullyGrown(currentStage))
			return currentStage;
		int max = type == 0 ? GROWN_NORMAL : GROWN_DRIED;
		if (currentStage >= max - 1) {
			return max + optAdd;
		}
		return currentStage + 1;
	}

}
