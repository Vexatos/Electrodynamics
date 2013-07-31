package electrodynamics.configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import electrodynamics.Electrodynamics;
import electrodynamics.configuration.annotation.EDXProperty;
import electrodynamics.core.EDLogger;
import electrodynamics.lib.core.ModInfo;

public class ConfigurationHandler {

	public static final String CATEGORY_SETTINGS = "user.settings";
	public static final String CATEGORY_ELMAG = "elmag_armor.settings";
	public static final String CATEGORY_KEYS = "user.keybindings";
	public static final String CATEGORY_GRAPHICS = "user.graphics";
	public static final String CATEGORY_SOUND = "user.sound";

	public static void handleClass(Class<?> classFile) {
		Configuration config = new Configuration(new File(Electrodynamics.instance.configFolder, ModInfo.GENERIC_MOD_ID + ".cfg"));

		Field[] fields = classFile.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			
			EDXProperty edx = field.getAnnotation(EDXProperty.class);
			if (edx == null) {
				continue;
			}
			
			try {
				String name = edx.name().isEmpty() ? field.getName() : edx.name();
				boolean usePresent = edx.usePresent();
				Property property = null;
				
				if (field.getType().equals(String.class)) {
					String defaultString = usePresent && edx.defaultString().isEmpty() ? (String) field.get(null) : edx.defaultString();
					property = config.get(edx.category(), name, defaultString);
					field.set(null, property.getString());
				} else if (field.getType().equals(int.class)) {
					int defaultInt = usePresent && edx.defaultInt() == -1 ? field.getInt(null) : edx.defaultInt();
					property = config.get(edx.category(), name, defaultInt);
					field.set(null, property.getInt());
				} else if (field.getType().equals(boolean.class)) {
					boolean defaultBoolean = usePresent ? field.getBoolean(null) : edx.defaultBoolean();
					property = config.get(edx.category(), name, defaultBoolean);
					field.set(null, property.getInt());
				} else if (field.getType().equals(double.class)) {
					double defaultDouble = usePresent && edx.defaultDouble() == -1.0D ? field.getDouble(null) : edx.defaultDouble();
					property = config.get(edx.category(), name, defaultDouble);
					field.set(null, property.getDouble(defaultDouble));
				}
				
				if (!edx.comment().isEmpty()) {
					property.comment = edx.comment();
				}
			} catch( Exception e ) {
				EDLogger.warn( "Had trouble reading/writing to the configuration file." );
			} finally {
				if( config.hasChanged() ) {
					config.save();
				}
			}
		}
		
	}

	
	
}
