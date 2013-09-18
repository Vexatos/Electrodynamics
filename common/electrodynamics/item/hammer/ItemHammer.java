package electrodynamics.item.hammer;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.api.tool.ITool;
import electrodynamics.api.tool.ToolType;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.tileentity.TileEntityAnvil;
import electrodynamics.util.PlayerUtil;

public class ItemHammer extends Item implements ITool {

	public ItemHammer(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabED.tool);
	}
	
	public ItemHammer(int id, int maxDamage) {
		this(id);
		setMaxDamage(maxDamage);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				if (world.getBlockId(x, y, z) == Block.anvil.blockID) {
					world.setBlock(x, y, z, BlockIDs.BLOCK_ANVIL_ID);
					((TileEntityAnvil)world.getBlockTileEntity(x, y, z)).rotation = PlayerUtil.determine3DOrientation_F(world, x, y, z, player);
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public ToolType getToolType() {
		return ToolType.HAMMER;
	}

	@Override
	public void onToolUsed(ItemStack stack, World world, int x, int y, int z, EntityPlayer player) {
		stack.damageItem(1, player);		
	}
	
}
