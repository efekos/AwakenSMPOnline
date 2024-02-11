package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.FriendCommand;
import me.efekos.awakensmponline.commands.args.FriendArgument;
import me.efekos.awakensmponline.data.FriendModifications;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.config.YamlConfig;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "info", description = "Information about your friends", permission = "awakensmp.friend.info")
public class FriendInfoCommand extends SubCommand {
    public FriendInfoCommand(@NotNull String name) {
        super(name);
    }

    public FriendInfoCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return FriendCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new FriendArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        me.efekos.awakensmponline.data.Friend friendData = data.getFriend(args[0]);
        YamlConfig lang = Main.LANG;

        if (friendData == null) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.not-friend", "&b%player% &cis not your friend.'").replace("%player%", args[0])));
            return;
        }
        OfflinePlayer offlineFriend = Bukkit.getOfflinePlayer(friendData.getPlayerId());
        if (!offlineFriend.isOnline()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.not-online", "&b%player% &cis not online.").replace("%player%", offlineFriend.getName())));
        }
        Player friend = offlineFriend.getPlayer();
        FriendModifications modifications = friendData.getModifications();

        player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.info.header", "&5----------&d%player%''s Information&5----------").replace("%player%", friend.getName())));

        if (modifications.isHealthAllowed()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.info.health", "&dHealth: &c%health%/%max%").replace("%health%", friend.getHealth() + "").replace("%max%", friend.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "")));
        }

        if (modifications.isFoodAllowed()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.info.food", "&dHunger: &#945623%food%")
                    .replace("%food%", friend.getFoodLevel() + "")
            ));
        }

        if (modifications.isExpAllowed()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.info.exp", "&dEXP: &a%exp%&2/&a%max% &2(&a%%percentage%&2) &aLevel %level%")
                    .replace("%exp%", friend.getExp() + "")
                    .replace("%level%", friend.getLevel() + "")
                    .replace("%percentage%", ((friend.getExp() / friend.getTotalExperience()) * 100) + "")
                    .replace("%max%", friend.getTotalExperience() + "")
            ));
        }

        if (modifications.isLocationAllowed()) {
            Location loc = friend.getLocation();
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.info.location", "&dLocation: %x%&5,&d%y%&5,&d%z%")
                    .replace("%x%", loc.getBlockX() + "")
                    .replace("%y%", loc.getBlockY() + "")
                    .replace("%z%", loc.getBlockZ() + "")
            ));
        }

        if (modifications.isWorldAllowed()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.info.world", "&dWorld: &a%world%")
                    .replace("%world%", friend.getWorld().getName())
            ));
        }

        player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.info.footer", "&5------------------------------------------")));

    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
