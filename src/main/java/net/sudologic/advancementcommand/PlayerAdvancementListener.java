package net.sudologic.advancementcommand;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.logging.Level;

public class PlayerAdvancementListener implements Listener {
    AdvancementCommand plugin;

    public PlayerAdvancementListener(AdvancementCommand plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        Player p = event.getPlayer();
        Advancement a = event.getAdvancement();
        if(!p.getAdvancementProgress(a).isDone()) {
            return;
        }
        Bukkit.getLogger().log(Level.INFO, "Player " + p.getName() + " has completed advancement " + a.getKey());
        for(Set set : plugin.getSets()) {
            Bukkit.getLogger().log(Level.INFO, "Checking if set contains advancement");
            if(set.containsAdvancement(a)) {
                Bukkit.getLogger().log(Level.INFO, "Set contains advancement. Checking other criteria.");
                if(set.checkMissingAdvancements(p).isEmpty()) {
                    Bukkit.getLogger().log(Level.INFO, "Other criteria met. Running commands");
                    set.runCommands(p);
                }
            }
        }
    }
}
