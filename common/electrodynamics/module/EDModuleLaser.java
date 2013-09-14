package electrodynamics.module;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.block.BlockHiddenRedstoneSource;
import electrodynamics.block.EDBlocks;
import electrodynamics.client.render.entity.RenderBeam;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.entity.EntityBeam;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemRedstoneEmitter;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.ItemIDs;
import electrodynamics.recipe.vanilla.IRecipeRedstoneEmitter;
import electrodynamics.tileentity.TileEntityRSSource;

public class EDModuleLaser extends EDModule {

	@Override
	public void preInit() {
//		EDBlocks.blockLaserEmitter = new BlockLaserEmitter(BlockIDs.BLOCK_LASER_EMITTER_ID).setUnlocalizedName(Strings.BLOCK_LASER_EMITTER);
//		GameRegistry.registerBlock(EDBlocks.blockLaserEmitter, Strings.BLOCK_LASER_EMITTER);
//		EDLanguage.getInstance().registerBlock(EDBlocks.blockLaserEmitter);
//		
//		EDItems.itemPlasmaRifle = new ItemPlasmaRifle(ItemIDs.ITEM_PLASMA_RIFLE_ID).setUnlocalizedName(Strings.ITEM_PLASMA_RIFLE);
//		GameRegistry.registerItem(EDItems.itemPlasmaRifle, Strings.ITEM_PLASMA_RIFLE);
//		EDLanguage.getInstance().registerItem(EDItems.itemPlasmaRifle);
		
		EDBlocks.blockRSSource = new BlockHiddenRedstoneSource(BlockIDs.BLOCK_REDSTONE_SOURCE_ID).setUnlocalizedName("hiddenRS");
		GameRegistry.registerBlock(EDBlocks.blockRSSource, "hiddenRS");
		
		EDItems.itemRedstoneEmitter = new ItemRedstoneEmitter(ItemIDs.ITEM_REDSTONE_EMITTER_ID).setUnlocalizedName(Strings.ITEM_REDSTONE_EMITTER);
		GameRegistry.registerItem(EDItems.itemRedstoneEmitter, Strings.ITEM_REDSTONE_EMITTER);
		EDLanguage.getInstance().registerItem(EDItems.itemRedstoneEmitter);
	}

	@Override
	public void init() {
//		GameRegistry.registerTileEntity(TileEntityLaserEmitter.class, Strings.BLOCK_LASER_EMITTER);
		
		GameRegistry.registerTileEntity(TileEntityRSSource.class, "hiddenRS");
		
		EntityRegistry.registerGlobalEntityID(EntityBeam.class, "entityBeam", 150);
		
		GameRegistry.addRecipe(new IRecipeRedstoneEmitter());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void initClient() {
//		MinecraftForgeClient.registerItemRenderer(EDItems.itemPlasmaRifle.itemID, new RenderItemPlasmaRifle());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBeam.class, new RenderBeam());
//		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmaBeam.class, new RenderBeam());
	}
	
}
