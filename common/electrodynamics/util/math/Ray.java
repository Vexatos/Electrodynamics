package electrodynamics.util.math;

public class Ray
{
    public Vector3D location;    
    public Vector3D direction;
    
    public Vector3D directionInv;
    public int[] sign = new int[3];
    
    public Ray(Vector3D loc, Vector3D dir)
    {
        location = loc;
        direction = dir;
        directionInv = new Vector3D(1/dir.x, 1/dir.y, 1/dir.z);
        
        if(directionInv.x < 0) sign[0] = 1; else sign[0] = 0;
        if(directionInv.y < 0) sign[1] = 1; else sign[1] = 0;
        if(directionInv.z < 0) sign[2] = 1; else sign[2] = 0;
    }
    
    public boolean intersects(AABB box, float t0, float t1)
    {
        return box.intersects(this, t0, t1);
    }
}
