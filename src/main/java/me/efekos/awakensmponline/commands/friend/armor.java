package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.Friend;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.menus.ArmorMenu;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class armor extends SubCommand {
    public armor() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "armor";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-fr-arm");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/friend armor " + cf.getString(p + "player");
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
        PlayerData pData = PlayerDataManager.getDataFromUniqueId(p.getUniqueId());
        if(args.length == 1) {
            p.sendMessage(a("messages.commands.friend.generic.no-name"));
            return;
        }
        PlayerData friendPdata = PlayerDataManager.getDataFromName(args[1]);
        if(friendPdata == null) {
            p.sendMessage(a("messages.commands.friend.generic.no-real-name"));
            return;
        }
        Friend friendData = pData.findFriend(friendPdata.getPlayerUniqueId());
        if(friendData == null){
            p.sendMessage(a("messages.commands.friend.armor.no-fri"));
            return;
        }
        OfflinePlayer friendP = p.getServer().getOfflinePlayer(friendData.getUuid());
        if(friendP.isOnline()){
            Player friendPO = (Player) friendP;


            PlayerMenuUtility utility = new PlayerMenuUtility(p);
            utility.setData("invToOpen",friendPO.getInventory());
            try {
                ArmorMenu.class.getConstructor(PlayerMenuUtility.class).newInstance(utility).open();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            p.sendMessage(a("messages.commands.armor.no-onli"));
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
        if(args.length == 2) {
            PlayerData pData = PlayerDataManager.getDataFromUniqueId(player.getUniqueId());
            for (Friend friend : pData.getFriends()) {
                list.add(friend.getName());
            }
        }
        return list;
    }
}
