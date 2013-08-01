package electrodynamics.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import electrodynamics.core.CreativeTabED;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.item.Dust;
import electrodynamics.lib.item.Grinding;

public class ItemDust extends Item {

	private Icon[] textures;
	
	public ItemDust(int id) {
		super(id);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabED.resource);
	}
	
	public static boolean isDust(ItemStack stack) {
		return stack.getItem() instanceof ItemDust;
	}
	
	@Override
	public Icon getIconFromDamage(int damage) {
		return textures[damage];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int damage = stack.getItemDamage();
		
		if (damage < Dust.values().length) {
			return EDLanguage.getFormattedItemName(Dust.get(damage).unlocalizedName);
		} else {
			return EDLanguage.getFormattedItemName(Grinding.get(damage - Dust.values().length).unlocalizedName);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (Dust dust : Dust.values()) {
			list.add(new ItemStack(id, 1, dust.ordinal()));
		}
		
		for (Grinding grinding : Grinding.values()) {
			list.add(new ItemStack(id, 1, grinding.ordinal() + Dust.values().length));
		}
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		textures = new Icon[Dust.values().length + Grinding.values().length];
		
		for (int i=0; i<textures.length; i++) {
			if (i < Dust.values().length) {
				//We're loading dusts
				textures[i] = register.registerIcon(Dust.get(i).getTextureFile());
			} else {
				//Otherwise, grindings
				textures[i] = register.registerIcon(Grinding.get(i - Dust.values().length).getTextureFile());
			}
		}
	}
	
}
