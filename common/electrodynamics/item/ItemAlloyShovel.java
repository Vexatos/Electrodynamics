package electrodynamics.item;

import net.minecraft.block.material.Material;

public class ItemAlloyShovel extends ItemAlloyTool {

	public ItemAlloyShovel(int id) {
		super(id, ToolType.SHOVEL);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] {Material.grass, Material.ground, Material.sand, Material.clay};
	}

	@Override
	public String getWoodenHandle() {
		return "shovelHandleWood";
	}

	@Override
	public String getMetalHandle() {
		return "shovelHandleMetal";
	}
	
	@Override
	public String getHead() {
		return "headShovel";
	}
	
}
