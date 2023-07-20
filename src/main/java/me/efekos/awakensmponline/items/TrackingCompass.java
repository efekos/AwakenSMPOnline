package me.efekos.awakensmponline.items;

import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.utils.DateUtils;
import me.efekos.simpler.annotations.Listen;
import me.efekos.simpler.annotations.RightClick;
import me.efekos.simpler.commands.translation.TranslateManager;
import me.efekos.simpler.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;

public class TrackingCompass extends CustomItem {
    private Player whoToTrack;
    private Player whoBelongs;
    private Date expiresAt;

    public TrackingCompass() {
    }

    public TrackingCompass(Player whoToTrack, Player whoBelongs, Date expiresAt) {
        this.whoToTrack = whoToTrack;
        this.whoBelongs = whoBelongs;
        this.expiresAt = expiresAt;
    }

    @Listen
    @RightClick
    public void onRightClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(new Date().after(expiresAt)){
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("items.tracking_compass.expired").replace("%date%",DateUtils.translateDate(expiresAt))));
            return;
        }
        if(!p.equals(whoBelongs)){
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("items.tracking_compass.not-owner")));
            return;
        }
        if(!whoToTrack.isOnline()){
            p.sendMessage(TranslateManager.translateColors(LangConfig.get("items.tracking_compass.not-online").replace("%player%",whoToTrack.getName())));
        }


        ItemStack stack = event.getItem();
        assert stack != null;
        CompassMeta meta = (CompassMeta) stack.getItemMeta();
        assert meta != null;
        meta.setLodestoneTracked(false);
        meta.setLodestone(whoToTrack.getLocation());
        stack.setItemMeta(meta);
        p.getInventory().setItemInMainHand(stack);

        p.sendMessage(TranslateManager.translateColors(LangConfig.get("items.tracking_compass.refreshed")));

    }

    @Override
    public @NotNull String getId() {
        return "tracking_compass";
    }

    @Override
    public @NotNull ItemMeta getDefaultMeta() {
        CompassMeta meta = (CompassMeta) new ItemStack(Material.COMPASS).getItemMeta();
        assert meta != null;
        meta.setLodestone(whoToTrack.getLocation());
        meta.setDisplayName(TranslateManager.translateColors(LangConfig.get("items.tracking_compass.name")));
        meta.setLore(Arrays.asList(
               TranslateManager.translateColors(LangConfig.get("items.tracking_compass.description.attach").replace("%player%",whoToTrack.getName())),
               TranslateManager.translateColors(LangConfig.get("items.tracking_compass.description.own").replace("%tracker%",whoBelongs.getName())),
                TranslateManager.translateColors(LangConfig.get("items.tracking_compass.description.expire").replace("%date%", DateUtils.translateDate(expiresAt)))
        ));

        return meta;
    }

    @Override
    public @NotNull Material getMaterial() {
        return Material.COMPASS;
    }
}
