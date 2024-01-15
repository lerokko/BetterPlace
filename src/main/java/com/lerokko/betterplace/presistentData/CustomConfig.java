package com.lerokko.betterplace.presistentData;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private static File file;
    private static FileConfiguration persistentDataFile;
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Betterplace").getDataFolder(),"PersistentData.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                //big F
                System.out.println("Unable to create config file.");
            }
        }
        persistentDataFile = YamlConfiguration.loadConfiguration(file);
    }

    public static Configuration get() {
        return persistentDataFile;
    }

    public static void save() {
        try {
            persistentDataFile.save(file);
        }catch (IOException e){
            System.out.println("Could not save PersistentData in file");
        }
    }
    public static void reload(){
        persistentDataFile = YamlConfiguration.loadConfiguration(file);
    }
}
