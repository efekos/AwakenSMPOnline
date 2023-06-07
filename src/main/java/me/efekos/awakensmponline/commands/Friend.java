package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.commands.friend.*;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.translation.TranslateManager;
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
    public void renderHelpList(CommandSender sender, ArrayList<SubCommand> subInstances) {
        sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.help.header")));
        subInstances.forEach(subCommand -> {
            sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.help.format")
                    .replace("%syntax%",subCommand.getUsage())
                    .replace("%description%",subCommand.getDescription())
            ));
        });
        sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.help.footer")));
    }

    public Friend(@NotNull String name) {
        super(name);
    }

    public Friend(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
