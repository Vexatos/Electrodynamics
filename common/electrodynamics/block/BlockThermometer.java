package electrodynamics.block;

import electrodynamics.core.CreativeTabED;
import electrodynamics.tileentity.TileEntityThermometer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityThermometer();
	}

}
