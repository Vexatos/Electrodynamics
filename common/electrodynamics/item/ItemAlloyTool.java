package electrodynamics.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.purity.AlloyStack;
import electrodynamics.purity.Attribute;
import electrodynamics.purity.Attribute.AttributeType;
import electrodynamics.purity.DynamicAlloyPurities;
import electrodynamics.purity.MetalData;

public abstract class ItemAlloyTool extends Item {

	public static final String HARVEST_LEVEL_TAG = "harvestLevel";
	public static final String MAX_DAMAGE_TAG = "maxDamage";
	public static final String CURRENT_DAMAGE_TAG = "damage";
	
	public ToolType type;
	
	private Icon handleWood;
	private Icon handleMetal;
	private Icon head;
	
	public ItemAlloyTool(int id, ToolType type) {
		super(id);
		setMaxDamage(1); // Not used
		setMaxStackSize(1);
		setNoRepair();
		
		this.type = type;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean show) {
		//TODO
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
    	return true;
    }
    
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamageForRenderPass(int damage, int pass) {
		return pass == 0 ? this.handleWood : this.head; //TODO Custom render to check handle type
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
    	if (renderPass == 0) return 0xFFFFFF;
    	return ItemAlloy.getColorFromAlloy(stack).toInt();
    }
	
    public boolean harvestBlock(ItemStack stack, int x, int y, int z, EntityPlayer player) {
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
		
		if (harvestLevel >= EnumToolMaterial.IRON.getHarvestLevel()) {
			return false;
		}
		
		return true;
    }
    
	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (harvestBlock(stack, x, y, z, player)) {
			setDamage(stack, getDamage(stack) + 1);
		}
		return false;
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
		if (this.type != ToolType.SWORD) {
			if (!stack.hasTagCompound()) {
				return 1.0F;
			}
			
			NBTTagCompound tag = stack.getTagCompound();
			
			if (canHarvestBlock(block)) {
				float miningSpeed = AttributeType.EFFICIENCY.baseValue;
				
				AlloyStack tool = new AlloyStack(stack);
				
				if (tool.getMetals() != null && tool.getMetals().length > 0) {
					float modifierSum = 0F;
					int modifiersCount = 0;
					for (MetalData data : tool.getMetals()) {
						ItemStack component = data.component;
						int total = data.getTotal();
						
						modifiersCount += total;
						for (Attribute attribute : DynamicAlloyPurities.getAttributesForStack(component)) {
							if (attribute.attribute == AttributeType.EFFICIENCY) {
								modifierSum += attribute.modifier * total;
							}
						}
					}
					
					miningSpeed *= 1 + modifierSum / modifiersCount;
				}
				
				return miningSpeed;
			}
		}
		
		return super.getStrVsBlock(stack, block);
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int meta) {
		return getStrVsBlock(stack, block);
	}
	
	@Override
	public boolean canHarvestBlock(Block block) {
		if (block.blockMaterial == Material.plants ||
			block.blockMaterial == Material.vine ||
			block.blockMaterial == Material.coral ||
			block.blockMaterial.isAdventureModeExempt() ||
			block.blockMaterial.isToolNotRequired()) { // Plants apparently require tools
			return false;
		}
		
		for (Material material : getEffectiveMaterials()) {
			if (material == block.blockMaterial) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int id, boolean k) {
		if (getMaxDamage(stack) == 0) {
			setMaxDamage(EnumToolMaterial.IRON.getMaxUses());
		}
	}
	
	@Override
	public int getDamage(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			return 0;
		}
		
		return stack.getTagCompound().getInteger(CURRENT_DAMAGE_TAG);
	}

	@Override
	public int getDisplayDamage(ItemStack stack) {
//		return MathUtil.reverseNumber(getDamage(stack), 0, getMaxDamage(stack));
		return getDamage(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			return 0;
		}
		
		return stack.getTagCompound().getInteger(MAX_DAMAGE_TAG);
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return getDamage(stack) > 0;
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setInteger(CURRENT_DAMAGE_TAG, damage);
		stack.setTagCompound(nbt);
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}
	
	@Override
	public float getDamageVsEntity(Entity entity, ItemStack stack) {
		//Modifiable?
		return this.type.baseDamage;
	}
	
	public void setMaxDamage(ItemStack stack, int damage) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setInteger(MAX_DAMAGE_TAG, damage);
		stack.setTagCompound(nbt);
	}
	
	public String getHarvestType() {
		return this.type.toString().toLowerCase();
	}

	public Attribute[] getAttributes(ItemStack stack) {
		AlloyStack tool = new AlloyStack(stack);
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		if (tool.getMetals() != null && tool.getMetals().length > 0) {
			for (MetalData data : tool.getMetals()) {
				for (Attribute attribute : DynamicAlloyPurities.getAttributesForStack(data.component)) {
					attributes.add(attribute);
				}
			}
		}
		
		return attributes.toArray(new Attribute[attributes.size()]);
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register) {
    	this.handleWood = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":tool/component/" + getWoodenHandle());
    	this.handleMetal = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":tool/component/" + getMetalHandle());
    	this.head = register.registerIcon(ModInfo.GENERIC_MOD_ID.toLowerCase() + ":tool/component/" + getHead());
    }
	
	public abstract String getWoodenHandle();
	
	public abstract String getMetalHandle();
	
	public abstract String getHead();
	
	public abstract Material[] getEffectiveMaterials();
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	public enum ToolType {
		PICKAXE(ItemPickaxe.class),
		SHOVEL(ItemSpade.class),
		AXE(ItemAxe.class),
		HOE(ItemHoe.class),
		SWORD(ItemSword.class);
		
		/** Base damage against mobs */
		public int baseDamage;
		
		private Class<? extends Item> itemClazz;
		
		private ToolType(Class<? extends Item> clazz) {
			this.itemClazz = clazz;
			this.baseDamage = 1;
		}
		
		private ToolType(Class<? extends Item> clazz, int baseDamage) {
			this.itemClazz = clazz;
			this.baseDamage = baseDamage;
		}
		
		public Item getItem() {
			switch(this) {
			case AXE: return EDItems.itemAlloyAxe;
			case HOE: return EDItems.itemAlloyHoe;
			case PICKAXE: return EDItems.itemAlloyPickaxe;
			case SHOVEL: return EDItems.itemAlloyShovel;
			case SWORD: return EDItems.itemAlloySword;
			default: return null;
			}
		}
		
		public static ToolType getTypeFromClass(Class<? extends Item> clazz) {
			for (ToolType type : ToolType.values()) {
				if (type.itemClazz.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
					return type;
				}
			}
			
			return null;
		}
	}
	
}
