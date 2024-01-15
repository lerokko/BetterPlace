package com.lerokko.betterplace.listeners;

import com.lerokko.betterplace.ClickInfo;
import com.lerokko.betterplace.presistentData.ConfigManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Axis;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import java.util.List;

import static org.bukkit.block.BlockFace.*;

public class RotatePlacedBlock implements Listener {
    //this whole schmuu is needed to access the blacklist. I am not good enough with code to know if ChatGPT is talking bs
    private ConfigManager configManager;

    public RotatePlacedBlock(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        // Check if the placed block has directional data
        Block placedBlock = event.getBlockPlaced();
        BlockData blockData = placedBlock.getBlockData();
        String material = String.valueOf(placedBlock.getType());

        //load blacklist
        List<String> blacklist = configManager.getBlacklist();

        //get player rotation lock
        Player player = event.getPlayer();
        ClickInfo clickInfo = ClickDetector.getClickInfo(player);
        if (player.hasPermission("bplace.place.rotated")) {
            if ((clickInfo != null) && !blacklist.contains(material)) {
                if ((blockData instanceof Directional) && (((Directional) blockData).getFaces().contains(clickInfo.getDirection()))) {
                    //System.out.println("DEBUG: Block placed with Directional data. Setting direction to north.");
                    setDirectionalBlockRotation(placedBlock, clickInfo.getDirection());
                    sendActionBar(player, ChatColor.AQUA + "Placing locked to " + ChatColor.GREEN + clickInfo.getDirection() + ChatColor.AQUA + " (Left-Click to disable)");
                } else if ((blockData instanceof Orientable) && (((Orientable) blockData).getAxes().contains(clickInfo.getAxis()))) {
                    //System.out.println("DEBUG: Block placed with Rotatable data. Setting rotation to north.");
                    setOrientableBlockRotation(placedBlock, clickInfo.getAxis());
                    sendActionBar(player, ChatColor.AQUA + "Placing locked to " + ChatColor.GREEN + clickInfo.getAxis() + "-Axis" + ChatColor.AQUA + " (Left-Click to disable)");
                }
            }
        } else {
            ClickInfo removedInfo = ClickDetector.clickInfoMap.remove(player.getUniqueId());
        }
    }

    private void setDirectionalBlockRotation(Block block, BlockFace facing) {
        // Check if the block supports direction
        if (block.getBlockData() instanceof Directional) {
            //System.out.println("DEBUG: Block supports direction. Setting facing direction to " + facing);

            Directional directionalBlock = (Directional) block.getBlockData();
            directionalBlock.setFacing(facing);
            block.setBlockData(directionalBlock, true); // 'true' indicates the block update is forced

            //System.out.println("DEBUG: Direction set successfully.");
        }

    }

    private void setOrientableBlockRotation(Block block, Axis axis) {
        // Check if the block supports rotation
        if (block.getBlockData() instanceof Orientable) {
            //System.out.println("DEBUG: Block supports rotation. Setting rotation to " + facing);

            Orientable orientableBlock = (Orientable) block.getBlockData();
            orientableBlock.setAxis(axis);
            block.setBlockData(orientableBlock, true); // 'true' indicates the block update is forced

            //System.out.println("DEBUG: Rotation set successfully.");
        }
    }
    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
