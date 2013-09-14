package electrodynamics.module;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.block.BlockEnergy;
import electrodynamics.block.EDBlocks;
import electrodynamics.block.item.ItemBlockEnergy;
import electrodynamics.client.render.item.RenderItemEnergy;
import electrodynamics.client.render.tileentity.RenderSolarPanel;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.EnergyProduction;
import electrodynamics.lib.core.Strings;
import electrodynamics.tileentity.machine.energy.TileEntitySolarPanel;

public class EDModuleEnergy extends EDModule {

	@Override
	public void preInit() {
		EDBlocks.blockEnergy = new BlockEnergy(BlockIDs.BLOCK_ENERGY_ID).setUnlocalizedName(Strings.BLOCK_ENERGY);
		GameRegistry.registerBlock(EDBlocks.blockEnergy, ItemBlockEnergy.class, Strings.BLOCK_ENERGY);
		for (EnergyProduction energy : EnergyProduction.values()) {
			EDLanguage.getInstance().registerItemStack(energy.toItemStack(), energy.unlocalizedName);
		}
	}

	@Override
	public void init() {
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, Strings.ENERGY_SOLAR_PANEL);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void preInitClient() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanel.class, new RenderSolarPanel());
		MinecraftForgeClient.registerItemRenderer(EDBlocks.blockEnergy.blockID, new RenderItemEnergy());
	}
	
}
