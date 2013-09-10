package electrodynamics.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import electrodynamics.core.CreativeTabED;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.purity.AlloyFactory;
import electrodynamics.purity.AlloyStack;
import electrodynamics.purity.MetalData;
import electrodynamics.purity.DynamicAlloyPurities.MetalType;
import electrodynamics.util.render.GLColor;
import electrodynamics.util.render.IconUtil;

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
