package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.args.GotRequestUUIDArgument;
import me.efekos.awakensmponline.data.*;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.config.Config;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "accept",description = "Accept a friend request!",permission = "awakensmp.friend.accept")
public class Accept extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return Friend.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new GotRequestUUIDArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        Config lang = Main.LANG;
        try {
            UUID.fromString(args[0]);
        } catch (IllegalArgumentException e){
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.accept.not-uuid","&b%uuid% &cis not a valid UUID.").replace("%uuid%",args[0])));
            return;
        }
        Request req = Main.REQUEST_DATA.get(UUID.fromString(args[0]));
        if(req==null){
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.accept.not-req","&cThere is no request with id &b%uuid%&c.").replace("%uuid%",args[0])));
            return;
        }
        if(!req.getGetter().equals(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.accept.not-urs","&cThis request was not sent to you.")));
            return;
        }
        if(!req.getType().equals(RequestType.FRIEND)){
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.accept.not-friend","&cThis is not a friend request.")));
            return;
        }
        // there is a friend request sent to us.
        OfflinePlayer offlineNewFriend = Bukkit.getOfflinePlayer(req.getSender());

        PlayerDataManager.makeFriends(player,offlineNewFriend);
        Main.REQUEST_DATA.delete(req.getId());

        player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.accept.done","&aSuccessfully accepted &b%player%&a''s friend request!").replace("%player%",offlineNewFriend.getName())));

        if(offlineNewFriend.isOnline()){
            offlineNewFriend.getPlayer().sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.accept.hey","&b%player% &aaccepted your friend request!").replace("%player%",player.getName())));
        } else {
            WaitingNotification notification = new WaitingNotification(NotificationType.FRIEND_ACCEPTED);
            notification.set("player",player.getUniqueId());

            PlayerData newFriendData = Main.fetchPlayer(offlineNewFriend.getUniqueId());
            newFriendData.addNotification(notification);

            Main.PLAYER_DATA.update(newFriendData.getUuid(),newFriendData);
        }
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }

    public Accept(@NotNull String name) {
        super(name);
    }

    public Accept(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
