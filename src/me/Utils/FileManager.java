package me.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import me.xSora.Main;

public class FileManager {	//This file is used for File Management

	public static File configFile;
	public static YamlConfiguration config;
	
	public static File languageFile;
	public static YamlConfiguration language;
	
	public enum Files{
		config,
		language
	}
	
	public static void InitFiles() {
		configFile = new File(Main.plugin.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		
		languageFile = new File(Main.plugin.getDataFolder(), "language.yml");
		language = YamlConfiguration.loadConfiguration(languageFile);
		
		//Creating Config File if not Existing
		if(!configFile.exists()) {
			CreateFile(Files.config);
		}
		
		if(!languageFile.exists()) {
			CreateFile(Files.language);
		}
		
	}
	
	public static void CreateFile(Files f) {
		switch(f) {
			case config:
				config.set("Coinflip.Price", 100.00);			// Roll Price (100 Default)
				config.set("Coinflip.NormalProfit", 2);		// Black / White Roll Profit (x2 Default)
				config.set("Coinflip.GreenProfit", 14);		// Green Profit (x14 Default)
				Save(Files.config);
				break;
			case language:
				language.set("NOT_ENOUGH_PERMISSIONS", "&cNo Permissions.");
				
				language.set("BET_ON_BLACK", "&6Bet on &0Black");
				language.set("BET_ON_GREEN", "&6Bet on &aGreen");
				language.set("BET_ON_WHITE", "&6Bet on &fWhite");
				
				language.set("NOT_ENOUGH_MONEY", "&6Not Enough Money. Price: #PRICE# $");
				
				Save(Files.language);
				break;
		}
	}
	public static void Save(Files f) {
		switch(f) {
			case config:
			try {
				config.save(configFile);
			} catch (IOException e) {
				System.err.println("Saving File 'config.yml' Failed!");
				e.printStackTrace();
			}
				break;
			case language:
			try {
				language.save(languageFile);
			} catch (IOException e) {
				System.err.println("Saving File 'language.yml' Failed!");
				e.printStackTrace();
			}
				break;
		}
	}
	
	
}
