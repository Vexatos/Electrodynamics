package electrodynamics.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import electrodynamics.core.CreativeTabED;
import electrodynamics.tileentity.TileEntityTreetap;

public class BlockTreetap extends BlockContainer {

	public BlockTreetap(int id) {
		super(id, Material.iron);
		setTickRandomly(true);
		setHardness(1F);
		setCreativeTab(CreativeTabED.tool);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		TileEntityTreetap tile = (TileEntityTreetap) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			if (!tile.isTapSupported()) {
				this.dropBlockAsItem_do(world, x, y, z, new ItemStack(this));
				world.setBlockToAir(x, y, z);
			}
		} else {
			this.dropBlockAsItem_do(world, x, y, z, new ItemStack(this));
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hX, float hY, float hZ) {
		TileEntityTreetap tile = (TileEntityTreetap) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.onBlockActivated(player);
		}
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntityTreetap tile = (TileEntityTreetap) world.getBlockTileEntity(x, y, z);
		
		if (tile != null) {
			switch(tile.rotation) {
			case EAST: {
				this.setBlockBounds(0.0F, 0.0F, 0.2F, 0.6F, 0.9F, 0.8F);
				break;
			}
			case SOUTH: {
				this.setBlockBounds(0.2F, 0.0F, 0.0F, 0.8F, 0.9F, 0.6F);
				break;
			}
			case WEST: {
				this.setBlockBounds(0.4F, 0.0F, 0.2F, 1.0F, 0.9F, 0.8F);
				break;
			}
			case NORTH: {
				this.setBlockBounds(0.2F, 0.0F, 0.4F, 0.8F, 0.9F, 1.0F);
				break;
			}
			default: break;
			}
		}
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		TileEntityTreetap tile = (TileEntityTreetap) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.tick();
		}
	}

	@Override
	public int getRenderType() {
		return -1;
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
		return new TileEntityTreetap();
	}

}
