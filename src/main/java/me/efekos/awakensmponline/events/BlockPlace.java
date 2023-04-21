package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.OfflineHeadsJSON;
import me.efekos.awakensmponline.utils.Particles;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class BlockPlace implements Listener {
    @EventHandler
    void onBlockPlace(BlockPlaceEvent e) {
        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        Configuration cf = plugin.getConfig();
        ItemMeta blockMeta = e.getItemInHand().getItemMeta();
        if (e.getItemInHand().getType() == Material.PLAYER_HEAD){
            if(PlayerDataManager.getDataFromName(Objects.requireNonNull(blockMeta).getDisplayName()) != null) {
                if(!Objects.requireNonNull(PlayerDataManager.getDataFromName(Objects.requireNonNull(blockMeta).getDisplayName())).isDead()){
                    Player p = e.getPlayer();
                    PlayerDataManager.fetch(p);
                    e.getBlockPlaced().breakNaturally();
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND,100,1);
                    p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.not-dead"))));
                    return;
                }
                PlayerData deadPlayerData = PlayerDataManager.getDataFromName(blockMeta.getDisplayName());
                if(Objects.requireNonNull(deadPlayerData).isDead()){
                    OfflinePlayer deadP = plugin.getServer().getOfflinePlayer(deadPlayerData.getPlayerUniqueId());
                    if(deadP.isOnline()){
                        Player deadPlayer = deadP.getPlayer();
                        Player p = e.getPlayer();
                        PlayerDataManager.fetch(Objects.requireNonNull(deadPlayer));

                        deadPlayerData.setIsDead(false);
                        PlayerDataManager.update(deadPlayerData.getPlayerUniqueId(),deadPlayerData);

                        Location locToTeleport = e.getBlockPlaced().getLocation();
                        locToTeleport.setX(locToTeleport.getX() + 0.5);
                        locToTeleport.setZ(locToTeleport.getZ() + 0.5);

                        Objects.requireNonNull(deadPlayer).setGameMode(GameMode.SURVIVAL);
                        deadPlayer.teleport(locToTeleport);
                        p.playSound(p.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,100,1);
                        e.getBlockPlaced().setType(Material.AIR);
                        if(cf.getBoolean("revive-particles")) {
                            Particles.Spawn(deadPlayer);
                        }


                        if (cf.getBoolean("announce.revived")){
                            Bukkit.broadcastMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.revived-announcement"))
                                    .replace("%reviver%",p.getName())
                                    .replace("%revived%",deadPlayer.getName())));
                        }
                    } else {
                        if(cf.getBoolean("offline-revives")) {
                            e.getBlockPlaced().setType(Material.AIR);
                            Location locationMain = e.getBlockPlaced().getLocation();
                            Player p = e.getPlayer();

                            ArmorStand hologram = (ArmorStand) e.getBlock().getWorld().spawnEntity(locationMain.add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                            hologram.setGravity(false);
                            hologram.setVisible(false);
                            hologram.setCustomName(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.offline-hologram"))
                                    .replace("%player%", Objects.requireNonNull(deadP.getName()))
                            ));
                            hologram.setCustomNameVisible(true);

                            Location locationBreak = e.getBlockPlaced().getLocation();
                            locationBreak.add(0, -1, 0);
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
                            OfflineHeadsJSON.createData(deadP.getUniqueId(), locationMain, hologram.getUniqueId(), locationBreak.getBlock().getType());
                            locationBreak.getBlock().setType(Material.BEDROCK);

                            deadPlayerData.setIsDead(false);
                            deadPlayerData.setIsOfflineReviving(true);
                            PlayerDataManager.update(deadPlayerData.getPlayerUniqueId(), deadPlayerData);
                        } else {
                            Player p = e.getPlayer();
                            e.getBlockPlaced().breakNaturally();
                            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,100,1);
                            p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.not-online"))));
                        }
                    } //isOnline's else

                } // getIsDead
            } else {
                Player p = e.getPlayer();
                e.getBlockPlaced().breakNaturally();
                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,100,1);
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.invalid-name"))));
            } // getDataFromName's else
        } // getType

    } // onBlockPlace
}