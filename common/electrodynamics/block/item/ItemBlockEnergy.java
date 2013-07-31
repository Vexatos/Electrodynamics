package electrodynamics.block.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.EnergyProduction;
import electrodynamics.tileentity.TileEntityEDRoot;

public class ItemBlockEnergy extends ItemBlock {

	public ItemBlockEnergy(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float i, float d, float k) {
		super.onItemUse(stack, player, world, x, y, z, side, i, d, k);
		
		ForgeDirection sideForge = ForgeDirection.getOrientation(side);
		
		x += sideForge.offsetX;
		y += sideForge.offsetY;
		z += sideForge.offsetZ;
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile != null && tile instanceof TileEntityEDRoot) {
			((TileEntityEDRoot)tile).onBlockAdded(player, sideForge.getOpposite());
		}
		
		return true;
	}
	
	@Override
	public boolean canPlaceItemBlockOnSide(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		if (stack.getItemDamage() == EnergyProduction.SOLAR_PANEL.ordinal()) {
			ForgeDirection sideForge = ForgeDirection.getOrientation(side);
			
			if (!world.isBlockSolidOnSide(x, y, z, sideForge, false) && !player.isSneaking()) {
				return false;
			}
			
			if (sideForge == ForgeDirection.DOWN) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return EDLanguage.getFormattedBlockName(EnergyProduction.get(itemstack.getItemDamage()).unlocalizedName);
	}
	
}
