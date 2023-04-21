package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.Friend;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.classes.Request;
import me.efekos.awakensmponline.classes.RequestType;
import me.efekos.awakensmponline.files.PlayerDataManager;
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

public class add extends SubCommand {
    public add() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "add";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-fr-add");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/friend add " + cf.getString(p + "player");
    }

    /**
     * Translates a config message in one action
     * @param key the key to get message from config
     * @return the color translated key
     */
    private String a(String key){
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        return ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(key)));
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        Player p = (Player) sender;
        if (args.length == 1){p.sendMessage(a("messages.commands.friend.generic.no-id"));return;}

        PlayerData toPData = PlayerDataManager.getDataFromName(args[1]);
        PlayerData pData = PlayerDataManager.getDataFromUniqueId(p.getUniqueId());
        Player toP = p.getServer().getPlayer(Objects.requireNonNull(toPData).getPlayerUniqueId());

        if (toP == null) {p.sendMessage(a("messages.commands.friend.add.plr-notreal"));return;}
        if (!toP.isOnline()) {p.sendMessage(a("messages.commands.friend.add.plr-notonli"));return;}
        if(toP.equals(p)){p.sendMessage(a("messages.commands.friend.add.itself"));return;}
        for(Friend friend : pData.getFriends()){
            if(friend.getUuid() == toP.getUniqueId()){p.sendMessage(a("messages.commands.friend.add.already-friends"));return;}
        }

        Request wrongRequest1 = new Request(toP.getUniqueId(),p.getUniqueId(),"1234",RequestType.FRIEND);
        Request wrongRequest2 = new Request(p.getUniqueId(),toP.getUniqueId(),"1234",RequestType.FRIEND);

        if(RequestsJSON.getDataFromEquality(wrongRequest1) != null){p.sendMessage(a("messages.commands.friend.add.already-req-from"));return;}
        if (RequestsJSON.getDataFromEquality(wrongRequest2) != null) {p.sendMessage(a("messages.commands.friend.add.already-req-to"));return;}

        Request request = RequestsJSON.createData(p, toP, RequestType.FRIEND);

        p.sendMessage(a("messages.commands.friend.add.success"));
        p.spigot().sendMessage(Friends.makeCancelButton(request.getId()));

        toP.sendMessage(a("messages.commands.friend.add.sent").replace("%player%",p.getName()));
        toP.spigot().sendMessage(Friends.makeAcceptButton(request.getId()));
        toP.spigot().sendMessage(Friends.makeDenyButton(request.getId()));
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
            for (PlayerData playerData : PlayerDataManager.getAllData()) {
                list.add(playerData.getPlayerName());
            }
        }
        return list;
    }
}
