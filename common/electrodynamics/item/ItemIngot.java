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

public class ItemIngot extends Item {

	private Icon[] textures;
	
	/** RGBA color definitions for ingots. Custom ingots first, then vanilla iron ingot, then vanilla gold ingot */
	public static int[][] ingotColors;
	
	static {
		ingotColors = new int[Ingot.values().length + 2][];
		
		ingotColors[Ingot.COPPER.ordinal()] = new int[] {200, 100, 0};
		ingotColors[Ingot.LEAD.ordinal()] = new int[] {85, 85, 120};
		ingotColors[Ingot.NICKEL.ordinal()] = new int[] {50, 210, 160};
		ingotColors[Ingot.TUNGSTEN.ordinal()] = new int[] {45, 45, 45};
		ingotColors[Ingot.COBALT.ordinal()] = new int[] {60, 60, 70};
		ingotColors[Ingot.TELLURIUM.ordinal()] = new int[] {250, 250, 250};
		ingotColors[Ingot.STEEL.ordinal()] = new int[] {130, 130, 130};
		ingotColors[Ingot.TIN.ordinal()] = new int[] {230, 230, 230};
		ingotColors[Ingot.URANIUM.ordinal()] = new int[] {90, 180, 65};
		ingotColors[Ingot.SILVER.ordinal()] = new int[] {220, 240, 245};
		
		// Iron Ingot
		ingotColors[Ingot.values().length] = new int[] {255, 255, 255};
		// Gold Ingot
		ingotColors[Ingot.values().length + 1] = new int[] {220, 220, 0};
	}
	
	public ItemIngot(int id) {
		super(id);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabED.resource);
	}
	
	public static boolean isIngot(ItemStack stack) {
		return (stack.getItem() == EDItems.itemIngot) || (stack.getItem() == Item.ingotGold) || (stack.getItem() == Item.ingotIron);
	}
	
	public static GLColor getColorForIngot(ItemStack stack) {
		int[] colors = null;
		
		if (stack.getItem() == EDItems.itemIngot) {
			colors = ingotColors[stack.getItemDamage()];
		} else if (stack.getItem() == Item.ingotIron) {
			colors = ingotColors[Ingot.values().length];
		} else if (stack.getItem() == Item.ingotGold) {
			colors = ingotColors[Ingot.values().length + 1];
		}

		if (colors != null) {
			return new GLColor(colors[0], colors[1], colors[2]);
		} else {
			return new GLColor(255, 255, 255);
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
		textures = new Icon[Ingot.values().length];
		
		for (int i=0; i<textures.length; i++) {
			textures[i] = register.registerIcon(Ingot.get(i).getTextureFile());
		}
	}
	
}
