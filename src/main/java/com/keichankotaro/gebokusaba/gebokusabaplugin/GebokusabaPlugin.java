package com.keichankotaro.gebokusaba.gebokusabaplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GebokusabaPlugin extends JavaPlugin{
	
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
	
	@Override
	public void onEnable() {
		getLogger().info("config.ymlの読み込み中...");
		saveDefaultConfig();
		getLogger().info("config.ymlの読み込み完了");
		getLogger().info("下僕鯖プラグインが起動しました。");
	}
	
	public void onDisable() {
		getLogger().info("下僕鯖プラグインを終了しました。");
	}
	
	
	// ここから拠点tp
	@SuppressWarnings("unlikely-arg-type")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tpbase")) {
			Player player = (Player)sender;
			Inventory inv = player.getInventory();
			ItemStack offhand = new ItemStack(player.getInventory().getItemInOffHand());
			
			if(offhand.getAmount() != 0) {
				sender.sendMessage("オフハンドにアイテムがあります。オフハンドからアイテムをなくしてください。");
				return true;
			} else if(inv.contains(Material.IRON_INGOT) == true) {
				int i = 0;
				for (ItemStack item : inv.getContents()) {
				  if (item != null && item.getType() == Material.IRON_INGOT){
					  i = i + item.getAmount();
				  }
				}
				
				if(i >= 2) {
					inv.remove(Material.IRON_INGOT);
					inv.addItem(new ItemStack(Material.IRON_INGOT,i-2));
					sender.sendMessage("拠点にテレポートします。");
					Integer x = getConfig().getInt("base.x");
					Integer y = getConfig().getInt("base.y");
					Integer z = getConfig().getInt("base.z");
					Integer yaw = getConfig().getInt("base.yaw");
					Integer pitch = getConfig().getInt("base.pitch");
					String world = getConfig().getString("base.world");
					Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
					player.teleport(loc);
				} else {
					sender.sendMessage("アイテムが足りません。");
				}
			} else {
				sender.sendMessage("アイテムが足りません。");
			} 
			return true;
		} else if(cmd.getName().equalsIgnoreCase("tpbrl")) {
			getLogger().info("config.ymlの読み込み中...");
			sender.sendMessage("config.ymlの読み込み中...");
			reloadConfig();
			saveDefaultConfig();
			sender.sendMessage("config.ymlの読み込み完了");
			getLogger().info("config.ymlの読み込み完了");
			return true;
			// ここまで拠点tp
			
			// ここから拠点tp+エンティティと一緒に
		} else if(cmd.getName().equalsIgnoreCase("tpbasewithentity")) {
			
			Player player = (Player)sender;
			Inventory inv = player.getInventory();
			ItemStack offhand = new ItemStack(player.getInventory().getItemInOffHand());
			
			List<Entity> near = player.getNearbyEntities(5.0D, 5.0D, 5.0D);
			List<Double> num = new ArrayList<Double>();
			for(int x = 0; x < near.size(); x++) {
				num.add(player.getLocation().distance(near.get(x).getLocation()));
			}
			
			if(offhand.getAmount() != 0) {
				sender.sendMessage("オフハンドにアイテムがあります。オフハンドからアイテムをなくしてください。");
				return true;
			} else if(inv.contains(Material.IRON_INGOT) == true) {
				int i = 0;
				for (ItemStack item : inv.getContents()) {
				  if (item != null && item.getType() == Material.IRON_INGOT){
					  i = i + item.getAmount();
				  }
				}
				
				if(near.size() == 0) {
					sender.sendMessage("近くにエンティティがいません。");
				} else if(i >= 4) {
					inv.remove(Material.IRON_INGOT);
					inv.addItem(new ItemStack(Material.IRON_INGOT,i-4));
					sender.sendMessage("拠点にテレポートします。");
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
					sender.sendMessage("アイテムが足りません。");
				}
			} else {
				sender.sendMessage("アイテムが足りません。");
			} 
			return true;
		}
		return false;
		// ここまで拠点tp+エンティティと一緒に
	}
	
}
	
