package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.menu.OptionsMenu;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.BaseCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "options",description = "Customize your reviving decorations!",playerOnly = true,permission = "awakensmp.options")
public class Options extends BaseCommand {
    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        MenuManager.Open(player, OptionsMenu.class);
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }

    public Options(@NotNull String name) {
        super(name);
    }

    public Options(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
