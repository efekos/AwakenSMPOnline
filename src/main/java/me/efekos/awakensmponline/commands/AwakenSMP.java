package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.commands.awakensmp.Deadplayers;
import me.efekos.awakensmponline.commands.awakensmp.Reloadconfig;
import me.efekos.awakensmponline.commands.awakensmp.Revive;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "awakensmp",description = "All awakensmp commands")
public class AwakenSMP extends CoreCommand {
    public AwakenSMP(@NotNull String name) {
        super(name);
    }

    public AwakenSMP(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull ArrayList<Class<? extends SubCommand>> getSubs() {
        ArrayList<Class<?extends SubCommand>> classes = new ArrayList<>();
        classes.add(Revive.class);
        classes.add(Deadplayers.class);
        classes.add(Reloadconfig.class);
        return classes;
    }

    @Override
    public void renderHelpList(CommandSender sender, List<SubCommand> subInstances) {
        sender.sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("commands.awakensmp.help.header","&2----------&aHelp Menu&2----------")));
        subInstances.forEach(subCommand -> {
            sender.sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("commands.awakensmp.help.format","&b%syntax% &6- &e%description%")
                    .replace("%syntax%",subCommand.getUsage())
                    .replace("%description%",subCommand.getDescription())
            ));
        });
        sender.sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("commands.awakensmp.help.footer","&2-----------------------------")));
    }
}
