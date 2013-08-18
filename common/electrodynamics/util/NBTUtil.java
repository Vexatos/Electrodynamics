package electrodynamics.util;

import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

	public static boolean hasKey(NBTTagCompound tag, String key) {
		if (tag.hasKey(key)) {
			return tag.getTag(key) != null;
		} else {
			return false;
		}
	}
	
}
