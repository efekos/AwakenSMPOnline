package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.team.*;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "team", description = "All team commands", playerOnly = true)
public class TeamCommand extends CoreCommand {
    public TeamCommand(@NotNull String name) {
        super(name);
    }

    public TeamCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull ArrayList<Class<? extends SubCommand>> getSubs() {
        ArrayList<Class<? extends SubCommand>> classes = new ArrayList<>();
        classes.add(TeamCreateCommand.class);
        classes.add(TeamDeleteCommand.class);
        classes.add(TeamMembersCommand.class);
        classes.add(TeamMyCommand.class);
        classes.add(TeamJoinCommand.class);
        classes.add(TeamRejectCommand.class);
        classes.add(TeamChatCommand.class);
        classes.add(TeamToggleCommand.class);
        classes.add(TeamLeaveCommand.class);
        classes.add(TeamInviteCommand.class);
        classes.add(TeamLeaveCommand.class);
        return classes;
    }

    @Override
    public void renderHelpList(CommandSender sender, List<SubCommand> subInstances) {
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.header", "&2----------&aHelp Menu&2----------")));
        subInstances.forEach(subCommand -> sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.format", "%syntax% - %description%")
                .replace("%syntax%", subCommand.getUsage())
                .replace("%description%", subCommand.getDescription())
        )));

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.footer", "&2-----------------------------")));
    }
}
