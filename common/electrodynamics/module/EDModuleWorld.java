package electrodynamics.module;

import java.util.Random;

import electrodynamics.item.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.block.BlockDecorative;
import electrodynamics.block.BlockLithiumClay;
import electrodynamics.block.BlockOre;
import electrodynamics.block.BlockRubberLeaves;
import electrodynamics.block.BlockRubberSapling;
import electrodynamics.block.BlockRubberWood;
import electrodynamics.block.BlockTreetap;
import electrodynamics.block.BlockWormwood;
import electrodynamics.block.EDBlocks;
import electrodynamics.block.item.ItemBlockDecorative;
import electrodynamics.block.item.ItemBlockOre;
import electrodynamics.block.item.ItemBlockTreetap;
import electrodynamics.block.item.ItemBlockWormwood;
import electrodynamics.client.render.block.RenderBlockDecorative;
import electrodynamics.client.render.block.RenderBlockOre;
import electrodynamics.client.render.block.RenderBlockStructure;
import electrodynamics.client.render.tileentity.RenderTreetap;
import electrodynamics.core.handler.IconHandler;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Decorative;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.ItemIDs;
import electrodynamics.tileentity.TileEntityTreetap;
import electrodynamics.util.BiomeHelper;
import electrodynamics.world.gen.WorldGenBlock;
import electrodynamics.world.gen.WorldGenClay;
import electrodynamics.world.gen.WorldGenNear;
import electrodynamics.world.gen.WorldGenRubberTree;
import electrodynamics.world.gen.WorldGenWormwood;
import electrodynamics.world.gen.feature.FeatureHandler;
import electrodynamics.world.handler.BonemealEventHandler;

public class EDModuleWorld extends EDModule {

