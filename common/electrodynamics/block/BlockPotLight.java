package electrodynamics.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.core.CreativeTabED;
import electrodynamics.tileentity.TileEntityPotLight;
import electrodynamics.util.BlockUtil;

public class BlockPotLight extends BlockContainer {

	public BlockPotLight(int id) {
		super(id, Material.rock);
		setCreativeTab(CreativeTabED.tool);
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
	public float getBlockHardness(World world, int x, int y, int z) {
		TileEntityPotLight tile = (TileEntityPotLight) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			return tile.getMimickedBlock().blockHardness;
		}
		
		return 1.0F;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		super.breakBlock(world, x, y, z, id, meta);
		
		TileEntityPotLight tile = (TileEntityPotLight) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			for (boolean bool : tile.hasLight) {
				if (bool) {
					BlockUtil.dropItemFromBlock(world, x, y, z, new ItemStack(EDBlocks.blockPotLight), new Random());
				}
			}
		}
		
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int[] coords = BlockUtil.getCoordsOnSide(world, x, y, z, dir);
			
			if (world.getBlockId(coords[0], coords[1], coords[2]) == EDBlocks.blockLightSource.blockID) {
				world.setBlockToAir(coords[0], coords[1], coords[2]);
			}
		}
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityPotLight tile = (TileEntityPotLight) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			Block mimicked = tile.getMimickedBlock();
			
			if (mimicked != null) {
				return mimicked.getIcon(side, tile.mimicMeta);
			}
		}
		
		return Block.stone.getIcon(0, 0);
	}
	
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		TileEntityPotLight tile = (TileEntityPotLight) world.getBlockTileEntity(x, y, z);
		
		if (tile != null) {
			return !tile.hasLight[side.ordinal()];
		}
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityPotLight();
	}

}
