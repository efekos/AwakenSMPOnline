package me.efekos.awakensmponline.data;

import java.util.UUID;

/**
 * Represents one of the friend inside the friend list of a {@link PlayerData}.
 */
public class Friend {
    /**
     * Modifications that the owner of this friend will apply to this friend. That friend can't see something disabled here.
     */
    private FriendModifications modifications;
    /**
     * UUID of the {@link org.bukkit.entity.Player} whose this friend.
     */
    private UUID playerId;

    /**
     * Last recorded name of the {@link org.bukkit.entity.Player} entity that this friend controls.
     */
    private String lastName;

    /**
     * @param modifications Modifications that will be applied to this friend by it's owner.
     * @param playerId      UUID of the {@link org.bukkit.entity.Player} whose this friend.
     * @param lastName      Name of the player.
     */
    public Friend(FriendModifications modifications, UUID playerId, String lastName) {
        this.modifications = modifications;
        this.playerId = playerId;
        this.lastName = lastName;
    }

    /**
     * @return Last recorded name of the {@link org.bukkit.entity.Player} entity that this friend controls.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Changes the last name recorded from this friend's {@link org.bukkit.entity.Player} entity.
     *
     * @param lastName New name of the {@link org.bukkit.entity.Player} entity.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return Modifications that the owner of this friend will apply to this friend. That friend can't see something disabled here.
     */
    public FriendModifications getModifications() {
        return modifications;
    }

    /**
     * Changes the modifications applied to this friend.
     *
     * @param modifications New modifications
     */
    public void setModifications(FriendModifications modifications) {
        this.modifications = modifications;
    }

    /**
     * @return Id of the player
     */
    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Changes the player id
     *
     * @param playerId New player's UniqueId
     */
    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
