package electrodynamics.util.math;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class Vector3D {

	public int x;
	public int y;
	public int z;
	
	public Vector3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3D vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public void set(Vector3D vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public Vector3D move(int xAdjust, int yAdjust, int zAdjust) {
		this.x += xAdjust;
		this.y += yAdjust;
		this.z += zAdjust;
		return this;
	}
	
	public Vec3 toVec3() {
		return Vec3.fakePool.getVecFromPool(x, y, z);
	}
	
	public Vector3D copy() {
		return new Vector3D(x, y, z);
	}
	
}
