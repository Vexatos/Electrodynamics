package electrodynamics.lib.block;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;
import electrodynamics.tileentity.machine.utilty.TileEntityActuator;

public enum UtilityBlock {

	BLOCK_DEPLOYER(Strings.UTILITY_ACTUATOR) {
		
		@Override
		public String[] getTextures() {
			final String ICON_PREFIX = ModInfo.ICON_PREFIX + "machine/utility/blockDeployer";
			return new String[] {ICON_PREFIX + "Front", ICON_PREFIX + "Back", ICON_PREFIX + "Side"};
		}

		@Override
		public TileEntity getTileEntity() {
			return new TileEntityActuator();
		}
		
	};
	
	public String unlocalizedName;
	
	private UtilityBlock(String unlocName) {
		this.unlocalizedName = unlocName;
	}
	
	public ItemStack toItemStack() {
		return new ItemStack(BlockIDs.BLOCK_UTILITY_ID, 1, this.ordinal());
	}
	
	public static UtilityBlock get(int id) {
		return UtilityBlock.values()[id];
	}
	
	/**
	 * Texture names for utility block
	 * @return Returns a 3-length String array of texture paths. <br />
	 * Order is FRONT, BACK, SIDES
	 */
	public abstract String[] getTextures();
	
	/**
	 * Tile Entity to be created on block placement
	 * @return Instance of desired TileEntity
	 */
	public abstract TileEntity getTileEntity();
	
}
