package me.efekos.awakensmponline.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void log(Object message){
        Bukkit.getConsoleSender().sendMessage("[LOG] "+message);
    }

    public static void warn(Object message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[WARNING] "+message);
    }

    public static void error(Object message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] "+message);
    }

    public static void info(Object message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[INFO] "+message);
    }

    public static void success(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"[SUCCESS] "+message);
    }
}
