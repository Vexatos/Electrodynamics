package electrodynamics.util.math;

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
	
	public Vector3D copy() {
		return new Vector3D(x, y, z);
	}
	
}
