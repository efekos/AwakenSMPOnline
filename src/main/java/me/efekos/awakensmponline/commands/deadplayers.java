package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class deadplayers extends SubCommand {
    public deadplayers() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "deadplayers";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-dp");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        return "/awakensmp deadplayers";
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        Configuration cf = AwakenSMPOnline.getPlugin().getConfig();
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("awakensmp.cmd.deadplayers")){
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.no-perm"))));
                return;
            }
        }

        List<PlayerData> deadPlayersList = DeadPlayersJSON.getAllData();
        sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.deadplayers.header"))));
        List<String> names = new ArrayList<>();

        for (PlayerData data : deadPlayersList){
            if(data.isDead()) names.add(data.getPlayerName());
        }

        if(names.size() == 0){
            sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.deadplayers.none"))));
        } else {
            for (String name : names) {
                sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.deadplayers.format"))
                        .replace("%player%",name)
                ));
            }
        }
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
