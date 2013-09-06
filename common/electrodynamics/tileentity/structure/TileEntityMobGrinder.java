package electrodynamics.tileentity.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import electrodynamics.api.misc.IRedstoneUser;
import electrodynamics.core.CoreUtils;
import electrodynamics.core.misc.DamageSourceBlock;
import electrodynamics.inventory.wrapper.InventoryWrapperStack;
import electrodynamics.lib.block.StructureComponent;
import electrodynamics.recipe.RecipeGrinder;
import electrodynamics.recipe.manager.CraftingManager;
import electrodynamics.util.InventoryUtil;
import electrodynamics.util.PlayerUtil;
import electrodynamics.util.misc.EntityReflectionWrapper;

public class TileEntityMobGrinder extends TileEntityStructure implements IFluidHandler, IInventory, IRedstoneUser {

	public InventoryWrapperStack inventoryWrapper = new InventoryWrapperStack();
	
	public FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);
	
	public boolean active = false;
	
	@Override
	public void updateEntity() {
		if (CoreUtils.isServer(worldObj)) {
			if (this.isValidStructure() && this.isCentralTileEntity() && this.active == true) {
				collectEntities();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void collectEntities() {
		List<EntityLiving> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, getCollectionAABB());
		
		if (entities != null && entities.size() > 0) {
			for (Entity entity : entities) {
				if (entity instanceof EntityLiving) {
					collectDropsFromEntity((EntityLiving) entity);
					entity.attackEntityFrom(new DamageSourceBlock(), ((EntityLivingBase)entity).getMaxHealth());
				} else if (entity instanceof EntityItem) {
					ItemStack stack = ((EntityItem)entity).getEntityItem();
					boolean flag = false;
					int count;
					
					for (count=1; count<stack.stackSize; count++) {
						ItemStack stackCopy = stack.copy();
						stackCopy.stackSize = 1;
						
						if (!collectItem(stackCopy)) {
							flag = true;
							break;
						}
					}
					
					if (flag) {
						stack.stackSize -= count;
					} else {
						entity.setDead();
					}
				}
			}
		}
	}
	
	private void collectDropsFromEntity(EntityLivingBase entity) {
		if (entity.getHealth() > 0) {
			if (!(entity instanceof EntityPlayer)) {
				EntityReflectionWrapper erw = new EntityReflectionWrapper(entity);
				int count = new Random().nextInt(3);

				for (int i=0; i<count; i++) {
					this.inventoryWrapper.getStack().push(new ItemStack(erw.getMainDropID(), 1, 0));
				}
			} else {
				EntityPlayer player = (EntityPlayer) entity;
				
				if (!player.capabilities.isCreativeMode) {
					for (int i=0; i<player.inventory.mainInventory.length; i++) {
						ItemStack stack = player.inventory.getStackInSlot(i);
						
						if (stack != null) {
							this.inventoryWrapper.getStack().push(stack.copy());
							player.inventory.setInventorySlotContents(i, null);
						}
					}
					
					for (int i=0; i<player.inventory.armorInventory.length; i++) {
						ItemStack stack = player.inventory.getStackInSlot(i);
						
						if (stack != null) {
							this.inventoryWrapper.getStack().push(stack.copy());
							player.inventory.setInventorySlotContents(i, null);
						}
					}
					
					// Who commented this out?
					if (new Random().nextInt(10) == 0) {
						this.inventoryWrapper.getStack().push(PlayerUtil.getPlayerHead(player).copy());
					}
				}
			}
		}
	}
	
	public boolean collectItem(ItemStack stack) {
		RecipeGrinder recipe = CraftingManager.getInstance().grindManager.getRecipe(stack);
		
		if (recipe != null) {
			// Doing liquid check first, since it has a chance to fail
			if (recipe.liquidOutput != null) {
				if (tank.fill(recipe.liquidOutput, true) == 0) {
					return false;
				}
			}
			
			if (recipe.itemOutput != null) {
				for (ItemStack stackOut : recipe.itemOutput) {
					if (stackOut != null) {
						this.inventoryWrapper.getStack().push(stackOut.copy());
					}
				}
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public void dispenseItem(ItemStack stack) {
		TileEntityStructure output = getOutputBlock();
		
		if (output != null) {
			int meta = this.worldObj.getBlockMetadata(output.xCoord, output.yCoord, output.zCoord);
			InventoryUtil.dispenseOutSide(worldObj, output.xCoord, output.yCoord, output.zCoord, ForgeDirection.getOrientation(meta), stack.copy(), new Random());
		}
	}
	
	public TileEntityStructure getOutputBlock() {
		for (int y = yCoord; y < this.yCoord + 2; y++) {
			for (int x = xCoord - 1; x < xCoord + 2; x++) {
				for (int z = zCoord - 1; z < zCoord + 2; z++) {
					TileEntity tile = this.worldObj.getBlockTileEntity(x, y, z);

					if (tile != null && tile instanceof TileEntityStructure) {
						if (StructureComponent.values()[((TileEntityStructure)tile).getSubBlock()] == StructureComponent.VALVE) {
							return (TileEntityStructure) tile;
						}
					}
				}
			}
		}
		
		return null;
	}
	
	private AxisAlignedBB getCollectionAABB() {
		double x = this.xCoord + .5;
		double y = this.yCoord + 1;
		double z = this.zCoord + .5;
		
		return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x, y, z).expand(.75, 2, .75);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.inventoryWrapper = new InventoryWrapperStack();
		this.inventoryWrapper.getStack().addAll(Arrays.asList(InventoryUtil.readItemsFromNBT("Items", nbt)));
		this.tank.readFromNBT(nbt.getCompoundTag("Fluid"));
		this.active = nbt.getBoolean("active");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		InventoryUtil.writeItemsToNBT("Items", nbt, this.inventoryWrapper.getStack().toArray(new ItemStack[this.inventoryWrapper.getStack().size()]));
		NBTTagCompound fluidtag = new NBTTagCompound();
		tank.writeToNBT(fluidtag);
		nbt.setCompoundTag("Fluid", fluidtag);
		nbt.setBoolean("active", this.active);
	}
	
	@Override
	public boolean onBlockActivatedBy(EntityPlayer player, int side, float xOff, float yOff, float zOff) {
		return false;
	}

	/* IFLUIDHANDLER */
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {tank.getInfo()};
	}

	/* IINVENTORY */
	@Override
	public int getSizeInventory() {
		return this.inventoryWrapper.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inventoryWrapper.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return this.inventoryWrapper.decrStackSize(slot, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return this.inventoryWrapper.getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventoryWrapper.setInventorySlotContents(slot, stack);
	}

	@Override
	public String getInvName() {
		return this.inventoryWrapper.getInvName();
	}

	@Override
	public boolean isInvNameLocalized() {
		return this.inventoryWrapper.isInvNameLocalized();
	}

	@Override
	public int getInventoryStackLimit() {
		return this.inventoryWrapper.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.inventoryWrapper.isUseableByPlayer(entityplayer);
	}

	@Override
	public void openChest() {
		this.inventoryWrapper.openChest();
	}

	@Override
	public void closeChest() {
		this.inventoryWrapper.closeChest();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return this.inventoryWrapper.isItemValidForSlot(slot, stack);
	}

	@Override
	public void updateSignalStrength(ForgeDirection side, int strength) {
		this.active = strength > 0;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("active", this.active);
		sendDataToClient(nbt);
	}

	@Override
	public int getSignalStrength() {
		return this.active == true ? 15 : 0;
	}

}
