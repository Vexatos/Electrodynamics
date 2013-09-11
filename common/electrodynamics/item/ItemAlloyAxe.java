package electrodynamics.item;

import net.minecraft.block.material.Material;

public class ItemAlloyAxe extends ItemAlloyTool {

	public ItemAlloyAxe(int id) {
		super(id, ToolType.AXE);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] {Material.wood, Material.pumpkin};
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
		return "headAxe";
	}
	
}
