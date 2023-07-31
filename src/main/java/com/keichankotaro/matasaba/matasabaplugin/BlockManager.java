package com.keichankotaro.matasaba.matasabaplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockManager implements Listener { 
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
	    // ブロックの ID を取得します。
	    Material blockId = event.getBlock().getType();
	
	    // ブロックの位置を取得します。
	    Location location = event.getBlock().getLocation();
	
	    // プレイヤー名を取得します。
	    String playerName = event.getPlayer().getName();
	
	    // メッセージを表示します。
	    //getLogger().info(playerName + " がブロックを設置しました。ブロック ID は " + blockId + "、位置は " + location + " です。");
	    /*
	    System.out.println("MCID(UUID): " + playerName + "(" + event.getPlayer().getUniqueId() + ")");
	    System.out.println("Block ID: " + blockId);
	    System.out.println("x: " + location.getBlockX());
	    System.out.println("y: " + location.getBlockY());
	    System.out.println("z: " + location.getBlockZ());
	    System.out.println("pitch: " + location.getPitch());
	    System.out.println("yaw: " + location.getYaw());
	    System.out.println("World: " + location.getWorld().getName());
	    System.out.println("Type: Place");
	    */
	    DBManager.AddDB_BLOCK(playerName, event.getPlayer().getUniqueId()+"", location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getPitch(), location.getYaw(), location.getWorld().getName(), "PLACE", blockId+"", 0, (int) (System.currentTimeMillis() / 1000));
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
	    // ブロックの ID を取得します。
	    Material blockId = event.getBlock().getType();
	
	    // ブロックの位置を取得します。
	    Location location = event.getBlock().getLocation();
	
	    // プレイヤー名を取得します。
	    String playerName = event.getPlayer().getName();
	
	    // メッセージを表示します。
	    //getLogger().info(playerName + " がブロックを設置しました。ブロック ID は " + blockId + "、位置は " + location + " です。");
	    /*
	    System.out.println("MCID(UUID): " + playerName + "(" + event.getPlayer().getUniqueId() + ")");
	    System.out.println("Block ID: " + blockId);
	    System.out.println("x: " + location.getBlockX());
	    System.out.println("y: " + location.getBlockY());
	    System.out.println("z: " + location.getBlockZ());
	    System.out.println("pitch: " + location.getPitch());
	    System.out.println("yaw: " + location.getYaw());
	    System.out.println("World: " + location.getWorld().getName());
	    System.out.println("Type: Break");
	    */
	    DBManager.AddDB_BLOCK(playerName, event.getPlayer().getUniqueId()+"", location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getPitch(), location.getYaw(), location.getWorld().getName(), "BREAK", blockId+"", 0, (int) (System.currentTimeMillis() / 1000));
	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		// ブロックの ID を取得します。
	    Material blockId = event.getBlock().getType();
	
	    // ブロックの位置を取得します。
	    Location location = event.getBlock().getLocation();
	
	    // メッセージを表示します。
	    //getLogger().info(playerName + " がブロックを設置しました。ブロック ID は " + blockId + "、位置は " + location + " です。");
	    /*
	    System.out.println("Block ID: " + blockId);
	    System.out.println("x: " + location.getBlockX());
	    System.out.println("y: " + location.getBlockY());
	    System.out.println("z: " + location.getBlockZ());
	    System.out.println("pitch: " + location.getPitch());
	    System.out.println("yaw: " + location.getYaw());
	    System.out.println("World: " + location.getWorld().getName());
	    System.out.println("Type: Burn");
	    */
	    DBManager.AddDB_BLOCK("NONE", "NONE", location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getPitch(), location.getYaw(), location.getWorld().getName(), "BURN", blockId+"", 0, (int) (System.currentTimeMillis() / 1000));
	}
	
	/*
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		// ブロックの ID を取得します。
	    Material blockId = event.getBlock().getType();
	
	    // ブロックの位置を取得します。
	    Location location = event.getBlock().getLocation();
	
	    // メッセージを表示します。
	    //getLogger().info(playerName + " がブロックを設置しました。ブロック ID は " + blockId + "、位置は " + location + " です。");
	    System.out.println("Block ID: " + blockId);
	    System.out.println("x: " + location.getBlockX());
	    System.out.println("y: " + location.getBlockY());
	    System.out.println("z: " + location.getBlockZ());
	    System.out.println("pitch: " + location.getPitch());
	    System.out.println("yaw: " + location.getYaw());
	    System.out.println("World: " + location.getWorld().getName());
	    System.out.println("Type: Explode");
	    DBManager.AddDB_BLOCK("NONE", "NONE", location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getPitch(), location.getYaw(), location.getWorld().getName(), "EXPLODE", blockId+"", 0, (int) (System.currentTimeMillis() / 1000));
	}
	*/
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
	    System.out.println("Block explosion event triggered!");
	    // 以下のコードをそのまま続けます...
	}
}