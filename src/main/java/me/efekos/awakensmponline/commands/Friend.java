package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.commands.friend.*;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "friend",playerOnly = true,description = "Friend commands!")
public class Friend extends CoreCommand {
    @Override
    public @NotNull ArrayList<Class<? extends SubCommand>> getSubs() {
        ArrayList<Class<? extends SubCommand>> classes = new ArrayList<>();
        classes.add(Info.class);classes.add(me.efekos.awakensmponline.commands.friend.List.class);classes.add(Teleport.class);classes.add(Add.class);classes.add(Accept.class);
        classes.add(Deny.class);classes.add(Cancel.class);classes.add(Remove.class);classes.add(Modify.class);classes.add(Inventory.class);classes.add(Armor.class);classes.add(Compass.class);
        return classes;
    }

    @Override
    public void renderHelpList(CommandSender sender, List<SubCommand> subInstances) {
        sender.sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("commands.awakensmp.help.header","&2----------&aHelp Menu&2----------")));
        subInstances.forEach(subCommand -> {
            sender.sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("commands.awakensmp.help.format","%syntax% - %description%")
                    .replace("%syntax%",subCommand.getUsage())
                    .replace("%description%",subCommand.getDescription())
            ));
        });

        sender.sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("commands.awakensmp.help.footer","&2-----------------------------")));
    }

    public Friend(@NotNull String name) {
        super(name);
    }

    public Friend(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
