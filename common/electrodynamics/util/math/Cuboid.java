package electrodynamics.util.math;

public class Cuboid {

	public Vector3D pointA;
	public Vector3D pointB;
	
	public Cuboid(Vector3D a, Vector3D b) {
		this.pointA = a;
		this.pointB = b;
	}

	public Cuboid(int x1, int y1, int z1, int x2, int y2, int z2) {
		this.pointA = new Vector3D(x1, y1, z1);
		this.pointB = new Vector3D(x2, y2, z2);
	}

	public int getWidth() {
		return pointB.x - pointA.x;
	}
	
	public int getHeight() {
		return pointB.y - pointA.y;
	}
	
	public int getDepth() {
		return pointB.z - pointA.z;
	}
	
	public boolean contains(Vector3D vec) {
		return (vec.x >= pointA.x && vec.x <= pointB.x && vec.y >= pointA.y && vec.y <= pointB.y && vec.z >= pointA.z && vec.z <= pointB.z);
	}
	
	public Cuboid copy() {
		return new Cuboid(pointA.copy(), pointB.copy());
	}
	
}
