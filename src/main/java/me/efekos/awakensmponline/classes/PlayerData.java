package me.efekos.awakensmponline.classes;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerData {

    private UUID playerUniqueId;
    private String playerName;
    private Boolean isDead;
    private Boolean isOfflineReviving;
    private Boolean isInstantOfflineReviving;
    private CustomParticleOptions particleOptions;
    private JSONLocation deadLocation;
    private ArrayList<Friend> friends;

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public Friend findFriend(UUID id){
        for (Friend friend: friends){
            if(friend.getUuid().equals(id)){
                return friend;
            }
        }
        return null;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public void addFriend(Friend friend){
        this.friends.add(friend);
    }

    public void removeFriend(Friend friend){
        this.friends.remove(friend);
    }

    public CustomParticleOptions getParticleOptions() {
        return particleOptions;
    }

    public void setParticleOptions(CustomParticleOptions particleOptions) {
        this.particleOptions = particleOptions;
    }

    public Location getDeadLocation() {
        return deadLocation.toLocation(deadLocation);
    }

    public void setDeadLocation(Location deadLocation) {
        this.deadLocation = new JSONLocation(deadLocation);
    }

    public Boolean isInstantOfflineReviving() {
        return isInstantOfflineReviving;
    }

    public void setInstantOfflineReviving(Boolean instantOfflineReviving) {
        isInstantOfflineReviving = instantOfflineReviving;
    }

    public PlayerData(UUID playerUniqueId, String playerName, Boolean isDead, Boolean isOfflineReviving, Boolean isInstantOfflineReviving, CustomParticleOptions particleOptions, Location deadLocation,ArrayList<Friend> friends) {
        this.playerUniqueId = playerUniqueId;
        this.playerName = playerName;
        this.isDead = isDead;
        this.isOfflineReviving = isOfflineReviving;
        this.isInstantOfflineReviving = isInstantOfflineReviving;
        this.particleOptions = particleOptions;
        this.deadLocation = new JSONLocation(deadLocation);
        this.friends = friends;
    }

    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Boolean isDead() {
        return isDead;
    }

    public void setIsDead(Boolean dead) {
        isDead = dead;
    }

    public Boolean isOfflineReviving() {
        return isOfflineReviving;
    }

    public void setIsOfflineReviving(Boolean offlineReviving) {
        isOfflineReviving = offlineReviving;
    }

    public boolean equals(PlayerData obj) {
        return getPlayerUniqueId().equals(obj.getPlayerUniqueId());
    }
}
