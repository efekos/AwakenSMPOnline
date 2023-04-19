package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class PlayerTeleport implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        Configuration cf = plugin.getConfig();
        Player p = e.getPlayer();
        DeadPlayersJSON.fetchData(p);
        if (Objects.requireNonNull(DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId())).isDead() && cf.getBoolean("freeze-dead")){
            e.setCancelled(true);
        }
    }
}
