package electrodynamics.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.core.CreativeTabED;
import electrodynamics.item.EDItems;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.client.Sound;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.packet.PacketSound;

public class BlockRubberWood extends Block {

	private Icon[] textures;
	
	public BlockRubberWood(int id) {
		super(id, Material.wood);
		setTickRandomly(true);
		setHardness(2.0F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(CreativeTabED.resource);
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return BlockIDs.BLOCK_RUBBER_WOOD_ID;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hX, float hY, float hZ) {
		if (!world.isRemote) {
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == EDItems.itemSpudPeeler && validTapSpot(world, x, y, z)) {
				world.setBlockMetadataWithNotify(x, y, z, side, 2);
				player.getCurrentEquippedItem().damageItem(1, player);
				
				PacketSound packet = new PacketSound(Sound.BARK_CUT, x, y, z);
				PacketDispatcher.sendPacketToAllAround(x, y, z, 32D, world.provider.dimensionId, PacketTypeHandler.fillPacket(packet));
				return true;
			}
		}
		
		return false;
	}
	
	private boolean validTapSpot(World world, int x, int y, int z) {
		return (world.getBlockId(x, y + 1, z) == BlockIDs.BLOCK_RUBBER_WOOD_ID && world.getBlockId(x, y - 1, z) == BlockIDs.BLOCK_RUBBER_WOOD_ID) && (world.getBlockId(x, y - 2, z) != BlockIDs.BLOCK_RUBBER_WOOD_ID) && (world.getBlockMetadata(x, y, z) == 1);
	}
	
	public static boolean suckLatex(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);

		if (meta >= 1 && meta <= 9) {
			world.setBlockMetadataWithNotify(x, y, z, meta - 4, 2);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.getBlockMetadata(x, y, z) < 1) {
			world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) - 4, 2);
		}
	}
	
	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isWood(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		return 150;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		if (side == 0 || side == 1) {
			return textures[0];
		}
		
		if (side == meta || side == meta - 4 || side == meta - 8) {
			return textures[2];
		}
		
		return textures[1];
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		textures = new Icon[3];
		
		textures[0] = Block.wood.getBlockTextureFromSide(0);
		textures[1] = register.registerIcon(ModInfo.ICON_PREFIX + "world/plant/woodRubber");
		textures[2] = register.registerIcon(ModInfo.ICON_PREFIX + "world/plant/woodRubber_cut");
	}
	
}
