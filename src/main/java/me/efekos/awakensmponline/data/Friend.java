package me.efekos.awakensmponline.data;

import java.util.UUID;

public class Friend {
    private FriendModifications modifications;
    private UUID playerId;
    private String lastName;

    public Friend(FriendModifications modifications, UUID playerId, String lastName) {
        this.modifications = modifications;
        this.playerId = playerId;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public FriendModifications getModifications() {
        return modifications;
    }

    public void setModifications(FriendModifications modifications) {
        this.modifications = modifications;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
