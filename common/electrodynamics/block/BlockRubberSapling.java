package electrodynamics.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.world.gen.feature.FeatureHandler;
import electrodynamics.world.gen.feature.FeatureRubberTree;

public class BlockRubberSapling extends BlockSapling {

	private Icon texture;
	
	public BlockRubberSapling(int id) {
		super(id);
		setCreativeTab(CreativeTabED.resource);
	}
	
	@Override
	public void markOrGrowMarked(World world, int x, int y, int z, Random random) {
		int l = world.getBlockMetadata(x, y, z);

		if ((l & 8) == 0) {
			world.setBlockMetadataWithNotify(x, y, z, l | 8, 4);
		} else {
			((FeatureRubberTree)FeatureHandler.getInstance().getFeatureForName("Rubber Tree")).grow(world, x, y, z, random);
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return texture;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.texture = register.registerIcon(ModInfo.ICON_PREFIX + "world/plant/saplingRubber_sparse");
	}
	
}
