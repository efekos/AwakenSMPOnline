package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.args.FriendArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.items.TrackingCompass;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import me.efekos.simpler.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

@Command(name = "compass",description = "Get a compass leads to your friends",permission = "awakensmp.command.friend.compass")
public class Compass extends SubCommand {
    public Compass(@NotNull String name) {
        super(name);
    }

    public Compass(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Friend.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax().withArgument(new FriendArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        if(!data.friendsWith(args[0])){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-friend").replace("%player%",args[0])));
            return;
        }
        me.efekos.awakensmponline.data.Friend friendData = data.getFriend(args[0]);
        if(!friendData.getModifications().isCompassAllowed()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-allowed").replace("%player%", friendData.getLastName())));
            return;
        }
        OfflinePlayer offlineFriend = Bukkit.getOfflinePlayer(friendData.getPlayerId());
        if(!offlineFriend.isOnline()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-online").replace("%player%", offlineFriend.getName())));
            return;
        }
        Player friend = offlineFriend.getPlayer();

        Date date = new Date();
        date.setTime(date.getTime()+(1000*60*60));

        ItemManager.giveItem(player,new TrackingCompass(friend,player,date));
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.compass.done").replace("%player%",friend.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
