package electrodynamics.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import electrodynamics.core.CreativeTabED;
import electrodynamics.core.handler.GuiHandler;
import electrodynamics.core.handler.GuiHandler.GuiType;

public class ItemGlassJar extends Item {

	public static final int SHAKE_PROGRESS_MAX = 20;
	
	public static final String DUST_LIST_KEY = "dusts";
	public static final String DUST_BOOL_KEY = "hasDusts";
	
	public ItemGlassJar(int id) {
		super(id);
		setCreativeTab(CreativeTabED.tool);
		setMaxStackSize(1);
	}

	public static ItemStack[] getStoredDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		if (hasDusts(jar)) {
			NBTTagList dustsNBT = jarNBT.getTagList(DUST_LIST_KEY);
			ItemStack[] dusts = new ItemStack[dustsNBT.tagCount()];
			
			for (int i=0; i<dustsNBT.tagCount(); i++) {
				NBTTagCompound dust = (NBTTagCompound) dustsNBT.tagAt(i);
				dusts[i] = ItemStack.loadItemStackFromNBT(dust);
			}
			
			return dusts;
		} else {
			return new ItemStack[0];
		}
	}
	
	public static void addDusts(ItemStack jar, ItemStack[] dusts) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;

		NBTTagList dustsNBT = null;
		
		if (!hasDusts(jar)) {
			dustsNBT = new NBTTagList();
		} else {
			dustsNBT = jarNBT.getTagList(DUST_LIST_KEY);
		}
		
		for (ItemStack dustStack : dusts) {
			NBTTagCompound dust = new NBTTagCompound();
			dustStack.writeToNBT(dust);
			dustsNBT.appendTag(dust);
		}
		jarNBT.setTag(DUST_LIST_KEY, dustsNBT);
		jarNBT.setBoolean(DUST_BOOL_KEY, true);
		jar.setTagCompound(jarNBT);
	}
	
	public static void dumpDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		jarNBT.setTag(DUST_LIST_KEY, new NBTTagList());
		jarNBT.setBoolean(DUST_BOOL_KEY, false);
		jar.setTagCompound(jarNBT);
	}
	
	public static boolean hasDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			return false;
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		if (!jarNBT.hasKey(DUST_LIST_KEY)) {
			return false;
		} else {
			if (!jarNBT.hasKey(DUST_BOOL_KEY) || (jarNBT.hasKey(DUST_BOOL_KEY) && jarNBT.getBoolean(DUST_BOOL_KEY) == false)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean show) {
		if (!hasDusts(stack)) {
			list.add("Empty");
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if (!player.isSneaking()) {
				GuiHandler.openGui(player, world, (int)player.posX, (int)player.posY, (int)player.posZ, GuiType.GLASS_JAR);
			} else {
				ItemStack[] dusts = ItemGlassJar.getStoredDusts(stack);
				
				for (ItemStack dust : dusts) {
					player.dropPlayerItem(dust);
				}
				
				ItemGlassJar.dumpDusts(stack);
			}
		}
		
		return stack;
	}

}
