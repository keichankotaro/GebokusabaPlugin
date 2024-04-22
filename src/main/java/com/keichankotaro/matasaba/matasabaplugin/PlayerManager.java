package com.keichankotaro.matasaba.matasabaplugin;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManager implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (DBManager.Check_EULA(player.getUniqueId()+"")) {
            List<Object> lastPlaceInfo = DBManager.GetDB_LastPlace(player.getUniqueId() + "");
            double x = (double) lastPlaceInfo.get(0);
            double y = (double) lastPlaceInfo.get(1);
            double z = (double) lastPlaceInfo.get(2);
            double yaw = (double) lastPlaceInfo.get(3);
            double pitch = (double) lastPlaceInfo.get(4);
            String worldName = (String) lastPlaceInfo.get(5);
            Location loc = new Location(Bukkit.getWorld(worldName), x, y, z, (float) yaw, (float) pitch);
            player.teleport(loc);
        } else {
        	Location loc = new Location(Bukkit.getWorld("lobby"), 0, -56, 0, 0, 0);
            player.teleport(loc);
            player.sendMessage(player.getName() + "さん、ようこそまた鯖へ！");
            player.sendMessage("簡単にこのサーバーのルールを紹介します！");
            player.sendMessage("- 他の人に迷惑をかけないこと。");
            player.sendMessage("- 荒さないこと。");
            player.sendMessage("- サーバーに過度な負荷をかける行為をしないこと。");
            player.sendMessage("参加するには /accept を実行してください。");
            player.sendMessage("上の簡単なルールに同意したことになります。");
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        double yaw = player.getLocation().getYaw();
        double pitch = player.getLocation().getPitch();
        String worldName = player.getWorld().getName();

        DBManager.AddDB_LastPlace(player.getName(), uuid, x, y, z, yaw, pitch, worldName);
    }
}
