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
import electrodynamics.purity.MetalData;

public class ItemGlassJar extends Item {

	public static final int SHAKE_PROGRESS_MAX = 20;
	
	public static final String DUST_LIST_KEY = "dusts";
	public static final String DUST_EXIST_BOOL_KEY = "hasDusts";
	public static final String DUST_SHAKEN_BOOL_KEY = "dustShaken";
	public static final String SHAKE_RESULTS_LIST_KEY = "shakenDusts";
	
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
		jarNBT.setBoolean(DUST_EXIST_BOOL_KEY, true);
		jar.setTagCompound(jarNBT);
	}
	
	public static void dumpDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		jarNBT.setTag(DUST_LIST_KEY, new NBTTagList());
		jarNBT.setBoolean(DUST_EXIST_BOOL_KEY, false);
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
			if (!jarNBT.hasKey(DUST_EXIST_BOOL_KEY) || (jarNBT.hasKey(DUST_EXIST_BOOL_KEY) && jarNBT.getBoolean(DUST_EXIST_BOOL_KEY) == false)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static MetalData[] getShakeResults(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		if (hasShakenDusts(jar)) {
			NBTTagList dustsNBT = jarNBT.getTagList(SHAKE_RESULTS_LIST_KEY);
			MetalData[] dusts = new MetalData[dustsNBT.tagCount()];
			
			for (int i=0; i<dustsNBT.tagCount(); i++) {
				NBTTagCompound dust = (NBTTagCompound) dustsNBT.tagAt(i);
				MetalData data = new MetalData();
				data.readFromNBT(dust);
				dusts[i] = data;
			}
			
			return dusts;
		} else {
			return new MetalData[0];
		}
	}
	
	public static void shakeDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		if (hasDusts(jar) && !hasShakenDusts(jar)) {
			ItemStack[] dusts = getStoredDusts(jar);
			
			//TODO
		}
	}
	
	public static boolean hasShakenDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			return false;
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		if (!jarNBT.hasKey(SHAKE_RESULTS_LIST_KEY)) {
			return false;
		} else {
			if (!jarNBT.hasKey(DUST_SHAKEN_BOOL_KEY) || (jarNBT.hasKey(DUST_SHAKEN_BOOL_KEY) && jarNBT.getBoolean(DUST_SHAKEN_BOOL_KEY) == false)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static void dumpShakenDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound jarNBT = jar.stackTagCompound;
		
		jarNBT.setTag(SHAKE_RESULTS_LIST_KEY, new NBTTagList());
		jarNBT.setBoolean(DUST_SHAKEN_BOOL_KEY, false);
		jar.setTagCompound(jarNBT);
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
//				ItemStack[] dusts = ItemGlassJar.getStoredDusts(stack);
//				
//				for (ItemStack dust : dusts) {
//					player.dropPlayerItem(dust);
//				}
//				
//				ItemGlassJar.dumpDusts(stack);
				
				
			}
		}
		
		return stack;
	}

}
