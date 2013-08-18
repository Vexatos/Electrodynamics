package electrodynamics.util.misc;

import electrodynamics.util.render.GLColor;

public class LaserWrapper {

	public double startX;
	public double startY;
	public double startZ;
	
	public double endX;
	public double endY;
	public double endZ;
	
	public float width;
	public float height;
	
	public GLColor color;
	
	public LaserWrapper() {}
	
	public LaserWrapper(double sx, double sy, double sz, double ex, double ey, double ez, GLColor color) {
		this.startX = sx;
		this.startY = sy;
		this.startZ = sz;
		this.endX = ex;
		this.endY = ey;
		this.endZ = ez;
		this.color = color;
	}
	
	public LaserWrapper setStart(double x, double y, double z) {
		this.startX = x;
		this.startY = y;
		this.startZ = z;
		
		return this;
	}
	
	public LaserWrapper setEnd(double x, double y, double z) {
		this.endX = x;
		this.endY = y;
		this.endZ = z;
		
		return this;
	}
	
	public LaserWrapper setColor(float r, float g, float b) {
		this.color = new GLColor(r, g, b);
		
		return this;
	}
	
	public LaserWrapper setSize(float width, float height) {
		this.width = width;
		this.height = height;
		
		return this;
	}
	
}
