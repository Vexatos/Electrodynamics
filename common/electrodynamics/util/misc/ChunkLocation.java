package electrodynamics.util.misc;

import net.minecraft.world.chunk.Chunk;

public class ChunkLocation {

	public int x;
	public int z;
	
	public ChunkLocation(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	public ChunkLocation(Chunk chunk) {
		this.x = chunk.xPosition;
		this.z = chunk.zPosition;
	}
	
}
