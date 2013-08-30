package electrodynamics.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.api.tool.ITool;
import electrodynamics.api.tool.ToolType;
import electrodynamics.client.render.block.RenderBlockStructure;
import electrodynamics.core.CreativeTabED;
import electrodynamics.interfaces.IAcceptsTool;
import electrodynamics.item.EDItems;
import electrodynamics.lib.block.StructureComponent;
import electrodynamics.tileentity.structure.TileEntityStructure;
import electrodynamics.world.TickHandlerMBS;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Set;

/**
 * Blocks used for MBS
 */
public class BlockStructure extends BlockGeneric implements IAcceptsTool {

	public BlockStructure(int blockID) {
		super( blockID, Material.iron );
		setHardness( 3.0F );
		setCreativeTab( CreativeTabED.block );
	}

	//TODO Move this somewhere better
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		
		if (tile != null) {
			if(tile instanceof TileEntityStructure) {
				if (StructureComponent.values()[((TileEntityStructure)tile).getSubBlock()] == StructureComponent.MOB_GRINDER_BLADE && ((TileEntityStructure)tile).isValidStructure()) {
					return null;
				}
			}
		}
		
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override
	protected Set<? extends SubBlock> getSubBlocks() {
		return EnumSet.allOf( StructureComponent.class );
	}

	@Override
	public TileEntity createSpecificTileEntity(World world, int x, int y, int z, NBTTagCompound nbt, int subBlock){
		return StructureComponent.createSpecificTileEntity( world, x, y, z, nbt, subBlock );
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		scheduleUpdate(world, x, y, z, false);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID) {
		TileEntity tile = world.getBlockTileEntity( x, y, z );
		
		if (tile != null) {
			if(tile instanceof TileEntityStructure) {
				((TileEntityStructure)tile).onBlockUpdate();
			}
		} else {
			Block block = Block.blocksList[neighborID];
			if( block != null && block instanceof BlockStructure ) {
				scheduleUpdate( world, x, y, z, false );
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOff, float yOff, float zOff) {
		TileEntity tile = world.getBlockTileEntity( x, y, z );
		
		if (tile != null) {
			if(tile instanceof TileEntityStructure) {
				if( player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ITool ) {
					if( player.getHeldItem().itemID != EDItems.itemSledgeHammer.itemID ) {
						scheduleUpdate( world, x, y, z, true );
					}
				}
				return ((TileEntityStructure)tile).onBlockActivatedBy( player, side, xOff, yOff, zOff );
			}
		}
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderBlockStructure.renderID;
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
	public int getLightOpacity(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity( x, y, z );
		if( tile != null && tile instanceof TileEntityStructure && (((TileEntityStructure) tile).isValidStructure() || (StructureComponent.values()[((TileEntityStructure)tile).getSubBlock()].getModel() != null)))
			return 0;
		return 255;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side) {
		Icon icon = super.getBlockTexture( access, x, y, z, side );
		if( icon == null ) // If the texture is invalid, better paint the "standard" texture.
			return getIcon( StructureComponent.MACHINE_FRAME.ordinal(), side );
		return icon;
	}

	protected void scheduleUpdate(World world, int x, int y, int z, boolean doValidate) {
		TickHandlerMBS.instance().scheduleTask( world, x, y, z, doValidate );
	}

	@Override
	public int idPicked(World world, int x, int y, int z) {
		return this.blockID;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile != null) {
			if (tile instanceof TileEntityStructure) {
				return ((TileEntityStructure)tile).getSubBlock();
			}	
		}
		
		
		return 0;
	}

	@Override
	public boolean accepts(ToolType tool) {
		return tool == ToolType.HAMMER;
	}

	@Override
	public boolean onToolUse(World world, int x, int y, int z, EntityPlayer player, ItemStack stack) {
		scheduleUpdate(world, x, y, z, true);
		return true;
	}
	
}
