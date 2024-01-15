package com.lerokko.betterplace;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class DetectItemSwap extends BukkitRunnable {

    private final Map<UUID, ClickInfo> clickInfoMap;

    public DetectItemSwap(Map<UUID, ClickInfo> clickInfoMap) {
        this.clickInfoMap = clickInfoMap;
    }

    @Override
    public void run() {
        //System.out.println("DEBUG: Void run runs.");
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerUUID = player.getUniqueId();
            if (clickInfoMap.containsKey(playerUUID)) {
                ClickInfo clickInfo = clickInfoMap.get(playerUUID);
                //System.out.println("DEBUG: Sending message to player: " + player.getName());
                //sendActionBar(player, "TEST");
            }
        }
    }


    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
