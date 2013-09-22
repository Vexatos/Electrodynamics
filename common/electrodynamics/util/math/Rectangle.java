package electrodynamics.util.math;

public class Rectangle {

	public Vector2D pointA;
	public Vector2D pointB;
	
	public Rectangle(Vector2D a, Vector2D b) {
		this.pointA = a;
		this.pointB = b;
	}

	public Rectangle(int x1, int y1, int x2, int y2) {
		this.pointA = new Vector2D(x1, y1);
		this.pointB = new Vector2D(x2, y2);
	}

	public int getWidth() {
		return pointB.x - pointA.x;
	}
	
	public int getHeight() {
		return pointB.y - pointA.y;
	}
	
	public Rectangle copy() {
		return new Rectangle(pointA.copy(), pointB.copy());
	}

}