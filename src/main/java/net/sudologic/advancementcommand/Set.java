package net.sudologic.advancementcommand;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Set {
    String name;
    String[] advancements;
    String[] commands;

    public Set(String name, String[] advancements, String[] commands) {
        Bukkit.getLogger().log(Level.INFO, "Creating Set " + name);
        this.name = name;
        this.advancements = advancements;
        this.commands = commands;
    }

    public void runCommands(Player p) {
        for (String command : commands) {
            String parsedCommand = command.toString();
            Bukkit.getLogger().log(Level.INFO, parsedCommand);
            parsedCommand = PlaceholderAPI.setPlaceholders(p, parsedCommand);
            Bukkit.getLogger().log(Level.INFO, parsedCommand);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsedCommand);
        }
    }

    public List<String> checkMissingAdvancements(Player p) {
        ArrayList<String> missingAdvancements = new ArrayList<>();
        for(String advancementName : advancements) {
            String[] parts = advancementName.split(":");
            if (parts.length == 2) {
                NamespacedKey key = new NamespacedKey(parts[0], parts[1]);
                Advancement advancement = Bukkit.getAdvancement(key);
                if (advancement != null) {
                    AdvancementProgress progress = p.getAdvancementProgress(advancement);
                    if (progress != null && progress.isDone()) {
                        // player has the advancement
                    } else {
                        // player does not have the advancement
                        missingAdvancements.add(advancementName);
                    }
                } else {
                    Bukkit.getLogger().log(Level.INFO, "Invalid advancement name, maybe misspelled? Check config.yml!");
                    // invalid advancement name
                }
            } else {
                Bukkit.getLogger().log(Level.INFO, "Invalid advancement name format, should be exactly 2 parts! Check config.yml!");
                // invalid advancement name format
            }
        }
        return missingAdvancements;
    }

    public boolean containsAdvancement(Advancement a) {
        String advancementName = a.getKey().toString();
        for (String name : advancements) {
            if (name.equals(advancementName)) {
                return true;
            }
        }
        return false;
    }
}
