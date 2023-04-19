package me.efekos.awakensmponline.classes;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class Friend {
    UUID uuid;
    String name;
    boolean allowCoords;
    JSONLocation coords;
    boolean allowWorld;
    String world;
    boolean allowHealth;
    boolean allowHunger;
    boolean allowXP;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAllowCoords() {
        return allowCoords;
    }

    public void setAllowCoords(Boolean allowCoords) {
        this.allowCoords = allowCoords;
    }

    public Location getCoords() {
        return coords.toLocation(coords);
    }

    public void setCoords(Location coords) {
        this.coords = new JSONLocation(coords);
    }

    public Boolean getAllowWorld() {
        return allowWorld;
    }

    public void setAllowWorld(Boolean allowWorld) {
        this.allowWorld = allowWorld;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public boolean getAllowHealth() {
        return allowHealth;
    }

    public void setAllowHealth(boolean allowHealth) {
        this.allowHealth = allowHealth;
    }

    public boolean getAllowHunger() {
        return allowHunger;
    }

    public void setAllowHunger(boolean allowHunger) {
        this.allowHunger = allowHunger;
    }

    public boolean getAllowXP() {
        return allowXP;
    }

    public void setAllowXP(boolean allowXP) {
        this.allowXP = allowXP;
    }
}
