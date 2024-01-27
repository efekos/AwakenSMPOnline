package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.data.AnimationType;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.awakensmponline.data.TempData;
import me.efekos.awakensmponline.utils.AnimationManager;
import me.efekos.awakensmponline.utils.ParticleManager;
import me.efekos.awakensmponline.utils.RecipeManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerEvents implements Listener {
    // simply player getting killed
    @EventHandler
    public void onPlayerDie(@NotNull PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = p.getKiller();

        if (killer == null) return;
        if (p.hasPermission("awakensmp.god")) return;
        if (!killer.hasPermission("awakensmp.kill")) return;

        PlayerData playerData = Main.fetchPlayer(p.getUniqueId());

        playerData.setAlive(false);
        playerData.setRevived(false);

        p.setGameMode(GameMode.SPECTATOR);

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(p);
        meta.setDisplayName(p.getName());
        item.setItemMeta(meta);

        e.getDrops().add(item);

        Main.PLAYER_DATA.update(playerData.getUuid(), playerData);

        if (Main.GAME.getBoolean("announcements.kills", false) && !p.getWorld().getGameRuleValue(GameRule.SHOW_DEATH_MESSAGES).booleanValue()) {
            Bukkit.broadcastMessage(TranslateManager.translateColors(Main.LANG.getString("announcements.killed", "&a%killer% &egot &a%victim%&e''s head!").replace("%killer%", killer.getName()).replace("%victim%", p.getName())));
        }
    }

    // checking dead players to make them freeze & blind
    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData playerData = Main.fetchPlayer(p.getUniqueId());

        if (playerData.isAlive()) return;

        if (Main.GAME.getBoolean("when-dead.freeze", true))
            e.setCancelled(true);


        if (Main.GAME.getBoolean("when-dead.blind", true))
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 255, false, false, false));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        Player player = e.getPlayer();
        if(!e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE))return;
        PlayerData playerData = Main.fetchPlayer(player.getUniqueId());

        if(!playerData.isAlive())e.setCancelled(true);
    }

    // making revive heads work
    @EventHandler
    public void onPlayerPlaceBlock(@NotNull BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block head = e.getBlockPlaced();
        ItemStack blockStack = e.getItemInHand();

        if (head.getType() != Material.PLAYER_HEAD) return;
        if (!p.hasPermission("awakensmp.revive")) return;

        Player pToRevive = Bukkit.getServer().getPlayerExact(blockStack.getItemMeta().getDisplayName());
        if (pToRevive == null) {
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-player", "&cThere is no one called &b%player%&c.").replace("%player%", blockStack.getItemMeta().getDisplayName())));
            return;
        }
        PlayerData data = Main.fetchPlayer(pToRevive.getUniqueId());
        if (data.isAlive()) {
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-dead", "&b%player% &cis not dead.").replace("%player%", pToRevive.getName())));
            return;
        }
        if (!pToRevive.isOnline()) {
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-online", "&b%player% &cis not online.").replace("%player%", pToRevive.getName())));
            return;
        }

        head.setType(Material.AIR);
        pToRevive.teleport(head.getLocation().add(0.5, 0, 0.5));

        AnimationManager.playAnimation(pToRevive, Main.GAME.getBoolean("revive-animations", false) ? data.getSelectedAnimation() : AnimationType.NONE, player -> {
            data.setRevived(true);
            data.setAlive(true);
            Main.PLAYER_DATA.update(data.getUuid(), data);

            pToRevive.setGameMode(GameMode.SURVIVAL);
            pToRevive.removePotionEffect(PotionEffectType.BLINDNESS);
            if (Main.GAME.getBoolean("revive-particles", true)) {
                ParticleManager.spawnParticle(data.getParticleOptions(), pToRevive);
            }

            pToRevive.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.hey", "&b%player% &arevived you!").replace("%player%", p.getName())));
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.done", "&aSuccessfully revived &b%player%&a!").replace("%player%", pToRevive.getName())));
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 100, 1);

            if (Main.GAME.getBoolean("announcements.revives", true)) {
                Bukkit.broadcastMessage(TranslateManager.translateColors(Main.LANG.getString("announcements.revived", "&a%reviver% &eused a %head-name% &eand revived &a%revived%&e!")
                        .replace("%reviver%", p.getName())
                        .replace("%revived%", pToRevive.getName())
                        .replace("%head-name%", TranslateManager.translateColors(Main.LANG.getString("items.revive_head.name", "&eRevive Head")))
                ));
            }
        });
    }

    // sending players notifications
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Main.fetchPlayer(p.getUniqueId());

        if (data.getNotifications().size() > 0) {
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("notifications.list", "&eYou have &b%count% &enew notifications:").replace("%count%", data.getNotifications().size() + "")));

            data.getNotifications().forEach(notification -> {

                OfflinePlayer player = Bukkit.getOfflinePlayer((UUID) notification.get("player"));

                switch (notification.getType()) {
                    case REVIVED:
                        p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("notifications.revived", "&b%player% &eRevived you!").replace("%player%", player.getName())));
                        break;
                    case FRIEND_ACCEPTED:
                        p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("notifications.friend.accepted", "&b%player% &eaccepted your friend request!").replace("%player%", player.getName())));
                        break;
                    case FRIEND_DENIED:
                        p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("notifications.friend.denied", "&b%player% &edenied your friend request!").replace("%player%", player.getName())));
                        break;
                    case FRIEND_REQUESTED:
                        p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("notifications.friend.requested", "&b%player% &esent you a friend request!").replace("%player%", player.getName())));
                        break;
                }

            });

            data.setNotifications(new ArrayList<>());
            Main.PLAYER_DATA.update(data.getUuid(), data);
        }
    }

    // make revive heads a working one & announce it
    @EventHandler
    public void onPlayerCraft(@NotNull CraftItemEvent e) {
        if (!e.getRecipe().equals(RecipeManager.getLastLoadedRecipe())) return;
        if (!Main.GAME.getBoolean("announcements.crafts", false)) return;

        Bukkit.broadcastMessage(TranslateManager.translateColors(Main.LANG.getString("announcements.head-crafted", "&a%player% &ejust crafted a %head-name%&e!")
                .replace("%player%", e.getWhoClicked().getName())
                .replace("%head-name%", TranslateManager.translateColors(Main.LANG.getString("items.revive_head.name", "&eRevive Head")))
        ));
    }

    // make team chats work
    @EventHandler
    public void onPlayerChat(@NotNull AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        PlayerData pData = Main.fetchPlayer(p.getUniqueId());

        if (TempData.get(p, "teamchat") == null) TempData.set(p, "teamchat", false);

        if (!(Boolean) TempData.get(p, "teamchat")) return;
        if (pData.getCurrentTeam() == null) return;

        TeamData team = Main.TEAM_DATA.get(pData.getCurrentTeam());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(TranslateManager.translateColors(Main.LANG.getString("notifications.team.chat", "5[&dTEAM CHAT&5] &e%player%&8: &a%message%")
                        .replace("%player%", p.getName())
                        .replace("%message%", e.getMessage())
                ));
            }
        });

        e.setCancelled(true);
    }
}
