package electrodynamics.item;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import electrodynamics.api.crafting.util.WeightedRecipeOutput;
import electrodynamics.api.render.ICustomRender;
import electrodynamics.api.render.ModelTechne;
import electrodynamics.core.CreativeTabED;
import electrodynamics.core.EDLogger;
import electrodynamics.core.handler.GuiHandler;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.recipe.RecipeSieve;
import electrodynamics.recipe.manager.CraftingManager;

public class ItemHandheldSieve extends Item implements ICustomRender {
 
	public static final int SHAKE_PER_DUST = 60;
	
	public static final Map<String, Integer> progressMapping = new HashMap<String, Integer>();
	
	private Icon texture;
	
	public ItemHandheldSieve(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabED.tool);
	}

	public static int getShakeProgress(EntityPlayer player) {
		if (progressMapping.containsKey(player.username)) {
			return progressMapping.get(player.username);
		} else {
			return 0;
		}
	}
	
	public static void setShakeProgress(EntityPlayer player, int progress) {
		progressMapping.put(player.username, progress);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.none;
    }
	
	@Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
		if (ItemGlassJar.hasDusts(stack)) {
			if (getShakeProgress(player) < SHAKE_PER_DUST) {
				setShakeProgress(player, getShakeProgress(player) + 1);
			} else {
				ItemStack dust = ItemGlassJar.getStoredDusts(stack)[0];
				setShakeProgress(player, 0);
				
				if (!player.worldObj.isRemote) {
					RecipeSieve recipe = CraftingManager.getInstance().sieveManager.getRecipe(dust);
					
					if (recipe != null) {
						float percentage = 0.0F;
						int index = -1;
						
						for (int i=0; i<recipe.itemOutputs.size(); i++) {
							WeightedRecipeOutput output = recipe.itemOutputs.get(i);
							
							if (output != null && output.chance > percentage) {
								percentage = output.chance;
								index = i;
							}
						}
						
						player.dropPlayerItem(recipe.itemOutputs.get(index).output.copy());
						ItemGlassJar.removeDust(stack, dust);
					}
				}
			}
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int duration) {
		setShakeProgress(player, 0);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking()) {
			if (!world.isRemote) {
				GuiHandler.openGui(player, world, (int)player.posX, (int)player.posY, (int)player.posZ, GuiHandler.GuiType.HAND_SIEVE);
			}
		} else {
			if (ItemGlassJar.hasDusts(stack)) {
				player.setItemInUse(stack, this.getMaxDamage(stack));
			}
		}
		
		return stack;
	}
	
	@Override
	public Icon getIconFromDamage(int damage) {
		return texture;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.texture = register.registerIcon(ModInfo.ICON_PREFIX + "tool/handSieveWood");
	}

	@Override
	public void glManipulation() {
		GL11.glRotated(-90, 1, 0, 0);
		GL11.glTranslated(0, -0.075, 0);
		GL11.glTranslated(0, 0, 0.25);
	}

	@Override
	public ModelTechne getCustomModel() { // Not used
		return null;
	}
	
}
