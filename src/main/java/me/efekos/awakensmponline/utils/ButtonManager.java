package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.Main;
import me.efekos.simpler.translation.TranslateManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class ButtonManager {
    public static @NotNull TextComponent generateModifyToggleButton(String friendName, String option, boolean value) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.toggle", "&6[&eToggle&6]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend modify " + friendName + " " + option + " " + value));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.toggle-desc", "&eClick to toggle this option.")))));

        return button;
    }

    public static @NotNull TextComponent generateAcceptFriendButton(String reqId) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.accept", "&2[&aAccept&2]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend accept " + reqId));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.accept-desc-friend", "&aClick to accept this friend request.")))));


        return button;
    }

    public static @NotNull TextComponent generateJoinTeamButton(String reqId) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.join", "&2[&aJoin&2]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:team join " + reqId));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.join-desc", "&aClick to join this team.")))));


        return button;
    }

    public static @NotNull TextComponent generateRejectTeamInviteButton(String reqId) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.reject", "&4[&cReject&4]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:team reject " + reqId));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.reject-desc-team", "&cClick to reject this team invite")))));


        return button;
    }

    public static @NotNull TextComponent generateDenyFriendButton(String reqId) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.deny", "&4[&cDeny&4]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend deny " + reqId));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.deny-desc", "&cClick to deny this friend request")))));


        return button;
    }

    public static @NotNull TextComponent generateCancelFriendButton(String reqId) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.cancel", "&6[&eCancel&6]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend cancel " + reqId));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.cancel-desc", "&eClick to cancel this request.")))));

        return button;
    }

    public static @NotNull TextComponent generateRemoveButton(String p) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.remove", "&4[&cRemove&4]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend remove " + p));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.remove-desc", "&cClick to remove this friend.")))));

        return button;
    }

    public static @NotNull TextComponent generateModifyButton(String p) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.modify", "&9[&bModify&9]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend modify " + p));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.modify-desc", "&bClick to modify this friend.")))));


        return button;
    }

    public static @NotNull TextComponent generateInventoryButton(String p) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.inventory", "&6[&eInventory&6]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend inventory " + p));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.inventory-desc", "&eClick to see the inventory of this friend.")))));


        return button;
    }

    public static @NotNull TextComponent generateArmorButton(String p) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.armor", "&6[&eArmor&6]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/awakensmponline:friend armor " + p));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.armor-desc", "&eClick to see armor of this friend.")))));


        return button;
    }

    public static @NotNull TextComponent generateBackButton(String back) {
        TextComponent button = new TextComponent(TranslateManager.translateColors(Main.LANG.getString("buttons.back", "&6[&eBack&6]")));
        button.setBold(true);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, back));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(TranslateManager.translateColors(Main.LANG.getString("buttons.back-desc", "&eClick to go back.")))));


        return button;
    }
}