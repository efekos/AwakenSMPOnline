package me.efekos.awakensmponline.data;

import me.efekos.simpler.config.Storable;

import java.util.UUID;

public class Request implements Storable {
    private final UUID id = UUID.randomUUID();
    private RequestType type;
    private UUID sender;
    private UUID getter;
    private boolean done;

    @Override
    public UUID getUniqueId() {
        return id;
    }

    public Request(RequestType type, UUID sender, UUID getter) {
        this.type = type;
        this.sender = sender;
        this.getter = getter;
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public UUID getId() {
        return this.id;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getGetter() {
        return getter;
    }

    public void setGetter(UUID getter) {
        this.getter = getter;
    }
}