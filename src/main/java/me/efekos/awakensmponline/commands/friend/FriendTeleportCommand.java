package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.AwakenSMPCommand;
import me.efekos.awakensmponline.commands.args.FriendArgument;
import me.efekos.awakensmponline.data.Friend;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "teleport", description = "Teleport to your friends!", permission = "awakensmp.friend.teleport")
public class FriendTeleportCommand extends SubCommand {
    public FriendTeleportCommand(@NotNull String name) {
        super(name);
    }

    public FriendTeleportCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return AwakenSMPCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new FriendArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        Friend friend = data.getFriend(args[0]);

        if (friend == null) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.not-friend", "&b%player% &cis not your friend.").replace("%player%", args[0])));
            return;
        }
        if (!friend.getModifications().isTeleportAllowed()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.not-allowed", "&b%player% &cdid not allow you to do that.").replace("%player%", friend.getLastName())));
            return;
        }
        OfflinePlayer offlineFriendP = Bukkit.getOfflinePlayer(friend.getPlayerId());
        if (!offlineFriendP.isOnline()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.not-online", "&b%player% &cis not online.").replace("%player%", friend.getLastName())));
            return;
        }
        Player friendP = offlineFriendP.getPlayer();

        player.teleport(friendP.getLocation());
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.teleport.success", "&aSuccessfully teleported to &b%player%&a!").replace("%player%", friendP.getName())));
        friendP.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.teleport.hey", "&b%player% &eteleported to you.").replace("%player%", friendP.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}