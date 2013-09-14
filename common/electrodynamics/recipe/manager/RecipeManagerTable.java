package electrodynamics.recipe.manager;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.api.crafting.util.TableRecipeType;
import electrodynamics.block.EDBlocks;
import electrodynamics.lib.block.Ore;
import electrodynamics.lib.client.FXType;
import electrodynamics.lib.item.Component;
import electrodynamics.lib.item.Dust;
import electrodynamics.lib.item.Grinding;
import electrodynamics.network.packet.PacketFX;
import electrodynamics.network.packet.PacketSound;
import electrodynamics.recipe.OreRecipeTable;
import electrodynamics.recipe.RecipeTable;
import electrodynamics.tileentity.machine.TileEntityTable;
import electrodynamics.util.BlockUtil;

public class RecipeManagerTable {

	public ArrayList<RecipeTable> smashingTableRecipes = new ArrayList<RecipeTable>();
	
	public void registerRecipe(RecipeTable recipe) {
		smashingTableRecipes.add(recipe);
	}
	
	public void registerSmashingRecipe(ItemStack in, ItemStack out, int damage) {
		this.registerRecipe(TableRecipeType.SMASH, in, out, damage);
	}
	
	public void registerSieveRecipe(ItemStack in, ItemStack out, int damage) {
		this.registerRecipe(TableRecipeType.SIEVE, in, out, damage);
	}
	
	public void registerRecipe(TableRecipeType type, ItemStack in, ItemStack out, int damage) {
		smashingTableRecipes.add(new RecipeTable(type, in, out, damage));
	}
	
	public RecipeTable getRecipe(ItemStack in, ItemStack tool) {
		if (in == null) return null;
		
		for (RecipeTable recipe : smashingTableRecipes) {
			if (recipe.isInput(in, tool)) {
				return recipe;
			}
		}
		
		return null;
	}
	
	public RecipeTable[] getRecipes() {
		return smashingTableRecipes.toArray(new RecipeTable[smashingTableRecipes.size()]);
	}
	
