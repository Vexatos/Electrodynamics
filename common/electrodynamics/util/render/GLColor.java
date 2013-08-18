package electrodynamics.util.render;

import org.lwjgl.opengl.GL11;

public class GLColor {

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
	
	public GLColor(float r, float g, float b) {
		this(r, g, b, 255);
	}
	
	public void apply() {
		GL11.glColor4f(r / 255, g / 255, b / 255, a / 255);
	}
	
}
