package electrodynamics.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import electrodynamics.client.render.block.RenderBlockDecorative;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.block.Decorative;

public class BlockDecorative extends Block {

	public Icon[] textures;
	
	public BlockDecorative(int id) {
		super(id, Material.rock);
		setHardness(1F);
		setResistance(1F);
		setCreativeTab(CreativeTabED.block);
	}
	
	@Override
	public int getRenderType() {
		return RenderBlockDecorative.renderID;
	}
	
	@Override
	public Icon getIcon(int side, int metadata) {
		return textures[metadata];
	}

	@Override
	public int damageDropped(int meta) {
		if (meta == 0) {
			return Decorative.LIMESTONE_BRITTLE.ordinal();
		}
		
		return meta;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		for (Decorative dec : Decorative.values()) {
			list.add(dec.toItemStack());
		}
	}
	
	@Override
	public void registerIcons(IconRegister registry) {
		textures = new Icon[Decorative.values().length];
		
		for (Decorative dec : Decorative.values()) {
			textures[dec.ordinal()] = registry.registerIcon(dec.getTextureFile());
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
