package electrodynamics.util.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import electrodynamics.core.EDLogger;
import electrodynamics.lib.core.ModInfo;

public class IconUtil {

	public static final String TEXTURE_PREFIX = "/assets/" + ModInfo.GENERIC_MOD_ID.toLowerCase() + "/textures/";
	
	private static InputStream getTextureResource(String texture) throws IOException {
		try {
			InputStream inStream = IconUtil.class.getResourceAsStream(texture);
			if (inStream == null) {
				throw new IOException();
			}
			return inStream;
		} catch (Exception ex) {
			EDLogger.warn("Failed to load texture file: " + texture);
		}
		
		return null;
	}

	public static BufferedImage loadBufferedImage(String texture) {
		texture = TEXTURE_PREFIX + texture;
		
		try {
			return loadBufferedImage(getTextureResource(texture));
		} catch (Exception e) {
			EDLogger.warn("Failed to load texture file: " + texture);
		}
		
		return null;
	}

	public static BufferedImage loadBufferedImage(InputStream in) throws IOException {
		BufferedImage img = ImageIO.read(in);
		in.close();
		return img;
	}
	
	public static GLColor getAverageColor(String texture) {
		return getAverageColor(loadBufferedImage(texture));
	}
	
	public static GLColor getAverageColor(BufferedImage image) {
		int rBucket = 0;
		int gBucket = 0;
		int bBucket = 0;
		int pCount = 0;
		
		for (int x=0; x<image.getWidth(); x++) {
			for (int y=0; y<image.getHeight(); y++) {
				GLColor color = new GLColor(image.getRGB(x, y));
				
				pCount++;
				rBucket += color.r;
				gBucket += color.g;
				bBucket += color.b;
			}
		}

		int ar = rBucket / pCount;
		int ag = gBucket / pCount;
		int ab = bBucket / pCount;
		
		return new GLColor(ar, ag, ab).multiply(0.9F);
	}
	
}
