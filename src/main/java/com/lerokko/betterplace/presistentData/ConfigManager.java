package com.lerokko.betterplace.presistentData;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigManager {

    private Plugin plugin;
    private List<String> blacklist;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        blacklist = config.getStringList("blacklist");
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public void saveConfig() {
        plugin.saveConfig();
    }
}
