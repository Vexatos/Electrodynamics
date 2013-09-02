package electrodynamics.util.render;

import org.lwjgl.opengl.GL11;

public class GLColor {

	public static final GLColor WHITE = new GLColor(255, 255, 255);
	
	public float r;
	public float g;
	public float b;
	public float a;
	
	public GLColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public GLColor(int color) {
		this.r = (color >> 16) & 0xFF;
		this.g = (color >> 8) & 0xFF;
		this.b = (color) & 0xFF;
		this.a = 255;
	}
	
	public GLColor(float r, float g, float b) {
		this(r, g, b, 255);
	}
	
	public GLColor(GLColor ... colors) {
		int rBucket = 0;
		int gBucket = 0;
		int bBucket = 0;
		
		for (GLColor color : colors) {
			rBucket += color.r;
			gBucket += color.g;
			bBucket += color.b;
		}
		
		this.r = rBucket / colors.length;
		this.g = gBucket / colors.length;
		this.b = bBucket / colors.length;
		this.a = 255;
	}
	
	public void apply() {
		GL11.glColor4f(r / 255, g / 255, b / 255, a / 255);
	}
	
	@Override
	public String toString() {
		return ("R: " + r + " G: " + g + " B: " + b + " A: " + a);
	}
	
}
