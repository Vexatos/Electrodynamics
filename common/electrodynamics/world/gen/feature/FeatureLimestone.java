package electrodynamics.world.gen.feature;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.lib.block.Decorative;
import electrodynamics.lib.block.BlockIDs;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.Configuration;

public class FeatureLimestone extends FeatureBase {

  private ArrayList<List> wouldDo;

  public FeatureLimestone(String name) {
    super(name);
  }

  public boolean exposed(World world, int x, int y, int z) {
    if(world.getBlockId(x + 1, y, z) == 0 ||
       world.getBlockId(x - 1, y, z) == 0 ||
       world.getBlockId(x, y + 1, z) == 0 ||
       world.getBlockId(x, y - 1, z) == 0 ||
       world.getBlockId(x, y, z + 1) == 0 ||
       world.getBlockId(x, y, z - 1) == 0) return true;
    return false;
  }

  public void limestone(World world, int x, int y, int z, int depth) {
    List t = Arrays.asList(new Integer[] {new Integer(x),new Integer(y),new Integer(z)});
    if(this.wouldDo.size() > 4095 || this.wouldDo.contains(t)) return;
    if(world.getBlockId(x, y, z) == Block.stone.blockID){
      this.wouldDo.add(t);
      surrounding(world, x, y, z, depth);
    }
  }

  public void surrounding(World world, int x, int y, int z, int depth) {
    int[][] directions = new int [][] {{x+1,y,z},{x-1,y,z},{x,y+1,z},{x,y-1,z},{x,y,z+1},{x,y,z-1}};
    if(depth < 3){
      for(int[] i : directions){
        limestone(world, i[0], i[1], i[2], exposed(world, i[0], i[1], i[2]) ? 0 : depth + 1);
      }
    }
  }

  @Override
  public void generateFeature(Random random, int chunkX, int chunkZ, World world, boolean newGeneration) {
    if(random.nextInt(15) != 7) return;
    if (world.getWorldInfo().getTerrainType() == WorldType.FLAT) {
      return;
    }

    this.wouldDo = new ArrayList<List>();

    // Find exposed stone
    int y;
    int x = chunkX * 16 + 8;
    int z = chunkZ * 16 + 8;
    for(y=96; y >= 40; y--) {
      // Todo: simplify
      if(world.getBlockId(x, y, z) == Block.stone.blockID && exposed(world, x,y,z)) break;
    }
    if(y == 39) return;

System.out.println(String.format("Limestoning at %d, %d, %d",x,y,z));

    limestone(world, x, y, z, 0);
System.out.println(String.format("Blocks added: %d",wouldDo.size()));
    for(List<Integer> i : this.wouldDo){
      world.setBlock(i.get(0).intValue(), i.get(1).intValue(), i.get(2).intValue(), BlockIDs.BLOCK_DECORATIVE_ID, 0, Decorative.LIMESTONE.ordinal());
    }
  }

  @Override
  public void handleConfig(Configuration config) {
    //Todo: load frequency, max depth, and max count
  }

}
