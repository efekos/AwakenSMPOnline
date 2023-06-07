package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.simpler.commands.translation.TranslateManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class ButtonManager {
    public static @NotNull TextComponent generateModifyToggleButton(String friendName, String option, boolean value){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.toggle")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend modify "+friendName+" "+option+" "+value));

        return button;
    }

    public static @NotNull TextComponent generateAcceptFriendButton(String reqId){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.accept")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend accept "+reqId));

        return button;
    }

    public static @NotNull TextComponent generateJoinTeamButton(String reqId){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.join")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:team join "+reqId));

        return button;
    }

    public static @NotNull TextComponent generateRejectTeamInviteButton(String reqId){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.reject")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:team reject "+reqId));

        return button;
    }

    public static @NotNull TextComponent generateDenyFriendButton(String reqId){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.deny")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend deny "+reqId));

        return button;
    }

    public static @NotNull TextComponent generateCancelFriendButton(String reqId){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.cancel")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend cancel "+reqId));

        return button;
    }

    public static @NotNull TextComponent generateRemoveButton(String p){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.remove")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend remove "+p));

        return button;
    }

    public static @NotNull TextComponent generateModifyButton(String p){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.modify")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend modify "+p));

        return button;
    }

    public static @NotNull TextComponent generateInventoryButton(String p ){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.inventory")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend inventory "+p));

        return button;
    }

    public static @NotNull TextComponent generateArmorButton(String p ){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.armor")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/awakensmponline:friend armor "+p));

        return button;
    }

    public static @NotNull TextComponent generateBackButton(String back){
        TextComponent button = new TextComponent(TranslateManager.translateColors(LangConfig.get("buttons.back")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,back));

        return button;
    }
}