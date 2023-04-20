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
        boolean debug = plugin.getDebug();
        Player p = event.getPlayer();
        Advancement a = event.getAdvancement();
        if(!p.getAdvancementProgress(a).isDone()) {
            return;
        }
        if (debug) {
            Bukkit.getLogger().log(Level.INFO, "Player " + p.getName() + " has completed advancement " + a.getKey());
        }

        for(Set set : plugin.getSets()) {
            if (debug) {
                Bukkit.getLogger().log(Level.INFO, "Checking if set " + set.getName() + " contains advancement");
            }
            if(set.containsAdvancement(a)) {
                if(debug) {
                    Bukkit.getLogger().log(Level.INFO, "Set " + set.getName() + " contains advancement. Checking other criteria.");
                }

                if(set.checkMissingAdvancements(p).isEmpty()) {
                    if(debug) {
                        Bukkit.getLogger().log(Level.INFO, "Other criteria met. Running commands");
                    }
                    set.runCommands(p);
                }
            }
        }
    }
}
