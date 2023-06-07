package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.args.GotRequestUUIDArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.*;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "accept",description = "Accept a friend request!",permission = "awakensmp.command.friend.accept")
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
        try {
            UUID.fromString(args[0]);
        } catch (IllegalArgumentException e){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.accept.not-uuid").replace("%uuid%",args[0])));
            return;
        }
        Request req = RequestDataManager.get(UUID.fromString(args[0]));
        if(req==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.accept.not-req").replace("%uuid%",args[0])));
            return;
        }
        if(!req.getGetter().equals(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.accept.not-urs")));
            return;
        }
        if(!req.getType().equals(RequestType.FRIEND)){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.accept.not-friend")));
            return;
        }
        // there is a friend request sent to us.
        OfflinePlayer offlineNewFriend = Bukkit.getOfflinePlayer(req.getSender());


        PlayerDataManager.makeFriends(player,offlineNewFriend);
        RequestDataManager.delete(req.getId());

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.accept.done").replace("%player%",offlineNewFriend.getName())));

        if(offlineNewFriend.isOnline()){
            offlineNewFriend.getPlayer().sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.accept.hey").replace("%player%",player.getName())));
        } else {
            WaitingNotification notification = new WaitingNotification(NotificationType.FRIEND_ACCEPTED);
            notification.set("player",player.getUniqueId());

            PlayerData newFriendData = PlayerDataManager.fetch(offlineNewFriend.getUniqueId());
            newFriendData.addNotification(notification);

            PlayerDataManager.update(newFriendData.getUuid(),newFriendData);
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
