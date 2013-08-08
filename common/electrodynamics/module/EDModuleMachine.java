package electrodynamics.module;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.block.*;
import electrodynamics.block.item.ItemBlockMachine;
import electrodynamics.block.item.ItemBlockStorage;
import electrodynamics.block.item.ItemBlockStructure;
import electrodynamics.block.item.ItemBlockTable;
import electrodynamics.block.item.ItemBlockUtility;
import electrodynamics.client.render.block.RenderBlockUtility;
import electrodynamics.client.render.item.RenderItemGlassJar;
import electrodynamics.client.render.item.RenderItemMachine;
import electrodynamics.client.render.item.RenderItemTable;
import electrodynamics.client.render.tileentity.*;
import electrodynamics.core.handler.EntityDeathHandler;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.item.*;
import electrodynamics.item.hammer.ItemSledgeHammer;
import electrodynamics.item.hammer.ItemSteelHammer;
import electrodynamics.item.hammer.ItemStoneHammer;
import electrodynamics.lib.block.*;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.*;
import electrodynamics.mbs.MBSManager;
import electrodynamics.mbs.structure.MobGrinder;
import electrodynamics.mbs.structure.SinteringFurnace;
import electrodynamics.module.ModuleManager.Module;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.recipe.manager.RecipeManagerGrinder;
import electrodynamics.recipe.manager.RecipeManagerKiln;
import electrodynamics.recipe.manager.RecipeManagerSieve;
import electrodynamics.recipe.manager.RecipeManagerSinteringOven;
import electrodynamics.recipe.manager.RecipeManagerTable;
import electrodynamics.tileentity.machine.TileEntityBasicKiln;
import electrodynamics.tileentity.machine.TileEntityBasicSieve;
import electrodynamics.tileentity.machine.TileEntitySinteringOven;
import electrodynamics.tileentity.machine.TileEntityTable;
import electrodynamics.tileentity.machine.utilty.TileEntityActuator;
import electrodynamics.tileentity.structure.TileEntityConveyorBelt;
import electrodynamics.tileentity.structure.TileEntityHatch;
import electrodynamics.tileentity.structure.TileEntityMobGrinder;
import electrodynamics.tileentity.structure.TileEntityRedstoneConductor;
import electrodynamics.tileentity.structure.TileEntityStructure;
import electrodynamics.tileentity.structure.TileEntityValve;
import electrodynamics.util.ItemUtil;
import electrodynamics.world.TickHandlerMBS;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumSet;

public class EDModuleMachine extends EDModule {

