package com.lerokko.betterplace.commands;

import com.lerokko.betterplace.ClickInfo;
import com.lerokko.betterplace.listeners.ClickDetector;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Remove the click information associated with the player
            if (!player.hasPermission("bplace.place.rotated")){
                player.sendMessage(ChatColor.AQUA + "[BetterPlace]" + ChatColor.RED + " You do not have permission to run this command");
            } else {
                ClickInfo removedInfo = ClickDetector.clickInfoMap.remove(player.getUniqueId());
                if (removedInfo != null) {
                    player.sendMessage(ChatColor.AQUA + "[BetterPlace]" + ChatColor.RED + "Rotation lock disabled. (Set with shift + left-click)");
                    player.playSound(player.getLocation(), Sound.ENTITY_GLOW_ITEM_FRAME_REMOVE_ITEM, 1.0f, 0.5f);
                }
            }
        }

        return true;
    }
}
