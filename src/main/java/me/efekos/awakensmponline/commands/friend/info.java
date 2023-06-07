package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.args.FriendArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.FriendModifications;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "info",description = "Information about your friends",permission = "awakensmp.command.friend.info")
public class Info extends SubCommand {
    public Info(@NotNull String name) {
        super(name);
    }

    public Info(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
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
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        me.efekos.awakensmponline.data.Friend friendData = data.getFriend(args[0]);
        if(friendData==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-friend").replace("%player%",args[0])));
            return;
        }
        OfflinePlayer offlineFriend = Bukkit.getOfflinePlayer(friendData.getPlayerId());
        if(!offlineFriend.isOnline()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-online").replace("%player%",offlineFriend.getName())));
        }
        Player friend = offlineFriend.getPlayer();
        FriendModifications modifications = friendData.getModifications();

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.info.header").replace("%player%",friend.getName())));

        if(modifications.isHealthAllowed()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.info.health").replace("%health%",friend.getHealth()+"").replace("%max%",friend.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()+"")));
        }

        if(modifications.isFoodAllowed()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.info.food")
                    .replace("%food%",friend.getFoodLevel()+"")
            ));
        }

        if(modifications.isExpAllowed()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.info.exp")
                    .replace("%exp%",friend.getExp()+"")
                    .replace("%level%",friend.getLevel()+"")
                    .replace("%percentage%",((friend.getExp()/friend.getTotalExperience())*100)+"")
                    .replace("%max%",friend.getTotalExperience()+"")
            ));
        }

        if(modifications.isLocationAllowed()){
            Location loc = friend.getLocation();
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.info.location")
                    .replace("%x%",loc.getBlockX()+"")
                    .replace("%y%",loc.getBlockY()+"")
                    .replace("%z%",loc.getBlockZ()+"")
            ));
        }

        if(modifications.isWorldAllowed()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.info.world")
                    .replace("%world%",friend.getWorld().getName())
            ));
        }

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.info.footer")));

    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
