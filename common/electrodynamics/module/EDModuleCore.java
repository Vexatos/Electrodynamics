package electrodynamics.module;

import cpw.mods.fml.common.registry.GameRegistry;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemComponent;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.ItemIDs;

public class EDModuleCore extends EDModule {

	@Override
	public void preInit() {
		EDItems.itemComponent = new ItemComponent(ItemIDs.ITEM_COMPONENT_ID).setUnlocalizedName(Strings.ITEM_COMPONENT);
		GameRegistry.registerItem(EDItems.itemComponent, Strings.ITEM_COMPONENT);
		for (Component component : Component.values()) {
			EDLanguage.getInstance().registerItemStack(component.toItemStack(), component.unlocalizedName);
		}
	}

}
