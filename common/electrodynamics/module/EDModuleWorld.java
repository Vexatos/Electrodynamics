package electrodynamics.module;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
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
import electrodynamics.client.handler.IconHandler;
import electrodynamics.client.render.block.RenderBlockDecorative;
import electrodynamics.client.render.block.RenderBlockOre;
import electrodynamics.client.render.block.RenderBlockStructure;
import electrodynamics.client.render.tileentity.RenderTreetap;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemLatexBucket;
import electrodynamics.item.ItemPeelingSpud;
import electrodynamics.item.ItemSeed;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Decorative;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.ItemIDs;
import electrodynamics.tileentity.TileEntityTreetap;
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
        MinecraftForge.setBlockHarvestLevel(EDBlocks.blockDecorative, "pickaxe", EnumToolMaterial.WOOD.getHarvestLevel());

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
		
		MinecraftForge.addGrassSeed( new ItemStack(EDItems.itemWormSeed), 5 );
		
		GameRegistry.registerTileEntity(TileEntityTreetap.class, Strings.BLOCK_TREETAP);
		
		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_DECORATIVE_ID, 3, new ItemStack(EDBlocks.blockDecorative, 1, 0), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_COMPONENT_ID + 256, Component.WORMWOOD_LEAF.ordinal(), Component.SAP.toItemStack(), 0F);
		
		// Limestone
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, 1), "XX", "XX", 'X', new ItemStack(EDBlocks.blockDecorative, 1, 0));
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, 2), "XX", "XX", 'X', new ItemStack(EDBlocks.blockDecorative, 1, 1));
		 
		// Tiled Limestone
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, Decorative.LIMESTONE_TILED.ordinal()), "XY", "YX", 'X', Decorative.LIMESTONE.toItemStack(), 'Y', Decorative.LIMESTONE_SCORCHED.toItemStack());
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockDecorative, 4, Decorative.LIMESTONE_TILED.ordinal()), "YX", "XY", 'X', Decorative.LIMESTONE.toItemStack(), 'Y', Decorative.LIMESTONE_SCORCHED.toItemStack());
		
		// Twine -> String
		GameRegistry.addRecipe(new ItemStack(Item.silk), "TTT", 'T', Component.TWINE.toItemStack());
		
		// Glass Jar
		GameRegistry.addRecipe(new ItemStack(EDItems.itemGlassJar), "GLG", "G G", "GGG", 'G', Block.glass, 'L', Decorative.LIMESTONE.toItemStack());
		
		// Spud Peeler
		GameRegistry.addRecipe(new ItemStack(EDItems.itemSpudPeeler), "I", "W", "S", 'I', Item.ingotIron, 'W', new ItemStack(Block.woodSingleSlab, 1, OreDictionary.WILDCARD_VALUE), 'S', Item.stick);
		
		// Treetap
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockTreetap), " I", "M ", 'I', Item.ingotIron, 'M', Component.METAL_BAR.toItemStack());
		
		FeatureHandler.getInstance().registerFeatures();
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
