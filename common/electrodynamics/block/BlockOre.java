package electrodynamics.block;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.client.render.block.RenderBlockOre;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.util.BlockUtil;
import electrodynamics.util.math.BlockCoord;

public class BlockOre extends Block {

	@SideOnly(Side.CLIENT)
	public static Map<String, Icon[]> mimicCache = new HashMap<String, Icon[]>();
	
	public static Set<Integer> mimicBlacklist = new HashSet<Integer>();
	
	static {
		mimicBlacklist.add(Block.bedrock.blockID);
		mimicBlacklist.add(Block.oreCoal.blockID);
		mimicBlacklist.add(Block.oreDiamond.blockID);
		mimicBlacklist.add(Block.oreEmerald.blockID);
		mimicBlacklist.add(Block.oreGold.blockID);
		mimicBlacklist.add(Block.oreIron.blockID);
		mimicBlacklist.add(Block.oreLapis.blockID);
		mimicBlacklist.add(Block.oreNetherQuartz.blockID);
		mimicBlacklist.add(Block.oreRedstone.blockID);
		mimicBlacklist.add(Block.oreRedstoneGlowing.blockID);
		mimicBlacklist.add(Block.furnaceBurning.blockID);
		mimicBlacklist.add(Block.furnaceIdle.blockID);
		mimicBlacklist.add(Block.silverfish.blockID);
		mimicBlacklist.add(Block.stoneBrick.blockID);
		mimicBlacklist.add(Block.coalBlock.blockID);
	}
	
    public static final float DEFAULT_RESISTANCE = 5F;
    public static final float DEFAULT_HARDNESS = 3F;

	public Icon[] textures;
	public Icon[] oreOnly;
	
	public Icon voidstoneTexture;

	public Icon oreTransparency;
	
	public Icon darkOre;
	
	@SideOnly(Side.CLIENT)
	public long soundDelay;
	
	public BlockOre(int i) {
		super(i, Material.rock);
		setHardness(DEFAULT_HARDNESS);
		setResistance(DEFAULT_RESISTANCE);
		setCreativeTab(CreativeTabED.resource);
	}

	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return true;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return Ore.get(meta).blockHardness;
	}
	
	@Override
	public int idDropped(int meta, Random random, int j) {
		return BlockIDs.BLOCK_ORE_ID;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 1;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		for (Ore ore : Ore.values()) {
			if (ore != Ore.JUNK) {
				subItems.add(ore.toItemStack());
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta) {
		if (FMLClientHandler.instance().getClient().theWorld != null) { // Seriously needed...? :|
			String coords = x + "_" + y + "_" + z + "_" + FMLClientHandler.instance().getClient().theWorld.provider.dimensionId;
			
			if (mimicCache.containsKey(coords)) {
				mimicCache.remove(coords);
			}
		}
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		boolean cache = false;
		int[] blockInfo = null;
		Icon[] blockIcons = null;
		String coords = x + "_" + y + "_" + z + "_" + FMLClientHandler.instance().getClient().theWorld.provider.dimensionId;
		
		if (mimicCache.containsKey(coords)) {
			blockIcons = mimicCache.get(coords);
		} else {
			blockInfo = BlockUtil.getRandomBlockOnSide(world, x, y, z, BlockIDs.BLOCK_ORE_ID, true, Material.rock, mimicBlacklist.toArray(new Integer[mimicBlacklist.size()]));
			cache = true;
		}
		
		if (meta != Ore.VOIDSTONE.ordinal()) {
			if (blockInfo != null) {
				if (blockInfo.length == 2) {
					Block block = Block.blocksList[blockInfo[0]];
					if (cache) {
						blockIcons = new Icon[ForgeDirection.VALID_DIRECTIONS.length];
						for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
							blockIcons[dir.ordinal()] = block.getIcon(dir.ordinal(), blockInfo[1]);
							mimicCache.put(coords, blockIcons);
						}
					}
					return block.getIcon(side, blockInfo[1]);
				}
			} else if (blockIcons != null) {
				if (blockIcons.length == 6) {
					return blockIcons[side];
				}
			}
		}
		
		return getIcon(side, meta);
	}
	
	@Override
	public Icon getIcon(int side, int metadata) {
		return (metadata == Ore.VOIDSTONE.ordinal() ? oreTransparency : textures[metadata]);
	}

	@Override
	public int getRenderType() {
		return RenderBlockOre.renderID;
	}
	
	@Override
	public void registerIcons(IconRegister registry) {
		textures = new Icon[Ore.values().length];
		oreOnly = new Icon[Ore.values().length];
		
		for (int i = 1; i < Ore.values().length; i++) {
			textures[i] = registry.registerIcon(Ore.get(i).getTextureFile());
			oreOnly[i] = registry.registerIcon(Ore.get(i).getOreTexture());
		}
		
		//TODO Fix this!
		voidstoneTexture = registry.registerIcon(Ore.VOIDSTONE.getTextureFile() + "Effect");
		oreTransparency = registry.registerIcon(ModInfo.ICON_PREFIX + "world/ore/oreTransparent");
		darkOre = registry.registerIcon(ModInfo.ICON_PREFIX + "world/ore/darkStoneOre");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (world.getBlockMetadata(x, y, z) == Ore.VOIDSTONE.ordinal()) {
			if (ConfigurationSettings.VOIDSTONE_AMBIENT_SOUND) {
				//Hard coded limit of ten blocks for sound to play.
				final int DISTANCE_LIMIT = 10;
				double distanceToPlayer = Minecraft.getMinecraft().thePlayer.getDistanceSq(x, y, z);
				
				if (distanceToPlayer <= DISTANCE_LIMIT) {
					if (this.soundDelay < System.currentTimeMillis()) {
						this.soundDelay = (System.currentTimeMillis() + 5000 + (rand.nextInt(10)) * 1000);
//						world.playSound(x, y, z, Sound.VOIDSTONE_AMBIENT.getTag(), 0.75F, 1.1F, false);
					}
				}
			}
		}
	}
	
	@Override
	public int idPicked(World world, int x, int y, int z) {
		return this.blockID;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
}