	@Override
	public void preInit() {
		EDItems.itemSpudPeeler = new ItemPeelingSpud(ItemIDs.ITEM_SPUD_PEELER_ID).setUnlocalizedName(Strings.ITEM_SPUD_PEELER);
		GameRegistry.registerItem(EDItems.itemSpudPeeler, Strings.ITEM_SPUD_PEELER);
		EDLanguage.getInstance().registerItem(EDItems.itemSpudPeeler);
		
		EDBlocks.blockOre = new BlockOre( BlockIDs.BLOCK_ORE_ID ).setUnlocalizedName( Strings.BLOCK_ORE );
		GameRegistry.registerBlock( EDBlocks.blockOre, ItemBlockOre.class, Strings.BLOCK_ORE );
		for (Ore ore : Ore.values()) {
			MinecraftForge.setBlockHarvestLevel(EDBlocks.blockOre, ore.ordinal(), "pickaxe", ore.harvestLevel);
			EDLanguage.getInstance().registerItemStack(ore.toItemStack(), ore.unlocalizedName);
		}
		
		EDBlocks.blockDecorative = new BlockDecorative( BlockIDs.BLOCK_DECORATIVE_ID ).setUnlocalizedName( Strings.BLOCK_DECORATIVE );
		GameRegistry.registerBlock( EDBlocks.blockDecorative, ItemBlockDecorative.class, Strings.BLOCK_DECORATIVE );
		for (Decorative dec : Decorative.values()) {
			EDLanguage.getInstance().registerItemStack(dec.toItemStack(), dec.unlocalizedName);
		}

		EDBlocks.blockWormwood = new BlockWormwood( BlockIDs.BLOCK_WORMWOOD_ID ).setUnlocalizedName( Strings.BLOCK_WORMWOOD );
		GameRegistry.registerBlock( EDBlocks.blockWormwood, ItemBlockWormwood.class, Strings.BLOCK_WORMWOOD );
		EDLanguage.getInstance().registerBlock(EDBlocks.blockWormwood);

		EDBlocks.blockLithiumClay = new BlockLithiumClay(BlockIDs.BLOCK_LITHIUM_CLAY_ID).setUnlocalizedName(Strings.BLOCK_LITHIUM_CLAY);
		GameRegistry.registerBlock(EDBlocks.blockLithiumClay, Strings.BLOCK_LITHIUM_CLAY);
		EDLanguage.getInstance().registerBlock(EDBlocks.blockLithiumClay);
		
		EDBlocks.blockRubberWood = new BlockRubberWood(BlockIDs.BLOCK_RUBBER_WOOD_ID).setUnlocalizedName(Strings.BLOCK_RUBBER_WOOD);
		GameRegistry.registerBlock(EDBlocks.blockRubberWood, Strings.BLOCK_RUBBER_WOOD);
		EDLanguage.getInstance().registerBlock(EDBlocks.blockRubberWood);
		
		EDBlocks.blockRubberLeaves = new BlockRubberLeaves(BlockIDs.BLOCK_RUBBER_LEAVES_ID).setUnlocalizedName(Strings.BLOCK_RUBBER_LEAF);
		GameRegistry.registerBlock(EDBlocks.blockRubberLeaves, Strings.BLOCK_RUBBER_LEAF);
		EDLanguage.getInstance().registerBlock(EDBlocks.blockRubberLeaves);
		
		EDBlocks.blockRubberSapling = new BlockRubberSapling(BlockIDs.BLOCK_RUBBER_SAPLING_ID).setUnlocalizedName(Strings.BLOCK_RUBBER_SAPLING);
		GameRegistry.registerBlock(EDBlocks.blockRubberSapling, Strings.BLOCK_RUBBER_SAPLING);
		EDLanguage.getInstance().registerBlock(EDBlocks.blockRubberSapling);
		
		EDBlocks.blockTreetap = new BlockTreetap(BlockIDs.BLOCK_TREETAP_ID).setUnlocalizedName(Strings.BLOCK_TREETAP);
		GameRegistry.registerBlock(EDBlocks.blockTreetap, ItemBlockTreetap.class, Strings.BLOCK_TREETAP);
		EDLanguage.getInstance().registerBlock(EDBlocks.blockTreetap);
		
		EDItems.itemLatexBucket = new ItemLatexBucket(ItemIDs.ITEM_LATEX_BUCKET_ID).setUnlocalizedName(Strings.ITEM_LATEX_BUCKET);
		GameRegistry.registerItem(EDItems.itemLatexBucket, Strings.ITEM_LATEX_BUCKET);
		EDLanguage.getInstance().registerItem(EDItems.itemLatexBucket);
		
		EDItems.itemWormSeed = new ItemSeed(ItemIDs.ITEM_WORMSEED_ID).setUnlocalizedName(Strings.ITEM_SEED_WORMSEED);
		GameRegistry.registerItem(EDItems.itemWormSeed, Strings.ITEM_SEED_WORMSEED);
		EDLanguage.getInstance().registerItem(EDItems.itemWormSeed);
	}

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new BonemealEventHandler());

		Fluid latex = new Fluid("Latex").setIcons(IconHandler.getInstance().registeredIcons.get("misc.liquidLatex"));
		
		if (FluidRegistry.registerFluid(latex)) {
			FluidContainerRegistry.registerFluidContainer(latex, new ItemStack(EDItems.itemLatexBucket));
		}
		
		// Wormwood
		GameRegistry.registerWorldGenerator(new WorldGenWormwood());

		MinecraftForge.addGrassSeed( new ItemStack(EDItems.itemWormSeed), 5 );
		
		GameRegistry.registerTileEntity(TileEntityTreetap.class, Strings.BLOCK_TREETAP);
		
		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_DECORATIVE_ID, 3, new ItemStack(EDBlocks.blockDecorative, 1, 0), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_COMPONENT_ID + 256, Component.WORMWOOD_LEAF.ordinal(), Component.SAP.toItemStack(), 0F);
		
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, 1), "XX", "XX", 'X', new ItemStack(EDBlocks.blockDecorative, 1, 0));
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, 2), "XX", "XX", 'X', new ItemStack(EDBlocks.blockDecorative, 1, 1));
		
		GameRegistry.addRecipe(new ItemStack(Item.silk), "TTT", 'T', Component.TWINE.toItemStack());
		
		FeatureHandler featureHandler = FeatureHandler.getInstance();
		featureHandler.prepareFeatures();
		featureHandler.insertFeatures();
		
		// Lithium
		GameRegistry.registerWorldGenerator(new WorldGenClay(BlockIDs.BLOCK_LITHIUM_CLAY_ID, 4));
		//MinecraftForge.TERRAIN_GEN_BUS.register(new electrodynamics.world.handler.CreateDecoratorHandler());
		// Wolframite
		GameRegistry.registerWorldGenerator(new WorldGenNear(BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), 4, 4).setTarget(Block.lavaStill.blockID, 0).setYValues(6, 16));
		// Voidstone
		GameRegistry.registerWorldGenerator(new WorldGenBlock(BlockIDs.BLOCK_ORE_ID, Ore.VOIDSTONE.ordinal(), 1, 10) {
			@Override
			public void onGenned(World world, int x, int y, int z, Random random) {
				for (int ix = x - 2; ix < x + 2; ix++) {
					for (int iy = y - 2; iy < y + 2; iy++) {
						for (int iz = z - 2; iz < z + 2; iz++) {
							if (world.getBlockId(ix, iy, iz) != blockID) {
								world.setBlockToAir(ix, iy, iz);
							}
						}
					}
				}
			}
		});

		// Rubber Trees
		GameRegistry.registerWorldGenerator(new WorldGenRubberTree(10, BiomeHelper.getBiomesForTypes(Type.PLAINS, Type.SWAMP, Type.JUNGLE)));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void initClient() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreetap.class, new RenderTreetap());
		
		RenderingRegistry.registerBlockHandler(new RenderBlockOre());
		RenderingRegistry.registerBlockHandler(new RenderBlockStructure());
		RenderingRegistry.registerBlockHandler(new RenderBlockDecorative());
	}
	
	@Override
	public void postInit() {
	}

}
