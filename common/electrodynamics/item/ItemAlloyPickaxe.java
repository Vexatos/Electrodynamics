package electrodynamics.item;

import net.minecraft.block.material.Material;

public class ItemAlloyPickaxe extends ItemAlloyTool {

	public ItemAlloyPickaxe(int id) {
		super(id, ToolType.PICKAXE);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] {Material.rock, Material.iron, Material.anvil};
	}

	@Override
	public String getWoodenHandle() {
		return "toolHandleWood";
	}

	@Override
	public String getMetalHandle() {
		return "toolHandleMetal";
	}
	
	@Override
	public String getHead() {
		return "headPick";
	}
	
}
