package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.Objects;

public class Craft implements Listener {
    @EventHandler
    void onCraft(CraftItemEvent e) {
        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        Configuration cf = plugin.getConfig();
        if (cf.getBoolean("announce.crafted") && e.getRecipe().getResult().getType() == Material.PLAYER_HEAD){
            Player p = (Player) e.getWhoClicked();
            Bukkit.broadcastMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.crafted-announcement"))
                    .replace("%player%",p.getName())));
        }
    }
}
