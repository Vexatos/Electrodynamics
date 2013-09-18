package electrodynamics.module;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.block.BlockAnvil;
import electrodynamics.block.BlockMachine;
import electrodynamics.block.BlockStorage;
import electrodynamics.block.BlockStructure;
import electrodynamics.block.BlockTable;
import electrodynamics.block.BlockThermometer;
import electrodynamics.block.BlockUtility;
import electrodynamics.block.EDBlocks;
import electrodynamics.block.item.ItemBlockMachine;
import electrodynamics.block.item.ItemBlockStorage;
import electrodynamics.block.item.ItemBlockStructure;
import electrodynamics.block.item.ItemBlockTable;
import electrodynamics.block.item.ItemBlockThermometer;
import electrodynamics.block.item.ItemBlockUtility;
import electrodynamics.client.render.block.RenderBlockUtility;
import electrodynamics.client.render.item.RenderItemGlassJar;
import electrodynamics.client.render.item.RenderItemHandSieve;
import electrodynamics.client.render.item.RenderItemMachine;
import electrodynamics.client.render.item.RenderItemTable;
import electrodynamics.client.render.tileentity.RenderActuator;
import electrodynamics.client.render.tileentity.RenderAnvil;
import electrodynamics.client.render.tileentity.RenderBasicKiln;
import electrodynamics.client.render.tileentity.RenderBasicSieve;
import electrodynamics.client.render.tileentity.RenderSinteringOven;
import electrodynamics.client.render.tileentity.RenderTable;
import electrodynamics.client.render.tileentity.RenderThermometer;
import electrodynamics.client.render.tileentity.RenderTileStructure;
import electrodynamics.core.handler.EntityDeathHandler;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemAlloy;
import electrodynamics.item.ItemAlloyAxe;
import electrodynamics.item.ItemAlloyHoe;
import electrodynamics.item.ItemAlloyPickaxe;
import electrodynamics.item.ItemAlloyShovel;
import electrodynamics.item.ItemAlloySword;
import electrodynamics.item.ItemBlacksmithApron;
import electrodynamics.item.ItemDust;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.item.ItemHandheldSieve;
import electrodynamics.item.ItemIngot;
import electrodynamics.item.ItemTray;
import electrodynamics.item.hammer.ItemSledgeHammer;
import electrodynamics.item.hammer.ItemSteelHammer;
import electrodynamics.item.hammer.ItemStoneHammer;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.block.Decorative;
import electrodynamics.lib.block.Machine;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.block.Storage;
import electrodynamics.lib.block.StructureComponent;
import electrodynamics.lib.block.UtilityBlock;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.lib.item.Grinding;
import electrodynamics.lib.item.Ingot;
import electrodynamics.lib.item.ItemIDs;
import electrodynamics.mbs.MBSManager;
import electrodynamics.mbs.structure.MobGrinder;
import electrodynamics.mbs.structure.SinteringFurnace;
import electrodynamics.module.ModuleManager.Module;
import electrodynamics.purity.DynamicAlloyPurities;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.recipe.manager.RecipeManagerGrinder;
import electrodynamics.recipe.manager.RecipeManagerKiln;
import electrodynamics.recipe.manager.RecipeManagerSieve;
import electrodynamics.recipe.manager.RecipeManagerTable;
import electrodynamics.recipe.vanilla.IRecipeAlloyPickaxe;
import electrodynamics.tileentity.TileEntityAnvil;
import electrodynamics.tileentity.TileEntityThermometer;
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
		
		EDBlocks.blockThermometer = new BlockThermometer(BlockIDs.BLOCK_THERMOMETER).setUnlocalizedName(Strings.BLOCK_THERMOMETER);
		GameRegistry.registerBlock(EDBlocks.blockThermometer, ItemBlockThermometer.class, Strings.BLOCK_THERMOMETER);
		EDLanguage.getInstance().registerBlock(EDBlocks.blockThermometer);
		
		EDBlocks.blockAnvil = new BlockAnvil(BlockIDs.BLOCK_ANVIL_ID).setUnlocalizedName(Strings.BLOCK_ANVIL);
		GameRegistry.registerBlock(EDBlocks.blockAnvil, Strings.BLOCK_ANVIL);
		EDLanguage.getInstance().registerBlock(EDBlocks.blockAnvil);
		
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
		for (int i=0; i<2; i++) {
			EDLanguage.getInstance().registerItemStack(new ItemStack(EDItems.itemAlloy, 1, i), i == 0 ? Strings.ITEM_ALLOY_DUST : Strings.ITEM_ALLOY_INGOT);
		}
		
		EDItems.itemGlassJar = new ItemGlassJar(ItemIDs.ITEM_GLASS_JAR).setUnlocalizedName(Strings.ITEM_GLASS_JAR);
		GameRegistry.registerItem(EDItems.itemGlassJar, Strings.ITEM_GLASS_JAR);
		EDLanguage.getInstance().registerItem(EDItems.itemGlassJar);
		
		EDItems.itemAlloyPickaxe = new ItemAlloyPickaxe(ItemIDs.ITEM_ALLOY_PICKAXE).setUnlocalizedName(Strings.ITEM_ALLOY_PICKAXE);
		GameRegistry.registerItem(EDItems.itemAlloyPickaxe, Strings.ITEM_ALLOY_PICKAXE);
		EDLanguage.getInstance().registerItem(EDItems.itemAlloyPickaxe);
		
		EDItems.itemAlloyAxe = new ItemAlloyAxe(ItemIDs.ITEM_ALLOY_AXE).setUnlocalizedName(Strings.ITEM_ALLOY_AXE);
		GameRegistry.registerItem(EDItems.itemAlloyAxe, Strings.ITEM_ALLOY_AXE);
		EDLanguage.getInstance().registerItem(EDItems.itemAlloyAxe);
		
		EDItems.itemAlloyShovel = new ItemAlloyShovel(ItemIDs.ITEM_ALLOY_SHOVEL).setUnlocalizedName(Strings.ITEM_ALLOY_SHOVEL);
		GameRegistry.registerItem(EDItems.itemAlloyShovel, Strings.ITEM_ALLOY_SHOVEL);
		EDLanguage.getInstance().registerItem(EDItems.itemAlloyShovel);
		
		EDItems.itemAlloySword = new ItemAlloySword(ItemIDs.ITEM_ALLOY_SWORD).setUnlocalizedName(Strings.ITEM_ALLOY_SWORD);
		GameRegistry.registerItem(EDItems.itemAlloySword, Strings.ITEM_ALLOY_SWORD);
		EDLanguage.getInstance().registerItem(EDItems.itemAlloySword);
		
		EDItems.itemAlloyHoe = new ItemAlloyHoe(ItemIDs.ITEM_ALLOY_HOE).setUnlocalizedName(Strings.ITEM_ALLOY_HOE);
		GameRegistry.registerItem(EDItems.itemAlloyHoe, Strings.ITEM_ALLOY_HOE);
		EDLanguage.getInstance().registerItem(EDItems.itemAlloyHoe);
		
		EDItems.itemBlacksmithApron = new ItemBlacksmithApron(ItemIDs.ITEM_BLACKSMITH_APRON).setUnlocalizedName(Strings.ITEM_BLACKSMITH_APRON);
		GameRegistry.registerItem(EDItems.itemBlacksmithApron, Strings.ITEM_BLACKSMITH_APRON);
		EDLanguage.getInstance().registerItem(EDItems.itemBlacksmithApron);
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
		GameRegistry.addRecipe(new ItemStack(Block.torchWood, 16), "A", "W", "S", 'W', new ItemStack(Block.cloth, 1, OreDictionary.WILDCARD_VALUE), 'A', Component.SAP.toItemStack(), 'S', Item.stick);
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
		GameRegistry.addRecipe(ItemUtil.getAndResize(Component.OVEN_WALL.toItemStack(), 5), " IL", " IL", " IL", 'I', Item.ingotIron, 'L', Decorative.LIMESTONE.toItemStack());
		GameRegistry.addRecipe(ItemUtil.getAndResize(Component.OVEN_WALL.toItemStack(), 5), "LI ", "LI ", "LI ", 'I', Item.ingotIron, 'L', Decorative.LIMESTONE.toItemStack());
		// Sintering Oven
		GameRegistry.addRecipe(Machine.SINTERING_FURNACE.toItemStack(), "WWW", "WBW", "III", 'W', Component.OVEN_WALL.toItemStack(), 'B', Block.fenceIron, 'I', Item.ingotIron);
		// Oven Tray
		GameRegistry.addRecipe(new ItemStack(EDItems.itemTray), new Object[] {"RIR", 'R', Component.METAL_BAR.toItemStack(), 'I', Item.ingotIron});
		
