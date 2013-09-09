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

public class ItemAlloyPickaxe extends ItemAlloyTool {

	public static Map<ItemStack, GLColor> iconColorCache = new HashMap<ItemStack, GLColor>();
	
	private Icon handle;
	private Icon head;
	
	public ItemAlloyPickaxe(int id) {
		super(id, ToolType.PICKAXE);
		setCreativeTab(CreativeTabED.tool);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] {Material.rock}; //TODO fill
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamageForRenderPass(int damage, int pass) {
		return pass == 0 ? this.handle : this.head;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register) {
    	this.handle = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":tool/component/pickHandle_wood");
    	this.head = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":tool/component/pickHead_metal");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
    	return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
    	if (renderPass == 0) return 0xFFFFFF;
    	
    	AlloyStack alloy = new AlloyStack(stack);
		if (alloy.getMetals().length > 0) {
			GLColor[] colors = new GLColor[alloy.getMetals().length];
			
			for (int i=0; i<alloy.getMetals().length; i++) {
				MetalData data = alloy.getMetals()[i];
				MetalType type = MetalType.get(data.metalID);
				
				if (type != null) {
					ItemStack stack1 = stack.getItemDamage() == 0 ? type.getDust() : type.getSolid();
					
					if (ItemIngot.isIngot(stack1)) {
						colors[i] = ItemIngot.getColorForIngot(stack1);
					} else if (ItemDust.isDust(stack1)) {
						colors[i] = ItemDust.getColorForDust(stack1);
					} else {
						if (!iconColorCache.containsKey(stack1)) {
							GLColor average = IconUtil.getAverageColor(stack1.getIconIndex().getIconName());
							colors[i] = average;
							iconColorCache.put(stack1, average);
						} else {
							colors[i] = iconColorCache.get(stack1);
						}
					}
				}
			}
			
			return new GLColor(colors).toInt();
		}
		
		return 0xFFFFF;
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
