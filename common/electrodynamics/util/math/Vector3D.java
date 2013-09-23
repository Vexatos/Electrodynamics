package electrodynamics.util.math;

public class Vector3D {

	public float x;
	public float y;
	public float z;
	
	public static Vector3D UNIT_X = new Vector3D(1, 0, 0);
	public static Vector3D UNIT_Y = new Vector3D(0, 1, 0);
	public static Vector3D UNIT_Z = new Vector3D(0, 0, 1);
	public static Vector3D UNIT_XYZ = new Vector3D(1, 1, 1).normalize();
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3D vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public Vector3D add(float mx, float my, float mz)
	{
	    return new Vector3D(this.x + mx, this.y + my, this.z + mz);
	}
	
	public Vector3D subtract(float mx, float my, float mz)
	{
	    return new Vector3D(this.x - mx, this.y - my, this.z - mz);
	}
	
	public Vector3D scale(float r)
	{
	    return new Vector3D(this.x * r, this.y * r, this.z * r);
	}
	
	public float getLength()
	{
	    return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	public Vector3D normalize()
	{	    
	    return this.scale(1/this.getLength());
	}	
	
	public float dotProduct(Vector3D vec)
	{
	    return this.x * vec.x + this.y * vec.y + this.z * vec.z;
	}
	
	public Vector3D crossProduct(Vector3D vec)
	{
	    return new Vector3D(this.y*vec.z - this.z*vec.y, this.z*vec.x - this.x*vec.z, this.x*vec.y - this.y*vec.x);
	}
}
