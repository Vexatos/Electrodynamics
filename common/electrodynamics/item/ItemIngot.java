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
	
	/** RGBA color definitions for vanilla ingots. Vanilla iron ingot, then vanilla gold ingot */
	public static GLColor[] ingotColors;
	
	public ItemIngot(int id) {
		super(id);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabED.resource);
	}
	
	public static boolean isIngot(ItemStack stack) {
		return (stack.getItem() == EDItems.itemIngot) || (stack.getItem() == Item.ingotGold) || (stack.getItem() == Item.ingotIron);
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
		this.ingotColors = new GLColor[2];
		this.textures = new Icon[Ingot.values().length];
		
		for (int i=0; i<textures.length; i++) {
			textures[i] = register.registerIcon(Ingot.get(i).getTextureFile());
		}
		
		// Iron Ingot
		ingotColors[0] = new GLColor(255, 255, 255, 255);
		// Gold Ingot
		ingotColors[1] = new GLColor(220, 220, 0, 255);
	}
	
}
