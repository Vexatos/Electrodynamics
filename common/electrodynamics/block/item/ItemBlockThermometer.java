package electrodynamics.block.item;

import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Machine;
import electrodynamics.lib.client.Sound;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.packet.PacketSound;
import electrodynamics.tileentity.TileEntityTreetap;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemBlockThermometer extends ItemBlock {

	private Icon texture;
	
	public ItemBlockThermometer(int id) {
		super(id);
	}

	@Override
	public Icon getIconFromDamage(int meta) {
		return texture;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		ForgeDirection sideForge = ForgeDirection.getOrientation(side);
		int xOrig = x - sideForge.offsetX;
		int yOrig = y - sideForge.offsetY;
		int zOrig = z - sideForge.offsetZ;
		
		if (sideForge != ForgeDirection.UP) {
			return false;
		}
		
		if (world.getBlockId(xOrig, yOrig, zOrig) != BlockIDs.BLOCK_MACHINE_ID && world.getBlockMetadata(xOrig, yOrig, zOrig) != Machine.SINTERING_FURNACE.ordinal()) {
			return false;
		}
		
		return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
	}
	
	@Override
	public int getSpriteNumber() {
		return 1;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.texture = register.registerIcon(ModInfo.ICON_PREFIX + "tool/thermometer");
	}
	
}
