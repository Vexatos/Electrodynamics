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
import electrodynamics.tileentity.machine.TileEntityMachine;
import electrodynamics.util.PlayerUtil;

public class BlockAnvil extends BlockContainer {

	public static final float AABB_MIN = 0.150F;
	public static final float AABB_MAX = 0.850F;
	
	public BlockAnvil(int id) {
		super(id, Material.iron);
		setHardness(2F);
		setResistance(10F);
		setCreativeTab(CreativeTabED.block);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack itemStack) {
		TileEntity tile = world.getBlockTileEntity(i, j, k);

		if (tile != null) {
			((TileEntityMachine) tile).rotation = PlayerUtil.determine3DOrientation_F(world, i, j, k, entityLiving);
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);

		if (tile != null && tile instanceof TileEntityMachine && ((TileEntityMachine)tile).rotation != null) {
			ForgeDirection rotation = ((TileEntityMachine)tile).rotation;
			
			if (rotation == ForgeDirection.WEST || rotation == ForgeDirection.EAST) {
				this.setBlockBounds(AABB_MIN, 0.0F, 0.0F, AABB_MAX, 1.0F, 1.0F);
				return;
			} else if (rotation == ForgeDirection.NORTH || rotation == ForgeDirection.SOUTH) {
				this.setBlockBounds(0.0F, 0.0F, AABB_MIN, 1.0F, 1.0F, AABB_MAX);
				return;
			}
		}
		
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
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
		return new TileEntityAnvil();
	}
	
}
