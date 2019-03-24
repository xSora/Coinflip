package me.Utils;

public class Messages {	//This file is used for Translations

	public static String NOT_ENOUGH_PERMISSIONS() {
		String Default = FileManager.language.getString("NOT_ENOUGH_PERMISSIONS");
		String Coloured = Default.replace("&", "§");
		//String Replaced = Coloured.replace("#VALUE#", null);
		return Coloured;
	}
	
	public static String BET_ON_BLACK() {
		String Default = FileManager.language.getString("BET_ON_BLACK");
		String Coloured = Default.replace("&", "§");
		//String Replaced = Coloured.replace("#VALUE#", null);
		return Coloured;
	}
	
	public static String BET_ON_GREEN() {
		String Default = FileManager.language.getString("BET_ON_GREEN");
		String Coloured = Default.replace("&", "§");
		//String Replaced = Coloured.replace("#VALUE#", null);
		return Coloured;
	}
	
	public static String BET_ON_WHITE() {
		String Default = FileManager.language.getString("BET_ON_WHITE");
		String Coloured = Default.replace("&", "§");
		//String Replaced = Coloured.replace("#VALUE#", null);
		return Coloured;
	}
	
	public static String NOT_ENOUGH_MONEY(double Price) {
		String Default = FileManager.language.getString("NOT_ENOUGH_MONEY");
		String Coloured = Default.replace("&", "§");
		String Replaced = Coloured.replace("#PRICE#", ""+Price);
		return Replaced;
	}
	
}
