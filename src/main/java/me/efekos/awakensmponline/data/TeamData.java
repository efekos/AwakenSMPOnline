package me.efekos.awakensmponline.data;

import me.efekos.simpler.config.Storable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamData implements Storable {
    private final UUID id = UUID.randomUUID();
    private String name;
    private String displayName;
    private String description;
    private List<UUID> members;
    private UUID owner;

    public TeamData(String name) {
        this(name, name, "Another brand new team!", new ArrayList<>(), null);
    }

    public TeamData(String name, String displayName) {
        this(name, displayName, "Another brand new team!", new ArrayList<>(), null);
    }

    public TeamData(String name, String displayName, String description) {
        this(name, displayName, description, new ArrayList<>(), null);
    }

    public TeamData(String name, String displayName, String description, List<UUID> members) {
        this(name, displayName, description, members, null);
    }

    public TeamData(String name, String displayName, String description, List<UUID> members, UUID owner) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.members = members;
        this.owner = owner;
    }

    @Override
    public UUID getUniqueId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }
}
