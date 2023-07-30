package com.keichankotaro.matasaba.matasabaplugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MatasabaPlugin extends JavaPlugin implements CommandExecutor, TabCompleter {
	
	// リスト内の最小値を取得。(Double型)
	public static Double getMin(List<Double> list) {
		Double min = Double.MAX_VALUE;

		for (Double i: list)
		{
			if (min > i) {
				min = i;
			}
		}

		return min;
	}

	//public static HashMap playerItemTracker;
	private Method getMethod(String name, Class<?> clazz) {
		for (Method m : clazz.getDeclaredMethods()) {
			if (m.getName().equals(name))
				return m;
		}
		return null;
	}
	
	@Override
	public void onEnable() {
		getLogger().info("config.ymlの読み込み中...");
		saveDefaultConfig();
		getLogger().info("config.ymlの読み込み完了");
		
		/*
		何かの作りかけです。乞うご期待!
		
		ItemStack customItem = new ItemStack(Material.*****, 1);
		ItemMeta customMeta = customItem.getItemMeta();
		if(customMeta == null) return;
		customMeta.setDisplayName(ChatColor.AQUA + "******* ***: " + ChatColor.RED + "None");
		NamespacedKey customKey = new NamespacedKey(this, "**********");
		ShapedRecipe customRecipe = new ShapedRecipe(customKey, customItem);
		customRecipe.shape("CCC", "CIC", "CCC");
		customRecipe.setIngredient('C', Material.*****);
		customRecipe.setIngredient('I', Material.**********);
		Bukkit.addRecipe(customRecipe);
		*/
		
		//getServer().getPluginManager().registerEvents(new ItemListener(), this);
		//playerItemTracker = new HashMap<>();
		getServer().getPluginManager().registerEvents(new BlockManager(), this);
		DBManager.connect();
		DBManager.createTable();
		getLogger().info("また鯖プラグインが起動しました。");
	}
	
	public void onDisable() {
		//playerItemTracker.clear();
		DBManager.disconnect();
		getLogger().info("また鯖プラグインを終了しました。");
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("event")) {
			if (args.length == 1) {
				return Arrays.asList("list", "search");
			} else if (args.length >= 2 && args.length % 2 == 0 && args[0].equalsIgnoreCase("search")) {
				return Arrays.asList("ID", "UUID", "X", "Y", "Z", "Yaw", "Pitch", "World", "FUNC", "ITEM", "NUM", "TIME");
			} else if (args.length >= 2 && args.length % 2 == 1 && args[0].equalsIgnoreCase("search") && args[args.length-2].equalsIgnoreCase("func")) {
				return Arrays.asList("PLACE", "BREAK", "BURN", "EXPLODE");
			} /*else if (args.length >= 2 && args.length % 2 == 1 && args[0].equalsIgnoreCase("search") && args[args.length-2].equalsIgnoreCase("world")) {
				return Arrays.asList("world", "world_nether", "world_the_end");
			}*/ else {
				return null;
			}
		}
		return null;
	}
	
	// ここから拠点tp
	@SuppressWarnings("unlikely-arg-type")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		// ここからクライアントの言語の取得
		// 日本語:ja_jp
		// 英語:en_us
		Player sendplayer = (Player)sender;
		Object ep = null;
		Field f = null;
		
		try {
			ep = getMethod("getHandle", sendplayer.getClass()).invoke(sendplayer, (Object[]) null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		try {
			f = ep.getClass().getDeclaredField("locale");
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		f.setAccessible(true);
		String language = null;
		try {
			language = (String) f.get(ep);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		// ここまでクライアントの言語の取得
		
		if(cmd.getName().equalsIgnoreCase("tpbase")) {
			Player player = (Player)sender;
			Inventory inv = player.getInventory();
			ItemStack offhand = new ItemStack(player.getInventory().getItemInOffHand());
			String setitem_normal = getConfig().getString("items.tpalone.item");
			int setnum_normal = getConfig().getInt("items.tpalone.num");
			
			int i = 0;
			if(offhand.getAmount() != 0) {
				if(language.equalsIgnoreCase("ja_jp")) {
					sender.sendMessage("オフハンドにアイテムがあります。オフハンドからアイテムをなくしてください。");
				} else {
					sender.sendMessage("You have an item in your offhand. Remove items from your offhand.");
				}
				return true;
			} else if(inv.contains(Material.getMaterial(setitem_normal)) == true) {
				for (ItemStack item : inv.getContents()) {
				  if (item != null && item.getType() == Material.getMaterial(setitem_normal)){
					  i = i + item.getAmount();
				  }
				}
				
				if(i >= setnum_normal) {
					inv.remove(Material.getMaterial(setitem_normal));
					inv.addItem(new ItemStack(Material.getMaterial(setitem_normal),i-setnum_normal));
					if(language.equalsIgnoreCase("ja_jp")) {
						sender.sendMessage(ChatColor.GREEN+"拠点にテレポートします。"+ChatColor.RESET);
					} else {
						sender.sendMessage(ChatColor.GREEN+"Teleport to base."+ChatColor.RESET);
					}
					Integer x = getConfig().getInt("base.x");
					Integer y = getConfig().getInt("base.y");
					Integer z = getConfig().getInt("base.z");
					Integer yaw = getConfig().getInt("base.yaw");
					Integer pitch = getConfig().getInt("base.pitch");
					String world = getConfig().getString("base.world");
					Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
					player.teleport(loc);
				} else {
					if(language.equalsIgnoreCase("ja_jp")) {
						sender.sendMessage(ChatColor.RED+"アイテムが足りません。(不足:"+(setnum_normal-i)+Material.getMaterial(setitem_normal)+")"+ChatColor.RESET);
					} else {
						sender.sendMessage(ChatColor.RED+"Not enough items. (missing: "+(setnum_normal-i)+" "+Material.getMaterial(setitem_normal)+")"+ChatColor.RESET);
					}
				}
			} else {
				if(language.equalsIgnoreCase("ja_jp")) {
					sender.sendMessage(ChatColor.RED+"アイテムが足りません。(不足:"+(setnum_normal-i)+Material.getMaterial(setitem_normal)+")"+ChatColor.RESET);
				} else {
					sender.sendMessage(ChatColor.RED+"Not enough items. (missing: "+(setnum_normal-i)+" "+Material.getMaterial(setitem_normal)+")"+ChatColor.RESET);
				}
			}
			return true;
			// ここまで拠点tp
			
			// ここからコンフィグのリロード
		} else if(cmd.getName().equalsIgnoreCase("rlgeboku")) {
			getLogger().info("config.ymlの読み込み中...");
			sender.sendMessage("config.ymlの読み込み中...");
			reloadConfig();
			saveDefaultConfig();
			sender.sendMessage("config.ymlの読み込み完了");
			getLogger().info("config.ymlの読み込み完了");
			return true;
			// ここまでコンフィグのリロード
			
			// ここから拠点tp(+エンティティ)
		} else if(cmd.getName().equalsIgnoreCase("tpbasewithentity")) {
			
			String setitem_withentity = getConfig().getString("items.tpwithentity.item");
			int setnum_withentity = getConfig().getInt("items.tpwithentity.num");
			
			Player player = (Player)sender;
			Inventory inv = player.getInventory();
			ItemStack offhand = new ItemStack(player.getInventory().getItemInOffHand());
			
			List<Entity> near = player.getNearbyEntities(5.0D, 5.0D, 5.0D);
			List<Double> num = new ArrayList<Double>();
			for(int x = 0; x < near.size(); x++) {
				num.add(player.getLocation().distance(near.get(x).getLocation()));
			}
			
			int i = 0;
			if(offhand.getAmount() != 0) {
				if(language.equalsIgnoreCase("ja_jp")) {
					sender.sendMessage("オフハンドにアイテムがあります。オフハンドからアイテムをなくしてください。");
				} else {
					sender.sendMessage("You have an item in your offhand. Remove items from your offhand.");
				}
				return true;
			} else if(inv.contains(Material.getMaterial(setitem_withentity)) == true) {
				for (ItemStack item : inv.getContents()) {
				  if (item != null && item.getType() == Material.getMaterial(setitem_withentity)){
					  i = i + item.getAmount();
				  }
				}
				
				if(near.size() == 0) {
					if(language.equalsIgnoreCase("ja_jp")) {
						sender.sendMessage(ChatColor.RED+"近くにエンティティがいません。"+ChatColor.RESET);
					} else {
						sender.sendMessage(ChatColor.RED+"There are no entities nearby."+ChatColor.RESET);
					}
				} else if(i >= setnum_withentity) {
					inv.remove(Material.getMaterial(setitem_withentity));
					inv.addItem(new ItemStack(Material.getMaterial(setitem_withentity),i-setnum_withentity));
					if(language.equalsIgnoreCase("ja_jp")) {
						sender.sendMessage(ChatColor.GREEN+"拠点にテレポートします。"+ChatColor.RESET);
					} else {
						sender.sendMessage(ChatColor.GREEN+"Teleport to base."+ChatColor.RESET);
					}
					Integer x = getConfig().getInt("base.x");
					Integer y = getConfig().getInt("base.y");
					Integer z = getConfig().getInt("base.z");
					Integer yaw = getConfig().getInt("base.yaw");
					Integer pitch = getConfig().getInt("base.pitch");
					String world = getConfig().getString("base.world");
					Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
					near.get(num.indexOf(getMin(num))).teleport(loc);
					player.teleport(loc);
				} else {
					if(language.equalsIgnoreCase("ja_jp")) {
						sender.sendMessage(ChatColor.RED+"アイテムが足りません。(不足:"+(setnum_withentity-i)+Material.getMaterial(setitem_withentity)+")"+ChatColor.RESET);
					} else {
						sender.sendMessage(ChatColor.RED+"Not enough items. (missing: "+(setnum_withentity-i)+" "+Material.getMaterial(setitem_withentity)+")"+ChatColor.RESET);
					}
				}
			} else {
				if(language.equalsIgnoreCase("ja_jp")) {
					sender.sendMessage(ChatColor.RED+"アイテムが足りません。(不足:"+(setnum_withentity-i)+Material.getMaterial(setitem_withentity)+")"+ChatColor.RESET);
				} else {
					sender.sendMessage(ChatColor.RED+"Not enough items. (missing: "+(setnum_withentity-i)+" "+Material.getMaterial(setitem_withentity)+")"+ChatColor.RESET);
				}
			} 
			return true;
			// ここまで拠点tp(+エンティティ)
			
			// ここからシード値の取得
		} else if(cmd.getName().equalsIgnoreCase("seed")) {
			Player player = (Player)sender;
			
			if(language.equalsIgnoreCase("ja_jp")) {
				sender.sendMessage("シード値：["+ChatColor.GREEN+player.getWorld().getSeed()+ChatColor.RESET+"]");
			} else {
				sender.sendMessage("seed：["+ChatColor.GREEN+player.getWorld().getSeed()+ChatColor.RESET+"]");
			}
			
			return true;
			// ここまでシード値の取得
		} /*else if (cmd.getName().equalsIgnoreCase("event")) {
			System.out.println(args);
		}*/
		return false;
	}
}