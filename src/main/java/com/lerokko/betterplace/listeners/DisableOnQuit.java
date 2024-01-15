package com.lerokko.betterplace.listeners;
import com.lerokko.betterplace.ClickInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
public class DisableOnQuit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // Remove the click information associated with the player when they log out
        ClickInfo removedInfo = ClickDetector.clickInfoMap.remove(playerUUID);
    }
}
