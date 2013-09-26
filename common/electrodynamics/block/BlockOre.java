package electrodynamics.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.client.render.block.RenderBlockOre;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.client.Sound;
import electrodynamics.lib.core.ModInfo;

public class BlockOre extends Block {

    public static final float DEFAULT_RESISTANCE = 5F;
    public static final float DEFAULT_HARDNESS = 3F;

	public Icon[] textures;
	
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

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return Ore.get(meta).blockHardness;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return meta == Ore.VOIDSTONE.ordinal() ? 3 : 0;
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
			subItems.add(ore.toItemStack());
		}
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
		
		for (int i = 0; i < Ore.values().length; i++) {
			textures[i] = registry.registerIcon(Ore.get(i).getTextureFile());
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
