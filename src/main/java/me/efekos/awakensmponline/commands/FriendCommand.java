package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.friend.*;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "friend", playerOnly = true, description = "Friend commands!")
public class FriendCommand extends CoreCommand {
    @Override
    public @NotNull ArrayList<Class<? extends SubCommand>> getSubs() {
        ArrayList<Class<? extends SubCommand>> classes = new ArrayList<>();
        classes.add(FriendInfoCommand.class);
        classes.add(FriendListCommand.class);
        classes.add(FriendTeleportCommand.class);
        classes.add(FriendAddCommand.class);
        classes.add(FriendAcceptCommand.class);
        classes.add(FriendDenyCommand.class);
        classes.add(FriendCancelCommand.class);
        classes.add(FriendRemoveCommand.class);
        classes.add(FriendModifyCommand.class);
        classes.add(FriendInventoryCommand.class);
        classes.add(FriendArmorCommand.class);
        classes.add(FriendCompassCommand.class);
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

    public FriendCommand(@NotNull String name) {
        super(name);
    }

    public FriendCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
