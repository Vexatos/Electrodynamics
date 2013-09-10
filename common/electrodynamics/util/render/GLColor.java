package electrodynamics.util.render;

import org.lwjgl.opengl.GL11;

public class GLColor {

	public static final GLColor BLACK = new GLColor(0, 0, 0);
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
		float rBucket = 0;
		float gBucket = 0;
		float bBucket = 0;
		
		for (GLColor color : colors) {
			if (color != null) {
				rBucket += color.r;
				gBucket += color.g;
				bBucket += color.b;
			}
		}
		
		this.r = rBucket / colors.length;
		this.g = gBucket / colors.length;
		this.b = bBucket / colors.length;
		this.a = 255;
	}
	
	public GLColor add(GLColor...colors) {
		float rBucket = this.r;
		float gBucket = this.g;
		float bBucket = this.b;
		
		for (GLColor color : colors) {
			if (color != null) {
				rBucket += color.r;
				gBucket += color.g;
				bBucket += color.b;
			}
		}
		
		this.r = rBucket / colors.length;
		this.g = gBucket / colors.length;
		this.b = bBucket / colors.length;
		this.a = 255;
		
		return this;
	}
	
	public GLColor multiply(float value) {
		return multiply(value, value, value);
	}
	
	public GLColor multiply(float r, float g, float b) {
		this.r *= r;
		this.g *= g;
		this.b *= b;
		
		return this;
	}
	
	public void apply() {
		GL11.glColor4f(r / 255, g / 255, b / 255, a / 255);
	}
	
	public int toInt() {
		return  (((int)a & 0xFF) << 24) | //alpha
	            (((int)r & 0xFF) << 16) | //red
	            (((int)g & 0xFF) << 8)  | //green
	            (((int)b & 0xFF) << 0);   //blue
	}
	
	@Override
	public String toString() {
		return ("R: " + r + " G: " + g + " B: " + b + " A: " + a);
	}
	
}
