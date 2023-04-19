package me.efekos.awakensmponline.classes;

import java.util.UUID;

public class Request {
    UUID from;
    UUID to;
    String id;
    RequestType type;

    public Request(UUID from, UUID to, String id, RequestType type) {
        this.from = from;
        this.to = to;
        this.id = id;
        this.type = type;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equalsWithoutId(Request request){
        if(request.getType() == type){
            if(request.getFrom() == from){
                if(request.getTo() == to){
                    return true;
                }
            }
        }
        return false;
    }
}
