package electrodynamics.util.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import electrodynamics.core.EDLogger;
import electrodynamics.item.ItemIngot;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.item.Dust;

public class IconUtil {

	public static final String TEXTURE_PREFIX = "/assets/" + ModInfo.GENERIC_MOD_ID.toLowerCase() + "/textures/items/";
	
	public static Map<String, GLColor> iconColorCache = new HashMap<String, GLColor>();
	
	static {
		cacheColor(Dust.GREENSTONE.toItemStack(), new GLColor(0, 170, 0));
		cacheColor(Dust.BLUESTONE.toItemStack(), new GLColor(0, 85, 255));
		cacheColor(Dust.VOIDSTONE.toItemStack(), new GLColor(50, 25, 120));
		cacheColor(Dust.SULFUR.toItemStack(), new GLColor(180, 190, 180));
		cacheColor(Dust.LIME_PURE.toItemStack(), new GLColor(250, 250, 250));
		cacheColor(Dust.TELLURIUM.toItemStack(), new GLColor(230, 230, 230));
		cacheColor(Dust.CLAY.toItemStack(), new GLColor(130, 145, 170));
		cacheColor(Dust.IRON.toItemStack(), new GLColor(180, 180, 180));
		cacheColor(Dust.GOLD.toItemStack(), new GLColor(255, 235, 40));
		cacheColor(Dust.COBALT.toItemStack(), new GLColor(40, 40, 60));
		cacheColor(Dust.TUNGSTEN.toItemStack(), new GLColor(35, 35, 35));
		cacheColor(Dust.NICKEL.toItemStack(), new GLColor(40, 205, 140));
		cacheColor(Dust.MAGNETITE.toItemStack(), new GLColor(120, 110, 90));
		cacheColor(Dust.LEAD.toItemStack(), new GLColor(60, 50, 75));
		cacheColor(Dust.LITHIUM.toItemStack(), new GLColor(210, 210, 210));
		cacheColor(Dust.COPPER.toItemStack(), new GLColor(185, 100, 30));
		cacheColor(Dust.SILICON.toItemStack(), new GLColor(60, 60, 60));
		cacheColor(Dust.COBALT_HEXAHYDRATE.toItemStack(), new GLColor(205, 110, 130));
	}
	
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
				EDLogger.info(image.toString() + ":" + image.getRGB(x, y));
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

	public static GLColor getCachedColor(ItemStack stack) {
		if (stack.isItemEqual(new ItemStack(Item.ingotGold))) {
			return ItemIngot.ingotColors[1];
		}
		
		if (stack.isItemEqual(new ItemStack(Item.ingotIron))) {
			return ItemIngot.ingotColors[0];
		}
		
		try {
			return getCachedColor(stack.getIconIndex().getIconName().split(":")[1] + ".png");
		} catch (Exception ex) {
			return GLColor.WHITE;
		}
	}
	
	public static void cacheColor(ItemStack stack, GLColor color) {
		String id = stack.getIconIndex().getIconName().split(":")[1] + ".png";
		iconColorCache.put(id, color);
	}
	
	public static GLColor getCachedColor(String texture) {
		GLColor average = null;
		
		if (!iconColorCache.containsKey(texture)) {
			average = IconUtil.getAverageColor(texture);
			iconColorCache.put(texture, average);
		} else {
			average = iconColorCache.get(texture);
		}
		
		return average;
	}
	
}
