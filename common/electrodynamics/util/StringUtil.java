package electrodynamics.util;

import java.util.ArrayList;
import java.util.List;


public class StringUtil {

	public static List<String> splitStringAfterNChars(String string, int length) {
		if (string == null || string.length() == 0) return null;
		
		ArrayList<String> strings = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		String[] splitStrings = string.split(" ");
		
		for (int i=0; i<splitStrings.length; i++) {
			String splitString = splitStrings[i];
			int stringLength = splitString.length();
			
			if (sb.toString().length() + stringLength <= length) {
				sb.append(splitString);
				sb.append(" ");
				
				if (i == splitStrings.length - 1) {
					strings.add(sb.toString().trim());
				}
			} else {
				strings.add(sb.toString().trim());
				sb = new StringBuilder();
			}
		}
		
		return strings;
	}

	public static String condenseStringArray(String[] array, char splitter) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<array.length; i++) {
			String string = array[i];
			
			sb.append(string);
			if (i != array.length - 1) {
				sb.append(splitter);
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	public static String toTitleCase(String word) {
		StringBuilder sb = new StringBuilder();
		boolean next = false;
		
		for (char c : word.toCharArray()) {
			if (Character.isWhitespace(c)) {
				next = true;
			} else if (next) {
				Character.toTitleCase(c);
			}
			
			sb.append(c);
		}
		
		return sb.toString();
	}
	
}
