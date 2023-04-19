package me.efekos.awakensmponline.events;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.OfflineHead;
import me.efekos.awakensmponline.files.OfflineHeadsJSON;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKilled implements Listener {
    @EventHandler
    void onEntityKilled(EntityDeathEvent e){
        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        Configuration cf = plugin.getConfig();
        if(!cf.getBoolean("offline-revives")) return;
        if(e.getEntity().getType() == EntityType.ARMOR_STAND){
            for (OfflineHead data : OfflineHeadsJSON.getAllData()) {
                if(data.getHologramId().equals(e.getEntity().getUniqueId())){


                    ArmorStand hologram = (ArmorStand) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(),EntityType.ARMOR_STAND);
                    hologram.setGravity(false);
                    hologram.setVisible(false);
                    hologram.setCustomName(e.getEntity().getCustomName());
                    hologram.setCustomNameVisible(true);

                    data.setHologramId(hologram.getUniqueId());
                    OfflineHeadsJSON.updateData(data.getRevivingPlayer(),data);
                }
            }
        }
    }
}
