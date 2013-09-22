package electrodynamics.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.core.CreativeTabED;
import electrodynamics.tileentity.TileEntityAnvil;
import electrodynamics.tileentity.machine.TileEntityForge;
import electrodynamics.tileentity.machine.TileEntityMachine;
import electrodynamics.util.PlayerUtil;

public class BlockForge extends BlockContainer {

	public BlockForge(int id) {
		super(id, Material.rock);
		setHardness(3.5F);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack itemStack) {
		TileEntity tile = world.getBlockTileEntity(i, j, k);

		if (tile != null) {
			((TileEntityMachine) tile).rotation = PlayerUtil.determine3DOrientation_F(world, i, j, k, entityLiving);
		}
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}
	
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityForge();
	}
	
}
