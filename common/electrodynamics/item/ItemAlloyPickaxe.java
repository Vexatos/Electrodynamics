package electrodynamics.item;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import electrodynamics.core.CreativeTabED;
import electrodynamics.purity.AlloyFactory;
import electrodynamics.purity.AlloyStack;

public class ItemAlloyPickaxe extends ItemAlloyTool {

	public ItemAlloyPickaxe(int id) {
		super(id, ToolType.PICKAXE);
		setCreativeTab(CreativeTabED.tool);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] {Material.rock}; //TODO fill
	}

	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		AlloyFactory factory = new AlloyFactory();
		factory.addMetal("TUNGSTEN");
		factory.addMetal("TUNGSTEN");
		factory.addMetal("TUNGSTEN");
		factory.addMetal("TUNGSTEN");
		factory.addMetal("TUNGSTEN");
		factory.addMetal("TUNGSTEN");
		factory.addMetal("TUNGSTEN");
		factory.addMetal("NICKEL");
		factory.addMetal("COPPER");
		AlloyStack alloy = factory.generateAlloyStack(0);
		ItemStack pick = new ItemStack(EDItems.itemAlloyPickaxe);
		AlloyStack alloyPick = new AlloyStack(pick);
		alloyPick.setMetals(alloy.getMetals());
		list.add(pick);
	}
	
}
