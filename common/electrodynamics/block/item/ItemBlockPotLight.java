package electrodynamics.block.item;

import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.block.EDBlocks;
import electrodynamics.interfaces.IHeatable;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Machine;
import electrodynamics.lib.client.Sound;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.packet.PacketSound;
import electrodynamics.tileentity.TileEntityPotLight;
import electrodynamics.tileentity.TileEntityTreetap;
import electrodynamics.tileentity.machine.TileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemBlockPotLight extends ItemBlock {

	private Icon texture;
	
	public ItemBlockPotLight(int id) {
		super(id);
	}

	@Override
	public Icon getIconFromDamage(int meta) {
		return texture;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float i, float d, float k) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if (id != EDBlocks.blockPotLight.blockID && id != Block.grass.blockID && id != Block.leaves.blockID) {
			Block block = Block.blocksList[id];
			
			if (block != null && !player.isSneaking()) {
				if (!block.isAirBlock(world, x, y, z) && !block.hasTileEntity(meta) && block.isBlockSolid(world, x, y, z, side)) {
					world.setBlock(x, y, z, EDBlocks.blockPotLight.blockID);
					
					TileEntityPotLight tile = (TileEntityPotLight) world.getBlockTileEntity(x, y, z);
					
					if (tile != null) {
						tile.setMimickBlock(id, meta);
						tile.hasLight[side] = true;
						tile.requiresUpdate = true;
						--stack.stackSize;
					}
				}
			}
		} else {
			TileEntityPotLight tile = (TileEntityPotLight) world.getBlockTileEntity(x, y, z);
			
			if (tile != null) {
				if (tile.hasLight[side] && player.isSneaking()) {
					tile.hasLight[side] = false;
					tile.requiresUpdate = true;
					++stack.stackSize;
					
					if (!tile.hasLight()) {
						world.setBlock(x, y, z, tile.mimicID, tile.mimicMeta, 2);
					}
				} else {
					tile.hasLight[side] = true;
					tile.requiresUpdate = true;
					--stack.stackSize;
				}
			}
		}

		return true;
	}
	
}
