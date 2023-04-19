package me.efekos.awakensmponline.utils;

import java.util.UUID;

public class Requests {
    public static String makeRequestId(UUID id1,UUID id2){
        return id1.toString().substring(1,10) + id2.toString().substring(1,10) + Math.floor(Math.random()*10);
    }
}
