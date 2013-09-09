package electrodynamics.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import electrodynamics.purity.AlloyStack;
import electrodynamics.purity.Attribute;
import electrodynamics.purity.Attribute.AttributeType;
import electrodynamics.purity.MetalData;
import electrodynamics.purity.DynamicAlloyPurities.MetalType;

public abstract class ItemAlloyTool extends Item {

	public ToolType type;
	
	public ItemAlloyTool(int id, ToolType type) {
		super(id);
		setMaxDamage(100); //TEMP
		setMaxStackSize(1);
		
		this.type = type;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (!stack.hasTagCompound()) {
			return false;
		}
	
		NBTTagCompound tag = stack.getTagCompound();
		World world = player.worldObj;
		int blockID = world.getBlockId(x, y, z);
		int blockMeta = world.getBlockMetadata(x, y, z);
		Material blockMaterial = world.getBlockMaterial(x, y, z);
		Block block = Block.blocksList[blockID];
		int harvestLevel = MinecraftForge.getBlockHarvestLevel(block, blockMeta, this.type.toString().toLowerCase());
		if (block == null || blockID < 1) {
			return false;
		}
		
		if (harvestLevel <= tag.getInteger("harvestLevel")) {
			return false;
		} else {
			world.setBlockToAir(x, y, z);
			if (!player.capabilities.isCreativeMode) {
				onBlockDestroyed(stack, world, blockID, x, y, z, player);
			}
			if (!world.isRemote) {
				world.playAuxSFX(2001, x, y, z, blockID + (blockMeta << 12));
			}
			
			return true;
		}
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int meta) {
		if (!stack.hasTagCompound()) {
			return 1.0F;
		}
		
		NBTTagCompound tag = stack.getTagCompound();
		
		if (canHarvestBlock(block)) {
			float miningSpeed = 4.0F;
			AlloyStack tool = new AlloyStack(stack);
			
			if (tool.getMetals() != null && tool.getMetals().length > 0) {
				for (MetalData data : tool.getMetals()) {
					MetalType type = MetalType.get(data);
					
					for (Attribute attribute : type.getAttributes()) {
						if (attribute.attribute == AttributeType.MINING_SPEED) {
							miningSpeed *= (attribute.modifier + 1);
						}
					}
				}
			}
			
			return miningSpeed;
		}
		
		return super.getStrVsBlock(stack, block, meta);
	}
	
	@Override
	public boolean canHarvestBlock(Block block) {
		if (block.blockMaterial.isToolNotRequired()) {
			return true;
		}
		
		for (Material material : getEffectiveMaterials()) {
			if (material == block.blockMaterial) {
				return true;
			}
		}
		
		return false;
	}
	
	public enum ToolType {
		PICKAXE,
		SHOVEL,
		AXE,
		HOE,
		SWORD;
	}

	public String getHarvestType() {
		return this.type.toString().toLowerCase();
	}
	
	public abstract Material[] getEffectiveMaterials();
	
}
