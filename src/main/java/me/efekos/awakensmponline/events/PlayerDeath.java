package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.heads.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PlayerDeath implements Listener {
    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e) {
        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        Configuration cf = plugin.getConfig();
        Player p = e.getEntity();

        if (p.getKiller() != null && !p.hasPermission("awakensmp.god")) {
            ItemStack head = SkullCreator.itemFromUuid(p.getUniqueId());
            ItemMeta hM = head.getItemMeta();
            Objects.requireNonNull(hM).setDisplayName(p.getName());
            head.setItemMeta(hM);

            e.getDrops().add(head);
            p.setGameMode(GameMode.SPECTATOR);

            PlayerData pData = DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId());
            Objects.requireNonNull(pData).setIsDead(true);
            Objects.requireNonNull(pData).setDeadLocation(p.getLocation());
            DeadPlayersJSON.updateData(p.getUniqueId(),pData);

            if(cf.getBoolean("announce.slain") && Objects.equals(p.getWorld().getGameRuleValue(GameRule.SHOW_DEATH_MESSAGES), false)){
                Bukkit.broadcastMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.slain-announcement"))
                        .replace("%killer%", Objects.requireNonNull(e.getEntity().getKiller()).getName())
                        .replace("%victim%",p.getName())));
            }
        }
    }
}