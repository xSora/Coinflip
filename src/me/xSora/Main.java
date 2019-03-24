package me.xSora;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.Utils.FileManager;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{
	
	public static Main plugin;
	{
		plugin = this;
	}
	
	/*
	 * TODO LIST OF DOOM
	 * 
	 * - Pay for Coinflip
	 * 
	 */
	
    public static Economy econ = null;
	
	public void onEnable() {
		//Commands
		this.getCommand("coinflip").setExecutor(new Coinflip());
		
		//Events
		getServer().getPluginManager().registerEvents(new Coinflip(), this);
		
		setupEconomy();
		FileManager.InitFiles();
		Coinflip.InitGUI();
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}
