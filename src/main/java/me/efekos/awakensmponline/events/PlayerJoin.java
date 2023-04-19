package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.OfflineHead;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.efekos.awakensmponline.files.OfflineHeadsJSON;
import me.efekos.awakensmponline.utils.Particles;
import me.efekos.awakensmponline.utils.UpdateChecker;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoin implements Listener {
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e){
        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        Player p = e.getPlayer();
        Configuration cf = plugin.getConfig();
        if(p.isOp()) {
            new UpdateChecker(plugin, 102573).getLatestVersion(version -> {
                if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                    p.getName();
                } else {
                    p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.update-available-player"))));
                }

            });
        }

        DeadPlayersJSON.fetchData(p);

        if(cf.getBoolean("offline-revives")) {
            PlayerData PlayerData = DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId());

            if(PlayerData.isInstantOfflineReviving()){
                p.teleport(p.getWorld().getSpawnLocation());
                p.setGameMode(GameMode.SURVIVAL);
                if(cf.getBoolean("revive-particles")) {
                    Particles.Spawn(p);
                }

                DeadPlayersJSON.updateData(PlayerData.getPlayerUniqueId(),PlayerData);
            }

            if (Objects.requireNonNull(PlayerData).isOfflineReviving()) {
                OfflineHead HeadData = OfflineHeadsJSON.getData(p.getUniqueId());
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(Objects.requireNonNull(HeadData).getLocation());
                if (cf.getBoolean("revive-particles")) {
                    Particles.Spawn(p);
                }
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.revive-notification"))));
                PlayerData.setIsOfflineReviving(false);
                DeadPlayersJSON.updateData(p.getUniqueId(), PlayerData);

                for (Entity entity : p.getWorld().getEntities()) {
                    if (entity.getUniqueId().equals(HeadData.getHologramId())) entity.remove();
                }
                Location replaceLocation = HeadData.getLocation().add(0, -1, 0);
                replaceLocation.getBlock().setType(HeadData.getBedrockBlock());

                OfflineHeadsJSON.deleteData(p.getUniqueId());
            }
        }
    }
}
