package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.AwakenSMPCommand;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "reloadconfig", permission = "awakensmp.reloadconfig", description = "Reloads the config")
public class AwakenReloadConfigCommand extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return AwakenSMPCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        Main.LANG.reload();
        Main.GAME.reload();
        Main.PLAYER_DATA.load(PlayerData[].class);
        Main.TEAM_DATA.load(TeamData[].class);
        Main.REQUEST_DATA.load(Request[].class);
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.reloadconfig.success", "&aSuccessfully reloaded the config!")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        Main.GAME.reload();
        Main.LANG.reload();
        Main.PLAYER_DATA.load(PlayerData[].class);
        Main.TEAM_DATA.load(TeamData[].class);
        Main.REQUEST_DATA.load(Request[].class);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.reloadconfig.success", "&aSuccessfully reloaded the config!")));
    }

    public AwakenReloadConfigCommand(@NotNull String name) {
        super(name);
    }

    public AwakenReloadConfigCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
