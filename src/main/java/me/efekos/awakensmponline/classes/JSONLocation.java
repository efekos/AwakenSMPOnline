package me.efekos.awakensmponline.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class JSONLocation {
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float pitch;
    private Float yaw;

    public JSONLocation(Location l) {
        this.world = Objects.requireNonNull(l.getWorld()).getName();
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        this.pitch = l.getPitch();
        this.yaw = l.getYaw();
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Float getPitch() {
        return pitch;
    }

    public void setPitch(Float pitch) {
        this.pitch = pitch;
    }

    public Float getYaw() {
        return yaw;
    }

    public void setYaw(Float yaw) {
        this.yaw = yaw;
    }

    public Location toLocation(JSONLocation jsloc){
        return new Location(Bukkit.getWorld(jsloc.getWorld()),jsloc.getX(),jsloc.getY(),jsloc.getZ(),jsloc.getYaw(),jsloc.getPitch());
    }
}
