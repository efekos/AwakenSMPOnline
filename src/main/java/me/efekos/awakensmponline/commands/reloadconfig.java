package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.security.auth.login.Configuration;
import java.util.List;
import java.util.Objects;

public class reloadconfig extends SubCommand {
    public reloadconfig() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "reloadconfig";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-rc");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        return "/awakensmp reloadconfig";
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("awakensmp.cmd.reloadconfig")) {
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.no-perm"))));
                return;
            }
        }

        AwakenSMPOnline plugin = AwakenSMPOnline.getPlugin();
        plugin.reloadConfig();
        sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(plugin.getConfig().getString("messages.commands.reloadconfig"))));
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
