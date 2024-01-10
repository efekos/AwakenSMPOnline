package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.FriendCommand;
import me.efekos.awakensmponline.commands.args.GotRequestUUIDArgument;
import me.efekos.awakensmponline.data.*;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.config.YamlConfig;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "deny", description = "Accept a friend request!", permission = "awakensmp.friend.deny")
public class FriendDenyCommand extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return FriendCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new GotRequestUUIDArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        YamlConfig lang = Main.LANG;
        try {
            UUID.fromString(args[0]);
        } catch (IllegalArgumentException e) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.deny.not-uuid", "&b%uuid% &cis not a valid UUID.").replace("%uuid%", args[0])));
            return;
        }
        Request req = Main.REQUEST_DATA.get(UUID.fromString(args[0]));
        if (req == null) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.deny.not-req", "&cThere is no request with id &b%uuid%&c.").replace("%uuid%", args[0])));
            return;
        }
        if (!req.getGetter().equals(player.getUniqueId())) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.deny.not-urs", "&cThis request was not sent to you.")));
            return;
        }
        if (!req.getType().equals(RequestType.FRIEND)) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.deny.not-friend", "&cThis is not a friend request.")));
            return;
        }
        // there is a friend request sent to us.
        OfflinePlayer offlineNewFriend = Bukkit.getOfflinePlayer(req.getSender());

        req.setDone(true);
        Main.REQUEST_DATA.delete(req.getId());

        player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.deny.done", "&aSuccessfully denied &b%player%&a''s friend request!").replace("%player%", offlineNewFriend.getName())));

        if (!offlineNewFriend.isOnline()) {
            WaitingNotification notification = new WaitingNotification(NotificationType.FRIEND_DENIED);
            notification.set("player", player.getUniqueId());

            PlayerData newFriendData = Main.fetchPlayer(offlineNewFriend.getUniqueId());
            newFriendData.addNotification(notification);

            Main.PLAYER_DATA.update(newFriendData.getUuid(), newFriendData);
        } else {
            offlineNewFriend.getPlayer().sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.deny.hey", "&b%player% &edenied your friend request.").replace("%player%", player.getName())));
        }
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }

    public FriendDenyCommand(@NotNull String name) {
        super(name);
    }

    public FriendDenyCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
