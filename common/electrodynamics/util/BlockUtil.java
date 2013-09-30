package electrodynamics.util;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.oredict.OreDictionary;

public class BlockUtil {

	public static int[] getRandomBlockOnSide(IBlockAccess world, int x, int y, int z, int ignore, boolean opaque, Material mat, Integer[] blacklist) {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int[] block = getBlockOnSide(world, x, y, z, dir);
			Block bBlock = Block.blocksList[block[0]];
			
			if (block[0] != 0 && bBlock != null && (ignore != 0 && block[0] != ignore) && (mat != null && bBlock.blockMaterial == mat) && (blacklist == null || blacklist.length == 0 || !Arrays.asList(blacklist).contains(block[0])) && (opaque && bBlock.isOpaqueCube())) {
				return block;
			}
		}
		
		return new int[] {};
	}
	
	public static int[] getBlockOnSide(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		x += side.offsetX;
		y += side.offsetY;
		z += side.offsetZ;
		return new int[] {world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z)};
	}
	
	//TODO Neaten
	public static boolean applyBonemeal(ItemStack par0ItemStack, World par1World, int par2, int par3, int par4, EntityPlayer player)
    {
		Random itemRand = new Random();
        int l = par1World.getBlockId(par2, par3, par4);

        BonemealEvent event = new BonemealEvent(player, par1World, l, par2, par3, par4);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return false;
        }

        if (event.getResult() == Result.ALLOW)
        {
            return true;
        }

        if (l == Block.sapling.blockID)
        {
            if (!par1World.isRemote)
            {
                if ((double)par1World.rand.nextFloat() < 0.45D)
                {
                    ((BlockSapling)Block.sapling).markOrGrowMarked(par1World, par2, par3, par4, par1World.rand);
                }
            }

            return true;
        }
        else if (l != Block.mushroomBrown.blockID && l != Block.mushroomRed.blockID)
        {
            if (l != Block.melonStem.blockID && l != Block.pumpkinStem.blockID)
            {
                if (l > 0 && Block.blocksList[l] instanceof BlockCrops)
                {
                    if (par1World.getBlockMetadata(par2, par3, par4) == 7)
                    {
                        return false;
                    }
                    else
                    {
                        if (!par1World.isRemote)
                        {
                            ((BlockCrops)Block.blocksList[l]).fertilize(par1World, par2, par3, par4);
                        }

                        return true;
                    }
                }
                else
                {
                    int i1;
                    int j1;
                    int k1;

                    if (l == Block.cocoaPlant.blockID)
                    {
                        i1 = par1World.getBlockMetadata(par2, par3, par4);
                        j1 = BlockDirectional.getDirection(i1);
                        k1 = BlockCocoa.func_72219_c(i1);

                        if (k1 >= 2)
                        {
                            return false;
                        }
                        else
                        {
                            if (!par1World.isRemote)
                            {
                                ++k1;
                                par1World.setBlockMetadataWithNotify(par2, par3, par4, k1 << 2 | j1, 2);
                            }

                            return true;
                        }
                    }
                    else if (l != Block.grass.blockID)
                    {
                        return false;
                    }
                    else
                    {
                        if (!par1World.isRemote)
                        {
                            label102:

                            for (i1 = 0; i1 < 128; ++i1)
                            {
                                j1 = par2;
                                k1 = par3 + 1;
                                int l1 = par4;

                                for (int i2 = 0; i2 < i1 / 16; ++i2)
                                {
                                    j1 += itemRand.nextInt(3) - 1;
                                    k1 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
                                    l1 += itemRand.nextInt(3) - 1;

                                    if (par1World.getBlockId(j1, k1 - 1, l1) != Block.grass.blockID || par1World.isBlockNormalCube(j1, k1, l1))
                                    {
                                        continue label102;
                                    }
                                }

                                if (par1World.getBlockId(j1, k1, l1) == 0)
                                {
                                    if (itemRand.nextInt(10) != 0)
                                    {
                                        if (Block.tallGrass.canBlockStay(par1World, j1, k1, l1))
                                        {
                                            par1World.setBlock(j1, k1, l1, Block.tallGrass.blockID, 1, 3);
                                        }
                                    }
                                    else
                                    {
                                        ForgeHooks.plantGrass(par1World, j1, k1, l1);
                                    }
                                }
                            }
                        }

                        return true;
                    }
                }
            }
            else if (par1World.getBlockMetadata(par2, par3, par4) == 7)
            {
                return false;
            }
            else
            {
                if (!par1World.isRemote)
                {
                    ((BlockStem)Block.blocksList[l]).fertilizeStem(par1World, par2, par3, par4);
                }

                return true;
            }
        }
        else
        {
            if (!par1World.isRemote)
            {
                if ((double)par1World.rand.nextFloat() < 0.4D)
                {
                    ((BlockMushroom)Block.blocksList[l]).fertilizeMushroom(par1World, par2, par3, par4, par1World.rand);
                }
            }

            return true;
        }
    }
	
	public static int[] getCoordsOnSide(World world, int x, int y, int z, ForgeDirection side) {
		if (side == null) return null;
		return new int[] {x + side.offsetX, y + side.offsetY, z + side.offsetZ};
	}
	
	/**
	 * Determines which side of the block is facing the specified entity, by using angles.
	 * Will only look around in the XZ-plane.
	 *
	 * @param entity the entity facing the block.
	 * @param x      the x coordinate of the block
	 * @param z      the z coordinate of the block
	 */
	public static ForgeDirection getSideFacingEntity(Entity entity, int x, int z) {
		double diffX = entity.posX - x;
		double diffZ = entity.posZ - z;
		double angle = Math.atan2( diffZ, diffX ) / Math.PI * 180 + 180;

		if( angle < 45 || angle > 315 )
			return ForgeDirection.getOrientation( 4 ); // WEST  (-X)
		else if( angle < 135 )
			return ForgeDirection.getOrientation( 2 ); // NORTH (-Z)
		else if( angle < 225 )
			return ForgeDirection.getOrientation( 5 ); // EAST  (+X)
		else
			return ForgeDirection.getOrientation( 3 ); // SOUTH (+Z)
	}


	public static void dropItemFromBlock(World world, int x, int y, int z, ItemStack stack, Random random) {
		if( stack != null ) {
			float f = random.nextFloat() * 0.9F + 0.1F;
			float f1 = random.nextFloat() * 0.9F + 0.1F;
			float f2 = random.nextFloat() * 0.9F + 0.1F;
			EntityItem entityitem = new EntityItem( world, x + f, y + 1.1F + f1, z + f2, stack );

			float f3 = 0.05F;
			entityitem.motionX = (float) random.nextGaussian() * f3;
			entityitem.motionY = (float) random.nextGaussian() * f3 + 0.1F;
			entityitem.motionZ = (float) random.nextGaussian() * f3;
			if( stack.hasTagCompound() ) {
				entityitem.getEntityItem().setTagCompound( (NBTTagCompound) stack.getTagCompound().copy() );
			}

			world.spawnEntityInWorld( entityitem );
		}
	}

	/**
	 * Spawns the experience orbs in the world at the given coordinates.
	 * The total amount of experience is split into several orbs, following what EntityXPOrb.getXPSplit() dictates.
	 *
	 * @param world      the World object
	 * @param x          the x coordinate
	 * @param y          the y coordinate
	 * @param z          the z coordinate
	 * @param experience the amount of experience to spawn.
	 * @see EntityXPOrb#getXPSplit(int)
	 */
	public static void spawnExperienceOrbs(World world, int x, int y, int z, float experience) {
		int exp = MathHelper.floor_float( experience );
		if( exp < MathHelper.ceiling_float_int( experience ) && (float) Math.random() < experience - exp ) {
			exp++;
		}

		while( exp > 0 ) {
			int split = EntityXPOrb.getXPSplit( exp );
			exp -= split;
			world.spawnEntityInWorld( new EntityXPOrb( world, x, y + 0.5D, z, split ) );
		}
	}

	public static int getFirstInstanceOfBlock(World world, int x, int z, int id, int meta) {
		int y = -1;
		
		for (y = 0; y<255; y++) {
			if (id == world.getBlockId(x, y, z) && (meta == world.getBlockMetadata(x, y, z) || meta == OreDictionary.WILDCARD_VALUE)) {
				break;
			}
		}
		
		return y;
	}
	
	public static int getFirstUncoveredYPos(World world, int x, int z) {
		int y;
		
		for (y = 255; world.isAirBlock(x, y - 1, y); --y) {
			;
		}

		return y;
	}
	
	public static boolean matches(World world, int x, int y, int z, int id, int meta) {
		return (id == world.getBlockId(x, y, z) && (meta == world.getBlockMetadata(x, y, z) || meta == OreDictionary.WILDCARD_VALUE));
	}
	
}
