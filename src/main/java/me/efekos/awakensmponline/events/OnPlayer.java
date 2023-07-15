package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.config.GameConfig;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.AnimationType;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.awakensmponline.data.TempData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
import me.efekos.awakensmponline.utils.RecipeManager;
import me.efekos.awakensmponline.utils.AnimationManager;
import me.efekos.awakensmponline.utils.ParticleManager;
import me.efekos.simpler.commands.translation.TranslateManager;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class OnPlayer implements Listener {
    @EventHandler
    public void onPlayerDie(@NotNull PlayerDeathEvent e){
        Player p = e.getEntity();
        Player killer = p.getKiller();

        if(killer==null) return;
        if(p.hasPermission("awakensmp.god")) return;
        if(!killer.hasPermission("awakensmp.kill")) return;

        PlayerData playerData = PlayerDataManager.get(p.getUniqueId());

        playerData.setAlive(false);
        playerData.setRevived(false);

        p.setGameMode(GameMode.SPECTATOR);

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(p);
        meta.setDisplayName(p.getName());
        item.setItemMeta(meta);

        e.getDrops().add(item);

        PlayerDataManager.update(playerData.getUuid(),playerData);

        if (GameConfig.get().getBoolean("announcements.kills") && !p.getWorld().getGameRuleValue(GameRule.SHOW_DEATH_MESSAGES).booleanValue()) {
            Bukkit.broadcastMessage(TranslateManager.translateColors(LangConfig.get("announcements.killed").replace("%killer%",killer.getName()).replace("%victim%",p.getName())));
        }
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent e){
        Player p = e.getPlayer();
        PlayerData playerData = PlayerDataManager.get(p.getUniqueId());

        if(playerData.isAlive())return;

        if(GameConfig.get().getBoolean("when-dead.freeze"))
            e.setCancelled(true);


        if(GameConfig.get().getBoolean("when-dead.blind"))
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,999999,255,false,false,false));
    }

    @EventHandler
    public void onPlayerPlaceBlock(@NotNull BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block head = e.getBlockPlaced();
        ItemStack blockStack = e.getItemInHand();

        if(head.getType() != Material.PLAYER_HEAD) return;
        if(!p.hasPermission("awakensmp.revive")) return;

        Player pToRevive = Bukkit.getServer().getPlayerExact(blockStack.getItemMeta().getDisplayName());
        if(pToRevive==null) {
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.not-player").replace("%player%",blockStack.getItemMeta().getDisplayName())));
            return;
        }
        PlayerData data = PlayerDataManager.fetch(pToRevive.getUniqueId());
        if(data.isAlive()){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.not-dead").replace("%player%",pToRevive.getName())));
            return;
        }
        if(!pToRevive.isOnline()){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.not-online").replace("%player%", pToRevive.getName())));
            return;
        }

        head.setType(Material.AIR);
        pToRevive.teleport(head.getLocation().add(0.5,0,0.5));

        AnimationManager.playAnimation(pToRevive, AnimationType.NONE,player -> {
            data.setRevived(true);
            data.setAlive(true);
            PlayerDataManager.update(data.getUuid(),data);

            pToRevive.setGameMode(GameMode.SURVIVAL);
            pToRevive.removePotionEffect(PotionEffectType.BLINDNESS);
            if(GameConfig.get().getBoolean("revive-particles")){
                ParticleManager.spawnParticle(data.getParticleOptions(),pToRevive);
            }

            pToRevive.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.hey").replace("%player%",p.getName())));
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.done").replace("%player%",pToRevive.getName())));
            p.playSound(p.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,SoundCategory.PLAYERS,100,1);

            if(GameConfig.get().getBoolean("announcements.revives")){
                Bukkit.broadcastMessage(TranslateManager.translateColors(LangConfig.get("announcements.revived")
                        .replace("%reviver%",p.getName())
                        .replace("%revived%",pToRevive.getName())
                        .replace("%head-name%",TranslateManager.translateColors(LangConfig.get("items.revive_head.name")))
                ));
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent e){
        Player p = e.getPlayer();
        PlayerData data = PlayerDataManager.fetch(p.getUniqueId());

        if(data.getNotifications().size()>0){
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.list").replace("%count%",data.getNotifications().size()+"")));

            data.getNotifications().forEach(notification -> {

                OfflinePlayer player = Bukkit.getOfflinePlayer((UUID) notification.get("player"));

                switch (notification.getType()){
                    case REVIVED:
                        p.sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.revived").replace("%player%",player.getName())));
                        break;
                    case FRIEND_ACCEPTED:
                        p.sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.friend.accepted").replace("%player%",player.getName())));
                        break;
                    case FRIEND_DENIED:
                        p.sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.friend.denied").replace("%player%",player.getName())));
                        break;
                    case FRIEND_REQUESTED:
                        p.sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.friend.requested").replace("%player%",player.getName())));
                        break;
                }

            });

            data.setNotifications(new ArrayList<>());
            PlayerDataManager.update(data.getUuid(),data);
        }
    }

    @EventHandler
    public void onPlayerCraft(@NotNull CraftItemEvent e){
        if(!e.getRecipe().equals(RecipeManager.getLastLoadedRecipe())) return;
        if(!GameConfig.get().getBoolean("announcements.crafts")) return;

        Bukkit.broadcastMessage(TranslateManager.translateColors(LangConfig.get("announcements.head-crafted")
                .replace("%player%",e.getWhoClicked().getName())
                .replace("%head-name%",TranslateManager.translateColors(LangConfig.get("items.revive_head.name")))
        ));
    }

    @EventHandler
    public void onPlayerChat(@NotNull AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        PlayerData pData = PlayerDataManager.fetch(p);

        if(TempData.get(p,"teamchat")==null) TempData.set(p,"teamchat",false);

        if(!(Boolean)TempData.get(p,"teamchat")) return;
        if(pData.getCurrentTeam()==null)return;

        TeamData team = TeamDataManager.get(pData.getCurrentTeam());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if(offlinePlayer.isOnline()){
                offlinePlayer.getPlayer().sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.team.chat")
                        .replace("%player%",p.getName())
                        .replace("%message%",e.getMessage())
                ));
            }
        });

        e.setCancelled(true);
    }
}
