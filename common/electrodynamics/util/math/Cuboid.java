package electrodynamics.util.math;

public class Cuboid {

	public Vector3D min;
	public Vector3D max;
	
	/**
	 * Corners must be element-wise sorted
	 * 
	 * @param min 
	 * @param max
	 */
	public Cuboid(Vector3D min, Vector3D max) {
		this.min = min;
		this.max = max;
	}

	public Cuboid(int x1, int y1, int z1, int x2, int y2, int z2) {
		this.min = new Vector3D(x1, y1, z1);
		this.max = new Vector3D(x2, y2, z2);
	}

	public float getWidth() {
		return max.x - min.x;
	}
	
	public float getHeight() {
		return max.y - min.y;
	}
	
	public float getDepth() {
		return max.z - min.z;
	}
	
	public boolean contains(Vector3D vec) {
		return (vec.x >= min.x && vec.x <= max.x && vec.y >= min.y && vec.y <= max.y && vec.z >= min.z && vec.z <= max.z);
	}
	
	public Cuboid copy() {
		return new Cuboid(new Vector3D(min), new Vector3D(max));
	}
	
}