	@Override
	public void preInit() {
		/* BLOCK */
		EDBlocks.blockTable = new BlockTable(BlockIDs.BLOCK_TABLE_ID).setUnlocalizedName(Strings.BLOCK_TABLE);
		GameRegistry.registerBlock(EDBlocks.blockTable, ItemBlockTable.class, Strings.BLOCK_TABLE);
		for (int i = 0; i < 2; i++) {
			EDLanguage.getInstance().registerItemStack(new ItemStack(EDBlocks.blockTable, 1, i), BlockTable.subNames[i]);
		}

		EDBlocks.blockMachine = new BlockMachine(BlockIDs.BLOCK_MACHINE_ID).setUnlocalizedName(Strings.BLOCK_MACHINE);
		GameRegistry.registerBlock(EDBlocks.blockMachine, ItemBlockMachine.class, Strings.BLOCK_MACHINE);
		for (Machine machine : Machine.values()) {
			EDLanguage.getInstance().registerItemStack(machine.toItemStack(), machine.unlocalizedName);
		}

		EDBlocks.blockStorage = new BlockStorage(BlockIDs.BLOCK_STORAGE_ID).setUnlocalizedName(Strings.BLOCK_STORAGE);
		GameRegistry.registerBlock(EDBlocks.blockStorage, ItemBlockStorage.class, Strings.BLOCK_STORAGE);
		for (Storage storage : Storage.values()) {
			EDLanguage.getInstance().registerItemStack(storage.toItemStack(), storage.unlocalizedName);
		}

		EDBlocks.blockStructureComponent = new BlockStructure(BlockIDs.BLOCK_STRUCTURE_COMPONENT_ID).setUnlocalizedName(Strings.BLOCK_STRUCTURE_COMPONENT);
		GameRegistry.registerBlock(EDBlocks.blockStructureComponent, ItemBlockStructure.class, Strings.BLOCK_STRUCTURE_COMPONENT);
		for (StructureComponent component : StructureComponent.values()) {
			EDLanguage.getInstance().registerItemStack(component.toItemStack(), component.getUnlocalizedName());
		}

		EDBlocks.blockUtility = new BlockUtility(BlockIDs.BLOCK_UTILITY_ID).setUnlocalizedName(Strings.BLOCK_UTILITY);
		GameRegistry.registerBlock(EDBlocks.blockUtility, ItemBlockUtility.class, Strings.BLOCK_UTILITY);
		for (UtilityBlock util : UtilityBlock.values()) {
			EDLanguage.getInstance().registerItemStack(util.toItemStack(), util.unlocalizedName);
		}
		
		/* ITEM */
		EDItems.itemDust = new ItemDust(ItemIDs.ITEM_DUST_ID).setUnlocalizedName(Strings.ITEM_DUST);
		GameRegistry.registerItem(EDItems.itemDust, Strings.ITEM_DUST);
		for (Dust dust : Dust.values()) {
			// dust.registerWithOreDictionary();
			EDLanguage.getInstance().registerItemStack(dust.toItemStack(), dust.unlocalizedName);
		}
		for (Grinding grinding : Grinding.values()) {
			EDLanguage.getInstance().registerItemStack(grinding.toItemStack(), grinding.unlocalizedName);
		}

		EDItems.itemIngot = new ItemIngot(ItemIDs.ITEM_INGOT_ID).setUnlocalizedName(Strings.ITEM_INGOT);
		GameRegistry.registerItem(EDItems.itemIngot, Strings.ITEM_INGOT);
		for (Ingot ingot : Ingot.values()) {
			EDLanguage.getInstance().registerItemStack(ingot.toItemStack(), ingot.unlocalizedName);
		}

		EDItems.itemStoneHammer = new ItemStoneHammer(ItemIDs.ITEM_STONE_HAMMER_ID).setUnlocalizedName(Strings.ITEM_STONE_HAMMER);
		GameRegistry.registerItem(EDItems.itemStoneHammer, Strings.ITEM_STONE_HAMMER);
		EDLanguage.getInstance().registerItem(EDItems.itemStoneHammer);

		EDItems.itemSteelHammer = new ItemSteelHammer(ItemIDs.ITEM_STEEL_HAMMER_ID).setUnlocalizedName(Strings.ITEM_STEEL_HAMMER);
		GameRegistry.registerItem(EDItems.itemSteelHammer, Strings.ITEM_STEEL_HAMMER);
		EDLanguage.getInstance().registerItem(EDItems.itemSteelHammer);

		EDItems.itemSledgeHammer = new ItemSledgeHammer(ItemIDs.ITEM_SLEDGE_HAMMER_ID).setUnlocalizedName(Strings.ITEM_SLEDGE_HAMMER);
		GameRegistry.registerItem(EDItems.itemSledgeHammer, Strings.ITEM_SLEDGE_HAMMER);
		EDLanguage.getInstance().registerItem(EDItems.itemSledgeHammer);

		EDItems.itemHandheldSieve = new ItemHandheldSieve(ItemIDs.ITEM_HANDHELD_SIEVE_ID).setUnlocalizedName(Strings.ITEM_HANDHELD_SIEVE);
		GameRegistry.registerItem(EDItems.itemHandheldSieve, Strings.ITEM_HANDHELD_SIEVE);
		EDLanguage.getInstance().registerItem(EDItems.itemHandheldSieve);

		EDItems.itemTray = new ItemTray(ItemIDs.ITEM_TRAY_ID, ItemTray.TrayType.OVEN_TRAY).setUnlocalizedName(Strings.ITEM_TRAY);
		GameRegistry.registerItem(EDItems.itemTray, Strings.ITEM_TRAY);
		EDLanguage.getInstance().registerItem(EDItems.itemTray);

		EDItems.itemTrayKiln = new ItemTray(ItemIDs.ITEM_TRAY_KILN_ID, ItemTray.TrayType.KILN_TRAY).setUnlocalizedName(Strings.ITEM_TRAY_KILN);
		GameRegistry.registerItem(EDItems.itemTrayKiln, Strings.ITEM_TRAY_KILN);
		EDLanguage.getInstance().registerItem(EDItems.itemTrayKiln);
		
		EDItems.itemAlloy = new ItemAlloy(ItemIDs.ITEM_ALLOY_ID).setUnlocalizedName(Strings.ITEM_ALLOY);
		GameRegistry.registerItem(EDItems.itemAlloy, Strings.ITEM_ALLOY);
		
		EDItems.itemGlassJar = new ItemGlassJar(ItemIDs.ITEM_GLASS_JAR).setUnlocalizedName(Strings.ITEM_GLASS_JAR);
		GameRegistry.registerItem(EDItems.itemGlassJar, Strings.ITEM_GLASS_JAR);
		EDLanguage.getInstance().registerItem(EDItems.itemGlassJar);
	}

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new EntityDeathHandler());
		
		for (Storage storage : Storage.values()) {
			GameRegistry.addRecipe(storage.toItemStack(), "XXX", "XXX", "XXX", 'X', storage.ingot.toItemStack());
			GameRegistry.addShapelessRecipe(new ItemStack(ItemIDs.ITEM_INGOT_ID + 256, 9, storage.ingot.ordinal()), storage.toItemStack());
		}

		// Stone Hammer
		GameRegistry.addRecipe(new ItemStack(EDItems.itemStoneHammer), "C", "T", "S", 'C', Block.cobblestone, 'T', Component.TWINE.toItemStack(), 'S', Item.stick);
		// Steel Hammer
		GameRegistry.addRecipe(new ItemStack(EDItems.itemSteelHammer), "I", "A", "S", 'I', Ingot.STEEL.toItemStack(), 'A', Component.SAP.toItemStack(), 'S', Item.stick);
		// Sledgehammer
		GameRegistry.addRecipe(new ItemStack(EDItems.itemSledgeHammer), "S", "M", "M", 'S', Ingot.STEEL.toItemStack(), 'M', Component.METAL_BAR.toItemStack());
		// Sap Torches
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 16), "W", "A", "S", 'W', new ItemStack(Block.cloth, 1, OreDictionary.WILDCARD_VALUE), 'A', Component.SAP.toItemStack(), 'S', Item.stick);
		// Metal Rod
		GameRegistry.addRecipe(ItemUtil.getAndResize(Component.METAL_BAR.toItemStack(), 6), "  I", " I ", "I  ", 'I', Item.ingotIron);
		// Basic Sieve
		GameRegistry.addRecipe(Machine.BASIC_SIEVE.toItemStack(), "S S", "RBR", "WWW", 'S', new ItemStack(Block.woodSingleSlab, 1, OreDictionary.WILDCARD_VALUE), 'R', Component.METAL_BAR.toItemStack(), 'B', Block.fenceIron, 'W', new ItemStack(Block.planks, 1, OreDictionary.WILDCARD_VALUE));
		// Hand Sieve
		GameRegistry.addRecipe(new ItemStack(EDItems.itemHandheldSieve), "SSS", "SMS", "SSS", 'S', Item.stick, 'M', Component.TWINE_MESH.toItemStack());
		// Twine Mesh
		GameRegistry.addRecipe(Component.TWINE_MESH.toItemStack(), "TTT", "TTT", "TTT", 'T', Component.TWINE.toItemStack());
		// Basic Table
		GameRegistry.addRecipe(new ItemStack(EDBlocks.blockTable), "SSS", "WWW", "T T", 'S', new ItemStack(Block.woodSingleSlab, 1, OreDictionary.WILDCARD_VALUE), 'W', new ItemStack(Block.planks, 1, OreDictionary.WILDCARD_VALUE), 'T', Item.stick);
		// Oven Wall Component
		GameRegistry.addRecipe(Component.OVEN_WALL.toItemStack(), " IL", " IL", " IL", 'I', Item.ingotIron, 'L', Decorative.LIMESTONE.toItemStack());
		// Sintering Oven
		GameRegistry.addRecipe(Machine.SINTERING_FURNACE.toItemStack(), "WWW", "WBW", "III", 'W', Component.OVEN_WALL.toItemStack(), 'B', Block.fenceIron, 'I', Item.ingotIron);
		
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_COMPONENT_ID + 256, Component.LITHIUM_CLAY_WET.ordinal(), Component.LITHIUM_CLAY.toItemStack(), 0F);

		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.COBALT.ordinal(), Ingot.COBALT.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.COPPER.ordinal(), Ingot.COPPER.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.LEAD.ordinal(), Ingot.LEAD.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.NICKEL.ordinal(), Ingot.NICKEL.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.TELLURIUM.ordinal(), Ingot.TELLURIUM.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.TUNGSTEN.ordinal(), Ingot.TUNGSTEN.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.TIN.ordinal(), Ingot.TIN.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.SILVER.ordinal(), Ingot.SILVER.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.URANIUM.ordinal(), Ingot.URANIUM.toItemStack(), 0F);

		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.IRON.ordinal(), new ItemStack(Item.ingotIron), 0F);
		FurnaceRecipes.smelting().addSmelting(ItemIDs.ITEM_DUST_ID + 256, Dust.GOLD.ordinal(), new ItemStack(Item.ingotGold), 0F);

		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_ORE_ID, Ore.CHALCOPYRITE.ordinal(), Ingot.COPPER.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_ORE_ID, Ore.COBALTITE.ordinal(), Ingot.COBALT.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_ORE_ID, Ore.GALENA.ordinal(), Ingot.LEAD.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_ORE_ID, Ore.NICKEL.ordinal(), Ingot.NICKEL.toItemStack(), 0F);
		FurnaceRecipes.smelting().addSmelting(BlockIDs.BLOCK_ORE_ID, Ore.WOLFRAMITE.ordinal(), Ingot.TUNGSTEN.toItemStack(), 0F);
		
		GameRegistry.registerTileEntity(TileEntitySinteringOven.class, Strings.MACHINE_SINTERING_OVEN);
		GameRegistry.registerTileEntity(TileEntityTable.class, Strings.BLOCK_TABLE);
		GameRegistry.registerTileEntity(TileEntityBasicSieve.class, Strings.MACHINE_BASIC_SIEVE);
		GameRegistry.registerTileEntity(TileEntityBasicKiln.class, Strings.MACHINE_BASIC_KILN);
		GameRegistry.registerTileEntity(TileEntityConveyorBelt.class, Strings.MACHINE_CONVEYOR_BELT);
		GameRegistry.registerTileEntity(TileEntityStructure.TileStructurePlaceHolder.class, Strings.MACHINE_STRUCTURE_PLACE_HOLDER);
		GameRegistry.registerTileEntity(TileEntityMobGrinder.class, "edxMobGrinder");
		GameRegistry.registerTileEntity(TileEntityValve.class, Strings.STRUCTURE_COMPONENT_MACHINE_VALVE);
		GameRegistry.registerTileEntity(TileEntityHatch.class, Strings.STRUCTURE_COMPONENT_MACHINE_HATCH);
		GameRegistry.registerTileEntity(TileEntityActuator.class, Strings.UTILITY_ACTUATOR);
		GameRegistry.registerTileEntity(TileEntityRedstoneConductor.class, Strings.STRUCTURE_COMPONENT_MACHINE_RS_CONDUCTOR);
		
		CraftingManager.getInstance().tableManager = new RecipeManagerTable();
		CraftingManager.getInstance().tableManager.initRecipes();

		CraftingManager.getInstance().sieveManager = new RecipeManagerSieve();
		CraftingManager.getInstance().sieveManager.initRecipes();

		CraftingManager.getInstance().ovenManager = new RecipeManagerSinteringOven();
		CraftingManager.getInstance().ovenManager.initRecipes();

		CraftingManager.getInstance().kilnManager = new RecipeManagerKiln();
		CraftingManager.getInstance().kilnManager.initRecipes();

		CraftingManager.getInstance().grindManager = new RecipeManagerGrinder();
		CraftingManager.getInstance().grindManager.initRecipes();
		
		// Ore Dictionary registration
		// Weird, but it ended up working out this way. ;)
		for (Dust dust : Dust.values()) {
			OreDictionary.registerOre(dust.textureFile, dust.toItemStack());
		}
		
		for (Ingot ingot : Ingot.values()) {
			OreDictionary.registerOre(ingot.textureFile, ingot.toItemStack());
		}
		
		for (Ore ore : Ore.values()) {
			OreDictionary.registerOre(ore.textureFile, ore.toItemStack());
		}
		
		// Multi-block Structures
		MBSManager.registerMBS(new SinteringFurnace()); // Sintering Furnace
		MBSManager.registerMBS(new MobGrinder()); // Mob Grinder
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void initClient() {
		RenderingRegistry.registerBlockHandler(new RenderBlockUtility());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySinteringOven.class, new RenderSinteringOven());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTable.class, new RenderTable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicSieve.class, new RenderBasicSieve());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicKiln.class, new RenderBasicKiln());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStructure.class, new RenderTileStructure());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityActuator.class, new RenderActuator());
		
		MinecraftForgeClient.registerItemRenderer(EDBlocks.blockTable.blockID, new RenderItemTable());
		MinecraftForgeClient.registerItemRenderer(EDBlocks.blockMachine.blockID, new RenderItemMachine());
		MinecraftForgeClient.registerItemRenderer(EDItems.itemGlassJar.itemID, new RenderItemGlassJar());
	}

	@Override
	public EnumSet<Module> dependencies() {
		return EnumSet.of(Module.CORE, Module.WORLD);
	}

	@Override
	public void postInit() {
		// Register TickHandler for MBS validation.
		TickRegistry.registerTickHandler(TickHandlerMBS.instance(), Side.SERVER);
	}

}
