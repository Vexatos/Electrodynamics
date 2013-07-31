package electrodynamics.configuration;

import electrodynamics.core.EDLogger;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.ItemIDs;
import net.minecraftforge.common.Configuration;

import java.io.File;

public class ConfigurationHandler {

	public static final String CATEGORY_SETTINGS = "user.settings";
	public static final String CATEGORY_ELMAG = "elmag_armor.settings";
	public static final String CATEGORY_KEYS = "user.keybindings";
	public static final String CATEGORY_GRAPHICS = "user.graphics";
	public static final String CATEGORY_SOUND = "user.sound";

	public static void handleConfig(File file) {
		Configuration config = new Configuration( file );

		try {
			ConfigurationSettings.MAGNETIC_RANGE = config.get( CATEGORY_ELMAG, ConfigurationSettings.MAGNETIC_RANGE_CONFIGNAME, ConfigurationSettings.MAGNETIC_RANGE_DEFAULT ).getDouble( ConfigurationSettings.MAGNETIC_RANGE_DEFAULT );
			ConfigurationSettings.MAGNETIC_ATTRACTION_SPEED = config.get( CATEGORY_ELMAG, ConfigurationSettings.MAGNETIC_ATTRACTION_SPEED_CONFIGNAME, ConfigurationSettings.MAGNETIC_ATTRACTION_SPEED_DEFAULT ).getDouble( ConfigurationSettings.MAGNETIC_ATTRACTION_SPEED_DEFAULT );
			
			/* Audio settings */
			ConfigurationSettings.VOIDSTONE_AMBIENT_SOUND = config.get( CATEGORY_SOUND, ConfigurationSettings.VOIDSTONE_AMBIENT_SOUND_NAME, ConfigurationSettings.VOIDSTONE_AMBIENT_SOUND_DEFAULT ).getBoolean( ConfigurationSettings.VOIDSTONE_AMBIENT_SOUND_DEFAULT );
			
			/* General Settings */
			ConfigurationSettings.SHOW_LOCALIZATION_ERRORS = config.get( CATEGORY_SETTINGS, ConfigurationSettings.SHOW_LOCALIZATION_ERRORS_NAME, ConfigurationSettings.SHOW_LOCALIZATION_ERRORS_DEFAULT, "Whether missing localization entries should print errors to the console." ).getBoolean( ConfigurationSettings.SHOW_LOCALIZATION_ERRORS_DEFAULT );
			
			/* Block IDs */
			BlockIDs.BLOCK_ORE_ID = config.getBlock( Strings.BLOCK_ORE, BlockIDs.BLOCK_ORE_DEFAULT_ID ).getInt( BlockIDs.BLOCK_ORE_DEFAULT_ID );
			BlockIDs.BLOCK_RED_WIRE_ID = config.getBlock( Strings.BLOCK_RED_WIRE, BlockIDs.BLOCK_RED_WIRE_DEFAULT_ID ).getInt( BlockIDs.BLOCK_RED_WIRE_DEFAULT_ID );
			BlockIDs.BLOCK_TABLE_ID = config.getBlock( Strings.BLOCK_TABLE, BlockIDs.BLOCK_TABLE_DEFAULT_ID ).getInt( BlockIDs.BLOCK_TABLE_DEFAULT_ID );
			BlockIDs.BLOCK_LASER_EMITTER_ID = config.getBlock( Strings.BLOCK_LASER_EMITTER, BlockIDs.BLOCK_LASER_EMITTER_DEFAULT_ID ).getInt( BlockIDs.BLOCK_LASER_EMITTER_DEFAULT_ID );
			BlockIDs.BLOCK_MACHINE_ID = config.getBlock( Strings.BLOCK_MACHINE, BlockIDs.BLOCK_MACHINE_DEFAULT_ID ).getInt( BlockIDs.BLOCK_MACHINE_DEFAULT_ID );
			BlockIDs.BLOCK_DECORATIVE_ID = config.getBlock( Strings.BLOCK_DECORATIVE, BlockIDs.BLOCK_DECORATIVE_DEFAULT_ID ).getInt( BlockIDs.BLOCK_DECORATIVE_DEFAULT_ID );
			BlockIDs.BLOCK_WORMWOOD_ID = config.getBlock( Strings.BLOCK_WORMWOOD, BlockIDs.BLOCK_WORMWOOD_DEFAULT_ID ).getInt( BlockIDs.BLOCK_WORMWOOD_DEFAULT_ID );
			BlockIDs.BLOCK_LITHIUM_CLAY_ID = config.getBlock( Strings.BLOCK_LITHIUM_CLAY, BlockIDs.BLOCK_LITHIUM_CLAY_DEFAULT_ID ).getInt( BlockIDs.BLOCK_LITHIUM_CLAY_DEFAULT_ID );
			BlockIDs.BLOCK_GAS_ID = config.getBlock( Strings.BLOCK_GAS, BlockIDs.BLOCK_GAS_DEFAULT_ID ).getInt( BlockIDs.BLOCK_GAS_DEFAULT_ID );
			BlockIDs.BLOCK_STORAGE_ID = config.getBlock( Strings.BLOCK_STORAGE, BlockIDs.BLOCK_STORAGE_DEFAULT_ID ).getInt( BlockIDs.BLOCK_STORAGE_DEFAULT_ID );
			BlockIDs.BLOCK_RUBBER_WOOD_ID = config.getBlock( Strings.BLOCK_RUBBER_WOOD, BlockIDs.BLOCK_RUBBER_WOOD_DEFAULT_ID ).getInt( BlockIDs.BLOCK_RUBBER_WOOD_DEFAULT_ID );
			BlockIDs.BLOCK_RUBBER_LEAVES_ID = config.getBlock( Strings.BLOCK_RUBBER_LEAF, BlockIDs.BLOCK_RUBBER_LEAVES_DEFAULT_ID ).getInt( BlockIDs.BLOCK_RUBBER_LEAVES_DEFAULT_ID );
			BlockIDs.BLOCK_RUBBER_SAPLING_ID = config.getBlock( Strings.BLOCK_RUBBER_SAPLING, BlockIDs.BLOCK_RUBBER_SAPLING_DEFAULT_ID ).getInt( BlockIDs.BLOCK_RUBBER_SAPLING_DEFAULT_ID );
			BlockIDs.BLOCK_TREETAP_ID = config.getBlock( Strings.BLOCK_TREETAP, BlockIDs.BLOCK_TREETAP_DEFAULT_ID ).getInt( BlockIDs.BLOCK_TREETAP_DEFAULT_ID );
			BlockIDs.BLOCK_STRUCTURE_COMPONENT_ID = config.getBlock( Strings.BLOCK_STRUCTURE_COMPONENT, BlockIDs.BLOCK_STRUCTURE_COMPONENT_DEFAULT_ID ).getInt( BlockIDs.BLOCK_STRUCTURE_COMPONENT_DEFAULT_ID );
			BlockIDs.BLOCK_REDSTONE_SOURCE_ID = config.getBlock("hiddenRS", BlockIDs.BLOCK_REDSTONE_SOURCE_DEFAULT_ID).getInt(BlockIDs.BLOCK_REDSTONE_SOURCE_DEFAULT_ID);
			BlockIDs.BLOCK_UTILITY_ID = config.getBlock(Strings.UTILITY_ACTUATOR, BlockIDs.BLOCK_UTILITY_DEFAULT_ID).getInt(BlockIDs.BLOCK_UTILITY_DEFAULT_ID);
			BlockIDs.BLOCK_ENERGY_ID = config.getBlock(Strings.BLOCK_ENERGY, BlockIDs.BLOCK_ENERGY_DEFAULT_ID).getInt(BlockIDs.BLOCK_ENERGY_DEFAULT_ID);			
			/* Item IDs */
			ItemIDs.ITEM_COMPONENT_ID = config.getItem( Strings.ITEM_COMPONENT, ItemIDs.ITEM_COMPONENT_DEFAULT_ID ).getInt( ItemIDs.ITEM_COMPONENT_DEFAULT_ID );
			ItemIDs.ITEM_ELMAG_HELM_ID = config.getItem( Strings.ITEM_ELMAG_HAT, ItemIDs.ITEM_ELMAG_HELM_DEFAULT_ID ).getInt( ItemIDs.ITEM_ELMAG_HELM_DEFAULT_ID );
			ItemIDs.ITEM_ELMAG_CHEST_ID = config.getItem( Strings.ITEM_ELMAG_CHEST, ItemIDs.ITEM_ELMAG_CHEST_DEFAULT_ID ).getInt( ItemIDs.ITEM_ELMAG_CHEST_DEFAULT_ID );
			ItemIDs.ITEM_ELMAG_LEGS_ID = config.getItem( Strings.ITEM_ELMAG_LEGS, ItemIDs.ITEM_ELMAG_LEGS_DEFAULT_ID ).getInt( ItemIDs.ITEM_ELMAG_LEGS_DEFAULT_ID );
			ItemIDs.ITEM_ELMAG_BOOTS_ID = config.getItem( Strings.ITEM_ELMAG_BOOTS, ItemIDs.ITEM_ELMAG_BOOTS_DEFAULT_ID ).getInt( ItemIDs.ITEM_ELMAG_BOOTS_DEFAULT_ID );
			ItemIDs.ITEM_STONE_HAMMER_ID = config.getItem( Strings.ITEM_STONE_HAMMER, ItemIDs.ITEM_STONE_HAMMER_DEFAULT_ID ).getInt( ItemIDs.ITEM_STONE_HAMMER_DEFAULT_ID );
			ItemIDs.ITEM_STEEL_HAMMER_ID = config.getItem( Strings.ITEM_STEEL_HAMMER, ItemIDs.ITEM_STEEL_HAMMER_DEFAULT_ID ).getInt( ItemIDs.ITEM_STEEL_HAMMER_DEFAULT_ID );
			ItemIDs.ITEM_SLEDGE_HAMMER_ID = config.getItem( Strings.ITEM_SLEDGE_HAMMER, ItemIDs.ITEM_SLEDGE_HAMMER_DEFAULT_ID ).getInt( ItemIDs.ITEM_SLEDGE_HAMMER_DEFAULT_ID );
			ItemIDs.ITEM_DUST_ID = config.getItem( Strings.ITEM_DUST, ItemIDs.ITEM_DUST_DEFAULT_ID ).getInt( ItemIDs.ITEM_DUST_DEFAULT_ID );
			ItemIDs.ITEM_INGOT_ID = config.getItem( Strings.ITEM_INGOT, ItemIDs.ITEM_INGOT_DEFAULT_ID ).getInt( ItemIDs.ITEM_INGOT_DEFAULT_ID );
			ItemIDs.ITEM_HANDHELD_SIEVE_ID = config.getItem( Strings.ITEM_HANDHELD_SIEVE, ItemIDs.ITEM_HANDHELD_SIEVE_DEFAULT_ID ).getInt( ItemIDs.ITEM_HANDHELD_SIEVE_DEFAULT_ID );
			ItemIDs.ITEM_TRAY_ID = config.getItem( Strings.ITEM_TRAY, ItemIDs.ITEM_TRAY_DEFAULT_ID ).getInt( ItemIDs.ITEM_TRAY_DEFAULT_ID );
			ItemIDs.ITEM_PLASMA_RIFLE_ID = config.getItem( Strings.ITEM_PLASMA_RIFLE, ItemIDs.ITEM_PLASMA_RIFLE_DEFAULT_ID ).getInt( ItemIDs.ITEM_PLASMA_RIFLE_DEFAULT_ID );
			ItemIDs.ITEM_SPUD_PEELER_ID = config.getItem( Strings.ITEM_SPUD_PEELER, ItemIDs.ITEM_SPUD_PEELER_DEFAULT_ID ).getInt( ItemIDs.ITEM_SPUD_PEELER_DEFAULT_ID );
			ItemIDs.ITEM_LATEX_BUCKET_ID = config.getItem( Strings.ITEM_LATEX_BUCKET, ItemIDs.ITEM_LATEX_BUCKET_DEFAULT_ID ).getInt( ItemIDs.ITEM_LATEX_BUCKET_DEFAULT_ID );
			ItemIDs.ITEM_ELMAG_MODULE_ID = config.getItem( Strings.ITEM_ELMAG_MODULE, ItemIDs.ITEM_ELMAG_MODULE_DEFAULT_ID ).getInt( ItemIDs.ITEM_ELMAG_MODULE_DEFAULT_ID );
			ItemIDs.ITEM_TRAY_KILN_ID = config.getItem( Strings.ITEM_TRAY_KILN, ItemIDs.ITEM_TRAY_KILN_DEFAULT_ID ).getInt( ItemIDs.ITEM_TRAY_KILN_DEFAULT_ID );
			ItemIDs.ITEM_REDSTONE_EMITTER_ID = config.getItem(Strings.ITEM_REDSTONE_EMITTER, ItemIDs.ITEM_REDSTONE_EMITTER_DEFAULT_ID).getInt(ItemIDs.ITEM_REDSTONE_EMITTER_DEFAULT_ID);
			
			ItemIDs.ITEM_WORMSEED_ID = config.getItem( Strings.ITEM_SEED_WORMSEED, ItemIDs.ITEM_WORMSEED_DEFAULT_ID ).getInt( ItemIDs.ITEM_WORMSEED_DEFAULT_ID );

		} catch( Exception e ) {
			EDLogger.warn( "Had trouble reading/writing to the configuration file." );
		} finally {
			if( config.hasChanged() ) {
				config.save();
			}
		}
	}

}
