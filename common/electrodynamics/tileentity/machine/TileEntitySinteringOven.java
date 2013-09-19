package electrodynamics.tileentity.machine;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeDirection;
import electrodynamics.api.tool.ITool;
import electrodynamics.api.tool.ToolType;
import electrodynamics.core.CoreUtils;
import electrodynamics.interfaces.IClientDisplay;
import electrodynamics.interfaces.IHeatable;
import electrodynamics.inventory.InventoryItem;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemAlloy;
import electrodynamics.purity.AlloyStack;
import electrodynamics.purity.DynamicAlloyPurities;
import electrodynamics.purity.MetalData;
import electrodynamics.util.InventoryUtil;
import electrodynamics.util.ItemUtil;

public class TileEntitySinteringOven extends TileEntityMachine implements IClientDisplay, IHeatable {

	public final float ROTATIONAL_MAX = 1.5F;
	
	public final int MAX_HEAT = 4000;
	
	public final int LAST_BAREABLE_HEAT = 50; //Move?
	
	public float doorAngle = 0;
	
	public boolean open = false;
	public boolean burning = false;
	
	/** Based off of furnace fuel burn time, but constantly drained "to keep oven hot" */
	public int fuelLevel;
	
	/** Set to the recipes value when tray is input. If this is greater than zero, a valid recipe is present */
	public int totalCookTime;

	/** Set to the recipes value when tray is input, when equal to zero, tray contents are replaced with recipe output */
	public int currentCookTime;
	
	/** Current heat of oven, gotten through IHeatable's getHeat */
	private int currentHeat = 0;
	
	/** Tray's internal inventory */
	public InventoryItem trayInventory;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("open", open);
		nbt.setInteger("fuelLevel", fuelLevel);
		nbt.setInteger("currentHeat", this.currentCookTime);
		if (this.trayInventory != null) {
			this.trayInventory.writeToNBT(nbt);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.open = nbt.getBoolean("open");
		this.fuelLevel = nbt.getInteger("fuelLevel");
		if(this.fuelLevel > 0)
			this.burning = true;
		else
			this.burning = false;
		this.currentHeat = nbt.getInteger("currentHeat");
		if (nbt.hasKey("Items")) {
			this.trayInventory = new InventoryItem(9, new ItemStack(EDItems.itemTray));
			this.trayInventory.readFromNBT(nbt);
		}
	}

	@Override
	public void onUpdatePacket(NBTTagCompound nbt) {
		super.onUpdatePacket(nbt);
		
		if(nbt.hasKey("open"))
		{
			this.open = nbt.getBoolean("open");
		}
		
		if(nbt.hasKey("burning"))
		{
			this.burning = nbt.getBoolean("burning");
		}
		
		if(nbt.hasKey("noTray"))
		{
			this.trayInventory = null;
		}
		
		if(nbt.hasKey("Items"))
		{
			this.trayInventory = new InventoryItem(1, new ItemStack(EDItems.itemTray), 9);
			this.trayInventory.readFromNBT(nbt);
		}
		
		if (nbt.hasKey("currentHeat")) {
			this.currentHeat = nbt.getInteger("currentHeat");
		}
	}

	@Override
	public void onDescriptionPacket(NBTTagCompound nbt) {
		super.onDescriptionPacket(nbt);

		this.open = nbt.getBoolean("open");
		this.burning = nbt.getBoolean("burning");
		this.fuelLevel = nbt.getInteger("fuelLevel");
		this.currentHeat = nbt.getInteger("currentHeat");

		if (nbt.hasKey("Items")) {
			this.trayInventory = new InventoryItem(1, new ItemStack(EDItems.itemTray), 9);
			this.trayInventory.readFromNBT(nbt);
		}else{
			this.trayInventory = null;
		}
	}

	@Override
	public void getDescriptionForClient(NBTTagCompound nbt) {
		super.getDescriptionForClient(nbt);
		
		nbt.setBoolean("open", open);
		nbt.setBoolean("burning", burning);
		nbt.setInteger("fuelLevel", fuelLevel);
		nbt.setInteger("currentHeat", this.currentHeat);
		if (this.trayInventory != null) {
			this.trayInventory.writeToNBT(nbt);
		}
	}

