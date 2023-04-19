package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.utils.Logger;
import me.efekos.awakensmponline.utils.SettingsMenu;
import me.kodysimpson.simpapi.SimpAPI;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class settings extends SubCommand {
    public settings() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "settings";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-opt");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/awakensmp settings " + cf.getString(p + "sett-option") + " " + cf.getString(p + "sett-value");
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();

            if(!p.hasPermission("awakensmp.cmd.settings")){
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.no-perm"))));
                return;
            }

            if (args.length == 3) {
                switch (args[1]) {
                    case "revive-particles":
                        if (args[2].equals("true") || args[2].equals("false"))
                            cf.set("revive-particles", Boolean.valueOf(args[2]));
                        break;
                    case "freeze-dead":
                        if (args[2].equals("true") || args[2].equals("false"))
                            cf.set("freeze-dead", Boolean.valueOf(args[2]));
                        break;
                    case "offline-revives":
                        if (args[2].equals("true") || args[2].equals("false"))
                            cf.set("offline-revives", Boolean.valueOf(args[2]));
                        break;
                    case "announce-slain":
                        if (args[2].equals("true") || args[2].equals("false"))
                            cf.set("announce.slain", Boolean.valueOf(args[2]));
                        break;
                    case "announce-craft":
                        if (args[2].equals("true") || args[2].equals("false"))
                            cf.set("announce.crafted", Boolean.valueOf(args[2]));
                        break;
                    case "announce-revive":
                        if (args[2].equals("true") || args[2].equals("false"))
                            cf.set("announce.revived", Boolean.valueOf(args[2]));
                        break;
                    case "use-default-recipe":
                        if (args[2].equals("true") || args[2].equals("false"))
                            cf.set("recipe.use-default", Boolean.valueOf(args[2]));
                        break;
                    case "head-name":
                        cf.set("recipe.head.name", args[2].replace("&sp", " "));
                        break;
                    case "head-desc":
                        cf.set("recipe.head.desc", args[2].replace("&sp", " "));
                        break;
                }
                AwakenSMPOnline.getPlugin().saveConfig();
                AwakenSMPOnline.getPlugin().saveDefaultConfig();
            }
            SettingsMenu.sendMenu(p);
        }
    }

    /**
     * @param player The player who ran the command
     * @param args   The args passed into the command when run
     * @return A list of arguments to be suggested for autocomplete
     */
    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            List<String> list = new ArrayList<>();
            list.add("offline-revives");
            list.add("revive-particles");
            list.add("freeze-dead");
            list.add("announce-slain");
            list.add("announce-craft");
            list.add("announce-revive");
            list.add("use-default-recipe");
            list.add("head-name");
            list.add("head-desc");
            return list;
        } else if (args.length == 3) {
            switch (args[1]) {
                case "offline-revives":
                case "revive-particles":
                case "freeze-dead":
                case "announce-slain":
                case "announce-craft":
                case "announce-revive":
                case "use-default-recipe":
                    if (getBooleanOptions().contains("true")) return getBooleanOptions();
                    break;
            }

        }
        return null;
    }

    private static List<String> getBooleanOptions(){
        List<String> list = new ArrayList<>();
        list.add("true");
        list.add("false");
        return list;
    }
}
