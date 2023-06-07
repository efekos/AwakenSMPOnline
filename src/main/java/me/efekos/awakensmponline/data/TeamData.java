package me.efekos.awakensmponline.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamData {
    private UUID id;
    private String name;
    private String displayName;
    private String description;
    private List<UUID> members;
    private UUID owner;

    public TeamData(UUID id, String name) {
        this(id,name,name,"Another brand new team!",new ArrayList<>(),null);
    }

    public TeamData(UUID id, String name, String displayName) {
        this(id,name,displayName,"Another brand new team!",new ArrayList<>(),null);
    }

    public TeamData(UUID id, String name, String displayName, String description) {
        this(id,name,displayName,description,new ArrayList<>(),null);
    }

    public TeamData(UUID id, String name, String displayName, String description, List<UUID> members) {
        this(id,name,displayName,description,members,null);
    }

    public TeamData(UUID id, String name, String displayName, String description, List<UUID> members, UUID owner) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.members = members;
        this.owner = owner;
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

    public void setId(UUID id) {
        this.id = id;
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
