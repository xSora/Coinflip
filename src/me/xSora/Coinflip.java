package me.xSora;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import me.Utils.FileManager;
import me.Utils.Messages;

public class Coinflip implements CommandExecutor, Listener{

	public static Inventory CoinflipGUI = Bukkit.createInventory(null, 9, "§6Coinflip");
	
	public static ItemStack[] ItemArray = new ItemStack[3];
	
	public enum BetOption{
		BLACK,
		GREEN,
		WHITE
	}
	
	//CoinflipGUI
	private static ItemStack IS_BET_BLACK;
	private static ItemStack IS_BET_GREEN;
	private static ItemStack IS_BET_WHITE;
	

	
	public static void InitGUI() {
		//Init Black Betting Option
		IS_BET_BLACK = new ItemStack(Material.BLACK_WOOL, 1);
		ItemMeta META_BET_BLACK = IS_BET_BLACK.getItemMeta();
		ArrayList<String> LORE_BET_BLACK = new ArrayList<String>();
		LORE_BET_BLACK.add("§cClick to Roll!");
		LORE_BET_BLACK.add("§6Chance: 49,5% (Number: 1 - 50)");
		META_BET_BLACK.setLore(LORE_BET_BLACK);
		META_BET_BLACK.setDisplayName(Messages.BET_ON_BLACK());
		IS_BET_BLACK.setItemMeta(META_BET_BLACK);
		
		//Init Green Betting Option
		IS_BET_GREEN = new ItemStack(Material.GREEN_WOOL, 1);
		ItemMeta META_BET_GREEN = IS_BET_GREEN.getItemMeta();
		ArrayList<String> LORE_BET_GREEN = new ArrayList<String>();
		LORE_BET_GREEN.add("§cClick to Roll!");
		LORE_BET_GREEN.add("§6Chance: 1% (Number: 0)");
		META_BET_BLACK.setLore(LORE_BET_GREEN);
		META_BET_GREEN.setDisplayName(Messages.BET_ON_GREEN());
		IS_BET_GREEN.setItemMeta(META_BET_GREEN);
		
		//Init White Betting Option
		IS_BET_WHITE = new ItemStack(Material.WHITE_WOOL, 1);
		ItemMeta META_BET_WHITE = IS_BET_WHITE.getItemMeta();
		ArrayList<String> LORE_BET_WHITE = new ArrayList<String>();
		LORE_BET_WHITE.add("§cClick to Roll!");
		LORE_BET_WHITE.add("§6Chance: 49,5% (Number: 51 - 100)");
		META_BET_BLACK.setLore(LORE_BET_WHITE);
		META_BET_WHITE.setDisplayName(Messages.BET_ON_WHITE());
		IS_BET_WHITE.setItemMeta(META_BET_WHITE);
		
		CoinflipGUI.setItem(2, IS_BET_BLACK);
		CoinflipGUI.setItem(4, IS_BET_GREEN);
		CoinflipGUI.setItem(6, IS_BET_WHITE);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String arg, String[] args) {
		Player p = (Player)cs;
		if(p.hasPermission("coinflip.use")) {
			p.openInventory(CoinflipGUI);
		}else {
			p.sendMessage(Messages.NOT_ENOUGH_PERMISSIONS());
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickEvent(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		Inventory inventory = e.getInventory();
		
		double CoinflipFee = FileManager.config.getDouble("Coinflip.Price");
		double PlayerMoney = Main.econ.getBalance(p);
		
		//Select GUI
		if(inventory.getName().equals(CoinflipGUI.getName())) {
			if(clicked != null) {
				if(PlayerMoney >= CoinflipFee) {
				if(clicked.getI18NDisplayName() == IS_BET_BLACK.getI18NDisplayName()) {
					//Clicked on Black
					e.setCancelled(true);
					p.closeInventory();
					StartRoll(BetOption.BLACK, p, CoinflipFee);
					}
				if(clicked.getI18NDisplayName() == IS_BET_GREEN.getI18NDisplayName()) {
					//Clicked on Green
					e.setCancelled(true);
					p.closeInventory();
					StartRoll(BetOption.GREEN, p, CoinflipFee);
					}
				if(clicked.getI18NDisplayName() == IS_BET_WHITE.getI18NDisplayName()) {
					//Clicked on White
					e.setCancelled(true);
					p.closeInventory();
					StartRoll(BetOption.WHITE, p, CoinflipFee);
					}
				}else {
					if(clicked.getI18NDisplayName() == IS_BET_BLACK.getI18NDisplayName()			// Do not execute if player clicks on other than the items
							|| clicked.getI18NDisplayName() == IS_BET_GREEN.getI18NDisplayName()
							|| clicked.getI18NDisplayName() == IS_BET_WHITE.getI18NDisplayName()) {
						p.sendMessage(Messages.NOT_ENOUGH_MONEY(CoinflipFee));
						e.setCancelled(true);
						p.closeInventory();
					}
				}
			}else {
				return;
			}
		}
	}
	
	private static void StartRoll(BetOption b, Player p, double coinflipFee) {
		Main.econ.withdrawPlayer(p, coinflipFee);
		BetOption bet = Winner(p);
		if(bet == b) {
			if(bet == BetOption.GREEN) {
				p.sendMessage("You won "+(coinflipFee * FileManager.config.getInt("Coinflip.GreenProfit")));
			}else {
				p.sendMessage("You won "+(coinflipFee * FileManager.config.getInt("Coinflip.NormalProfit")));
			}
		}else {
			p.sendMessage("You lost "+coinflipFee);
		}
	}
	
	private static BetOption Winner(Player p) {
		Random rand = new Random();
		int Number = rand.nextInt(100);
		p.sendMessage("§bRolled Number: "+Number);
		if(Number == 0) {
			return BetOption.GREEN;
		}
		if(Number > 0 && Number <= 50) {
			return BetOption.BLACK;
		}
		if(Number > 50 && Number <= 100) {
			return BetOption.WHITE;
		}else {
			return null;
		}
	}
	
	
}
