package electrodynamics.util.math;

public class Vector2D {

	public float x;
	public float y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public Vector2D add(float mx, float my, float mz)
    {
        return new Vector2D(this.x + mx, this.y + my);
    }
    
    public Vector2D subtract(float mx, float my, float mz)
    {
        return new Vector2D(this.x - mx, this.y - my);
    }
    
    public Vector2D scale(float r)
    {
        return new Vector2D(this.x * r, this.y * r);
    }
    
    public float getLength()
    {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    public Vector2D normalize()
    {       
        return this.scale(1/this.getLength());
    }   
    
    public float dotProduct(Vector2D vec)
    {
        return this.x * vec.x + this.y * vec.y;
    }	
	
}
