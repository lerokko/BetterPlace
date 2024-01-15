package com.lerokko.betterplace.listeners;

import com.lerokko.betterplace.ClickInfo;
import com.lerokko.betterplace.listeners.ClickDetector;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DisableOnItemChange implements Listener {

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        //System.out.println("DEBUG: Held itme changed?");
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int previousSlot = event.getPreviousSlot();

        // Check if the held item has changed
        ItemStack newItem = player.getInventory().getItem(newSlot);
        ItemStack previousItem = player.getInventory().getItem(previousSlot);

        if (!areItemsEqual(newItem, previousItem)) {
            // Handle held item change
            ClickInfo removedInfo = ClickDetector.clickInfoMap.remove(player.getUniqueId());

            if (removedInfo != null) {
                sendActionBar(player, ChatColor.RED + "Rotation lock disabled. (Set with SHIFT + Left-Click)");
                player.playSound(player.getLocation(), Sound.ENTITY_GLOW_ITEM_FRAME_REMOVE_ITEM, 1.0f, 0.5f);
            }
        }
    }

    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    private boolean areItemsEqual(ItemStack item1, ItemStack item2) {
        // Check if the two ItemStacks are equal, considering item type, amount, and meta
        return item1 != null && item2 != null && item1.isSimilar(item2);
    }
}
