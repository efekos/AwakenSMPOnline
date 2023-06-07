package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.args.SentRequestUUIDArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.data.RequestType;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "cancel",description = "Cancel a friend requet.",permission = "awakensmp.command.friend.cancel")
public class Cancel extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return Friend.class;
    }

    public Cancel(@NotNull String name) {
        super(name);
    }

    public Cancel(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new SentRequestUUIDArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        try {
            UUID.fromString(args[0]);
        } catch (IllegalArgumentException e){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.cancel.not-uuid").replace("%uuid%",args[0])));
            return;
        }
        Request req = RequestDataManager.get(UUID.fromString(args[0]));
        if(req==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.cancel.not-req").replace("%uuid%",args[0])));
            return;
        }
        if(!req.getSender().equals(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.cancel.not-urs")));
            return;
        }
        if(!req.getType().equals(RequestType.FRIEND)){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.cancel.not-friend")));
            return;
        }
        // there is a friend request sent to us.
        req.setDone(true);
        RequestDataManager.delete(req.getId());

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.cancel.done")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
