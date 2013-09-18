package electrodynamics.world.gen.feature;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

import electrodynamics.lib.block.Decorative;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.world.gen.feature.FeatureHandler.FeatureType;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeDirection;

public class FeatureLimestone extends FeatureBase {

  private int frequency;
  private int maxDepth;
  private int maxSize;

  public FeatureLimestone(String name) {
    super(name);
  }

  public boolean exposed(World world, int x, int y, int z) {
	  for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
		  int xMod = x + dir.offsetX;
		  int yMod = y + dir.offsetY;
		  int zMod = z + dir.offsetZ;
		  
		  if (world.isAirBlock(xMod, yMod, zMod)) return true;
	  }
	  
	  return false;
  }

  @Override
  public void generateFeature(Random random, int chunkX, int chunkZ, World world, boolean newGeneration) {
    if(random.nextInt(frequency) != 1) return;
    if (world.getWorldInfo().getTerrainType() == WorldType.FLAT) {
      return;
    }

    // Find exposed stone
    int y;
    int x = chunkX * 16 + 8;
    int z = chunkZ * 16 + 8;
    for(y=96; y >= 40; y--) {
      // Todo: simplify
      if(world.getBlockId(x, y, z) == Block.stone.blockID && exposed(world, x,y,z)) break;
    }
    if(y == 39) return;

    int initialMapSize = (int)Math.floor(maxSize * 1.4);

    HashMap<List<Integer>,Integer> depthMap = new HashMap<List<Integer>,Integer>(initialMapSize);
    LinkedList<List<Integer>> work = new LinkedList<List<Integer>>();
    HashMap<List<Integer>,Boolean> placed = new HashMap<List<Integer>,Boolean>(initialMapSize);

    List<Integer> t = new ArrayList<Integer>(Arrays.asList(new Integer(x), new Integer(y), new Integer(z)));
    work.add(t);
    depthMap.put(t,new Integer(0));
    while(work.size() > 0 && placed.size() < maxSize){
      t = work.remove();
      Integer d = depthMap.get(t);

      if(d > maxDepth) continue;
      int x1 = t.get(0).intValue(); int y1 = t.get(1).intValue(); int z1 = t.get(2).intValue();
      if(d > 0 && exposed(world, x1, y1, z1)){
        d = 0; depthMap.put(t,new Integer(0));
      }

      if(world.getBlockId(x1, y1, z1) == Block.stone.blockID){
        world.setBlock(x1, y1, z1, BlockIDs.BLOCK_DECORATIVE_ID, 0, Decorative.LIMESTONE.ordinal());
        placed.put(t, true);

        d++;
        List<Integer> u;
        Integer x2 = t.get(0); Integer y2 = t.get(1); Integer z2 = t.get(2);
        u = new ArrayList<Integer>(Arrays.asList(x2+1, y2, z2));
        if(!placed.containsKey(u)) work.add(u); if(depthMap.get(u) == null || depthMap.get(u) > d) depthMap.put(u,d);
        u = new ArrayList<Integer>(Arrays.asList(x2-1, y2, z2));
        if(!placed.containsKey(u)) work.add(u); if(depthMap.get(u) == null || depthMap.get(u) > d) depthMap.put(u,d);
        u = new ArrayList<Integer>(Arrays.asList(x2, y2+1, z2));
        if(!placed.containsKey(u)) work.add(u); if(depthMap.get(u) == null || depthMap.get(u) > d) depthMap.put(u,d);
        u = new ArrayList<Integer>(Arrays.asList(x2, y2-1, z2));
        if(!placed.containsKey(u)) work.add(u); if(depthMap.get(u) == null || depthMap.get(u) > d) depthMap.put(u,d);
        u = new ArrayList<Integer>(Arrays.asList(x2, y2, z2+1));
        if(!placed.containsKey(u)) work.add(u); if(depthMap.get(u) == null || depthMap.get(u) > d) depthMap.put(u,d);
        u = new ArrayList<Integer>(Arrays.asList(x2, y2, z2-1));
        if(!placed.containsKey(u)) work.add(u); if(depthMap.get(u) == null || depthMap.get(u) > d) depthMap.put(u,d);
      }
    }
  }

  @Override
  public void handleConfig(Configuration config) {
    //Todo: double defaults?
    this.frequency = config.get(FeatureHandler.getFeatureCategory(FeatureType.LIMESTONE), "frequency", 5).getInt(5);
    this.maxDepth = config.get(FeatureHandler.getFeatureCategory(FeatureType.LIMESTONE), "max_depth", 3).getInt(3) - 1;
    this.maxSize = config.get(FeatureHandler.getFeatureCategory(FeatureType.LIMESTONE), "max_size", 2048).getInt(2048);
  }

}
