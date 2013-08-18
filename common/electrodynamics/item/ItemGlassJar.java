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
import electrodynamics.util.NBTUtil;

public class ItemGlassJar extends Item {

	public static final int SHAKE_PROGRESS_MAX = 20;
	
	public ItemGlassJar(int id) {
		super(id);
		setCreativeTab(CreativeTabED.tool);
		setMaxStackSize(1);
	}

	public static int getShakeProgess(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		if (!jar.stackTagCompound.hasKey("progress")) {
			return 0;
		} else {
			return jar.stackTagCompound.getInteger("progress");
		}
	}
	
	public static void setShakeProgress(ItemStack jar, int progress) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		if (progress <= SHAKE_PROGRESS_MAX) {
			jar.stackTagCompound.setInteger("progress", progress);
		} else {
			jar.stackTagCompound.setInteger("progress", SHAKE_PROGRESS_MAX);
		}
	}
	
	public static void increaseShakeProgress(ItemStack jar, int increase) {
		int current = getShakeProgess(jar);
		setShakeProgress(jar, current + increase);
	}
	
	public static ItemStack[] getStoredDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		if (jar.stackTagCompound.hasKey("dusts")) {
			NBTTagList dusts = jar.stackTagCompound.getTagList("dusts");
			ItemStack[] dustsArray = new ItemStack[dusts.tagCount()];
			
			for (int i=0; i<dusts.tagCount(); i++) {
				NBTTagCompound dust = (NBTTagCompound) dusts.tagAt(i);
				dustsArray[i] = ItemStack.loadItemStackFromNBT(dust);
			}
			
			return dustsArray;
		}
		
		return new ItemStack[0];
	}
	
	public static void addDust(ItemStack jar, ItemStack dust) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		if (NBTUtil.hasKey(jar.stackTagCompound, "dusts")) {
			NBTTagList dusts = jar.stackTagCompound.getTagList("dusts");
			NBTTagCompound dustNBT = new NBTTagCompound();
			dust.writeToNBT(dustNBT);
			dusts.appendTag(dustNBT);
			
			jar.stackTagCompound.setTag("dusts", dusts);
		}
	}
	
	public static void dumpDusts(ItemStack jar) {
		if (jar.stackTagCompound == null) {
			jar.setTagCompound(new NBTTagCompound());
		}
		
		jar.stackTagCompound.setTag("dusts", null);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean show) {
		ItemStack[] dusts = getStoredDusts(stack);
		
		if (dusts != null) {
			list.add(dusts.length + "");
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && !player.isSneaking()) {
			GuiHandler.openGui(player, world, (int)player.posX, (int)player.posY, (int)player.posZ, GuiType.GLASS_JAR);
		}
		
		return stack;
	}

}
