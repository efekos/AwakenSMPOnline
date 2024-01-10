package me.efekos.awakensmponline.data;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TempData {
    private static HashMap<UUID, HashMap<String, Object>> data = new HashMap<>();

    public static void set(UUID uuid, String key, Object value) {
        data.computeIfAbsent(uuid, k -> new HashMap<>());
        data.get(uuid).put(key, value);
    }

    public static Object get(UUID uuid, String key) {
        data.computeIfAbsent(uuid, k -> new HashMap<>());
        return data.get(uuid).get(key);
    }

    public static void set(Player player, String key, Object value) {
        set(player.getUniqueId(), key, value);
    }

    public static void set(OfflinePlayer player, String key, Object value) {
        set(player.getUniqueId(), key, value);
    }

    public static Object get(Player player, String key) {
        return get(player.getUniqueId(), key);
    }

    public static Object get(OfflinePlayer player, String key) {
        return get(player.getUniqueId(), key);
    }
}
