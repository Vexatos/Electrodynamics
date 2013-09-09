package electrodynamics.item;

import java.util.List;

import electrodynamics.client.render.item.RenderItemCompact;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/** Utility item class that allows for multiple items with
 * 	varying stats (including damage) be stored in one ID
 * 	@author Dylan
 */

public abstract class ItemCompact extends Item {

	public static final String MAX_DAMAGE_TAG_KEY = "MaxDamage";
	public static final String DAMAGE_TAG_KEY = "Damage";
	public static final String ITEM_TYPE_KEY = "ItemType";
	
	public ItemCompact(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
		
		RenderItemCompact.register(this.itemID);
	}

	public static int getStackDamage(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		return stack.stackTagCompound.hasKey(DAMAGE_TAG_KEY) ? stack.stackTagCompound.getInteger(DAMAGE_TAG_KEY) : 0;
	}
	
	public static int getMaxStackDamage(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		return stack.stackTagCompound.hasKey(MAX_DAMAGE_TAG_KEY) ? stack.stackTagCompound.getInteger(MAX_DAMAGE_TAG_KEY) : 0;
	}
	
	public static int getItemType(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		return stack.stackTagCompound.hasKey(ITEM_TYPE_KEY) ? stack.stackTagCompound.getInteger(ITEM_TYPE_KEY) : 0;
	}
	
	public static void setStackDamage(ItemStack stack, int damage) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound tag = stack.stackTagCompound;
		
		tag.setInteger(DAMAGE_TAG_KEY, damage);
		
		stack.setTagCompound(tag);
	}
	
	public static void setMaxStackDamage(ItemStack stack, int damage) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound tag = stack.stackTagCompound;
		
		tag.setInteger(MAX_DAMAGE_TAG_KEY, damage);
		
		stack.setTagCompound(tag);
	}
	
	public static void setItemType(ItemStack stack, int type) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound tag = stack.stackTagCompound;
		
		tag.setInteger(ITEM_TYPE_KEY, type);
		
		stack.setTagCompound(tag);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int int1, boolean bool1) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if (!stack.stackTagCompound.hasKey(DAMAGE_TAG_KEY)) {
			setStackDamage(stack, 0);
		}
		
		if (!stack.stackTagCompound.hasKey(MAX_DAMAGE_TAG_KEY)) {
			setMaxStackDamage(stack, getMaxDamageForItem(stack));
		}
	}
	
	@Override
	public int getDamage(ItemStack stack) {
		return getStackDamage(stack);
	}

	@Override
	public int getDisplayDamage(ItemStack stack) {
		return getStackDamage(stack);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return getMaxDamage(stack);
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return getStackDamage(stack) > 0;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		if (damage < 0) {
			damage = 0;
		}
		
		setStackDamage(stack, damage);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + getItemName(stack);
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (ItemStack stack : getItemsForCreativeTab()) {
			list.add(stack);
		}
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return getIcon(getItemType(stack));
	}
	
	public Icon getIcon(int type) {
		return null;
	}
	
	public abstract int getMaxDamageForItem(ItemStack stack);
	
	public abstract String getItemName(ItemStack stack);

	public abstract ItemStack[] getItemsForCreativeTab();
	
}
