package me.efekos.awakensmponline.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void log(Object message){
        Bukkit.getConsoleSender().sendMessage("[AwakenSMPOnline] [LOG] "+message);
    }

    public static void warn(Object message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[AwakenSMPOnline] [WARNING] "+message);
    }

    public static void error(Object message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[AwakenSMPOnline] [ERROR] "+message);
    }

    public static void info(Object message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[AwakenSMPOnline] [INFO] "+message);
    }

    public static void success(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"[AwakenSMPOnline] [SUCCESS] "+message);
    }
}
