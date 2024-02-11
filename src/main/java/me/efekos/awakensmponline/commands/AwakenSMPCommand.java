package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.awakensmp.AwakenDeadPlayersCommand;
import me.efekos.awakensmponline.commands.awakensmp.AwakenReloadConfigCommand;
import me.efekos.awakensmponline.commands.awakensmp.AwakenReviveCommand;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "awakensmp", description = "All awakensmp commands")
public class AwakenSMPCommand extends CoreCommand {
    public AwakenSMPCommand(@NotNull String name) {
        super(name);
    }

    public AwakenSMPCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull ArrayList<Class<? extends SubCommand>> getSubs() {
        ArrayList<Class<? extends SubCommand>> classes = new ArrayList<>();
        classes.add(AwakenReviveCommand.class);
        classes.add(AwakenDeadPlayersCommand.class);
        classes.add(AwakenReloadConfigCommand.class);
        return classes;
    }

    @Override
    public void renderHelpList(CommandSender sender, List<SubCommand> subInstances) {
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.header", "&2----------&aHelp Menu&2----------")));
        subInstances.forEach(subCommand -> sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.format", "&b%syntax% &6- &e%description%")
                .replace("%syntax%", subCommand.getUsage())
                .replace("%description%", subCommand.getDescription())
        )));
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.awakensmp.help.footer", "&2-----------------------------")));
    }
}
