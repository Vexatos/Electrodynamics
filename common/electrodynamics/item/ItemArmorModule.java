package electrodynamics.item;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import electrodynamics.api.tool.IArmorModule;
import electrodynamics.core.CreativeTabED;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.ArmorModule;
import electrodynamics.util.StringUtil;

public class ItemArmorModule extends Item implements IArmorModule {

	private Icon[] textures;
	
	public ItemArmorModule(int id) {
		super(id);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabED.tool);
	}

	@Override
	public Icon getIconFromDamage(int damage) {
		return textures[damage];
	}
	
	@Override
	public String getModuleName(ItemStack stack) {
		return EDLanguage.getInstance().translate(ArmorModule.get(stack.getItemDamage()).unlocalizedName);
	}

	@Override
	public String getModuleDescription(ItemStack stack) {
		return EDLanguage.getInstance().translate(ArmorModule.get(stack.getItemDamage()).moduleDescription);
	}

	@Override
	public int[] validArmorTypes(ItemStack stack) {
		return ArmorModule.get(stack.getItemDamage()).validArmorTypes;
	}

	@Override
	public void onArmorTick(ArmorModule module, World world, EntityPlayer player, ItemStack itemStack) {
		if (module.hasLogic()) {
			module.armorLogic.onArmorTick(world, player, itemStack);
		}
	}
	
	@Override
	public void onKeypress(ArmorModule module, EntityPlayer player, ItemStack stack, int key) {
		if (module.hasLogic()) {
			module.armorLogic.onKeypress(player, stack, key);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return EDLanguage.getFormattedItemName(ArmorModule.get(stack.getItemDamage()).unlocalizedName);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean show) {
		if (GuiScreen.isShiftKeyDown()) {
			List<String> strings = StringUtil.splitStringAfterNChars(EDLanguage.getInstance().translate(ArmorModule.get(stack.getItemDamage()).moduleDescription), 25);
			if (strings != null && strings.size() > 0) {
				for (String string : strings) {
					if (string != null && string.length() > 0) {
						list.add(string);
					}
				}
			}
		} else {
			list.add(EDLanguage.getInstance().translate(Strings.MODULE_HELP_MESSAGE));
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (ArmorModule module : ArmorModule.values()) {
			list.add(module.toItemStack());
		}
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.textures = new Icon[ArmorModule.values().length];
		
		for (ArmorModule module : ArmorModule.values()) {
			this.textures[module.ordinal()] = register.registerIcon(module.getTextureFile());
		}
	}

}
