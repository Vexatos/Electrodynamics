package electrodynamics.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.core.ModInfo;

public class BlockRubberLeaves extends BlockLeaves {

	private Icon[] textures;

	public BlockRubberLeaves(int id) {
		super(id);
		setHardness(0.2F);
		setLightOpacity(1);
		setStepSound(soundGrassFootstep);
		setCreativeTab(CreativeTabED.resource);
	}

	@Override
	public int idDropped(int i, Random d, int k) {
		return BlockIDs.BLOCK_RUBBER_SAPLING_ID;
	}

	@Override
	public int quantityDropped(Random rand) {
		return rand.nextInt(30) == 0 ? 1 : 0;
	}
	
	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (!par1World.isRemote) {
			int j1 = 20;

			if ((par5 & 3) == 3) {
				j1 = 40;
			}

			if (par7 > 0) {
				j1 -= 2 << par7;

				if (j1 < 10) {
					j1 = 10;
				}
			}

			if (par1World.rand.nextInt(j1) == 0) {
				int k1 = this.idDropped(par5, par1World.rand, par7);
				this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(k1, 1, this.damageDropped(par5)));
			}

			j1 = 200;

			if (par7 > 0) {
				j1 -= 10 << par7;

				if (j1 < 40) {
					j1 = 40;
				}
			}
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics == true ? textures[1] : textures[0];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube() {
		return !Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		return 150;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.textures = new Icon[2];

		this.textures[0] = register.registerIcon(ModInfo.ICON_PREFIX + "world/plant/rubberLeaves_fast");
		this.textures[1] = register.registerIcon(ModInfo.ICON_PREFIX + "world/plant/rubberLeaves_fancy");
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		int i1 = par1IBlockAccess.getBlockId(par2, par3, par4);
		return !Minecraft.getMinecraft().gameSettings.fancyGraphics && i1 == this.blockID ? false : par5 == 0 && this.minY > 0.0D ? true : (par5 == 1 && this.maxY < 1.0D ? true : (par5 == 2 && this.minZ > 0.0D ? true : (par5 == 3 && this.maxZ < 1.0D ? true : (par5 == 4 && this.minX > 0.0D ? true : (par5 == 5 && this.maxX < 1.0D ? true : !par1IBlockAccess.isBlockOpaqueCube(par2, par3, par4))))));
	}
	
}
