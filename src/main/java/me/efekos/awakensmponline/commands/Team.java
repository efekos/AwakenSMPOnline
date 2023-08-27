package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.team.*;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "team",description = "All team commands",playerOnly = true)
public class Team extends CoreCommand {
    @Override
    public @NotNull ArrayList<Class<? extends SubCommand>> getSubs() {
        ArrayList<Class<? extends SubCommand>> classes = new ArrayList<>();
        classes.add(Create.class);classes.add(Delete.class);classes.add(Members.class);classes.add(My.class);classes.add(Join.class);classes.add(Reject.class);classes.add(Chat.class);
        classes.add(ToggleChat.class);classes.add(Leave.class);classes.add(Invite.class);classes.add(Leave.class);
        return classes;
    }

    @Override
    public void renderHelpList(CommandSender sender, List<SubCommand> subInstances) {
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.header","&2----------&aHelp Menu&2----------")));
        subInstances.forEach(subCommand -> {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.format","%syntax% - %description%")
                    .replace("%syntax%",subCommand.getUsage())
                    .replace("%description%",subCommand.getDescription())
            ));
        });

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.footer","&2-----------------------------")));
    }

    public Team(@NotNull String name) {
        super(name);
    }

    public Team(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
