package electrodynamics.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import electrodynamics.network.packet.PacketUpdateSlot;
import electrodynamics.purity.Attribute;
import electrodynamics.purity.Attribute.AttributeType;
import electrodynamics.util.BlockUtil;

public class ItemAlloyHoe extends ItemAlloyTool {

	public static final ItemStack BONEMEAL = new ItemStack(Item.dyePowder, 1, 15);
	
	public ItemAlloyHoe(int id) {
		super(id, ToolType.HOE);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		World world = player.worldObj;
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		int blockID = block.blockID;
		int blockMeta = world.getBlockMetadata(x, y, z);
		
		if (!player.capabilities.isCreativeMode) {
			if (block != null) {
				if (block instanceof IPlantable) { // Sickle functionality
					boolean efficientFlag = false;
					
					for (Attribute attribute : getAttributes(stack)) {
						if (attribute.attribute == AttributeType.EFFICIENCY) {
							efficientFlag = true;
						}
					}
					
					for (int newX = efficientFlag ? -2 : -1; newX<(efficientFlag ? 3 : 2); newX++) {
						for (int newZ = efficientFlag ? -2 : -1; newZ<(efficientFlag ? 3 : 2); newZ++) {
							block = Block.blocksList[world.getBlockId(x + newX, y, z + newZ)];
							
							if (block instanceof IPlantable) {
								if (!world.isRemote) {
									block.harvestBlock(world, player, x + newX, y, z + newZ, blockMeta);
									world.playAuxSFX(2001, x + newX, y, z + newZ, blockID + (blockMeta << 12));
									world.setBlockToAir(x + newX, y, z + newZ);
								}
							}
						}
					}
					
					return true;
				} else if (block instanceof BlockFarmland) {
					world.setBlockToAir(x, y, z);
					if (!world.isRemote) {
						BlockUtil.dropItemFromBlock(world, x, y, z, new ItemStack(Block.tilledField), new Random());
						world.playAuxSFX(2001, x, y, z, blockID + (blockMeta << 12));
					}
					return true;
				}
			}
		}
		
		return super.onBlockStartBreak(stack, x, y, z, player);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				if (player.inventory.hasItemStack(BONEMEAL) || player.capabilities.isCreativeMode) {
					boolean consumed = false;
					
					if (!player.capabilities.isCreativeMode) { // If not creative, consume
						for (int i=0; i<player.inventory.getSizeInventory(); i++) {
							ItemStack invStack = player.inventory.getStackInSlot(i);
							
							if (invStack != null && invStack.isItemEqual(BONEMEAL)) {
								player.inventory.decrStackSize(i, 1);
								consumed = true;
								PacketUpdateSlot packet = new PacketUpdateSlot(player.inventory.getStackInSlot(i), i);
								PacketDispatcher.sendPacketToPlayer(packet.makePacket(), (Player) player);
							}
						}
					}
					
					if (consumed || player.capabilities.isCreativeMode) { // Apply bonemeal ONLY if consumed or if creative
						boolean efficientFlag = false;
						
						for (Attribute attribute : getAttributes(stack)) {
							if (attribute.attribute == AttributeType.EFFICIENCY) {
								efficientFlag = true;
							}
						}
						
						for (int i=0; i<(efficientFlag ? 2 : 1); i++) {
							if (BlockUtil.applyBonemeal(stack, world, x, y, z, player)) {
								world.playAuxSFX(2005, x, y, z, 0);
							}
						}
					}
				}
			} else {
				UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
				if (MinecraftForge.EVENT_BUS.post(event)) {
					return false;
				}

				if (event.getResult() == Result.ALLOW) {
					return true;
				}

				int i1 = world.getBlockId(x, y, z);
				boolean air = world.isAirBlock(x, y + 1, z);

				if (side != 0 && air && (i1 == Block.grass.blockID || i1 == Block.dirt.blockID)) {
					Block block = Block.tilledField;
					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

					if (world.isRemote) {
						return true;
					} else {
						world.setBlock(x, y, z, block.blockID);
						return true;
					}
				} else {
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] {};
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
		return "headHoe";
	}
	
}
