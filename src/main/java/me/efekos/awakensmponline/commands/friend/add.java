package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.*;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.awakensmponline.utils.ButtonManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.PlayerArgument;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "add",description = "add someone as your friend!",permission = "awakensmp.command.friend.add")
public class Add extends SubCommand {
    public Add(@NotNull String name) {
        super(name);
    }

    public Add(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Friend.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new PlayerArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        PlayerData otherData = PlayerDataManager.get(args[0]);
        if(otherData==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.add.not-player").replace("%player%",args[0])));
            return;
        }
        if(otherData.friendsWith(data.getUuid())){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.add.friend-already").replace("%player%",otherData.getName())));
            return;
        }
        if(otherData.equals(data)){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.add.urself")));
            return;
        }
        OfflinePlayer offlineOther = Bukkit.getOfflinePlayer(otherData.getUuid());
        if(!offlineOther.isOnline()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-online")));
            return;
        }
        if(RequestDataManager.exists(player.getUniqueId(),offlineOther.getUniqueId(),RequestType.FRIEND)){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.add.already-sent").replace("%player%",offlineOther.getName())));
            return;
        }

        Request req = new Request(UUID.randomUUID(), RequestType.FRIEND,player.getUniqueId(),offlineOther.getUniqueId());
        RequestDataManager.create(req);


        if(!offlineOther.isOnline()){
            WaitingNotification notification = new WaitingNotification(NotificationType.FRIEND_DENIED);
            notification.set("player",player.getUniqueId());

            PlayerData newFriendData = PlayerDataManager.fetch(offlineOther.getUniqueId());
            newFriendData.addNotification(notification);

            PlayerDataManager.update(newFriendData.getUuid(),newFriendData);
        } else {
            offlineOther.getPlayer().sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.add.other").replace("%player%",player.getName())));
            offlineOther.getPlayer().spigot().sendMessage(ButtonManager.generateAcceptFriendButton(req.getId().toString()),new TextComponent(" "),ButtonManager.generateDenyFriendButton(req.getId().toString()));
        }

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.add.done").replace("%player%",offlineOther.getName())));
        player.spigot().sendMessage(ButtonManager.generateCancelFriendButton(req.getId().toString()));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
