package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;

public class Friends {
    /**
     * Translates a config message in one action
     * @param key the key to get message from config
     * @return the color translated key
     */
    private static String a(String key){
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        return ColorTranslator.translateColorCodes(cf.getString(key));
    }

    public static TextComponent makeAcceptButton(String id){
        net.md_5.bungee.api.chat.TextComponent btn = new net.md_5.bungee.api.chat.TextComponent(a("messages.commands.friend.buttons.accept"));
        btn.setColor(ChatColor.GREEN);
        btn.setBold(true);
        ClickEvent event = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/friend accept " + id);
        btn.setClickEvent(event);
        return btn;
    }

    public static TextComponent makeDenyButton(String id){
        net.md_5.bungee.api.chat.TextComponent btn = new net.md_5.bungee.api.chat.TextComponent(a("messages.commands.friend.buttons.deny"));
        btn.setColor(ChatColor.RED);
        btn.setBold(true);
        ClickEvent event = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/friend deny " + id);
        btn.setClickEvent(event);
        return btn;
    }

    public static TextComponent makeCancelButton(String id){
        net.md_5.bungee.api.chat.TextComponent btn = new net.md_5.bungee.api.chat.TextComponent(a("messages.commands.friend.buttons.cancel"));
        btn.setColor(ChatColor.GOLD);
        btn.setBold(true);
        ClickEvent event = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/friend cancel " + id);
        btn.setClickEvent(event);
        return btn;
    }
    public static TextComponent makeCoordsButton(String friendName){
        net.md_5.bungee.api.chat.TextComponent btn = new net.md_5.bungee.api.chat.TextComponent(a("messages.commands.friend.buttons.coords"));
        btn.setColor(ChatColor.YELLOW);
        btn.setBold(true);
        ClickEvent event = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/friend coords " + friendName);
        btn.setClickEvent(event);
        return btn;
    }

    public static TextComponent makeNoneText(String hoverText){
        net.md_5.bungee.api.chat.TextComponent noneText = new net.md_5.bungee.api.chat.TextComponent(a("messages.commands.friend.buttons.none-text"));
        noneText.setColor(ChatColor.RED);
        noneText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).color(ChatColor.RED).create()));
        return noneText;
    }

    public static TextComponent makeSeeArmorButton(String friendName){
        net.md_5.bungee.api.chat.TextComponent btn = new net.md_5.bungee.api.chat.TextComponent(a("messages.commands.friend.buttons.see-armor"));
        btn.setColor(ChatColor.YELLOW);
        btn.setBold(true);
        ClickEvent event = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/friend armor " + friendName);
        btn.setClickEvent(event);
        return btn;
    }

    public static TextComponent makeInfoButton(String friendName){
        net.md_5.bungee.api.chat.TextComponent btn = new net.md_5.bungee.api.chat.TextComponent(a("messages.commands.friend.buttons.info"));
        btn.setColor(ChatColor.AQUA);
        btn.setBold(true);
        ClickEvent event = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/friend info " + friendName);
        btn.setClickEvent(event);
        return btn;
    }
}
