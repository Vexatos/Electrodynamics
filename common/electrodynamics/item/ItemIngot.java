package electrodynamics.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import electrodynamics.core.CreativeTabED;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.item.Ingot;
import electrodynamics.util.render.GLColor;
import electrodynamics.util.render.IconUtil;

public class ItemIngot extends Item {

	private Icon[] textures;
	
	/** RGBA color definitions for ingots. Custom ingots first, then vanilla iron ingot, then vanilla gold ingot */
	public static GLColor[] ingotColors;
	
	public ItemIngot(int id) {
		super(id);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabED.resource);
	}
	
	public static boolean isIngot(ItemStack stack) {
		return (stack.getItem() == EDItems.itemIngot) || (stack.getItem() == Item.ingotGold) || (stack.getItem() == Item.ingotIron);
	}
	
	public static GLColor getColorForIngot(ItemStack stack) {
		GLColor colors = null;
		
		if (stack.getItem() == EDItems.itemIngot) {
			colors = ingotColors[stack.getItemDamage()];
		} else if (stack.getItem() == Item.ingotIron) {
			colors = ingotColors[Ingot.values().length];
		} else if (stack.getItem() == Item.ingotGold) {
			colors = ingotColors[Ingot.values().length + 1];
		}

		if (colors != null) {
			return colors;
		} else {
			return new GLColor(255, 255, 255, 255);
		}
	}
	
	@Override
	public Icon getIconFromDamage(int damage) {
		return textures[damage];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return EDLanguage.getFormattedItemName(Ingot.get(stack.getItemDamage()).unlocalizedName);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (Ingot ingot : Ingot.values()) {
			list.add(new ItemStack(id, 1, ingot.ordinal()));
		}
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.ingotColors = new GLColor[Ingot.values().length + 2];
		this.textures = new Icon[Ingot.values().length];
		
		for (int i=0; i<textures.length; i++) {
			textures[i] = register.registerIcon(Ingot.get(i).getTextureFile());
			this.ingotColors[i] = IconUtil.getAverageColor("items/ingot/" + Ingot.get(i).textureFile + ".png");
		}
		
		// Iron Ingot
		ingotColors[Ingot.values().length] = new GLColor(255, 255, 255, 255);
		// Gold Ingot
		ingotColors[Ingot.values().length + 1] = new GLColor(220, 220, 0, 255);
	}
	
}
