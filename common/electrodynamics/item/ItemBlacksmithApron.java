package electrodynamics.item;

import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.core.ModInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.common.EnumHelper;

public class ItemBlacksmithApron extends ItemArmor {

	private Icon texture;
	
	public static EnumArmorMaterial blacksmithApron = EnumHelper.addArmorMaterial("blacksmithArmor", 500, new int[] {0, 0, 0, 0}, 0);
	
	public ItemBlacksmithApron(int id) {
		super(id, blacksmithApron, 0, 1);
		setCreativeTab(CreativeTabED.tool);
	}

	@Override
	public Icon getIconFromDamage(int damage) {
		return texture;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ModInfo.GENERIC_MOD_ID.toLowerCase() + ":textures/armor/apron_1.png";
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		texture = register.registerIcon(ModInfo.ICON_PREFIX + "armor/blacksmithApron");
	}
	
}