	@SuppressWarnings("unchecked")
	public List<EntityLivingBase> getEntitiesInFireRange() {
		return this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getFireDetectionBoundingBox());
	}
	
	public AxisAlignedBB getFireDetectionBoundingBox() {
		return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).addCoord(1 * this.rotation.offsetX, 0, 1 * this.rotation.offsetZ);
	}
	
	@Override
	public void updateEntityClient()
	{
		if (open && doorAngle <= ROTATIONAL_MAX) {
			doorAngle += 0.2F;
		} else if (!open && doorAngle > 0) {
			doorAngle -= 0.2F;
		}
		
		if (doorAngle < 0) {
			doorAngle = 0;
		}
	}

	@Override
	public void updateEntityServer() {
		if (fuelLevel > 0) {
			--this.fuelLevel;
			if (this.worldObj.getTotalWorldTime() % 5 == 0 && this.currentHeat < MAX_HEAT) { // Heats four times per second (roughly?)
				++this.currentHeat;
				sendHeatUpdate();
			}
			
			if (this.open) {
				for (EntityLivingBase living : getEntitiesInFireRange()) {
					living.setFire(1);
				}
			} else {
				if (totalCookTime > 0) {
					if (trayInventory != null) {
						if (currentCookTime == 0 && isHotEnough()) {
							doProcess();

							this.totalCookTime = 0;
							this.currentCookTime = 0;

							sendTrayUpdate();
							return;
						} else {
							--currentCookTime;
						}
					} else {
						totalCookTime = 0;
					}
				} else {
					if (trayInventory != null) {
						ItemStack stack = this.trayInventory.getStackInSlot(0);
						if (stack != null && stack.getItem() instanceof ItemAlloy && stack.getItemDamage() == 0) {
							this.totalCookTime = this.currentCookTime = 200; //TEMP
//							this.totalCookTime = this.currentCookTime = getStrongestComponent() != null ? DynamicAlloyPurities.getSmeltInfoForStack(getStrongestComponent())[1] : 0;
							return;
						}
					}
				}
			}
		}else if(burning == true) {
			this.burning = false;
			sendBurningUpdate();
		} else {
			if (this.worldObj.getTotalWorldTime() % 5 == 0 && this.currentCookTime > 0) { // Heats four times per second (roughly?)
				--this.currentHeat;
				sendHeatUpdate();
			}
		}
	}

	@Override
	public void onBlockBreak() {
		if (this.trayInventory != null) {
			InventoryUtil.ejectItem(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, this.trayInventory.parent.copy(), new Random());
		}
	}
	
	@Override
	public void onBlockActivated(EntityPlayer player) {
		if(CoreUtils.isServer(worldObj)) {
			if (player.isSneaking()) {
				this.open = !this.open;
				sendOpenUpdate();
				return;
			}
			
			if (this.open) { // If oven is open
				if (player.getCurrentEquippedItem() != null) { // If player is holding item
					ItemStack currentItem = player.getCurrentEquippedItem();
				
					if (ItemUtil.getFuelValue(currentItem) > 0) { // If fuel, refuel
						addFuel(currentItem, player);
					} else if (this.trayInventory == null && currentItem.getItem() == EDItems.itemTray) { // If tray and oven is empty, insert
						insertTray(currentItem, player);
					} else if (currentItem.getItem() instanceof ITool && ((ITool)currentItem.getItem()).getToolType() == ToolType.HAMMER && this.trayInventory != null && this.totalCookTime > 0) { // If holding hammer and oven isn't empty, check for instasmelt
						if (player.capabilities.isCreativeMode) {
							if (this.totalCookTime > 0) {
								doProcess();

								this.totalCookTime = 0;
								this.currentCookTime = 0;

								sendTrayUpdate();
								return;
							}
						}
					} else { // If held item has no use, ignore and toggle door
						toggleDoor();
					}
				} else { // Else if empty hand
					if (this.trayInventory != null) { // If the oven has a tray, remove
						removeTray(player);
					} else {
						toggleDoor();
					}
				}
			} else { // Else oven is closed, nothing else can be done, so open
				toggleDoor();
			}
		}
	}

	/* INTERACTION FUNCTIONS */
	private void toggleDoor() {
		this.open = !this.open;
		sendOpenUpdate();
	}
	
	private void addFuel(ItemStack stack, EntityPlayer player) {
		this.fuelLevel += ItemUtil.getFuelValue(stack);
		--stack.stackSize;
		if( stack.itemID == Item.bucketLava.itemID ) { // return the empty bucket when using lava as fuel.
			ItemStack bucket = new ItemStack(Item.bucketEmpty, 1);
			if(stack.stackSize == 0)
			player.setCurrentItemOrArmor(0, bucket);
		else
			player.inventory.addItemStackToInventory(bucket);
		}
		this.burning = true;
		sendBurningUpdate();
	
		((EntityPlayerMP)player).updateHeldItem();
	}
	
	private void insertTray(ItemStack stack, EntityPlayer player) {
		this.trayInventory = new InventoryItem(1, stack.copy(), 9);
		--stack.stackSize;

		sendTrayUpdate();
		((EntityPlayerMP)player).updateHeldItem();
	}
	
	private void removeTray(EntityPlayer player) {
		player.setCurrentItemOrArmor(0, trayInventory.parent.copy());

		this.trayInventory = null;
		this.currentCookTime = 0;
		this.totalCookTime = 0;
	
		sendTrayUpdate();
		((EntityPlayerMP)player).updateHeldItem();
	}
	
	private void sendOpenUpdate()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("open", this.open);

		sendUpdatePacket(nbt);
	}
	
	private void sendBurningUpdate()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("burning", this.burning);

		sendUpdatePacket(nbt);
	}
	
	private void sendTrayUpdate()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(this.trayInventory != null)
			this.trayInventory.writeToNBT(nbt);
		else
			nbt.setBoolean("noTray", true);

		sendUpdatePacket(nbt);
	}

	private void sendHeatUpdate() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("currentHeat", this.currentHeat);
		sendUpdatePacket(nbt);
	}
	
	private boolean isHotEnough() {
//		return getStrongestComponent() != null && DynamicAlloyPurities.getSmeltInfoForStack(getStrongestComponent())[0] <= this.currentHeat;
		return true;
	}
	
	private ItemStack getStrongestComponent() {
		if (this.trayInventory != null) {
			ItemStack item = this.trayInventory.getStackInSlot(0);
			
			if (item != null) {
				AlloyStack alloy = new AlloyStack(item);
				
				int highestHeat = 0;
				ItemStack stack = null;
				
				for (MetalData data : alloy.getMetals()) {
					int[] info = DynamicAlloyPurities.getSmeltInfoForStack(data.component);
					if (info.length == 2) {
						if (info[0] > highestHeat) {
							highestHeat = info[0];
							stack = data.component;
						}
					}
				}
				
				return stack.copy();
			}
			
			return null;
		}
		
		return null;
	}
	
	private void doProcess() {
		ItemStack item = this.trayInventory.getStackInSlot(0).copy();
		
		if (item != null && item.getItem() instanceof ItemAlloy && item.getItemDamage() == 0) { // ALLOY!
			this.trayInventory.setInventorySlotContents(0, null);
			ItemStack alloy = item.copy();
			alloy.setItemDamage(1);
			alloy.stackSize = item.stackSize;
			this.trayInventory.setInventorySlotContents(0, alloy.copy());
		}
	}

	@Override
	public void onRandomDisplayTick(Random random) {
		if (this.burning) {
			Random rand = new Random();
			
	        float f = this.xCoord + 0.5F;
	        float f1 = this.yCoord + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
	        float f2 = this.zCoord + 0.5F;
	        float f4 = rand.nextFloat() * 0.6F - 0.3F;

	        this.worldObj.spawnParticle("smoke", f - (f4 / 2) + f4, f1, f2 - (f4 / 2) + f4, 0D, 0D, 0D);
	        this.worldObj.spawnParticle("flame", f - (f4 / 2) + f4, f1, f2 - (f4 / 2) + f4, 0D, 0D, 0D);
		}
	}

	@Override
	public void setHeat(int heat) {}

	@Override
	public void setMaxHeat(int max) {}

	@Override
	public int getHeat() {
		return this.currentHeat;
	}

	@Override
	public int getMaxHeat() {
		return this.MAX_HEAT;
	}

}
