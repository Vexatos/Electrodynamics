package electrodynamics.world.handler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import electrodynamics.block.BlockWormwood;
import electrodynamics.lib.block.BlockIDs;
import electrodynamics.world.gen.feature.FeatureHandler;
import electrodynamics.world.gen.feature.FeatureRubberTree;

public class BonemealEventHandler {

	@ForgeSubscribe
	public void onUseBonemeal(BonemealEvent e) {
		if (!e.world.isRemote) {
			if (e.ID == BlockIDs.BLOCK_RUBBER_SAPLING_ID) {
				((FeatureRubberTree)FeatureHandler.getInstance().getFeatureForName("Rubber Wood")).grow(e.world, e.X, e.Y, e.Z, new Random());
				e.setResult(Result.ALLOW);
			} else if(e.ID == BlockIDs.BLOCK_WORMWOOD_ID){
				if (!BlockWormwood.isFullyGrown(e.world.getBlockMetadata(e.X, e.Y, e.Z))) {
					((BlockWormwood) Block.blocksList[e.ID]).fertilize(e.world, e.X, e.Y, e.Z);
					e.setResult(Result.ALLOW);
				} else {
					e.setResult(Result.DENY);
				}
			}
		}
	}
}