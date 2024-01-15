package com.lerokko.betterplace;

import com.lerokko.betterplace.commands.UnlockCommand;
import com.lerokko.betterplace.listeners.ClickDetector;
import com.lerokko.betterplace.listeners.DisableOnItemChange;
import com.lerokko.betterplace.listeners.DisableOnQuit;
import com.lerokko.betterplace.listeners.RotatePlacedBlock;
import com.lerokko.betterplace.presistentData.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public final class Betterplace extends JavaPlugin {

    private static Betterplace plugin;
    private Map<UUID, ClickInfo> clickInfoMap = new HashMap<>();
    private BukkitTask periodicTask;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        //created to be able to access an instance of the plugin in another class
        plugin = this;

        //setup default config
        getConfig().options().copyDefaults();
        FileConfiguration config = getConfig();
        //create initial config if it does not exist
        saveDefaultConfig();

        // Initialize the ConfigManager with the plugin instance
        configManager = new ConfigManager(this);

        //setup custom config file (unused for now kept here for future use)
        //CustomConfig.setup();
        //CustomConfig.get().addDefault("enabled",true);
        //CustomConfig.get().options().copyDefaults(true);
        //CustomConfig.save();

        //register events
        getServer().getPluginManager().registerEvents(new RotatePlacedBlock(configManager), this);
        getServer().getPluginManager().registerEvents(new ClickDetector(configManager), this);
        getServer().getPluginManager().registerEvents(new DisableOnQuit(), this);
        getServer().getPluginManager().registerEvents(new DisableOnItemChange(), this);

        getCommand("bpunlock").setExecutor(new UnlockCommand());

        periodicTask = new DetectItemSwap(clickInfoMap).runTaskTimer(this, 0L, 60L);
        getLogger().info("Loading done");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown login
        configManager.saveConfig();
    }
    public static Betterplace getPlugin() {
        return plugin;
    }
}
