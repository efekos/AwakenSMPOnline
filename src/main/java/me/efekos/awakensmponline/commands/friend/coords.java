package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.Friend;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.efekos.awakensmponline.utils.Friends;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class coords extends SubCommand {
    public coords() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "coords";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-fr-coo");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/friend coords " + cf.getString(p + "player");
    }

    /**
     * Translates a config message in one action
     * @param key the key to get message from config
     * @return the color translated key
     */
    private String a(String key){
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        return ColorTranslator.translateColorCodes(cf.getString(key));
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        PlayerData pData = DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId());
        if(args.length == 1) {
            p.sendMessage(a("messages.commands.friend.generic.no-name"));
            return;
        }
        PlayerData friendPdata = DeadPlayersJSON.getDataFromName(args[1]);
        if(friendPdata == null) {
            p.sendMessage(a("messages.commands.friend.generic.no-real-name"));
            return;
        }
        Friend friendData = pData.findFriend(friendPdata.getPlayerUniqueId());
        if(friendData == null){
            p.sendMessage(a("messages.commands.friend.coords.no-fri"));
            return;
        }
        if(!friendData.getAllowCoords()){
            p.sendMessage(a("messages.commands.friend.coords.hided"));
            return;
        }
        OfflinePlayer friendP = p.getServer().getOfflinePlayer(friendData.getUuid());
        if(friendP.isOnline()) {
            Player friendPO = (Player) friendP;
            friendData.setCoords(friendPO.getLocation());
            DeadPlayersJSON.updateData(pData.getPlayerUniqueId(),pData);
        }

        if(friendP.isOnline()){
            p.sendMessage(a("messages.commands.friend.coords.header").replace("%player%",p.getName()));
        } else {
            p.sendMessage(a("messages.commands.friend.coords.header-last").replace("%player%",p.getName()));
        }
        Location loc = friendData.getCoords();
        p.sendMessage(a("messages.commands.friend.coords.x").replace("%x%",Math.floor(loc.getX()) + ""));
        p.sendMessage(a("messages.commands.friend.coords.y").replace("%y%",Math.floor(loc.getY()) + ""));
        p.sendMessage(a("messages.commands.friend.coords.z").replace("%z%",Math.floor(loc.getZ()) + ""));
        if(friendData.getAllowWorld()){
            p.sendMessage(a("messages.commands.friend.coords.world").replace("%world%",loc.getWorld().getName()));
        } else {
            p.spigot().sendMessage(new TextComponent(a("messages.commands.friend.coords.world").replace("%world%",Friends.makeNoneText("sa").toLegacyText())));
        }
        p.sendMessage(a("messages.commands.friend.coords.footer"));
    }

    /**
     * @param player The player who ran the command
     * @param args   The args passed into the command when run
     * @return A list of arguments to be suggested for autocomplete
     */
    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 2) {
            PlayerData pData = DeadPlayersJSON.getDataFromUniqueId(player.getUniqueId());
            for (Friend friend : pData.getFriends()) {
                list.add(friend.getName());
            }
        }
        return list;
    }
}
