package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SettingsMenu {
    public static TextComponent getEoDText(Boolean bool, String configName){
        Configuration cf = AwakenSMPOnline.getPlugin().getConfig();
        String mainPath = "messages.commands.settings.";
        if(bool) {
            TextComponent message = new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "boolean-true"))));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmp settings " + configName + " false"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "boolean-disable")))).create()
            ));
            return message;
        }
        TextComponent message = new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "boolean-false"))));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmp settings " + configName + " true"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "boolean-enable")))).create()
        ));
        return message;
    }

    public static TextComponent getEditText(String configName){
        String mainPath = "messages.commands.settings.";
        Configuration cf = AwakenSMPOnline.getPlugin().getConfig();
        TextComponent message = new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "string"))));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/awakensmp settings " + configName + " " + cf.getString(mainPath + "string-desc-cmd")));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "string-desc")))).create()
        ));
        return message;
    }

    public static void sendMenu(Player p){
        Configuration cf = AwakenSMPOnline.getPlugin().getConfig();
        String mainPath = "messages.commands.settings.";
        p.sendMessage("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(cf.getString(mainPath + "header"))));

        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "freeze-dead"))) + " "), getEoDText(cf.getBoolean("freeze-dead"),"freeze-dead"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "revive-particles"))) + " "), getEoDText(cf.getBoolean("revive-particles"),"revive-particles"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "offline-revives"))) + " "), getEoDText(cf.getBoolean("offline-revives"), "offline-revives"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "announce-slain"))) + " "), getEoDText(cf.getBoolean("announce.slain"), "announce-slain"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "announce-craft"))) + " "), getEoDText(cf.getBoolean("announce.crafted"), "announce-craft"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "announce-revive"))) + " "), getEoDText(cf.getBoolean("announce.revived"), "announce-revive"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "use-default"))) + " "), getEoDText(cf.getBoolean("recipe.use-default"), "use-default-recipe"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "head-name")) + cf.getString(mainPath + "string-option-suffix") + " " + cf.getString("recipe.head.name") + " ")), getEditText("head-name"));
        p.spigot().sendMessage(new TextComponent(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(mainPath + "head-desc")) + cf.getString(mainPath + "string-option-suffix") + " " + cf.getString("recipe.head.desc") + " ")), getEditText("head-desc"));

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',Objects.requireNonNull(cf.getString(mainPath + "footer"))));
    }
}
