package electrodynamics.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import electrodynamics.tileentity.TileEntityRSSource;

public class BlockHiddenRedstoneSource extends BlockContainer {

	public BlockHiddenRedstoneSource(int id) {
		super(id, Material.circuits);
		setBlockBounds(0, 0, 0, 0, 0, 0);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		
	}

	@Override
	public int idDropped(int id, Random random, int meta) {
		return 0;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return 15;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRSSource();
	}
	
}