	public void initRecipes() {
		/* Various vanilla recipes */
		registerSmashingRecipe(new ItemStack(Block.stone), new ItemStack(Block.cobblestone), 1);
		registerSmashingRecipe(new ItemStack(Block.brick), new ItemStack(Item.brick, 4, 0), 1);
		
		registerRecipe(new RecipeTable(TableRecipeType.SMASH, new ItemStack(Item.enderPearl), null, 1) {
			@Override
			public void onSmashed(EntityPlayer player, TileEntityTable table, ItemStack stack) {
				Random random = new Random();
				
				int xPosOrNeg = random.nextInt(2) == 1 ? -1 : 1;
				int zPosOrNeg = random.nextInt(2) == 1 ? -1 : 1;
				int xMove = random.nextInt(10);
				int zMove = random.nextInt(10);
				
				player.setPositionAndUpdate(player.posX + (xMove * xPosOrNeg), BlockUtil.getFirstUncoveredYPos(player.worldObj, xMove * xPosOrNeg, zMove * zPosOrNeg), player.posZ + (zMove * zPosOrNeg));

				PacketFX fx = new PacketFX(FXType.ENDER_PARTICLES, player.posX, player.posY, player.posZ, new int[] {});
				PacketSound sound = new PacketSound("mob.endermen.portal", player.posX, player.posY, player.posZ, PacketSound.TYPE_SOUND);
				
				PacketDispatcher.sendPacketToAllAround(player.posX, player.posY, player.posZ, 32D, player.worldObj.provider.dimensionId, fx.makePacket());
				PacketDispatcher.sendPacketToAllAround(player.posX, player.posY, player.posZ, 32D, player.worldObj.provider.dimensionId, sound.makePacket());
			}
		});
		
		registerRecipe(new RecipeTable(TableRecipeType.SMASH, new ItemStack(Item.gunpowder), null, 1) {
			@Override
			public void onSmashed(EntityPlayer player, TileEntityTable table, ItemStack stack) {
				player.worldObj.createExplosion(player, table.xCoord, table.yCoord, table.zCoord, 6F, false);
			}
		});
		
		registerRecipe(new RecipeTable(TableRecipeType.SMASH, new ItemStack(Block.tnt), null, 1) {
			@Override
			public void onSmashed(EntityPlayer player, TileEntityTable table, ItemStack stack) {
				EntityTNTPrimed tnt = new EntityTNTPrimed(player.worldObj, table.xCoord + 0.5, table.yCoord + 1.5, table.zCoord + 0.5, player);
				tnt.fuse = 0;
				player.worldObj.spawnEntityInWorld(tnt);
			}
		});
		
		//First 9 ore/grinding ordinals are equal
		for (int i=0; i<8; i++) {
			registerSmashingRecipe(Ore.get(i).toItemStack(), Grinding.get(i).toItemStack(), 1);
		}
		
		//Various other item smash recipes
		registerSmashingRecipe(Component.LITHIUM_CLAY.toItemStack(), Grinding.LITHIUM.toItemStack(), 1);
		registerSmashingRecipe(new ItemStack(EDBlocks.blockDecorative, 1, 0), Dust.LIMESTONE.toItemStack(), 1);
		
		//Vanilla ores
		registerSmashingRecipe(new ItemStack(Block.oreDiamond), Grinding.DIAMOND.toItemStack(), 1);
		registerSmashingRecipe(new ItemStack(Block.oreEmerald), Grinding.EMERALD.toItemStack(), 1);
		registerSmashingRecipe(new ItemStack(Block.oreGold), Grinding.GOLD.toItemStack(), 1);
		registerSmashingRecipe(new ItemStack(Block.oreIron), Grinding.IRON.toItemStack(), 1);
		registerSmashingRecipe(new ItemStack(Block.oreLapis), Grinding.LAPIS.toItemStack(), 1);
		registerSmashingRecipe(new ItemStack(Block.oreRedstone), Grinding.REDSTONE.toItemStack(), 1);
		
		//Ore dictionary ores
		registerRecipe(new OreRecipeTable(TableRecipeType.SMASH, "oreCopper", Grinding.COPPER.toItemStack(), 1));
		registerRecipe(new OreRecipeTable(TableRecipeType.SMASH, "oreSilver", Grinding.SILVER.toItemStack(), 1));
		registerRecipe(new OreRecipeTable(TableRecipeType.SMASH, "oreTin", Grinding.TIN.toItemStack(), 1));
		registerRecipe(new OreRecipeTable(TableRecipeType.SMASH, "oreUranium", Grinding.URANIUM.toItemStack(), 1));
		
		// Hand-Sieve recipes
		registerSieveRecipe(Grinding.COBALTITE.toItemStack(), Dust.COBALT.toItemStack(), 1);
		registerSieveRecipe(Grinding.CHALCOPYRITE.toItemStack(), Dust.COPPER.toItemStack(), 1);
		registerSieveRecipe(Grinding.GALENA.toItemStack(), Dust.LEAD.toItemStack(), 1);
		registerSieveRecipe(Grinding.LITHIUM.toItemStack(), Dust.LITHIUM.toItemStack(), 1);
		registerSieveRecipe(Grinding.MAGNETITE.toItemStack(), Dust.MAGNETITE.toItemStack(), 1);
		registerSieveRecipe(Grinding.NICKEL.toItemStack(), Dust.NICKEL.toItemStack(), 1);
		registerSieveRecipe(Grinding.WOLFRAMITE.toItemStack(), Dust.TUNGSTEN.toItemStack(), 1);
		registerSieveRecipe(Dust.LIMESTONE.toItemStack(), Dust.LIME_PURE.toItemStack(), 1);
		registerSieveRecipe(Grinding.VOIDSTONE.toItemStack(), Dust.VOIDSTONE.toItemStack(), 1);
		registerSieveRecipe(Grinding.GOLD.toItemStack(), Dust.GOLD.toItemStack(), 1);
		registerSieveRecipe(Grinding.IRON.toItemStack(), Dust.IRON.toItemStack(), 1);
		registerSieveRecipe(Grinding.COPPER.toItemStack(), Dust.COPPER.toItemStack(), 1);
		registerSieveRecipe(Grinding.TIN.toItemStack(), Dust.TIN.toItemStack(), 1);
		registerSieveRecipe(Grinding.URANIUM.toItemStack(), Dust.URANIUM.toItemStack(), 1);
	}
	
}
