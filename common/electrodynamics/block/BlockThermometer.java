package electrodynamics.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import electrodynamics.core.CreativeTabED;
import electrodynamics.tileentity.TileEntityThermometer;

public class BlockThermometer extends BlockContainer {

	public BlockThermometer(int id) {
		super(id, Material.circuits);
		setHardness(1);
		setResistance(1);
		setCreativeTab(CreativeTabED.tool);
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
