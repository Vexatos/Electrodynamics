package electrodynamics.util.math;

public class Vector2D {

	public int x;
	public int y;
	
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public Vector2D move(int xAdjust, int yAdjust) {
		this.x += xAdjust;
		this.y += yAdjust;
		return this;
	}
	
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
}
