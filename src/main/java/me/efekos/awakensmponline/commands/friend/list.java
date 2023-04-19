package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.Friend;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.classes.Request;
import me.efekos.awakensmponline.classes.RequestType;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.efekos.awakensmponline.files.RequestsJSON;
import me.efekos.awakensmponline.utils.Friends;
import me.kodysimpson.simpapi.command.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class list extends SubCommand {
    public list() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "list";
    }

    /**
     * @return The aliases that can be used for this command. Can be null
     */
    @Override
    public List<String> getAliases() {
        return null;
    }

    /**
     * @return A description of what the subcommand does to be displayed
     */
    @Override
    public String getDescription() {
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-fr-lst");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        return "/friend list";
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        PlayerData pData = DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId());

        p.sendMessage("----Friends----");
        for (Friend friend : pData.getFriends()){
            p.spigot().sendMessage(
                    new TextComponent(friend.getName() + " "),
                    Friends.makeCoordsButton(friend.getName()),
                    Friends.makeInfoButton(friend.getName())
                    );
        }
        p.sendMessage("------------------");
    }

    /**
     * @param player The player who ran the command
     * @param args   The args passed into the command when run
     * @return A list of arguments to be suggested for autocomplete
     */
    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
