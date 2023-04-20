package net.sudologic.advancementcommand;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class AdvancementCommand extends JavaPlugin {
    private ArrayList<Set> sets;
    private boolean debug;

    @Override
    public void onEnable() {
        // Plugin startup logic
        sets = new ArrayList<>();

        getServer().getPluginManager().registerEvents(new PlayerAdvancementListener(this), this);

        saveDefaultConfig();
        reloadConfig();
        loadConfigOptions();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public void loadConfigOptions() {
        FileConfiguration config = getConfig();
        debug = config.getBoolean("debug");

        ConfigurationSection setsSection = config.getConfigurationSection("sets");
        for(String key : setsSection.getKeys(false)) {
            if(debug) {
                Bukkit.getLogger().log(Level.INFO, "Parsing set " + key);
            }
            ConfigurationSection set = config.getConfigurationSection("sets." + key);
            List<String> advancementsList = set.getStringList("Advancements");
            String[] advancements = advancementsList.toArray(new String[0]);
            List<String> commandsList = set.getStringList("Commands");
            String[] commands = commandsList.toArray(new String[0]);
            sets.add(new Set(key, advancements, commands, this));
        }

        // Save configuration
        saveConfig();
    }


    public ArrayList<Set> getSets() {
        return sets;
    }

    public boolean getDebug() {
        return debug;
    }
}
