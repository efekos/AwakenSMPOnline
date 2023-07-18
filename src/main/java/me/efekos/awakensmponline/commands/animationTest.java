package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.data.AnimationType;
import me.efekos.awakensmponline.utils.AnimationManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.BaseCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "animationtest",playerOnly = true,description = "test animation")
public class animationTest extends BaseCommand {
    public animationTest(@NotNull String name) {
        super(name);
    }

    public animationTest(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        if(!player.getName().equals("efekos")) {
            player.sendMessage("You are not the right person for this!");
            return;
        }

        AnimationManager.playAnimation(player, AnimationType.BEAM,p -> {
            p.sendMessage("U should get out of the spec now");
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        sender.sendMessage("how did u see this wtf man");
    }
}
