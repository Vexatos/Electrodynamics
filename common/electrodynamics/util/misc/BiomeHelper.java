package electrodynamics.util.misc;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeHelper {

	public static ArrayList<BiomeGenBase> getBiomesForTypes(Type ... types) {
		ArrayList<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
		
		for (Type type : types) {
			biomes.addAll(Arrays.asList(BiomeDictionary.getBiomesForType(type)));
		}
		
		return biomes;
	}
	
}
