package electrodynamics.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.purity.AlloyStack;
import electrodynamics.purity.MetalData;
import electrodynamics.util.StringUtil;

public class ItemAlloy extends Item {

	@SideOnly(Side.CLIENT)
	public Icon dustIcon;
	@SideOnly(Side.CLIENT)
	public Icon ingotIcon;
	@SideOnly(Side.CLIENT)
	public Icon transIcon;
	
	public ItemAlloy(int id) {
		super(id);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabED.resource);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		AlloyStack alloy = new AlloyStack(stack);
		if (alloy.getMetals().length > 0) {
			for (MetalData data: alloy.getMetals()) {
				list.add(StringUtil.toTitleCase(data.metalID) + ": " + ((float) (Math.round(data.ratio * 10000.0) / 100.0)) + "%");
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int damage) {
		return damage == 0 ? this.dustIcon : this.ingotIcon;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register) {
    	this.dustIcon = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":dust/dustAlloy");
    	this.ingotIcon = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":ingot/ingotAlloy");
    	this.transIcon = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":misc/trans");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {

    }
    
    public static float[] getRGBFromInt(int color) {
    	float[] colors = new float[3];
    	colors[0] = (color >> 16 & 255) / 255.0F;
        colors[1] = (color >> 8 & 255) / 255.0F;
        colors[2] = (color & 255) / 255.0F;
        return colors;
    }
    
}
