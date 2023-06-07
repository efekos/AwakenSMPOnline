package me.efekos.awakensmponline.data;

import java.util.HashMap;

public class WaitingNotification {
    private final NotificationType type;
    private final HashMap<String,Object> data = new HashMap<>();

    public WaitingNotification(NotificationType type) {
        this.type = type;
    }

    public NotificationType getType() {
        return type;
    }

    public void set(String key,Object value){
        data.put(key, value);
    }

    public Object get(String key){
        return data.get(key);
    }
}
