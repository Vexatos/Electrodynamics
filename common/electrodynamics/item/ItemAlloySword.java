package electrodynamics.item;

import net.minecraft.block.material.Material;

public class ItemAlloySword extends ItemAlloyTool {

	public ItemAlloySword(int id) {
		super(id, ToolType.SWORD);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] {};
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
		return "bladeSword";
	}
	
}
