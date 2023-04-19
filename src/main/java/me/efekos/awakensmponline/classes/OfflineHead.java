package me.efekos.awakensmponline.classes;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class OfflineHead {
    private UUID revivingPlayer;
    private JSONLocation location;
    private Material bedrockBlock;
    private UUID hologramId;

    public UUID getRevivingPlayer() {
        return revivingPlayer;
    }

    public void setRevivingPlayer(UUID revivingPlayer) {
        this.revivingPlayer = revivingPlayer;
    }

    public Location getLocation() {
        return location.toLocation(location);
    }

    public Material getBedrockBlock() {
        return bedrockBlock;
    }

    public void setBedrockBlock(Material bedrockBlock) {
        this.bedrockBlock = bedrockBlock;
    }

    public JSONLocation getJSONLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = new JSONLocation(location);
    }

    public UUID getHologramId() {
        return hologramId;
    }

    public void setHologramId(UUID hologramId) {
        this.hologramId = hologramId;
    }

    public OfflineHead(UUID revivingPlayer, Location location, UUID hologramId, Material bedrockBlock) {
        this.revivingPlayer = revivingPlayer;
        this.location = new JSONLocation(location);
        this.hologramId = hologramId;
        this.bedrockBlock = bedrockBlock;
    }
}
