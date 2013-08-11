package electrodynamics.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import electrodynamics.core.CreativeTabED;
import electrodynamics.core.handler.GuiHandler;
import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.interfaces.IInventoryItem;
import electrodynamics.inventory.InventoryItem;

public class ItemGlassJar extends Item implements IInventoryItem {

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
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && !player.isSneaking()) {
			GuiHandler.openGui(player, world, (int)player.posX, (int)player.posY, (int)player.posZ, GuiType.GLASS_JAR);
		}
		
		return stack;
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		if (stack.getItem() instanceof IInventoryItem) {
			return new InventoryItem(1, stack, 1);
		}
		
		return null;
	}
	
}
