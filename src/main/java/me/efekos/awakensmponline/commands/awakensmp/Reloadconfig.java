package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.AwakenSMP;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "reloadconfig",permission = "awakensmp.reloadconfig",description = "Reloads the config")
public class Reloadconfig extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return AwakenSMP.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        Main.LANG.reload();
        Main.GAME.reload();
        PlayerDataManager.load();
        RequestDataManager.load();
        TeamDataManager.load();
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.reloadconfig.success","&aSuccessfully reloaded the config!")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        Main.GAME.reload();
        Main.LANG.reload();
        Main.getInstance().reloadConfig();
        PlayerDataManager.load();
        RequestDataManager.load();
        TeamDataManager.load();
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.reloadconfig.success","&aSuccessfully reloaded the config!")));
    }

    public Reloadconfig(@NotNull String name) {
        super(name);
    }

    public Reloadconfig(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
