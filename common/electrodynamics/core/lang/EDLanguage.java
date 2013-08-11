package electrodynamics.core.lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;
import electrodynamics.Electrodynamics;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.core.EDLogger;
import electrodynamics.lib.core.ModInfo;

public class EDLanguage {

	public static final String LANG_DIR = "/assets/" + ModInfo.GENERIC_MOD_ID.toLowerCase() + "/lang/";
	
	public boolean errorShown = false;
	
	/** Key is language name, value is properties file */
	public HashMap<String, Properties> languageMapping = new HashMap<String, Properties>();
	
	private static EDLanguage instance;
	
	public static EDLanguage getInstance() {
		if (instance == null) instance = new EDLanguage();
		return instance;
	}
	
	public static String getFormattedBlockName(String tag) {
		return "tile." + tag;
	}
	
	public static String getFormattedItemName(String tag) {
		return "item." + tag;
	}
	
	public EDLanguage() {
		loadDefaultLanguage("en_US");
		loadDefaultLanguage("es_ES");
		loadDefaultLanguage("zh_CH");
		loadDefaultLanguage("pt_BR");
		loadDefaultLanguage("de_DE");
		
		File langDir = new File(Electrodynamics.instance.configFolder, "lang");
		if (!langDir.exists()) {
			langDir.mkdirs();
		}
	}
	
	private void loadDefaultLanguage(String lang) {
		Properties langFile = new Properties(this.languageMapping.get(lang));
		InputStreamReader reader = null;
		
		try {
			InputStream inStream = getClass().getResourceAsStream(LANG_DIR + lang + ".properties");
			if (inStream == null) {
				throw new IOException();
			}
			reader = new InputStreamReader(inStream, "UTF-8");
			langFile.load(reader);
		} catch(Exception ex) {
			EDLogger.warn("Failed to load language: " + lang + " Reason: " + ex.getLocalizedMessage());
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}

		this.languageMapping.put(lang, langFile);
		EDLogger.info("Loaded language file: " + lang);
	}
	
	public String translate(String tag) {
		return translate(tag, Minecraft.getMinecraft().func_135016_M().func_135041_c().func_135034_a());
	}
	
	public String translate(String tag, String lang) {
		if (languageMapping.containsKey(lang)) {
			try {
				return languageMapping.get(lang).getProperty(tag);
			} catch(Exception ex) {
				printLanguageError(lang, tag);
				return lang + ": " + tag;
			}
		}
		
		EDLogger.warn("Language registery is missing a file for " + lang);
		return "";
	}
	
	public void registerItem(Item item) {
		registerItemStack(new ItemStack(item), item.getUnlocalizedName().substring("item.".length()));
	}
	
	public void registerBlock(Block block) {
		registerItemStack(new ItemStack(block), block.getUnlocalizedName().substring("tile.".length()));
	}
	
	public void registerItemStack(ItemStack stack, String unlocalized) {
		String currLang = "";
		
		for (String lang : languageMapping.keySet()) {
			try {
				currLang = lang;
				LanguageRegistry.instance().addNameForObject(stack, lang, translate(unlocalized, lang));
			} catch(Exception ex) {
				printLanguageError(currLang, unlocalized);
			}
		}
	}
	
	public void printLanguageError(String lang, String tag) {
		if (ConfigurationSettings.SHOW_LOCALIZATION_ERRORS) {
			EDLogger.warn("Failed to register " + lang + " localization for " + tag);
		} else {
			if (!errorShown) {
				EDLogger.warn("Ran into an issue with localization. This message will only show once, but you can enable localization errors in the config to see more info.");
				errorShown = true;
			}
		}
	}
	
}
