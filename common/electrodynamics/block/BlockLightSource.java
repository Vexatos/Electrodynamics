package electrodynamics.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockLightSource extends Block {

	public BlockLightSource(int id) {
		super(id, Material.air);
		setLightValue(1.0F);
		setBlockBounds(0, 0, 0, 0, 0, 0);
	}
	
	@Override
	public int idDropped(int id, Random random, int meta) {
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isAirBlock(World world, int x, int y, int z) {
		return true;
	}
	
}
