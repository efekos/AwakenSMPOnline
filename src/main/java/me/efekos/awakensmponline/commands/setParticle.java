package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.menus.ParticleMainMenu;
import me.efekos.awakensmponline.utils.Logger;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class setParticle extends SubCommand {
    public setParticle() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "particle";
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-prt");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        return "/smp particle";
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            PlayerDataManager.fetch(p);
            try {
                MenuManager.openMenu(ParticleMainMenu.class, p);
            } catch (MenuManagerException | MenuManagerNotSetupException e){
                e.printStackTrace();
                Logger.error("An error when trying to open menu at /smp particle");
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
