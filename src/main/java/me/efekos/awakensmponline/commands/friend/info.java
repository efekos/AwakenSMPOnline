package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.Friend;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.efekos.awakensmponline.menus.ArmorMenu;
import me.efekos.awakensmponline.utils.Friends;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class info extends SubCommand {
    public info() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "info";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-fr-inf");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/friend info " + cf.getString(p + "player");
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
            p.sendMessage(a("messages.commands.friend.info.no-fri"));
            return;
        }
        OfflinePlayer friendP = p.getServer().getOfflinePlayer(friendData.getUuid());
        p.sendMessage("----------" + friendData.getName() + "'s Info----------");
        if(friendP.isOnline()) {
            Player friendPO = (Player) friendP;
            friendData.setCoords(friendPO.getLocation());
            DeadPlayersJSON.updateData(pData.getPlayerUniqueId(),pData);

            TextComponent component = new TextComponent("" + friendPO.getLevel());
            component.setColor(ChatColor.GREEN);
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(Math.round(friendPO.getExp()) + "/" + friendPO.getExpToLevel()).color(ChatColor.GREEN).create()
            ));

            p.spigot().sendMessage(new TextComponent(a("messages.commands.friend.")),component);
            p.sendMessage("Health: " + Math.round(friendPO.getHealth() / 2) + "/10");
            p.sendMessage("Hunger: " + Math.round(friendPO.getFoodLevel() / 2) + "/10");
            p.spigot().sendMessage(new TextComponent("Armor: "),Friends.makeSeeArmorButton(friendData.getName()));
        }
        if(friendData.getAllowCoords()){
            p.sendMessage("coords: " + Math.round(friendData.getCoords().getX())+","+Math.round(friendData.getCoords().getY())+","+Math.floor(friendData.getCoords().getZ()));
        } else {
            p.spigot().sendMessage(new TextComponent("coords: "), Friends.makeNoneText("this player hided his coords to u"));
        }
        if(friendData.getAllowWorld()){
            p.sendMessage("World: " + friendData.getCoords().getWorld().getName());
        } else {
            p.spigot().sendMessage(new TextComponent("World: "), Friends.makeNoneText("this player hided his world to u"));
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
            PlayerData pData = DeadPlayersJSON.getDataFromUniqueId(player.getUniqueId());
            for (Friend friend : pData.getFriends()) {
                list.add(friend.getName());
            }
        }
        return list;
    }
}
