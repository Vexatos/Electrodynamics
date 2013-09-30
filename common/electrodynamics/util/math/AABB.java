package electrodynamics.util.math;

public class AABB {
	private Vector3D[] bounds = new Vector3D[2];

	public AABB(Vector3D min, Vector3D max) {
		if (min.x > max.x || min.y > max.y || min.z > max.z) {
			throw new RuntimeException("AABB corners must be element-wise sorted");
		}
		
		bounds[0] = min;
		bounds[1] = max;
	}

	public Vector3D getMin() {
		return bounds[0];
	}

	public Vector3D getMax() {
		return bounds[1];
	}

	public boolean contains(Vector3D vec) {
		return (vec.x >= bounds[0].x && vec.x <= bounds[1].x && vec.y >= bounds[0].y && vec.y <= bounds[1].y && vec.z >= bounds[0].z && vec.z <= bounds[1].z);
	}

	public boolean intersects(Ray r, float t0, float t1) {
		float tmin, tmax, tymin, tymax, tzmin, tzmax;

		tmin = (bounds[r.sign[0]].x - r.location.x) * r.directionInv.x;
		tmax = (bounds[1 - r.sign[0]].x - r.location.x) * r.directionInv.x;
		tymin = (bounds[r.sign[1]].y - r.location.y) * r.directionInv.y;
		tymax = (bounds[1 - r.sign[1]].y - r.location.y) * r.directionInv.y;

		if ((tmin > tymax) || (tymin > tmax)) {
			return false;
		}
		
		if (tymin > tmin) {
			tmin = tymin;
		}
		
		if (tymax < tmax) {
			tmax = tymax;
		}

		tzmin = (bounds[r.sign[2]].z - r.location.z) * r.directionInv.z;
		tzmax = (bounds[1 - r.sign[2]].z - r.location.z) * r.directionInv.z;

		if ((tmin > tzmax) || (tzmin > tmax)) {
			return false;
		}
		
		if (tzmin > tmin) {
			tmin = tzmin;
		}
		
		if (tzmax < tmax) {
			tmax = tzmax;
		}
		
		return ((tmin < t1) && (tmax > t0));

	}
}
