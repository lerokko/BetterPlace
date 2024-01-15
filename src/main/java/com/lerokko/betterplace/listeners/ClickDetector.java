package com.lerokko.betterplace.listeners;

import com.lerokko.betterplace.ClickInfo;
import com.lerokko.betterplace.presistentData.ConfigManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClickDetector implements Listener {

    //this whole schmuu is needed to access the blacklist. I am not good enough with code to know if ChatGPT is talking bs
    private ConfigManager configManager;

    public ClickDetector(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public static Map<UUID, ClickInfo> clickInfoMap = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //load blacklist
        List<String> blacklist = configManager.getBlacklist();

        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (!blacklist.contains(String.valueOf(heldItem.getType()))) {
            if (heldItem.getType().isBlock() && !heldItem.getType().isEdible() && player.hasPermission("bplace.place.rotated")) {
                BlockData blockData = Bukkit.getServer().createBlockData(heldItem.getType());
                if (blockData instanceof Directional || blockData instanceof Orientable) {
                    // Check if the player is sneaking and left-clicking
                    if (event.getAction().name().contains("LEFT_CLICK")) {

                        // Remove the stored data from the hashmap when player left-clicks without sneaking
                        ClickInfo removedInfo = clickInfoMap.remove(player.getUniqueId());

                        // Send a message to the player that their rotation lock has been reset
                        if (!player.isSneaking() && removedInfo != null) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Rotation lock disabled. (SHIFT-Click again to enable)"));
                            player.playSound(player.getLocation(), Sound.ENTITY_GLOW_ITEM_FRAME_REMOVE_ITEM, 1.0f, 0.5f);

                        } else if (player.isSneaking()) {
                            // Store the direction information
                            ClickInfo clickInfo = new ClickInfo(player.getLocation().getDirection());
                            //check if block is directional
                            if (blockData instanceof Directional) {
                                Directional directional = (Directional) blockData;
                                //check if the attempted lock is a valid direction for the block held
                                if (directional.getFaces().contains(clickInfo.getDirection())) {
                                    clickInfoMap.put(player.getUniqueId(), clickInfo);
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.AQUA + "Rotation locked to " + ChatColor.GREEN + clickInfo.getDirection() + ChatColor.AQUA + " (Left-Click to disable)"));
                                    player.playSound(player.getLocation(), Sound.ENTITY_GLOW_ITEM_FRAME_ADD_ITEM, 1.0f, 1.75f);
                                }
                            }
                            //check if block is orientable
                            if (blockData instanceof Orientable) {
                                Orientable orientable = (Orientable) blockData;
                                //check if the attempted lock is a valid orientation for the block held
                                if (orientable.getAxes().contains(clickInfo.getAxis())) {
                                    clickInfoMap.put(player.getUniqueId(), clickInfo);
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.AQUA + "Rotation locked to " + ChatColor.GREEN + clickInfo.getAxis() + "-Axis" + ChatColor.AQUA + " (Left-Click to disable)"));
                                    player.playSound(player.getLocation(), Sound.ENTITY_GLOW_ITEM_FRAME_ADD_ITEM, 1.0f, 1.75f);
                                }
                            }

                        }
                    }
                }
            }
        }
    }
    // Method to check if a player has sneaked and left-clicked
    public static ClickInfo getClickInfo(Player player) {
        return clickInfoMap.get(player.getUniqueId());
    }
}

