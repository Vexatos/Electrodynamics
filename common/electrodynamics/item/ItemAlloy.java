package electrodynamics.item;

import java.util.ArrayList;
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
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;
import electrodynamics.purity.AlloyStack;
import electrodynamics.purity.MetalData;
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
				list.add(data.component.getDisplayName() + ": " + ((float) (Math.round(data.ratio * 10000.0) / 100.0)) + "%");
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
    	return getColorFromAlloy(stack).toInt();
    }
    
    @Override
	public String getUnlocalizedName(ItemStack stack) {
    	return EDLanguage.getFormattedItemName(stack.getItemDamage() == 0 ? Strings.ITEM_ALLOY_DUST : Strings.ITEM_ALLOY_INGOT);
    }
    
    public static GLColor getColorFromAlloy(ItemStack stack) {
    	if (stack == null) {
    		return new GLColor(0xFFFFFF);
    	}
    	
    	AlloyStack alloy = new AlloyStack(stack);
		if (alloy.getMetals().length > 0) {
			List<GLColor> colors = new ArrayList<GLColor>();
			
			for (int i=0; i<alloy.getMetals().length; i++) {
				MetalData data = alloy.getMetals()[i];
				
				if (data != null) {
					for (int x=0; x<data.getTotal(); x++) {
						colors.add(IconUtil.getCachedColor(data.component));
					}
				}
			}
			
			return new GLColor(colors.toArray(new GLColor[colors.size()]));
		}
		
		return new GLColor(0xFFFFFF);
    }
    
}
