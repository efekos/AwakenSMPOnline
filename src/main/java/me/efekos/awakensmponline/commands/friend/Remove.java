package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.args.FriendArgument;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "remove",description = "Remove one of your friends",permission = "awakensmp.friend.remove")
public class Remove extends SubCommand {
    public Remove(@NotNull String name) {
        super(name);
    }

    public Remove(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Friend.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new FriendArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        me.efekos.awakensmponline.data.Friend friend = data.getFriend(args[0]);
        if(friend==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.not-friend","&b%player% &cis not your friend.").replace("%player%",args[0])));
        }
        data.getFriends().remove(friend);
        PlayerData friendsData = Main.fetchPlayer(friend.getPlayerId());
        friendsData.getFriends().remove(friendsData.getFriend(player.getUniqueId()));

        Main.PLAYER_DATA.update(data.getUuid(),data);
        Main.PLAYER_DATA.update(friendsData.getUuid(),friendsData);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.remove.done","&aSuccessfully removed &b%player% &afrom friends!").replace("%player%",friendsData.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
