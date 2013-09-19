package electrodynamics.block.item;

import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.interfaces.IHeatable;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Machine;
import electrodynamics.lib.client.Sound;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.network.PacketTypeHandler;
import electrodynamics.network.packet.PacketSound;
import electrodynamics.tileentity.TileEntityTreetap;
import electrodynamics.tileentity.machine.TileEntityMachine;
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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float i, float d, float k) {
		super.onItemUse(stack, player, world, x, y, z, side, i, d, k);
		
		ForgeDirection sideForge = ForgeDirection.getOrientation(side);
		x += sideForge.offsetX;
		y += sideForge.offsetY;
		z += sideForge.offsetZ;
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile != null && tile instanceof TileEntityMachine) {
			((TileEntityMachine)tile).rotation = ForgeDirection.getOrientation(side);
		}
		
		return true;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		ForgeDirection sideForge = ForgeDirection.getOrientation(side);
		int xOrig = x - sideForge.offsetX;
		int yOrig = y - sideForge.offsetY;
		int zOrig = z - sideForge.offsetZ;
		
		TileEntity tile = world.getBlockTileEntity(xOrig, yOrig, zOrig);
		 
		if (sideForge != ForgeDirection.UP) {
			return false;
		}
		
		if (tile == null || !(tile instanceof IHeatable)) {
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