//		GameRegistry.addRecipe(new IRecipeAlloyPickaxe());
		
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
		GameRegistry.registerTileEntity(TileEntityThermometer.class, Strings.BLOCK_THERMOMETER);
		GameRegistry.registerTileEntity(TileEntityAnvil.class, Strings.BLOCK_ANVIL);
		
		CraftingManager.getInstance().tableManager = new RecipeManagerTable();
		CraftingManager.getInstance().tableManager.initRecipes();

		CraftingManager.getInstance().sieveManager = new RecipeManagerSieve();
		CraftingManager.getInstance().sieveManager.initRecipes();

		CraftingManager.getInstance().kilnManager = new RecipeManagerKiln();
		CraftingManager.getInstance().kilnManager.initRecipes();

		CraftingManager.getInstance().grindManager = new RecipeManagerGrinder();
		CraftingManager.getInstance().grindManager.initRecipes();
		
		CraftingManager.getInstance().alloyManager = new DynamicAlloyPurities();
		
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityThermometer.class, new RenderThermometer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvil.class, new RenderAnvil());
		
		MinecraftForgeClient.registerItemRenderer(EDBlocks.blockTable.blockID, new RenderItemTable());
		MinecraftForgeClient.registerItemRenderer(EDBlocks.blockMachine.blockID, new RenderItemMachine());
		MinecraftForgeClient.registerItemRenderer(EDItems.itemGlassJar.itemID, new RenderItemGlassJar());
		MinecraftForgeClient.registerItemRenderer(EDItems.itemHandheldSieve.itemID, new RenderItemHandSieve());
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
