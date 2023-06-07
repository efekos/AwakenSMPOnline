package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.commands.AwakenSMP;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "reloadconfig",permission = "awakensmp.cmd.reloadconfig",description = "Reloads the config")
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
        LangConfig.reload();
        AwakenSMPOnline.getPlugin().reloadConfig();
        PlayerDataManager.load();
        RequestDataManager.load();
        TeamDataManager.load();
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.reloadconfig.success")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        onPlayerUse((Player) sender,args);
    }

    public Reloadconfig(@NotNull String name) {
        super(name);
    }

    public Reloadconfig(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
