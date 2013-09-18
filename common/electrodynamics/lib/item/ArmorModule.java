package electrodynamics.lib.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import electrodynamics.api.tool.IArmorLogic;
import electrodynamics.item.elmag.ItemElMagArmor;
import electrodynamics.item.elmag.logic.LogicMagneticPull;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;
import electrodynamics.util.InventoryUtil;

public enum ArmorModule {

	MAGNETIC_PULL(Strings.MODULE_MAGNETIC_PULL, Strings.MODULE_MAGNETIC_PULL_DESC, new int[] {1}, new LogicMagneticPull(), "moduleMagnet"),
	XRAY(Strings.MODULE_XRAY, Strings.MODULE_XRAY_DESC, new int[] {0}, null, "moduleXray");
//	BACKTRACK(Strings.MODULE_BACKTRACK, Strings.MODULE_BACKTRACK_DESC, new int[] {1}, null, "moduleBacktrack");
	
	public String unlocalizedName;
	public String moduleDescription;
	public int[] validArmorTypes;
	public IArmorLogic armorLogic;
	private String textureName;

	private ArmorModule(String unlocalizedName, String moduleDescription, int[] validArmorTypes, IArmorLogic armorLogic, String textureName) {
		this.unlocalizedName = unlocalizedName;
		this.moduleDescription = moduleDescription;
		this.validArmorTypes = validArmorTypes;
		this.armorLogic = armorLogic;
		this.textureName = textureName;
	}

	public boolean hasModule(EntityPlayer player) {
		for (ItemStack armor : player.inventory.armorInventory) {
			if (armor != null && armor.getItem() instanceof ItemElMagArmor) {
				if (InventoryUtil.contains((((ItemElMagArmor)armor.getItem()).getInventory(armor).inventory), this.toItemStack())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean hasLogic() {
		return this.armorLogic != null;
	}
	
	public String getTextureFile() {
		return ModInfo.ICON_PREFIX + "tesla/module/" + textureName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public String getLocalizedName(String language) {
		return "";
	}

	public ItemStack toItemStack() {
		return new ItemStack(ItemIDs.ITEM_ELMAG_MODULE_ID + 256, 1, this.ordinal());
	}

	public static ArmorModule get(int ordinal) {
		return ArmorModule.values()[ordinal];
	}
	
}
