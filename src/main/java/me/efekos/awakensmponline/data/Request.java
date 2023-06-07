package me.efekos.awakensmponline.data;

import java.util.UUID;

public class Request {
    private UUID id;
    private RequestType type;
    private UUID sender;
    private UUID getter;
    private boolean done;

    public Request(UUID id, RequestType type, UUID sender, UUID getter) {
        this.id = id;
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
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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