package me.efekos.awakensmponline.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage("[AwakenSMPOnline] " + message);
    }
    public static void info(String message){
        Bukkit.getConsoleSender().sendMessage("[AwakenSMPOnline] " + ChatColor.BLUE + "[INFO] " + message);
    }
    public static void warn(String message){
        Bukkit.getConsoleSender().sendMessage("[AwakenSMPOnline] " + ChatColor.GOLD + "[WARNING] " + message);
    }
    public static void error(String message){
        Bukkit.getConsoleSender().sendMessage("[AwakenSMPOnline] " + ChatColor.RED + "[ERROR] " + message);
    }
    public static void success(String message){
        Bukkit.getConsoleSender().sendMessage("[AwakenSMPOnline] " + ChatColor.GREEN + "[SUCCESS] " + message);
    }
}