package com.keichankotaro.matasaba.matasabaplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
	    event.getPlayer().sendMessage(playerName + " がブロックを設置しました。ブロック ID は " + blockId + "、位置は " + location + " です。");
	}
}