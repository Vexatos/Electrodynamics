package electrodynamics.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.core.CreativeTabED;
import electrodynamics.tileentity.TileEntityThermometer;
import electrodynamics.tileentity.machine.TileEntityMachine;
import electrodynamics.util.PlayerUtil;

public class BlockThermometer extends BlockContainer {

	public BlockThermometer(int id) {
		super(id, Material.circuits);
		setHardness(1);
		setResistance(1);
		setCreativeTab(CreativeTabED.tool);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack itemStack) {
		world.setBlockMetadataWithNotify(i, j, k, PlayerUtil.determine2DOrientation_F(entityLiving).ordinal(), 7);
	}
	
	@Override
	public int getRenderType() {
		return -1;
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
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityThermometer();
	}

}
