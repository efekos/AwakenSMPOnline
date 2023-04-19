package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.Friend;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.classes.Request;
import me.efekos.awakensmponline.classes.RequestType;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.efekos.awakensmponline.files.RequestsJSON;
import me.efekos.awakensmponline.utils.Friends;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class modify extends SubCommand {
    public modify() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "modify";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-fr-mod");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/friend mod " + cf.getString(p + "player");
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
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        Player p = (Player) sender;
        if (args.length == 1){
            p.sendMessage(a("messages.commands.friend.generic.no-name"));
            return;
        }

        PlayerData toPData = DeadPlayersJSON.getDataFromName(args[1]);
        PlayerData pData = DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId());
        Player toP = p.getServer().getPlayer(Objects.requireNonNull(toPData).getPlayerUniqueId());

        if(toPData.findFriend(p.getUniqueId()) == null){
            p.sendMessage(a("messages.commands.friend.modify.no-fri"));
            return;
        }
        if(!toP.isOnline()){
            p.sendMessage(a("messages.commands.friend.modify.itself"));
        }
    }

    /**
     * @param player The player who ran the command
     * @param args   The args passed into the command when run
     * @return A list of arguments to be suggested for autocomplete
     */
    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 2) {
            for (PlayerData playerData : DeadPlayersJSON.getAllData()) {
                list.add(playerData.getPlayerName());
            }
        }
        return list;
    }
}
