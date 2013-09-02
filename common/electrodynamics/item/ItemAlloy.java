package electrodynamics.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import electrodynamics.purity.DynamicAlloyPurities.MetalType;
import electrodynamics.util.StringUtil;
import electrodynamics.util.render.GLColor;
import electrodynamics.util.render.IconUtil;

public class ItemAlloy extends Item {

	public static Map<ItemStack, GLColor> iconColorCache = new HashMap<ItemStack, GLColor>();
	
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
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
    	return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (renderPass == 1) {
			return 0xFFFFFF; // White
		} else {
			AlloyStack alloy = new AlloyStack(stack);
			if (alloy.getMetals().length > 0) {
				GLColor[] colors = new GLColor[alloy.getMetals().length];
				
				for (int i=0; i<alloy.getMetals().length; i++) {
					MetalData data = alloy.getMetals()[i];
					MetalType type = MetalType.get(data.metalID);
					
					if (type != null) {
						ItemStack stack1 = stack.getItemDamage() == 0 ? type.getDust() : type.getSolid();
						
						if (ItemIngot.isIngot(stack1)) {
							colors[i] = ItemIngot.getColorForIngot(stack1);
						} else if (ItemDust.isDust(stack1)) {
							colors[i] = ItemDust.getColorForDust(stack1);
						} else {
							if (!iconColorCache.containsKey(stack1)) {
								GLColor average = IconUtil.getAverageColor(stack1.getIconIndex().getIconName());
								colors[i] = average;
								iconColorCache.put(stack1, average);
							} else {
								colors[i] = iconColorCache.get(stack1);
							}
						}
					}
				}
				
				return new GLColor(colors).toInt();
			}
			
			return 0xFFFFF;
		}
    }
    
    public static float[] getRGBFromInt(int color) {
    	float[] colors = new float[3];
    	colors[0] = (color >> 16 & 255) / 255.0F;
        colors[1] = (color >> 8 & 255) / 255.0F;
        colors[2] = (color & 255) / 255.0F;
        return colors;
    }
    
}
