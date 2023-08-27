package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.data.TempData;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "togglechat",description = "Switch to team chat channel!",permission = "awakensmp.command.team.togglechat")
public class ToggleChat extends SubCommand {

    public ToggleChat(@NotNull String name) {
        super(name);
    }

    public ToggleChat(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Team.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        if(TempData.get(player,"teamchat")==null) TempData.set(player,"teamchat",false);

        if((Boolean) TempData.get(player,"teamchat")){
            TempData.set(player.getUniqueId(),"teamchat",false);
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.togglechat.disabled","&eSwitched to general chat!")));
        } else {
            TempData.set(player.getUniqueId(),"teamchat",true);
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.togglechat.enabled","&eSwitched to team chat!")));
        }
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
