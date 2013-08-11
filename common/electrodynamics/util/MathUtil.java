package electrodynamics.util;

public class MathUtil {

	public static int reverseNumber(int num, int min, int max) {
		return (max + min) - num;
	}

	public static float roundFloat(float f) {
		return (float) ((Math.round(f) * 100.0) / 100.0);
	}
	
	public static double roundDouble(double d) {
		return (Math.round(d) * 100.0) / 100.0;
	}
	
}
