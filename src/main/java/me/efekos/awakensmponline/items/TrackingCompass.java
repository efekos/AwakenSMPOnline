package me.efekos.awakensmponline.items;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.utils.DateUtils;
import me.efekos.simpler.items.*;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

public class TrackingCompass extends CustomItem {
    @SaveField("track")
    private String whoToTrack;
    @SaveField("belong")
    private String whoBelongs;
    @SaveField(value = "expire", fieldType = FieldType.LONG)
    private long expiresAt;

    public Player getWhoToTrack() {
        return Bukkit.getPlayer(UUID.fromString(whoToTrack));
    }

    public void setWhoToTrack(Player whoToTrack) {
        this.whoToTrack = whoToTrack.getUniqueId().toString();
    }

    public Player getWhoBelongs() {
        return Bukkit.getPlayer(UUID.fromString(whoBelongs));
    }

    public void setWhoBelongs(Player whoBelongs) {
        this.whoBelongs = whoBelongs.getUniqueId().toString();
    }

    public Date getExpiresAt() {
        return new Date(expiresAt);
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt.getTime();
    }

    private final Consumer<ItemStack> appearance = stack -> {
        stack.setType(Material.COMPASS);
        CompassMeta meta = (CompassMeta) new ItemStack(Material.COMPASS).getItemMeta();
        assert meta != null;
        meta.setLodestone(getWhoToTrack().getLocation());
        meta.setDisplayName(TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.name", "&eTracking Compass")));
        meta.setLore(Arrays.asList(
                TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.description.attach", "&6Attached to &d%player%").replace("%player%", getWhoToTrack().getName())),
                TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.description.own", "&6Owned by &d%tracker%").replace("%tracker%", getWhoBelongs().getName())),
                TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.description.expire", "&6Expires at &b%date%").replace("%date%", DateUtils.translateDate(getExpiresAt())))
        ));

        stack.setItemMeta(meta);
    };

    public TrackingCompass() {
        super(new NamespacedKey(Main.getInstance(), "tracking_compass"), stack -> {
        });
    }

    public TrackingCompass(Player whoToTrack, Player whoBelongs, Date expiresAt) {
        this();
        setWhoToTrack(whoToTrack);
        setWhoBelongs(whoBelongs);
        setExpiresAt(expiresAt);
        //TODO wait for library to run #setAppearance
    }

    @HandleEvent(HandleType.PICKUP)
    public void onRightClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (new Date().after(getExpiresAt())) {
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.expired", "&cThis compass is expired at &a%date%&c.").replace("%date%", DateUtils.translateDate(getExpiresAt()))));
            return;
        }
        if (!p.equals(getWhoBelongs())) {
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.not-owner", "&cYou are not the owner of this compass.")));
            return;
        }
        if (!getWhoToTrack().isOnline()) {
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.not-online", "&a%player% &cis not online right now.").replace("%player%", getWhoToTrack().getName())));
        }


        ItemStack stack = event.getItem();
        assert stack != null;
        CompassMeta meta = (CompassMeta) stack.getItemMeta();
        assert meta != null;
        meta.setLodestoneTracked(false);
        meta.setLodestone(getWhoToTrack().getLocation());
        stack.setItemMeta(meta);
        p.getInventory().setItemInMainHand(stack);

        p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("items.tracking_compass.refreshed", "&aRefreshed location!")));

    }
}
