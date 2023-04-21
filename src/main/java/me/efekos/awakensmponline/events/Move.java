package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.files.PlayerDataManager;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class Move implements Listener {
    @EventHandler
    void onMove(PlayerMoveEvent e){
        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        Configuration cf = plugin.getConfig();
        PlayerDataManager.fetch(e.getPlayer());
        if(Objects.requireNonNull(PlayerDataManager.getDataFromUniqueId(e.getPlayer().getUniqueId())).isDead() && cf.getBoolean("freeze-dead")){
            e.setCancelled(true);
        }
    }
}
